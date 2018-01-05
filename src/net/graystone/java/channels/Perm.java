package net.graystone.java.channels;

public enum Perm
{
	
	CREATE("admin.create"),
	REMOVE("admin.remove"),
	FOCUS("default.focus"),
	INFO("default.info"),
	LIST("default.list"),
	
	IGNORE("default.ignore"),
	
	LISTEN("default.listen"),
	LEAVE("default.leave"),
	
	ME("default.me"),	
	PM("default.pm"),
	
	NICK_USE("nicknames.use"),
	NICK_SET("nicknames.set"),
	NICK_PREFIX_SET("nicknames.prefixes"),
	
	STAFF_DESC("admin.staffdesc"),
	
	CAN_COLOR("admin.color"), 
	
	DISPLAYNAME_USE("displayname.use");
	
	private String node;
	
	Perm(String node)
	{
		this.node = "massivechannels."+node;
	}
	
	public String getNode() { return this.node; }
	
}
