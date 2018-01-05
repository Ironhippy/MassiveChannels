package net.graystone.java.channels;

import org.bukkit.Bukkit;

import com.massivecraft.massivecore.MassivePlugin;
import com.massivecraft.massivecore.util.Txt;

import net.graystone.java.channels.entity.MConfColl;
import net.graystone.java.channels.entity.MPlayerColl;
import net.graystone.java.channels.command.CmdChannel;
import net.graystone.java.channels.command.CmdIgnore;
import net.graystone.java.channels.command.CmdMessage;
import net.graystone.java.channels.command.CmdReply;
import net.graystone.java.channels.command.CmdUnignore;
import net.graystone.java.channels.command.nick.CmdNickname;
import net.graystone.java.channels.engine.ChatEngine;
import net.graystone.java.channels.engine.TagEngine;
import net.graystone.java.channels.entity.MChannelColl;

public class MassiveChannels extends MassivePlugin
{
	
	private static MassiveChannels i;
	public MassiveChannels() { MassiveChannels.i = this; }
	public static MassiveChannels get() { return MassiveChannels.i; }
	
	private boolean allowPEX = false;
	
	public static String USERNAME = "%playername%";
	public static String NICKNAME = "%nickname%";
	public static String DISPLAYNAME = "%displayname%";
	public static String CHANNEL_NAME = "%channel_name%";
	public static String MESSAGE = "%message%";
	public static String LOCAL = "%local%";
	public static String PREFIX = "%prefix%";
	public static String SUFFIX = "%suffix%";
	public static String PRE = "%pre%";
	public static String SUF = "%suf%";
	
	@Override
	public void onEnableInner()
	{
		if (Bukkit.getPluginManager().getPlugin("PermissionsEx") != null && Bukkit.getPluginManager().getPlugin("PermissionsEx").isEnabled())
		{
			this.allowPEX = true;
		}
		
		this.activate(MConfColl.get(), MChannelColl.get(), MPlayerColl.get());
		
		this.activate(ChatEngine.get(), TagEngine.get());
		
		this.activate(CmdChannel.get(), CmdIgnore.get(), CmdUnignore.get(), CmdNickname.get(), CmdMessage.get(), CmdReply.get());
		
		TagEngine.setTag();
		
		if (this.allowPEX) log(Txt.parse("&dPermissionsEx Integration <i>activated!"));
	}
	
	public boolean isPEXAllowed()
	{
		return this.allowPEX;
	}
	
}
