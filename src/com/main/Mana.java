package com.main;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Mana implements Listener {
	private HashMap<String, Integer> mana=new HashMap<String, Integer>();
	
	public int getMana(String name) {
		return mana.get(name);
	}
	
	public void setMana(String name, int value) {
		mana.put(name, value);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(!(mana.containsKey(e.getPlayer().getName()))) {
			mana.put(e.getPlayer().getName(), 1000);
		}
	}
	
	@EventHandler
	public void Skill(PlayerInteractEvent e) {
		if(e.getPlayer().getInventory().getItemInMainHand().getType()==Material.STICK) {
			e.getPlayer().getWorld().strikeLightning(e.getClickedBlock().getLocation());
			int new_mana=mana.get(e.getPlayer().getName())-200;
			mana.put(e.getPlayer().getName(), new_mana);
			e.getPlayer().sendMessage("Была задействована магия... Осталось маны: "+mana.get(e.getPlayer().getName()));
		}
	}
}
