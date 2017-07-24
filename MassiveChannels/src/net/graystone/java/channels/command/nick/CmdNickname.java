package net.graystone.java.channels.command.nick;

import java.util.List;

import com.massivecraft.massivecore.util.MUtil;

import net.graystone.java.channels.command.ChannelCommand;

public class CmdNickname extends ChannelCommand
{
	
	private static CmdNickname i = new CmdNickname();
	public static CmdNickname get() { return CmdNickname.i; }
	
	public CmdNickname()
	{
		this.addChild(new CmdUse());
		this.addChild(new CmdSet());
	}
	
	@Override
	public List<String> getAliases()
	{
		return MUtil.list("nick", "nickname");
	}
	
}
