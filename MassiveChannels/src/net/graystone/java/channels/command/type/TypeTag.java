package net.graystone.java.channels.command.type;

import com.massivecraft.massivecore.command.type.enumeration.TypeEnum;

import net.graystone.java.channels.TagType;

public class TypeTag  extends TypeEnum<TagType>{
	
	private static TypeTag i = new TypeTag();
	public static TypeTag get() { return TypeTag.i; }
	
	public TypeTag()
	{
		super(TagType.class);
	}

}
