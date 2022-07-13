package com.main;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.commands.GroupCommand;

public class Main_old extends JavaPlugin implements Listener {

	private Logger log=getLogger();
	
	public static Main_old plugin;
	
	@Override
	public void onEnable() {
		
		plugin=this;
		registerListeners();
		registerCommands();
		log.info("MMO-RPG Plugin started...");
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	private void registerListeners() {
		PluginManager pm=Bukkit.getServer().getPluginManager();
		pm.registerEvents(new Mana(), this);
	}
	
	private void registerCommands() {
		getCommand("group").setExecutor(new GroupCommand());
	}
	
	//
	
	@Override
	public void onDisable() {
		//
	}
}
