package net.graystone.java.channels.command;

import org.bukkit.ChatColor;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.enumeration.TypeChatColor;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.util.Txt;

import net.graystone.java.channels.Perm;
import net.graystone.java.channels.entity.MChannel;
import net.graystone.java.channels.entity.MChannelColl;

public class CmdCreate extends ChannelCommand
{
	
	public CmdCreate()
	{
		this.addAliases("create");
		this.setDesc("create a chat channel");
		this.setDescPermission("create a new channel");
		
		this.addRequirements(RequirementHasPerm.get(Perm.CREATE.getNode()));
		
		this.addParameter(TypeString.get(), "channelName");
		this.addParameter(TypeChatColor.get(), "chatColor");
	}
	
	@Override
	public void perform() throws MassiveException
	{
		String channelName = this.readArg();
		ChatColor chatColor = this.readArg();
		
		String alias = channelName.substring(0, 1).toLowerCase();
		
		MChannel userChannel = MChannelColl.get().create(Txt.upperCaseFirst(channelName.toLowerCase()));
		
		userChannel.setColor(chatColor);
		
		userChannel.addAliases(alias, channelName.toLowerCase());
		
		msg("<i>You have created the "+userChannel.getFullId()+"<i>.");
	}
}
