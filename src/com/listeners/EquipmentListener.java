package com.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.data.PlayerConfigurator;
import com.inkzzz.spigot.armorevent.PlayerArmorEquipEvent;

public class EquipmentListener implements Listener {
	//TODO нормальный листенер брони + ограничения
	@EventHandler
	public void onArmorEquip(PlayerArmorEquipEvent e) {
		if(PlayerConfigurator.getClass(e.getPlayer()).equalsIgnoreCase("Алхимик")||PlayerConfigurator.getClass(e.getPlayer()).equalsIgnoreCase("Маг")) {
			if(e.getItemStack().getType().equals(Material.LEATHER_HELMET)||e.getItemStack().getType().equals(Material.LEATHER_CHESTPLATE)||e.getItemStack().getType().equals(Material.LEATHER_LEGGINGS)||e.getItemStack().getType().equals(Material.LEATHER_BOOTS)) {
				
			}
		} else if(PlayerConfigurator.getClass(e.getPlayer()).equalsIgnoreCase("Убийца")||PlayerConfigurator.getClass(e.getPlayer()).equalsIgnoreCase("Стрелок")) {
			
		}
	}
}
