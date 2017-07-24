package net.graystone.java.channels.command;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;

import net.graystone.java.channels.Perm;
import net.graystone.java.channels.command.type.TypeChannel;
import net.graystone.java.channels.entity.MChannel;

public class CmdRemove extends ChannelCommand
{
	
	public CmdRemove()
	{
		this.addAliases("remove", "r");
		this.setDesc("remove a chat channel");
		this.setDescPermission("remove a channel");
		
		this.addParameter(TypeChannel.get(), "channelName");
		
		this.addRequirements(RequirementHasPerm.get(Perm.REMOVE.getNode()));
	}
	
	@Override
	public void perform() throws MassiveException
	{
		MChannel targetChannel = this.readArg();
		
		msg("<i>Removed the "+targetChannel.getFullId()+" <i>from the MChannel list.");
		
		targetChannel.detach();
	}
}
