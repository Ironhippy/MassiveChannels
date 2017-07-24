package net.graystone.java.channels.command;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;

import net.graystone.java.channels.Perm;
import net.graystone.java.channels.command.type.TypeChannel;
import net.graystone.java.channels.entity.MChannel;

public class CmdFocus extends ChannelCommand
{
	
	public CmdFocus()
	{
		this.addAliases("focus", "foc");
		this.setDesc("focus a channel");
		this.setDescPermission("focus a new channel");
		
		this.addParameter(TypeChannel.get(), "channelName");
		
		this.addRequirements(RequirementHasPerm.get(Perm.FOCUS.getNode()));
	}
	
	@Override
	public void perform() throws MassiveException
	{
		MChannel targetChannel = this.readArg();
		
		//if (player.isFocused(targetChannel)) { msg("<i>You are <pink>already speaking <i>in the "+targetChannel.getFullId()+"<i>."); return; }
		
		player.setChannel(targetChannel);
		
		if (player.isIgnoringChannel(targetChannel)) 
		{ 
			player.listenTo(targetChannel); 
			msg("<i>You are now <pink>listening <i>to the "+targetChannel.getFullId()+"<i>."); 
		}
				
		msg("<i>You have <pink>focused into <i>the "+targetChannel.getFullId()+"<i>.");
	}
}
