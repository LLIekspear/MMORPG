package com.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.main.Main;

public class GuildDataConfiguration {

	private static Main plugin=Main.getInstance();
	
	protected static List<String> getGuildUuidList() {
		List<String> guildUuidList=new ArrayList<>();
		for(File file : new File(plugin.getDataFolder(), "GuildData/").listFiles()) {
			guildUuidList.add(file.getName().replaceAll(".yml", ""));
		}
		return guildUuidList;
	}
	
	protected static void deleteGuild(String guild) {
		File guildDataFile=new File(plugin.getDataFolder(), "GuildData/"+guild+".yml");
		guildDataFile.delete();
	}
	
	protected static void guildConfigSet(String guild, String path, Object value) {
		try {
			File guildDataFile=new File(plugin.getDataFolder(), "GuildData/"+guild+".yml");
			FileConfiguration guildDataConfig = YamlConfiguration.loadConfiguration(guildDataFile);
			//TODO ????????????????????
			guildDataConfig.set(path, value);
			guildDataConfig.save(guildDataFile);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected static String guildConfigGetString(String guild, String path) {
		File guildDataFile=new File(plugin.getDataFolder(), "GuildData/"+guild+".yml");
		FileConfiguration guildDataConfig=YamlConfiguration.loadConfiguration(guildDataFile);
		return guildDataConfig.getString(path);
	}
	
	protected static List<String> guildConfigGetStringList(String guild, String path) {
		File guildDataFile=new File(plugin.getDataFolder(), "GuildData/"+guild+".yml");
		FileConfiguration guildDataConfig=YamlConfiguration.loadConfiguration(guildDataFile);
		return guildDataConfig.getStringList(path);
	}
	
	protected static int guildConfigGetInt(String guild, String path) {
		File guildDataFile = new File(plugin.getDataFolder(), "GuildData/" + guild + ".yml");
		FileConfiguration guildDataConfig = YamlConfiguration.loadConfiguration(guildDataFile);	
		return guildDataConfig.getInt(path);
	}
	
	protected static ItemStack guildConfigGetItemStack(String guild, String path) {
		File guildDataFile = new File(plugin.getDataFolder(), "GuildData/" + guild + ".yml");
		FileConfiguration guildDataConfig = YamlConfiguration.loadConfiguration(guildDataFile);	
		return guildDataConfig.getItemStack(path);
	}
}
