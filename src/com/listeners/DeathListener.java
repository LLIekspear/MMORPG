package com.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathListener implements Listener {
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase("plugins/MMORPG/Dungeons/GhostCastle")) {
			e.setRespawnLocation(new Location(Bukkit.getWorld("plugins/MMORPG/Dungeons/GhostCastle"), 157, 65, -68));
		}
	}
}
