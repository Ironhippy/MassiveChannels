package net.graystone.java.channels.command;

import java.util.List;

import com.massivecraft.massivecore.command.MassiveCommandHelp;
import com.massivecraft.massivecore.command.MassiveCommandVersion;
import com.massivecraft.massivecore.util.MUtil;

import net.graystone.java.channels.MassiveChannels;

public class CmdChannel extends ChannelCommand
{
	
	private static CmdChannel i = new CmdChannel();
	public static CmdChannel get() { return CmdChannel.i; }
	
	public CmdChannel()
	{
		this.addChild(new MassiveCommandHelp());
		this.addChild(new CmdCreate());
		this.addChild(new CmdRemove());
		
		this.addChild(new CmdJoin());
		this.addChild(new CmdLeave());
		
		this.addChild(new CmdFocus());
		this.addChild(new CmdList());
		this.addChild(new MassiveCommandVersion(MassiveChannels.get()));
	}
	
	@Override
	public List<String> getAliases()
	{
		return MUtil.list("c", "channels", "chat", "ch");
	}
	
}
