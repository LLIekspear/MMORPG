package com.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.main.Main;

public class RaceDataConfiguration {

	private static Main plugin=Main.getInstance();
	protected static List<String> getRaceUuidList() {
		List<String> raceUuidList=new ArrayList<>();
		for(File file : new File(plugin.getDataFolder(), "RaceData/").listFiles()) {
			raceUuidList.add(file.getName().replaceAll(".yml", ""));
		}
		return raceUuidList;
	}
	
	protected static void deleteRace(String race) {
		File raceDataFile=new File(plugin.getDataFolder(), "RaceData/"+race+".yml");
		raceDataFile.delete();
	}
	
	protected  static void raceConfigSet(String race, String path, Object value) {
		try {
			File raceDataFile=new File(plugin.getDataFolder(), "RaceData/"+race+".yml");
			FileConfiguration raceDataConfig=YamlConfiguration.loadConfiguration(raceDataFile);
			raceDataConfig.set(path, value);
			raceDataConfig.save(raceDataFile);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected static String raceConfigGetString(String race, String path) {
		File raceDataFile=new File(plugin.getDataFolder(), "RaceData/"+race+".yml");
		FileConfiguration raceDataConfig=YamlConfiguration.loadConfiguration(raceDataFile);
		return raceDataConfig.getString(path);
	}
	
	protected static List<String> raceConfigGetStringList(String race, String path) {
		File raceDataFile=new File(plugin.getDataFolder(), "RaceData/"+race+".yml");
		FileConfiguration raceDataConfig=YamlConfiguration.loadConfiguration(raceDataFile);
		return raceDataConfig.getStringList(path);
	}
	
	protected static int raceConfigGetInt(String race, String path) {
		File raceDataFile=new File(plugin.getDataFolder(), "RaceData/"+race+".yml");
		FileConfiguration raceDataConfig=YamlConfiguration.loadConfiguration(raceDataFile);
		return raceDataConfig.getInt(path);
	}
}
