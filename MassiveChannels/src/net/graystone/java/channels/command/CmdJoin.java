package net.graystone.java.channels.command;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;

import net.graystone.java.channels.Perm;
import net.graystone.java.channels.command.type.TypeChannel;
import net.graystone.java.channels.entity.MChannel;

public class CmdJoin extends ChannelCommand
{
	
	public CmdJoin()
	{
		this.addAliases("join");
		this.setDesc("listen to a channel");
		this.setDescPermission("listen to this channel");
		
		this.addRequirements(RequirementHasPerm.get(Perm.LISTEN.getNode()));
		
		this.addParameter(TypeChannel.get(), "channelName");
	}
	
	@Override
	public void perform() throws MassiveException
	{
		MChannel channel = this.readArg();
		
		if (!player.isIgnoringChannel(channel))
		{
			msg("<rose>You are already <pink>listening <rose>to "+channel.getFullId()+"<rose>.");
			
			return;
		}
		
		player.listenTo(channel);
		
		msg("<i>You are now <pink>listening <i>to the "+channel.getFullId()+"<i>.");
	}
	
}
