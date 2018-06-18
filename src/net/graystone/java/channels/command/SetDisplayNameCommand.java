package net.graystone.java.channels.command;

import org.bukkit.entity.Player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.massivecraft.massivecore.util.Txt;

import net.graystone.java.channels.Obf;
import net.graystone.java.channels.Perm;
import net.graystone.java.channels.command.ChannelCommand;

public class SetDisplayNameCommand extends ChannelCommand
{
	
	public SetDisplayNameCommand()
	{
		this.addAliases("setdisplayname","setname");
		this.setDesc("use a display name for another user");
		this.setDescPermission("set another user's display name");
		
		this.addParameter(TypePlayer.get(), "player");
		this.addParameter(TypeString.get(), "displayname", true);
		
		this.addRequirements(RequirementHasPerm.get(Perm.DISPLAYNAME_USE.getNode()));
	}
	
	@Override
	public void perform() throws MassiveException
	{
		Player target = this.readArg();
		String nick = this.readArg();
				
		if (target.getDisplayName().equalsIgnoreCase(nick))
		{
			msg("<red>Error: <rose>The user already has the defined display name.");
			
			return;
		}
				
		boolean special = false;
		
		for (Character chars : nick.toCharArray())
		{
			if (!Obf.specialCharacters().contains(chars)) continue;
			
			msg("<rose>This user can't have any <pink>special characters <rose>in their name.");
			
			special = true;
			
			break;
		}
		
		if (special) return;
		
		if(sender.hasPermission("massivechannels.chat.color")) nick = Txt.parse(nick);
		
		target.setDisplayName(nick);
		
		msg("<i>You have set <pink>"+target.getName()+"'s display name <i>to: <pink>"+nick+"<i>.");
		
	}
	
}
