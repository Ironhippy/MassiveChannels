package net.graystone.java.channels.command;

import java.util.List;

import org.bukkit.entity.Player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;

import net.graystone.java.channels.MassiveChannels;
import net.graystone.java.channels.Perm;
import net.graystone.java.channels.entity.MPlayer;
import net.graystone.java.channels.integration.PEXIntegration;

public class CmdMessage extends ChannelCommand
{
	
	
	private static CmdMessage i = new CmdMessage();
	public static CmdMessage get() { return CmdMessage.i; }
	
	public CmdMessage()
	{
		this.setDescPermission("send a private message");
		
		this.addRequirements(RequirementHasPerm.get(Perm.PM.getNode()));
		
		this.addParameter(TypePlayer.get(), "player");
		this.addParameter(TypeString.get(), "message", true);
	}
	
	@Override
	public void perform() throws MassiveException
	{
		Player targetRaw = this.readArg();
		String message = this.readArg();
		
		MPlayer target = MPlayer.get(targetRaw);
		
		if (target.getName() == player.getName()) { player.message(Txt.parse("&cYou can't message &dyourself&c.")); return; }
		
		if (target.isIgnoringPlayer(player)) { player.message(Txt.parse("&cYou can't message &dthat player&c. They are &dignoring you&c!")); return; }
		if (player.isIgnoringPlayer(target)) { player.message(Txt.parse("&cYou can't message &dthat player&c. You are &dignoring them&c!")); return; }
		
		String firstMsg = "";
		String secondMsg = "";
		
		if (MassiveChannels.get().isPEXAllowed())
		{
			String prefix2 = Txt.parse(PEXIntegration.get().getPrefix(targetRaw));
			String suffix2 = Txt.parse(PEXIntegration.get().getSuffix(targetRaw));
			
			firstMsg = Txt.parse("<pink>TO " + prefix2 + target.getName() + suffix2 + "<pink>: ") + message;
		}
		
		if (!MassiveChannels.get().isPEXAllowed()) firstMsg = Txt.parse("<pink>TO " + target.getPrefix() + target.getName() + "<pink>: ") + message;
		
		msg(firstMsg);
		
		if (MassiveChannels.get().isPEXAllowed())
		{
			String prefix = Txt.parse(PEXIntegration.get().getPrefix(player.getPlayer()));
			String suffix = Txt.parse(PEXIntegration.get().getSuffix(player.getPlayer()));
			
			secondMsg = Txt.parse("<pink>FROM " + prefix + player.getName() + suffix + "<pink>: ") + message;
		}
		
		if (!MassiveChannels.get().isPEXAllowed()) secondMsg = Txt.parse("<pink>FROM " + target.getPrefix() + target.getName() + "<pink>: ") + message;
		
		target.message(secondMsg);
		player.setConvo(target);
		target.setConvo(player);
		player.setConvoName(target);
		target.setConvoName(player);
	}
	
	@Override
	public List<String> getAliases()
	{
		return MUtil.list("pm", "msg", "tell");
	}
	
}
