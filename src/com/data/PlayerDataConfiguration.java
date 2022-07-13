package com.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.main.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerDataConfiguration {

	private static Main plugin=Main.getInstance();
	
	private static File getPlayerDataFile(Player p, String path) {
		//String characterSlot=StringUtils.substringBefore(path, ".");
		File playerDirectory=new File(plugin.getDataFolder(), "PlayerData/"+p.getUniqueId()+"/");
		//return new File(playerDirectory, characterSlot+".yml");
		return new File(playerDirectory, path+".yml");
	}
	
	private static File getPlayerDataFile(OfflinePlayer p, String path) {
		String characterSlot=StringUtils.substringBefore(path, ".");
		File playerDirectory=new File(plugin.getDataFolder(), "PlayerData/"+p.getUniqueId()+"/");
		return new File(playerDirectory, characterSlot+".yml");
	}
	
	protected static void playerConfigSet(Player p, String path, Object value) {
		try {
			File playerDataFile=getPlayerDataFile(p, path);
			FileConfiguration playerDataConfig=YamlConfiguration.loadConfiguration(playerDataFile);
			playerDataConfig.set(path, value);
			playerDataConfig.save(playerDataFile);
		} catch(IOException e) {
			e.printStackTrace();
		}
		/*try {
			File playerDataFile=getPlayerDataFile(p, path);
			FileConfiguration playerDataConfig=YamlConfiguration.loadConfiguration(playerDataFile);
			playerDataConfig.set(path,  value);
		} catch(Exception e) {
			e.printStackTrace();
		}*/
	}
	
	protected static void playerConfigSet(OfflinePlayer p, String path, Object value) {
		try { 
			File playerDataFile=getPlayerDataFile(p, path);
			FileConfiguration playerDataConfig=YamlConfiguration.loadConfiguration(playerDataFile);
			playerDataConfig.set(path, value);
			playerDataConfig.save(playerDataFile);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	protected static String playerConfigGetString(Player p, String path) {
		File playerDataFile = getPlayerDataFile(p, path);
		FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
		/*p.sendMessage("Попытка получить значение "+path+" : "+playerDataConfig.getString(path));
		if(path.equalsIgnoreCase("guild"))
			p.sendMessage("Название легиона: ");
		if(path.equalsIgnoreCase("guild")) {
			p.sendMessage("UUID админа: "+Guild.getGuild(playerDataConfig.getString(path)).getAdminUuidString());
		}*/
		return playerDataConfig.getString(path);
	}
	
	protected static String playerConfigGetString(OfflinePlayer p, String path) { 
		File playerDataFile = getPlayerDataFile(p, path);
		FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
		return playerDataConfig.getString(path);
	}
	
	protected static List<String> playerConfigGetStringList(Player p, String path) {
		File playerDataFile = getPlayerDataFile(p, path);
		FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
		return playerDataConfig.getStringList(path);
	}
	
	protected static List<String> playerConfigGetStringList(OfflinePlayer p, String path) {
		File playerDataFile = getPlayerDataFile(p, path);
		FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
		return playerDataConfig.getStringList(path);
	}
	
	protected static Integer playerConfigGetInt(Player p, String path) {
		File playerDataFile = getPlayerDataFile(p, path);
		FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
		return playerDataConfig.getInt(path);
	}
	
	protected static Integer playerConfigGetInt(OfflinePlayer p, String path) {
		File playerDataFile = getPlayerDataFile(p, path);
		FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
		return playerDataConfig.getInt(path);
	}
	
	protected static Long playerConfigGetLong(Player p, String path) {
		File playerDataFile = getPlayerDataFile(p, path);
		FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
		return playerDataConfig.getLong(path);
	}
	
	protected static Long playerConfigGetLong(OfflinePlayer p, String path) {
		File playerDataFile = getPlayerDataFile(p, path);
		FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
		return playerDataConfig.getLong(path);
	}
	
	protected static Double playerConfigGetDouble(OfflinePlayer p, String path) {
		File playerDataFile = getPlayerDataFile(p, path);
		FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
		return playerDataConfig.getDouble(path);
	}
	
	protected static Boolean playerConfigGetBoolean(Player p, String path) {
		File playerDataFile = getPlayerDataFile(p, path);
		FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
		return playerDataConfig.getBoolean(path);
	}
	
	protected static Boolean playerConfigGetBoolean(OfflinePlayer p, String path) {
		File playerDataFile = getPlayerDataFile(p, path);
		FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
		return playerDataConfig.getBoolean(path);
	}
	
	protected static Location playerConfigGetLocation(Player p, String path) {
		File playerDataFile = getPlayerDataFile(p, path);
		FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
		List<Double> coords = new ArrayList<Double>();
		for(String coord : playerDataConfig.getString(path).split(" ")) {
			coords.add(Double.parseDouble(coord));
		}
		return new Location(Bukkit.getWorld(playerConfigGetString(p, "pos_world"))
				, coords.get(0), coords.get(1), coords.get(2));
	}
	
	protected static ItemStack[] playerConfigGetItemStacks(Player p, String path) {
		File playerDataFile = getPlayerDataFile(p, path);
		FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
		List<?> itemStackList = playerDataConfig.getList(path);
		return itemStackList != null ? itemStackList.toArray(new ItemStack[0]) : new ItemStack[0];
	}
}
