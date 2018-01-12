package net.graystone.java.channels.entity;

import java.util.List;

import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.store.SenderEntity;

import net.graystone.java.channels.MassiveChannels;
import net.graystone.java.channels.entity.MPlayerColl;
import net.graystone.java.channels.integration.PEXIntegration;

public class MPlayer extends SenderEntity<MPlayer>
{
	
	public static MPlayer get(Object oid) { return MPlayerColl.get().get(oid); }
	
	private String channelId = MChannelColl.GLOBAL;
	
	public MChannel getFocusedChannel() { 
		return MChannelColl.get().get(channelId); 
		}
	
	public void setChannel(MChannel channel) { this.channelId = channel.getId(); 
		this.changed(); }
	
	public boolean isFocused(MChannel channel)
	{
		if (!channelId.equalsIgnoreCase(channel.getId())) return false;
		
		return true;
	}
	
	private List<String> ignoredChannels = new MassiveList<String>();
	public void ignoreChannel(MChannel channel) { 
		this.ignoredChannels.add(channel.getId()); this.changed(); 
		}
	
	public boolean isIgnoringChannel(MChannel channel) { 
		return this.ignoredChannels.contains(channel.getId()); 
		}
	
	public boolean isIgnoringPlayer(MPlayer p) { 
		return this.ignoredIds.contains(p.getId()); 
		}
	
	public void listenTo(MChannel channel) { 
		this.ignoredChannels.remove(channel.getId()); this.changed();
		}
	
	private List<String> ignoredIds = new MassiveList<String>();
	
	public void ignore(MPlayer player)
	{
		this.ignoredIds.add(player.getId());
	}
	
	public boolean removeIgnore(MPlayer player)
	{
		return this.ignoredIds.remove(player.getId());
	}
	
	private String lastConvo;
	public void setConvo(MPlayer player) { this.lastConvo = player.getId(); this.changed(); }
	
	public String getConvo() { 
		if (lastConvo==null) return ""; return this.lastConvo;
		}
	
	private String lastConvoName;
	public void setConvoName(MPlayer player) { this.lastConvoName = player.getName(); this.changed(); }
	
	public String getConvoName() { 
		if (lastConvoName==null) return ""; return this.lastConvoName;
		}
	
	private String nickName;
	public void setNick(String arg0) { this.nickName = arg0; this.changed(); }
	public String getNick() { if (nickName==null) return this.getName(); return this.nickName; }
	
	private String displayName;
	public void setDisplayName(String arg0) { this.displayName = arg0; this.changed(); }
	public String getDisplayName() { if (displayName==null) return this.getName(); return this.displayName; }
	
	public PS getLocation () { return PS.valueOf(this.getPlayer().getLocation()); }
	
	public String getPrefix()
	{
		if (!MassiveChannels.get().isPEXAllowed()) return "";
		
		return PEXIntegration.get().getPrefix(getPlayer());
	}
	
}
