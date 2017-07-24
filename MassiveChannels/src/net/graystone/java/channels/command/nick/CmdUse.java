package net.graystone.java.channels.command.nick;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.util.Txt;

import net.graystone.java.channels.Obf;
import net.graystone.java.channels.Perm;
import net.graystone.java.channels.TagType;
import net.graystone.java.channels.command.ChannelCommand;
import net.graystone.java.channels.event.TagUpdateEvent;

public class CmdUse extends ChannelCommand
{
	
	public CmdUse()
	{
		this.addAliases("use");
		this.setDesc("use a nickname");
		this.setDescPermission("set your own nickname");
		
		this.addParameter(TypeString.get(), "nickname", true);
		
		this.addRequirements(RequirementHasPerm.get(Perm.NICK_USE.getNode()));
	}
	
	@Override
	public void perform() throws MassiveException
	{
		String nick = this.readArg();
		
		boolean special = false;
		
		for (Character chars : nick.toCharArray())
		{
			if (!Obf.specialCharacters().contains(chars)) continue;
			
			msg("<rose>You may not have any <pink>special characters <rose>in your name.");
			
			special = true;
			
			break;
		}
				
		if (special) return;
		
		if(sender.hasPermission("massivechannels.chat.color")) nick = Txt.parse(nick);
		
		TagUpdateEvent event = new TagUpdateEvent(TagType.SUFFIX, ": " + nick, player);
		
		event.run();
		
		if (event.isCancelled()) 
		{
			msg("<rose>Error: <i>Could not set nickname because "+event.getCancellationReason()+"<i>.");
			
			return;
		}
		
		player.setNick(nick);
		
		msg("<i>Your nickname has been <pink>updated <i>to: <pink>"+nick+"<i>.");
	}
	
}
