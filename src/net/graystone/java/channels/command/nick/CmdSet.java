package net.graystone.java.channels.command.nick;

import org.bukkit.entity.Player;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.massivecraft.massivecore.util.Txt;

import net.graystone.java.channels.Obf;
import net.graystone.java.channels.Perm;
import net.graystone.java.channels.TagType;
import net.graystone.java.channels.command.ChannelCommand;
import net.graystone.java.channels.entity.MPlayer;
import net.graystone.java.channels.event.TagUpdateEvent;

public class CmdSet extends ChannelCommand
{
	
	public CmdSet()
	{
		this.setDesc("set user's nickname");
		this.addAliases("set");
		
		this.addParameter(TypePlayer.get(), "player");
		this.addParameter(TypeString.get(), "nick", true);
		
		this.addRequirements(RequirementHasPerm.get(Perm.NICK_SET.getNode()));
	}
	
	@Override
	public void perform() throws MassiveException
	{
		Player targetRaw = this.readArg();
		String nick = this.readArg();
		
		MPlayer target = MPlayer.get(targetRaw);
		
		if (target.getNick().equalsIgnoreCase(nick))
		{
			msg("<red>Error: <rose>The user already has the defined nickname.");
			
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
		
		if(targetRaw.hasPermission("massivechannels.chat.color")) nick = Txt.parse(nick);
		
		TagUpdateEvent event = new TagUpdateEvent(TagType.SUFFIX, ": " + Txt.parse(nick), target);
				
		event.run();
		
		target.setNick(Txt.upperCaseFirst(nick));
		
		msg("<i>You have set <pink>"+target.getName()+"'s nickname <i>to: <pink>"+Txt.upperCaseFirst(nick)+"<i>.");
		
	}
}
