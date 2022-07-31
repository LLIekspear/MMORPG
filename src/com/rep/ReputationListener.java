package com.rep;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.data.GuildConfig;
import com.data.PlayerConfigurator;
import com.data.RaceConfig;

public class ReputationListener implements Listener {
	public HashMap<String, Inventory> rep_menues=new HashMap<String, Inventory>();
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		try {
			if(e.getView().getTitle().contains("Очки героя "))
				e.setCancelled(true);
		} catch(Exception error) {
			//
		}
	}
	
	public void setRepMenu(Player p) {
		Inventory inv=Bukkit.createInventory(p, 27, "Очки героя "+p.getName());
		HashMap<String, Integer> reps=new HashMap<String, Integer>();
		try {
			File directory=new File("plugins/MMORPG", "/PlayerData/");
			for(int i=0; i<directory.listFiles().length; ++i) {
				File playerDir=directory.listFiles()[i];
				String id=playerDir.getName();
				for(int j=0; j<playerDir.listFiles().length; ++j) {
					File dataFile=playerDir.listFiles()[j];
					if(dataFile.getName().equalsIgnoreCase("rep.yml")) {
						FileConfiguration dataConfig=YamlConfiguration.loadConfiguration(dataFile);
						int rep=dataConfig.getInt("rep");
						reps.put(id, rep);
					}
				}
			}
			Map<String, Integer> sorted=reps.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2)->e2, LinkedHashMap::new));
			Iterator<Map.Entry<String, Integer>> iter=sorted.entrySet().iterator();
			int count=0;
			while(iter.hasNext()) {
				Map.Entry<String, Integer> entry=iter.next();
				ItemStack item=new ItemStack(Material.PLAYER_HEAD);
				ItemMeta meta=item.getItemMeta();
				meta.setDisplayName(ChatColor.GREEN+Integer.toString(count+1)+" "+Bukkit.getOfflinePlayer(UUID.fromString(entry.getKey())).getName());
				List<String> lore=new ArrayList<String>();
				lore.add(ChatColor.AQUA+"Звание "+Reputation.getStringRank(Bukkit.getOfflinePlayer(UUID.fromString(entry.getKey()))));
				lore.add(ChatColor.AQUA+"Очки героя: "+Integer.toString(PlayerConfigurator.getHeroRep(Bukkit.getOfflinePlayer(UUID.fromString(entry.getKey())))));
				lore.add(ChatColor.GREEN+"Раса: "+RaceConfig.getRaceName(PlayerConfigurator.getRaceString(Bukkit.getOfflinePlayer(UUID.fromString(entry.getKey())))));
				lore.add(ChatColor.GREEN+"Класс: "+PlayerConfigurator.getClass(Bukkit.getOfflinePlayer(UUID.fromString(entry.getKey()))));
				lore.add(ChatColor.GREEN+"Легион: "+GuildConfig.getGuildName(PlayerConfigurator.getGuildString(Bukkit.getOfflinePlayer(UUID.fromString(entry.getKey())))));
				meta.setLore(lore);
				item.setItemMeta(meta);
				inv.addItem(item);
				++count;
			}
			ItemStack item=new ItemStack(Material.PLAYER_HEAD);
			ItemMeta meta=item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN+p.getName());
			List<String> lore=new ArrayList<String>();
			lore.add(ChatColor.AQUA+"Звание "+Reputation.getStringRank(Bukkit.getPlayer(p.getName())));
			lore.add(ChatColor.AQUA+"Очки героя: "+Integer.toString(PlayerConfigurator.getHeroRep(Bukkit.getOfflinePlayer(p.getName()))));
			lore.add(ChatColor.GREEN+"Раса: "+RaceConfig.getRaceName(PlayerConfigurator.getRaceString(Bukkit.getOfflinePlayer(p.getName()))));
			lore.add(ChatColor.GREEN+"Класс: "+PlayerConfigurator.getClass(Bukkit.getOfflinePlayer(p.getName())));
			lore.add(ChatColor.GREEN+"Легион: "+GuildConfig.getGuildName(PlayerConfigurator.getGuildString(Bukkit.getPlayer(p.getName()))));
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(26, item);
			rep_menues.put(p.getName(), inv);
		} catch(Exception error) {
			error.printStackTrace();
		}
	}
}
