package net.graystone.java.channels.command;

import org.apache.commons.lang.StringUtils;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.util.Txt;

import net.graystone.java.channels.Perm;
import net.graystone.java.channels.entity.MChannel;
import net.graystone.java.channels.entity.MChannelColl;

public class CmdList extends ChannelCommand
{
	
	public CmdList()
	{
		this.addAliases("list");
		this.setDesc("lists all channels");
		this.setDescPermission("list all available channels");
		
		this.addRequirements(RequirementHasPerm.get(Perm.LIST.getNode()));
	}
	
	@Override
	public void perform() throws MassiveException
	{
		String allList = "<gold>_____.[ <green>Channel List <gold>].____<i>";
		
		msg(allList);
		
		for (MChannel channels : MChannelColl.get().getAll())
		{
			message(descChannel(channels));
		}
	}
	
	private Mson descChannel(MChannel target)
	{
		String desc = "<gold>_____.[ <lime>Channel List <gold>].____<i>\n";
		
		if (target.isLocal())
		{
			desc += "Local: <lime>Yes<i>, Radius: <rose>"+target.getInnerRadius()+"<i>\n";
		}
		else if (!target.isLocal())
		{
			desc += "Local: <rose>No<i>\n";
		}
		
		String channelAliases = "Channel: <lime>" + StringUtils.join(target.getAliases(), "<i>, <lime>")+"<i>\n";
		desc += channelAliases;
		
		
		return Mson.parse(Txt.parse("<i> - "+target.getFullId()))
				             .suggest(target.getAliases().get(0)+":")
				             .tooltip(Txt.parse(desc));
	}
}
