package net.graystone.java.channels.entity;

import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.store.Coll;
import com.massivecraft.massivecore.store.MStore;

import net.graystone.java.channels.MassiveChannels;
import net.graystone.java.channels.entity.MConf;

public class MConfColl extends Coll<MConf>
{
	
	private static MConfColl i = new MConfColl();
	public static MConfColl get() { return MConfColl.i; }
	
	public MConfColl()
	{
		super("massivechannels_mconf", MConf.class, MStore.getDb(), MassiveChannels.get());
	}
	
	@Override
	public void setActive(boolean set)
	{
		super.setActive(set);
		
		if (!set) return;
		
		if (!get().containsId(MassiveCore.INSTANCE))
		{
			MConf.i = get().create(MassiveCore.INSTANCE);
			
			return;
		}
		
		MConf.i = get().get(MassiveCore.INSTANCE);
	}
	
}
