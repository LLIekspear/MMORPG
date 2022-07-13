package com.main;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class PlayerDataPool {

	private static Map<Player, PlayerData> storage=new HashMap<>();
	
	public static PlayerData getPlayer(Player player) {
		return storage.get(player);
	}
	
	public static PlayerData regPlayer(Player player) {
		PlayerData playerData = new PlayerData(player, storage.size()+1);
		storage.put(player, playerData);
		//DamageCalculator.setHealth(player, 20);
		return playerData;
	}
	
	public static void unregPlayer(Player player) {
		storage.remove(player);
	}
	
	/*public static boolean isCharacterSelected(Player player) {
		PlayerData playerData = getPlayer(player);
		return !Mode.inHub(player)&&playerData!=null&&playerData.getSelections().isCharacterSelected();
	}*/
}
