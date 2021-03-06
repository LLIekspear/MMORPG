package com.events;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.data.PlayerConfigurator;
import com.main.Race;
import com.menu.RaceMenu;

public class PlayerEventRacePromoteAdviser implements PlayerEvent {

	private OfflinePlayer resident;
	
	public PlayerEventRacePromoteAdviser(OfflinePlayer resident) {
		this.resident=resident;
	}
	
	@Override
	public boolean execute(Player p) {
		try {
			Race playerRace=PlayerConfigurator.getRace(p);
			if(playerRace!=null&&!playerRace.getRaceName().equalsIgnoreCase("none")&&!playerRace.getRaceName().equalsIgnoreCase("banished")) {
				playerRace.promoteToAdviser(p, resident);
				RaceMenu.open(p);
			} else {
				p.closeInventory();
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			p.sendMessage(ChatColor.RED+"???????? ?????? ??? ??????? ???????? ?????? ?? ?????????!");
			p.closeInventory();
			return false;
		}
	}
}
