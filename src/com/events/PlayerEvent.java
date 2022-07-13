package com.events;

import org.bukkit.entity.Player;

import com.main.Main;

public interface PlayerEvent {

	static Main plugin=Main.getInstance();
	public boolean execute(Player p);
	//public boolean execute(OfflinePlayer p);
}
