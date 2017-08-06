package net.graystone.java.channels.event;

import org.bukkit.event.HandlerList;

import com.massivecraft.massivecore.event.EventMassiveCore;

import net.graystone.java.channels.TagType;
import net.graystone.java.channels.entity.MPlayer;

public class TagUpdateEvent extends EventMassiveCore
{
	
	public static final HandlerList handlers = new HandlerList();
	public static HandlerList getHandlerList() { return handlers; }
	public HandlerList getHandlers() { return handlers; }
	
	private TagType tagType;
	private String argument;
	private MPlayer player;
	
	private String cancellationReason = "None";
	
	public TagUpdateEvent(TagType type, String argument, MPlayer player)
	{
		this.tagType = type;
		this.argument = argument;
		this.player = player;
	}
	
	public TagType getTag() { return this.tagType; }
	public String getArgument() { return this.argument; }
	public MPlayer getPlayer() { return this.player; }
	
	public void setCancellationReason(String arg0)
	{
		this.cancellationReason = arg0;
	}
	
	public String getCancellationReason()
	{
		return this.cancellationReason;
	}
	
}
