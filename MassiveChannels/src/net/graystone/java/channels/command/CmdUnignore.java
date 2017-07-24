package net.graystone.java.channels.command;

import java.util.List;

import org.bukkit.entity.Player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.massivecraft.massivecore.util.MUtil;

import net.graystone.java.channels.Perm;
import net.graystone.java.channels.entity.MPlayer;

public class CmdUnignore extends ChannelCommand
{
	
	private static CmdUnignore i = new CmdUnignore();
	public static CmdUnignore get() { return CmdUnignore.i; }
	
	public CmdUnignore()
	{
		this.setDescPermission("unignore another player");
		
		this.addRequirements(RequirementHasPerm.get(Perm.IGNORE.getNode()));
		
		this.addParameter(TypePlayer.get(), "playerName");
	}
	
	@Override
	public void perform() throws MassiveException
	{
		Player playerRaw = this.readArg();
		
		MPlayer target = MPlayer.get(playerRaw);
		
		player.removeIgnore(target);
		
		msg("<pink>You <i>are no longer <pink>ignoring "+target.getName()+"<i>.");
	}
	
	@Override
	public List<String> getAliases()
	{
		return MUtil.list("unignore");
	}
	
}
