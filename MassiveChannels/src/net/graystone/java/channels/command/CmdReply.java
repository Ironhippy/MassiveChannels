package net.graystone.java.channels.command;

import java.util.List;

import org.bukkit.Bukkit;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;

import net.graystone.java.channels.Perm;
import net.graystone.java.channels.entity.MPlayer;
import net.graystone.java.channels.integration.PEXIntegration;

public class CmdReply extends ChannelCommand
{
	
	
	private static CmdReply i = new CmdReply();
	public static CmdReply get() { return CmdReply.i; }
	
	public CmdReply()
	{
		this.setDescPermission("reply to a private message");
		
		this.addRequirements(RequirementHasPerm.get(Perm.PM.getNode()));
		
		this.addParameter(TypeString.get(), "message", true);
	}
	
	@Override
	public void perform() throws MassiveException
	{
		String targetRaw = player.getConvo();
		String message = this.readArg();
		String targetName = player.getConvoName();
		
		MPlayer target = MPlayer.get(targetRaw);
		
		if (!(target.isOnline())) { player.message(Txt.parse("&cNo online player matches " + '"' + "&d" + targetName + "&c" + '"' + ".")); return; }
		
		if (target.isIgnoringPlayer(player)) { player.message(Txt.parse("&cYou can't message &dthat player&c. They are &dignoring you&c!")); return; }
		if (player.isIgnoringPlayer(target)) { player.message(Txt.parse("&cYou can't message &dthat player&c. You are &dignoring them&c!")); return; }
		
		String firstMsg = "";
		String secondMsg = "";
		
		if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx")!=null)
		{
			String prefix2 = Txt.parse(PEXIntegration.get().getPrefix(target.getPlayer()));
			String suffix2 = Txt.parse(PEXIntegration.get().getSuffix(target.getPlayer()));
			
			firstMsg = Txt.parse("<pink>TO " + prefix2 + target.getName() + suffix2 + "<pink>: ") + message;
		}
		
		if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx")==null) firstMsg = Txt.parse("<pink>TO " + target.getPrefix() + target.getName() + "<pink>: ") + message;
		
		msg(firstMsg);
		
		if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx")!=null)
		{
			String prefix = Txt.parse(PEXIntegration.get().getPrefix(player.getPlayer()));
			String suffix = Txt.parse(PEXIntegration.get().getSuffix(player.getPlayer()));
			
			secondMsg = Txt.parse("<pink>FROM " + prefix + player.getName() + suffix + "<pink>: ") + message;
		}
		
		if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx")==null) secondMsg = Txt.parse("<pink>FROM " + target.getPrefix() + target.getName() + "<pink>: ") + message;
		
		target.message(secondMsg);
	}
	
	@Override
	public List<String> getAliases()
	{
		return MUtil.list("r", "replay", "respond");
	}
	
}
