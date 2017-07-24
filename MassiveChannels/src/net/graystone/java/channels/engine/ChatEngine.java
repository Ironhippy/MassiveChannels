package net.graystone.java.channels.engine;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.Txt;

import net.graystone.java.channels.Obf;
import net.graystone.java.channels.Parser;
import net.graystone.java.channels.entity.MChannel;
import net.graystone.java.channels.entity.MChannelColl;
import net.graystone.java.channels.entity.MPlayer;

public class ChatEngine extends Engine
{
	
	private static ChatEngine i = new ChatEngine();
	public static ChatEngine get() { return ChatEngine.i; }
	
	Parser parser = Parser.get();
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onAlias(AsyncPlayerChatEvent event)
	{
		if (!containsAlias(event.getMessage())) return;
		
		MPlayer sender = MPlayer.get(event.getPlayer());
		
		int index = event.getMessage().indexOf(":");
		
		String alias = event.getMessage().substring(0, index);
		
		MChannel targetChannel = MChannelColl.get().getChannelFromAlias(alias);
		
		if (targetChannel == null)
		{
			sender.message(Txt.parse("<rose>The alias you have entered is invalid, could not connect to a channel."));
			return;
		}
		
		assureMember(sender, targetChannel);
		
		sender.setChannel(targetChannel);
		
		sender.message(Txt.parse("<lime>You have focused onto the "+targetChannel.getFullId()+"<lime>."));
		
		String updated = event.getMessage().substring(index+1);
		
		event.setCancelled(true);
		
		if (event.getMessage().substring(index).equalsIgnoreCase("") || event.getMessage().substring(index)==null) return;
		
		AsyncPlayerChatEvent neuRegister = new AsyncPlayerChatEvent(true, event.getPlayer(), updated, event.getRecipients());
		
		Bukkit.getPluginManager().callEvent(neuRegister);
	}
	
	@EventHandler(priority=EventPriority.LOW)
	public void processGlobalChat(AsyncPlayerChatEvent event)
	{
		if (containsAlias(event.getMessage())) { event.setCancelled(true); return; }
		if (isBlank(event.getMessage())) { event.setCancelled(true); return; }
		if (testForException(event.getMessage(), event.getPlayer())) { if (event.isCancelled()) return; this.fixException(event.getPlayer()); event.setCancelled(true); return; }
		
		MChannel focused = MPlayer.get(event.getPlayer()).getFocusedChannel();
		if (focused.isLocal() || focused.isWorldSelective()) return;
		
		for (Player players : Bukkit.getOnlinePlayers())
		{
			MPlayer player = MPlayer.get(players);
			if (cantListenTo(player, focused, MPlayer.get(event.getPlayer())))
			{
				event.getRecipients().remove(players); continue;
			}
		}
		
		String format = parser.parse(focused.getFormat(), focused, MPlayer.get(event.getPlayer()), event.getMessage());
		
		event.setFormat(format);
	}
	
	@EventHandler(priority=EventPriority.LOW)
	public void processWorldChat(AsyncPlayerChatEvent event)
	{
		if (containsAlias(event.getMessage())) { event.setCancelled(true); return; }
		if (isBlank(event.getMessage())) { event.setCancelled(true); return; }
		if (testForException(event.getMessage(), event.getPlayer())) { if (event.isCancelled()) return; this.fixException(event.getPlayer()); event.setCancelled(true); return; }
		
		MChannel focused = MPlayer.get(event.getPlayer()).getFocusedChannel();
		if (!focused.isWorldSelective()) return;
		
		Player source = event.getPlayer();
		
		for (Player playersOnline : Bukkit.getOnlinePlayers())
		{
			MPlayer player = MPlayer.get(playersOnline);
			if (cantListenTo(player, focused, MPlayer.get(source)) || source.getWorld().getUID().toString() != playersOnline.getWorld().getUID().toString())
			{
				event.getRecipients().remove(playersOnline);
			}
		}
		
		String format = parser.parse(focused.getFormat(), focused, MPlayer.get(source), event.getMessage());
		
		event.setFormat(format);
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void processLocalChat(AsyncPlayerChatEvent event)
	{
		if (containsAlias(event.getMessage())) { event.setCancelled(true); return; }
		if (isBlank(event.getMessage())) { event.setCancelled(true); return; }
		if (testForException(event.getMessage(), event.getPlayer())) { if (event.isCancelled()) return; this.fixException(event.getPlayer()); event.setCancelled(true); return; }
		
		MChannel focused = MPlayer.get(event.getPlayer()).getFocusedChannel();
		if (!focused.isLocal()) return;
		
		event.getRecipients().clear();
		
		Player player = event.getPlayer();
		
		event.getRecipients().add(player);
		
		for (Entity nearbyEntities : event.getPlayer().getNearbyEntities(focused.getOuterRadius(), focused.getOuterRadius(), focused.getOuterRadius()))
		{
			if (!(nearbyEntities instanceof Player)) continue;
			Player recipientRaw = (Player) nearbyEntities;
			
			if (inRange(MPlayer.get(player), MPlayer.get(recipientRaw), focused))
			{
				event.getRecipients().add(recipientRaw);
			}
			
			if (inObfuscationRange(MPlayer.get(player),MPlayer.get(recipientRaw),focused))
			{
				String message = Obf.obfuscate(event.getMessage(), focused, MPlayer.get(player), MPlayer.get(recipientRaw));
				String format = parser.parseLocal(focused.getFormat(), focused, MPlayer.get(player), message);
				format = parser.parse(focused.getFormat(), focused, MPlayer.get(player), message);
				
				recipientRaw.sendMessage(format);
			}
			
		}
		
		String format = parser.parse(focused.getFormat(), focused, MPlayer.get(player), event.getMessage());
		for (Player recipient : event.getRecipients())
		{
			recipient.sendMessage(format);
		}
		
		event.setCancelled(true);
	}

	private void assureMember(MPlayer player, MChannel channel)
	{
		if (!player.isIgnoringChannel(channel)) return;
		
		player.listenTo(channel);
	}
	
	private boolean cantListenTo(MPlayer listening, MChannel channel, MPlayer player)
	{
		if (listening.isIgnoringChannel(channel) || listening.isIgnoringPlayer(MPlayer.get(player))) return true;
		
		return false;
	}
	
	private boolean inRange(MPlayer source, MPlayer recipient, MChannel channel)
	{
		double innerRadius = channel.getInnerRadius();
		
		if (PS.locationDistance(source.getLocation(), recipient.getLocation()) < innerRadius) return true;
		
		return false;
	}
	
	private boolean inObfuscationRange(MPlayer source, MPlayer recipient, MChannel channel)
	{
		double innerRadius = channel.getInnerRadius();
		double outerRadius = channel.getOuterRadius();
		
		double distance = PS.locationDistance(source.getLocation(), recipient.getLocation());
		
		if (distance >= innerRadius && distance <= outerRadius) return true;
		
		return false;
	}
	
	private boolean containsAlias(String message)
	{
		boolean ret = false;
		String alias = "";
		
		if (message.contains(":") && !message.startsWith(":")) ret = true;
		
		try {
			alias = message.substring(0, message.indexOf(':'));
		} catch(StringIndexOutOfBoundsException e) { }
			
		if (alias.contains(" ")) ret = false;
		
		return ret;
	}
	private boolean isBlank(String message)
	{
		if (message.equalsIgnoreCase("") || message==null) return true;
		
		return false;
	}
	
	private boolean testForException(String message, Player player)
	{
		MPlayer target = MPlayer.get(player);
		try
		{
			parser.parse(MChannelColl.get().get(MChannelColl.GLOBAL).getFormat(), MChannelColl.get().get(MChannelColl.GLOBAL), target, message);
		} catch (IllegalArgumentException exception)
		{
			return true;
		}
		return false;
	}
	
	private void fixException(Player player)
	{
		player.sendMessage(Txt.parse("<red>Error: <rose>You have sent a message that is incompatible with the latest version of <pink>MassiveChannels<rose>. We will have this fixed in a future update."));
	}
}
