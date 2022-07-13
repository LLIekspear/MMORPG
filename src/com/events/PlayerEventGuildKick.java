package com.events;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.data.PlayerConfigurator;
import com.main.Guild;
import com.menu.GuildMenu;

public class PlayerEventGuildKick implements PlayerEvent {

	private OfflinePlayer legionnaire;
	
	public PlayerEventGuildKick(OfflinePlayer legionnaire) {
		this.legionnaire=legionnaire;
	}
	
	@Override
	public boolean execute(Player p) {
		try {
			Guild playerGuild=PlayerConfigurator.getGuild(p);
			if(playerGuild!=null) {
				playerGuild.kickLegionnaire(p, legionnaire);
				GuildMenu.open(p);
			} else {
				p.closeInventory();
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			p.sendMessage(ChatColor.RED+"Возникла ошибка при попытке исключить игрока из легиона!");
			p.closeInventory();
			return false;
		}
	}
}
