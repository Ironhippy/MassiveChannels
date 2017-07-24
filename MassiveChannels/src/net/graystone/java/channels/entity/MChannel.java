package net.graystone.java.channels.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.World;

import com.massivecraft.massivecore.store.Entity;

import net.graystone.java.channels.MassiveChannels;

public class MChannel extends Entity<MChannel>
{
	
	private List<String> chatAliases = new ArrayList<String>();
	public void addAliases(String...aliases)
	{
		for (String alias : aliases)
		{
			if (this.chatAliases.contains(alias)) continue;
			
			this.chatAliases.add(alias);
		}
	}
	
	public boolean removeAliases(String...aliases)
	{
		for (String alias : aliases)
		{
			if (!this.chatAliases.contains(alias)) continue;
			
			this.chatAliases.remove(alias);
		}
		
		return true;
	}
	
	public boolean containsAlias(String alias)
	{
		if (this.chatAliases.contains(alias.toLowerCase())) return true;
		
		return false;
	}
	
	public List<String> getAliases()
	{
		return this.chatAliases;
	}
	
	private String messageFormat = "["+MassiveChannels.CHANNEL_NAME+" + "+MassiveChannels.USERNAME+"]: "+MassiveChannels.MESSAGE;
	
	public void setFormat(String arg0) { this.messageFormat = arg0; this.changed(); }
	
	public String getFormat() { return this.messageFormat; }
	
	public void setRange(double inner, double outer) { this.innerRadius = inner; this.outerRadius = outer; this.changed(); }
	
	private double innerRadius = 0D;
	public double getInnerRadius() { return this.innerRadius; }
	
	private double outerRadius = 0D;
	public double getOuterRadius() { return this.outerRadius; }
	
	private double innerShoutRadius = 0D;
	public double getInnerShoutRadius() { return this.innerShoutRadius; }
	
	private double outerShoutRadius = 0D;
	public double getOuterShoutRadius() { return this.outerShoutRadius; }

	private double innerYellRadius = 0D;
	public double getInnerYellRadius() { return this.innerYellRadius; }
	
	private double outerYellRadius = 0D;
	public double getOuterYellRadius() { return this.outerYellRadius; }
	
	private double innerWhisperRadius = 0D;
	public double getInnerWhisperRadius() { return this.innerWhisperRadius; }
	
	private double outerWhisperRadius = 0D;
	public double getOuterWhisperRadius() { return this.outerWhisperRadius; }
	
	public boolean isLocal()
	{
		if (this.outerRadius>0) return true;
		
		return false;
	}
	
	private boolean isRoleplay = false;
	public void setRoleplay(boolean set) { this.isRoleplay = set; this.changed(); }
	public boolean isRoleplay() { return this.isRoleplay; }	
	
	private boolean worldSelective = false;
	public void setWorldSelective(boolean set) { this.worldSelective = set; this.changed(); }
	public boolean isWorldSelective() { return this.worldSelective; }
	
	private List<UUID> worldsActive = new ArrayList<UUID>();
	public void addWorld(World world) { this.worldsActive.add(world.getUID()); this.changed(); }
	public boolean removeWorld(World world)
	{
		if (!worldsActive.contains(world.getUID())) return false;
		
		worldsActive.remove(world.getUID());
		
		return true;
	}
	public List<UUID> getWorlds() { return this.worldsActive; }
	
	private String chatColour = ChatColor.RED.toString();
	public void setColor(ChatColor color) { this.chatColour = color.toString(); this.changed(); }
	public ChatColor getColor() { return ChatColor.valueOf(chatColour); }
	
	public String getFullId()
	{
		return this.chatColour+this.getId()+" Channel";
	}
}
