package net.graystone.java.channels.entity;

import com.massivecraft.massivecore.store.MStore;
import com.massivecraft.massivecore.store.SenderColl;

import net.graystone.java.channels.MassiveChannels;
import net.graystone.java.channels.entity.MPlayer;

public class MPlayerColl extends SenderColl<MPlayer>
{
	
	private static MPlayerColl i = new MPlayerColl();
	public static MPlayerColl get() { return MPlayerColl.i; }
	
	public MPlayerColl()
	{
		super("massivechannels_mplayers", MPlayer.class, MStore.getDb(), MassiveChannels.get());
	}
	
}
