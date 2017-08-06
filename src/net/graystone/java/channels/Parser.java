package net.graystone.java.channels;

import com.massivecraft.massivecore.util.Txt;

import net.graystone.java.channels.entity.MChannel;
import net.graystone.java.channels.entity.MPlayer;
import net.graystone.java.channels.integration.PEXIntegration;

public class Parser
{
	
	private static Parser i = new Parser();
	public static Parser get() { return Parser.i; }
	
	public String parse(String format, MChannel channel, MPlayer player, String message)
	{
		
		format = Txt.parse(format);
		
		if (format.contains(MassiveChannels.CHANNEL_NAME))
		{
			format = format.replaceAll(MassiveChannels.CHANNEL_NAME, channel.getId());
		}
		if (format.contains(MassiveChannels.MESSAGE))
		{
			format = format.replaceAll(MassiveChannels.MESSAGE, message);
		}
		if (format.contains(MassiveChannels.NICKNAME))
		{		
			format = format.replaceAll(MassiveChannels.NICKNAME, player.getNick());
		}
		if (format.contains(MassiveChannels.USERNAME))
		{			
			format = format.replaceAll(MassiveChannels.USERNAME, player.getName());
		}
		if (format.contains(MassiveChannels.LOCAL))
		{
			format = parseLocal(format, channel, player, message);
		}
		
		if (format.contains(MassiveChannels.PRE)) format = format.replaceAll(MassiveChannels.PRE, player.getPrefix());
		
		if (MassiveChannels.get().isPEXAllowed())
		{
			String prefix = Txt.parse(PEXIntegration.get().getPrefix(player.getPlayer()));
			String suffix = Txt.parse(PEXIntegration.get().getSuffix(player.getPlayer()));
			
			format = format.replaceAll(MassiveChannels.PREFIX, prefix);
			format = format.replaceAll(MassiveChannels.SUFFIX, suffix);
		}
		
		if (player.getPlayer().hasPermission(Perm.CAN_COLOR.getNode()))
		{
			format = Txt.parse(format);
		}
		
		return format;
	}
	
	public String parseLocal(String string, MChannel channel, MPlayer player, String message)
	{
		if (!channel.isLocal()) return string;
		
		if (string.contains(MassiveChannels.LOCAL)&&channel.isLocal())
		{
			if (message.endsWith("!!"))
			{
				return string.replaceAll(MassiveChannels.LOCAL, "shouts");
			}
			else if (message.endsWith("!"))
			{
				return string.replaceAll(MassiveChannels.LOCAL, "yells");
			}
			else if (message.endsWith("^"))
			{
				return string.replaceAll(MassiveChannels.LOCAL, "whispers").replace("^", "");
			}
			else if (message.endsWith("?"))
			{
				return string.replaceAll(MassiveChannels.LOCAL, "asks");
			}
			else if (message.endsWith("+")){
				return Txt.parse("&7")+message.replace("+", "");
			}
			else if (message.endsWith("))") || message.endsWith("]")){
				return Txt.parse("&8[OOC] ")+MassiveChannels.PREFIX+player.getName()+MassiveChannels.SUFFIX+Txt.parse("&8]: ")+message.replace("]", "").replace("[", "").replace("))", "").replace("((", "");
			}
			else if (message.endsWith("*")||message.endsWith("-"))
			{
				return player.getPrefix()+player.getNick()+Txt.parse("&f&o ")+message.replace("*", "").replace("-", "");
			}
			else
			{
				return string.replaceAll(MassiveChannels.LOCAL, "says");
			}
		}
		
		return string;
	}
	
}
