package com.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public interface LInventory {

	public String getInventoryId();
	
	public default void openInstance(Player p) {
		throw new RuntimeException("Inventory cannot be openned directly!");
	}
	
	//public void selectMenuPoint(InventoryClickEvent e);
	
	public default void destroyInventory(InventoryCloseEvent e) {
		
	}
}
