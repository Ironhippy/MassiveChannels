package net.graystone.java.channels.command;

import java.util.List;

import org.bukkit.entity.Player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;

import net.graystone.java.channels.Perm;
import net.graystone.java.channels.entity.MPlayer;

public class CmdIgnore extends ChannelCommand
{
	
	private static CmdIgnore i = new CmdIgnore();
	public static CmdIgnore get() { return CmdIgnore.i; }
	
	public CmdIgnore()
	{
		this.setDescPermission("ignore another player");
		
		this.addRequirements(RequirementHasPerm.get(Perm.IGNORE.getNode()));
		
		this.addParameter(TypePlayer.get(), "playerName");
	}
	
	@Override
	public void perform() throws MassiveException
	{
		Player playerRaw = this.readArg();
		
		MPlayer target = MPlayer.get(playerRaw);
		
		if (this.readArg()==player) { player.message(Txt.parse("&cYou can't ignore &dyourself&c!")); return; }
		
		player.ignore(target);
		
		msg("<pink>You <i>are now <pink>ignoring "+target.getName()+"<i>.");
	}
	
	@Override
	public List<String> getAliases()
	{
		return MUtil.list("ignore");
	}
	
}
