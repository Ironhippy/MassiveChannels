package net.graystone.java.channels;

public enum Perm
{
	
	CREATE("channels.create"),
	REMOVE("channels.remove"),
	FOCUS("channels.focus"),
	INFO("channels.info"),
	LIST("channels.list"),
	
	IGNORE("channels.ignore"),
	
	LISTEN("channels.listen"),
	LEAVE("channels.leave"),
	
	ME("me"),	
	PM("pm"),
	
	NICK_USE("nicknames.use"),
	NICK_SET("nicknames.set"),
	NICK_PREFIX_SET("nicknames.prefixes"),
	
	STAFF_DESC("staffdesc"),
	
	CAN_COLOR("chat.color");
	
	private String node;
	
	Perm(String node)
	{
		this.node = "massivechannels."+node;
	}
	
	public String getNode() { return this.node; }
	
}
