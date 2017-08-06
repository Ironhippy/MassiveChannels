package net.graystone.java.channels.command;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;

import net.graystone.java.channels.Perm;
import net.graystone.java.channels.command.type.TypeChannel;
import net.graystone.java.channels.entity.MChannel;

public class CmdLeave extends ChannelCommand
{
	
	public CmdLeave()
	{
		this.addAliases("leave");
		this.setDesc("stop receiving a channel's messages");
		this.setDescPermission("stop listening to this channel");
		
		this.addParameter(TypeChannel.get(), "channel");
		
		this.addRequirements(RequirementHasPerm.get(Perm.LEAVE.getNode()));
	}
	
	@Override
	public void perform() throws MassiveException
	{
		MChannel channel = this.readArg();
				
		if (player.isIgnoringChannel(channel))
		{
			msg("<rose>You have already <pink>left <rose>this channel.");
			
			return;
		}
		
		player.ignoreChannel(channel);
		
		msg("<i>You are no longer listening to the "+channel.getFullId()+"<i>.");
	}
	
}
