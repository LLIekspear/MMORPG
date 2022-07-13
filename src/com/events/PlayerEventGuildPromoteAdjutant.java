package com.events;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.data.PlayerConfigurator;
import com.main.Guild;
import com.menu.GuildMenu;

public class PlayerEventGuildPromoteAdjutant implements PlayerEvent {

	private OfflinePlayer legionnaire;
	
	public PlayerEventGuildPromoteAdjutant(OfflinePlayer legionnaire) {
		this.legionnaire=legionnaire;
	}
	
	@Override
	public boolean execute(Player p) {
		try {
			Guild playerGuild=PlayerConfigurator.getGuild(p);
			if(playerGuild!=null) {
				playerGuild.promoteToAdjutant(p, legionnaire);
				GuildMenu.open(p);
			} else {
				p.closeInventory();
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			p.sendMessage(ChatColor.RED+"Возникла ошибка при повышении игрока!");
			p.closeInventory();
			return false;
		}
	}
}
