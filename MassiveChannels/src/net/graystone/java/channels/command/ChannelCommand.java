package net.graystone.java.channels.command;

import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.util.Txt;

import net.graystone.java.channels.entity.MPlayer;

public class ChannelCommand extends MassiveCommand
{
	
	protected MPlayer player;
	
	public boolean msg(String txt)
	{
		if (player==null) return false;
		
		player.message(Txt.parse(txt));
		
		return true;
	}
	
	@Override
	public void senderFields(boolean set)
	{
		
		this.player = set ? MPlayer.get(sender) : null;
	}
	
}
