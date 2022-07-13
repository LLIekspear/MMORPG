package com.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.StringUtils;

public class PlayerGroupPool {

	private static Map<String, Group> storage=new HashMap<String, Group>();
	
	public static Group getGroup(Player player) {
		PlayerData playerData=PlayerDataPool.getPlayer(player);
		if(playerData==null||StringUtils.isBlank(playerData.getSelections().getGroupUuidString())) {
			return null;
		} else {
			return getGroup(playerData.getSelections().getGroupUuidString());
		}
	}
	
	public static Group getGroup(String groupUuidString) {
		return storage.get(groupUuidString);
	}
	
	public static List<Group> getGroups() {
		return new ArrayList<>(storage.values());
	}
	
	public static Group regGroup(Group group) {
		storage.put(group.getGroupUuidString(), group);
		return group;
	}
	
	public static void unregGroup(String groupUuidString) {
		storage.remove(groupUuidString);
	}
}
