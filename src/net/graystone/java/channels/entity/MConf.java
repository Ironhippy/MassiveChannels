package net.graystone.java.channels.entity;

import java.util.Map;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.command.editor.annotation.EditorTypeInner;
import com.massivecraft.massivecore.command.type.TypeStringCommand;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;

@EditorName("config")
public class MConf extends Entity<MConf>
{
	
	protected static transient MConf i;
	public static MConf get() { return MConf.i; }
	
	@EditorTypeInner({TypeString.class, TypeStringCommand.class})
	private Map<String, String> characterPermissions =  MUtil.map("massivechannels.key.admin", "&6&e");
	
	@Override
	public MConf load(MConf that)
	{
		super.load(that);
		return this;
	}
	
}
