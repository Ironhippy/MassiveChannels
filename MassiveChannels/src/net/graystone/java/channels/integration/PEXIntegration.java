package net.graystone.java.channels.integration;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PEXIntegration
{
	
	private static PEXIntegration i = new PEXIntegration();
	public static PEXIntegration get() { return PEXIntegration.i; }
	
	public String getPrefix(Player player)
	{
		PermissionUser user = PermissionsEx.getUser(player.getName());
		
		String prefix = user.getPrefix();
		
		return prefix;
	}
	
	public String getSuffix(Player player)
	{
		PermissionUser user = PermissionsEx.getUser(player.getName());
		
		String suffix = user.getSuffix();
		
		return suffix;
	}
	
}
