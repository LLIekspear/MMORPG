package com.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemUseListener implements Listener {
	@EventHandler
	public void onItemUse(PlayerInteractEvent e) {
		try {
			if(e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if(e.getItem().getItemMeta().getLore().get(0).equalsIgnoreCase("При открытии отправляет в призрачную крепость")) {
					e.setCancelled(true);
					e.getPlayer().teleport(new Location(Bukkit.getWorld("plugins/MMORPG/Dungeons/GhostCastle"), 157, 65, -68));
					e.getPlayer().sendMessage(ChatColor.GREEN+"Вас окутала пустота...");
					e.getPlayer().getInventory().removeItem(e.getItem());
				}
			}
		} catch(Exception error) {
			//
		}
	}
}
