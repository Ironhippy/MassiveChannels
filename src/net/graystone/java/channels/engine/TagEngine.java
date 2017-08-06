package net.graystone.java.channels.engine;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.util.Txt;

import net.graystone.java.channels.MassiveChannels;
import net.graystone.java.channels.Perm;
import net.graystone.java.channels.TagType;
import net.graystone.java.channels.entity.MPlayer;
import net.graystone.java.channels.event.TagUpdateEvent;
import net.graystone.java.channels.integration.PEXIntegration;

public class TagEngine extends Engine
{
	
	private static TagEngine i = new TagEngine();
	public static TagEngine get() { return TagEngine.i; }
	
	private static Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
	
	@EventHandler
	public void initTag(PlayerJoinEvent event)
	{
		MPlayer player = MPlayer.get(event.getPlayer());
		
		player.getPlayer().setScoreboard(board);
		
		MassiveChannels.get().log(Txt.parse("<i>Added "+player.getName()+" to the <pink>"+board.toString()+" Scoreboard<i>."));
		
		if (player.getNick()!=null||player.getNick()!=player.getName())
		{
			String nick = player.getNick();
			
			if (player.hasPermission(Perm.CAN_COLOR, true)) nick = Txt.parse(nick);
						
			String fin = fixName(": "+nick);
			
			TagUpdateEvent suffixEvent = new TagUpdateEvent(TagType.SUFFIX, fin, player);
			
			suffixEvent.run();
		}
		
		if (!MassiveChannels.get().isPEXAllowed()) return;
		if (PEXIntegration.get().getPrefix(player.getPlayer()).equalsIgnoreCase("")) return;
		
		String prefix = PEXIntegration.get().getPrefix(player.getPlayer());
		
		TagUpdateEvent prefixEvent = new TagUpdateEvent(TagType.PREFIX, prefix, player);
		
		prefixEvent.run();
	}
	
	@EventHandler
	public void updateTag(TagUpdateEvent event)
	{
		MPlayer player = event.getPlayer();
		
		Team fakeTeam = getFakeTeam(player);
		
		if (!fakeTeam.getEntries().contains(player.getId()) || !fakeTeam.getEntries().contains(player.getName()))
		{
			fakeTeam.addEntry(player.getId());
			fakeTeam.addEntry(player.getName());
		}
		
		if (event.getTag()==TagType.PREFIX)
		{
			fakeTeam.setPrefix(fixName(Txt.parse(event.getArgument())));
		}
		else
		{
			fakeTeam.setSuffix(fixName(event.getArgument()));
		}
		
	}
	
	public Team getFakeTeam(MPlayer player)
	{
		String fakeId = player.getId().replaceAll("-", "").substring(0, 15);
		
		if (board.getTeam(fakeId)==null) return board.registerNewTeam(fakeId);
		
		return board.getTeam(fakeId);
	}
	
	protected static String fixName(String arg0)
	{
		if (!(arg0.length()>15))
		{
			return arg0;
		}
		
		return arg0.substring(0, 15);
	}
	
	public static void setTag()
	{	
		MPlayer player = null;
		
		for (Player onlinePlayer : Bukkit.getOnlinePlayers())
		{
			player = MPlayer.get(onlinePlayer);
		
		player.getPlayer().setScoreboard(board);
		
		MassiveChannels.get().log(Txt.parse("<i>Added "+player.getName()+" to the <pink>"+board.toString()+" Scoreboard<i>."));
		
		if (player.getNick()!=null||player.getNick()!=player.getName())
		{
			String nick = player.getNick();
									
			String fin = fixName(": "+nick);
			
			TagUpdateEvent suffixEvent = new TagUpdateEvent(TagType.SUFFIX, fin, player);
			
			suffixEvent.run();
		}
		
		else {
			String fin = fixName("");
			
			TagUpdateEvent suffixEvent = new TagUpdateEvent(TagType.SUFFIX, fin, player);
			
			suffixEvent.run();
		}
		
		if (!MassiveChannels.get().isPEXAllowed()) return;
		if (PEXIntegration.get().getPrefix(player.getPlayer()).equalsIgnoreCase("")) return;
		
		String prefix = PEXIntegration.get().getPrefix(player.getPlayer());
		
		TagUpdateEvent prefixEvent = new TagUpdateEvent(TagType.PREFIX, prefix, player);
		
		prefixEvent.run();
		}
	}
	
}