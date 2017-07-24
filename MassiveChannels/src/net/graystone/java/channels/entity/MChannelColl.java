package net.graystone.java.channels.entity;

import java.util.List;

import org.bukkit.ChatColor;

import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.store.Coll;
import com.massivecraft.massivecore.store.MStore;

import net.graystone.java.channels.MassiveChannels;

public class MChannelColl extends Coll<MChannel>
{
	
	public static String LOCAL = "Local";
	public static String GLOBAL = "Global";
	
	private static MChannelColl i = new MChannelColl();
	public static MChannelColl get() { return MChannelColl.i; }
	
	public MChannelColl()
	{
		super("massivechannels_channels", MChannel.class, MStore.getDb(), MassiveChannels.get());
	}
	
	@Override
	public void setActive(boolean set)
	{
		super.setActive(set);
		
		if (!set) return;
		
		if (!get().containsId(GLOBAL))
		{
			MChannel generalChannel = this.create(GLOBAL);
			generalChannel.setColor(ChatColor.GRAY);
			generalChannel.addAliases("g", "gl", "o", "ooc");
		}
		
		if (!get().containsId(LOCAL))
		{
			MChannel localChannel = this.create(LOCAL);
			
			localChannel.setColor(ChatColor.WHITE);
			localChannel.addAliases("l", "lo");
			localChannel.setRange(13D, 21D);
			
			localChannel.setFormat(MassiveChannels.NICKNAME+" "+MassiveChannels.LOCAL+", " + '"' + MassiveChannels.MESSAGE + '"');
		}
		
	}
	
	public boolean aliasIsntTaken(String alias)
	{
		boolean exists = false;
		
		for (MChannel channels : get().getAll())
		{
			if (!channels.containsAlias(alias)) continue;
			
			exists = true;
		}
		
		return exists;
	}
	
	public MChannel getChannelFromAlias(String alias)
	{
		for (MChannel channels : get().getAll())
		{
			if (!channels.containsAlias(alias.toLowerCase())) continue;
			
			return channels;
		}
		
		return null;
	}
	
	public List<String> getChannelTabList()
	{
		List<String> tabList = new MassiveList<String>();
		
		for (MChannel channels : MChannelColl.get().getAll())
		{
			for (String alias : channels.getAliases())
			{
				tabList.add(alias);
			}
		}
		
		return tabList;
	}
	
}
