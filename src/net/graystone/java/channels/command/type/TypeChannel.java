package net.graystone.java.channels.command.type;

import java.util.Collection;

import org.bukkit.command.CommandSender;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.TypeAbstract;

import net.graystone.java.channels.entity.MChannel;
import net.graystone.java.channels.entity.MChannelColl;

public class TypeChannel extends TypeAbstract<MChannel>
{
	
	private static TypeChannel i = new TypeChannel();
	public static TypeChannel get() { return TypeChannel.i; }
	
	public TypeChannel()
	{
		super(MChannel.class);
	}
	
	@Override
	public MChannel read(String arg, CommandSender sender) throws MassiveException
	{
		return MChannelColl.get().getChannelFromAlias(arg.toLowerCase());
	}

	@Override
	public Collection<String> getTabList(CommandSender sender, String arg)
	{
		return MChannelColl.get().getChannelTabList();
	}
	
}
