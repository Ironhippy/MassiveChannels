package net.graystone.java.channels.command.nick;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.util.Txt;

import net.graystone.java.channels.Perm;
import net.graystone.java.channels.TagType;
import net.graystone.java.channels.command.ChannelCommand;
import net.graystone.java.channels.event.TagUpdateEvent;

public class CmdClear extends ChannelCommand
{
	
	public CmdClear()
	{
		this.addAliases("clear");
		this.setDesc("clear a nickname");
		this.setDescPermission("clear your nickname");
		
		this.addRequirements(RequirementHasPerm.get(Perm.NICK_USE.getNode()));
	}
	
	@Override
	public void perform() throws MassiveException
	{								
		TagUpdateEvent event = new TagUpdateEvent(TagType.SUFFIX, "", player);
		
		event.run();
		
		if (event.isCancelled()) 
		{
			msg("<rose>Error: <i>Could not set nickname because "+event.getCancellationReason()+"<i>.");
			
			return;
		}
		
		player.setNick(player.getName());
		
		msg(Txt.parse("&eYour nickname has been &dcleared&e."));
	}
	
}
