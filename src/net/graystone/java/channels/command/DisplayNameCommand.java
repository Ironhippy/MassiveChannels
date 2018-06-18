package net.graystone.java.channels.command;

import org.bukkit.entity.Player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.util.Txt;

import net.graystone.java.channels.Obf;
import net.graystone.java.channels.Perm;
import net.graystone.java.channels.command.ChannelCommand;

public class DisplayNameCommand extends ChannelCommand
{
	
	public DisplayNameCommand()
	{
		this.addAliases("usedisplayname","name");
		this.setDesc("use a display name");
		this.setDescPermission("set your own display name");
		
		this.addParameter(TypeString.get(), "displayname", true);
		
		this.addRequirements(RequirementHasPerm.get(Perm.DISPLAYNAME_USE.getNode()));
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
		
		((Player) sender).setDisplayName(nick);
		
		player.setDisplayName(nick);
		
		msg("<i>Your display name has been <pink>updated <i>to: <pink>"+nick+"<i>.");
	}
	
}
