package com.main;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.brewery.BrewingRecipe;
import com.brewery.CustomAlco;
import com.brewery.PotionEvent;
import com.commands.ApplyCommand;
import com.commands.CommandExecutor;
import com.commands.GroupChatCommand;
import com.commands.GroupCommand;
import com.commands.GroupDescCommand;
import com.commands.GuildChatCommand;
import com.commands.GuildCommand;
import com.commands.GuildMotdCommand;
import com.commands.NPCCommand;
import com.commands.RaceChatCommand;
import com.commands.RaceCommand;
import com.commands.RaceMotdCommand;
import com.commands.SchemCommand;
import com.listeners.BlockPutListener;
import com.listeners.ClassListener;
import com.listeners.DamageListener;
import com.listeners.DialogListener;
import com.listeners.FishListener;
import com.listeners.GroupListener;
import com.listeners.GuildApplicationListener;
import com.listeners.GuildListener;
import com.listeners.HorseListener;
import com.listeners.MoveListener;
import com.listeners.NPCListener;
import com.listeners.PreJoinListener;
import com.listeners.RaceListener;

import net.milkbowl.vault.economy.Economy;


public class Main extends JavaPlugin {

	public static final int MAX_PLAYER_LEVEL=50;
	public static final int MAX_CRAFTING_SKILL=100;
	private static Main instance;
	private static boolean isStarted;
	private Logger log=getLogger();
	public static Economy economy=null;
	public List<BrewingRecipe> recipes=new ArrayList<BrewingRecipe>();
	//private static BukkitAudiences audiences;
	//private static DiscordBot discordBot;
	//private static WebServerManager webServerManager;
	//private static FtpServerManager ftpServerManager;
	
	private void setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider=getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if(economyProvider!=null) {
			economy=economyProvider.getProvider();
		}
	}
	
	@Override
	public void onEnable() {
		instance=this;
		isStarted=false;
		//audiences=BukkitAudiences.create(this);
		log.info("MMORPG is starting...");
		/*if(Modules.isMainModuleActive()) {
			WorldLoader.init();
			log.info("Finished loading world saves!");
		}*/
		getServer().getPluginManager().registerEvents(new PreJoinListener(), this);
		getServer().getPluginManager().registerEvents(new GroupListener(), this);
		getServer().getPluginManager().registerEvents(new GuildListener(), this);
		getServer().getPluginManager().registerEvents(new RaceListener(), this);
		getServer().getPluginManager().registerEvents(new DialogListener(), this);
		getServer().getPluginManager().registerEvents(new GuildApplicationListener(), this);
		getServer().getPluginManager().registerEvents(new ClassListener(), this);
		getServer().getPluginManager().registerEvents(new DamageListener(), this);
		getServer().getPluginManager().registerEvents(new MoveListener(), this);
		getServer().getPluginManager().registerEvents(new NPCListener(), this);
		getServer().getPluginManager().registerEvents(new FishListener(), this);
		getServer().getPluginManager().registerEvents(new PotionEvent(), this);
		getServer().getPluginManager().registerEvents(new BlockPutListener(), this);
		getServer().getPluginManager().registerEvents(new HorseListener(), this);
		getServer().getScheduler().scheduleSyncDelayedTask(this, instance::setupModules);
		Guild.init();
		Race.init();
		getCommand("race").setExecutor(new RaceCommand());
		getCommand("rc").setExecutor(new RaceChatCommand());
		getCommand("rmotd").setExecutor(new RaceMotdCommand());
		getCommand("group").setExecutor(new GroupCommand());
		getCommand("grc").setExecutor(new GroupChatCommand());
		getCommand("gdesc").setExecutor(new GroupDescCommand());
		getCommand("apply").setExecutor(new ApplyCommand());
		getCommand("legion").setExecutor(new GuildCommand());
		getCommand("lc").setExecutor(new GuildChatCommand());
		getCommand("lmotd").setExecutor(new GuildMotdCommand());
		getCommand("npctrader").setExecutor(new NPCCommand());
		getCommand("buy").setExecutor(new SchemCommand());
		setupEconomy();
		new BrewingRecipe(Material.BLACK_DYE, new CustomAlco());
	}
	
	private void setupModules() {
		PluginManager pm=getServer().getPluginManager();
		//....
		isStarted=true;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return CommandExecutor.execute(sender, cmd, args);
	}
	
	public static Player getOnlinePlayer(String name) {
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			if(player.getName().equals(name)) {
				return player;
			}
		}
		return null;
	}
	
	public static boolean isStarted() {
		return isStarted;
	}
	
	public static Main getInstance() {
		return instance;
	}
}
