package net.graystone.java.channels.event;

import org.bukkit.event.HandlerList;

import com.massivecraft.massivecore.event.EventMassiveCore;

import net.graystone.java.channels.entity.MChannel;
import net.graystone.java.channels.entity.MPlayer;

public class ChatPreprocessEvent extends EventMassiveCore
{
	
	public static final HandlerList handlers = new HandlerList();
	public static HandlerList getHandlerList() { return handlers; }
	public HandlerList getHandlers() { return handlers; }
	
	private String message;
	private MChannel channel;
	private MPlayer player;
	
	public ChatPreprocessEvent(String message, MChannel channel, MPlayer player)
	{
		this.message = message;
		this.channel = channel;
		this.player = player;
	}
	
	public String getMessage() { return this.message; }
	public MChannel getChannel() { return this.channel; }
	public MPlayer getPlayer() { return this.player; }
	
}
