package com.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.main.PlayerData;
import com.main.PlayerDataPool;
import com.menu.HeadUtils;

public class DialogListener implements Listener {

	@EventHandler
	public void selectMenuPoint(InventoryClickEvent e) {
		//e.setCancelled(true);
		ItemStack clicked=e.getCurrentItem();
		final Player p=(Player)e.getWhoClicked();
		PlayerData playerData=PlayerDataPool.getPlayer(p);
		if(playerData==null||clicked==null) {
			return;
		} else if(HeadUtils.isHeadMenuItem(clicked, "œŒƒ“¬≈–ƒ»“‹")) {
			e.setCancelled(true);
			playerData.getSelections().getPlayerEvent().execute(p);
		} else if(HeadUtils.isHeadMenuItem(clicked, "Œ“ ÀŒÕ»“‹")) {
			e.setCancelled(true);
			p.closeInventory();
		} else if(clicked.getType().equals(Material.IRON_BARS)) {
			e.setCancelled(true);
		}
	}
}
