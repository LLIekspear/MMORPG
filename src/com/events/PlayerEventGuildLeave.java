package com.events;

import org.bukkit.entity.Player;

import com.data.GuildConfig;
import com.data.PlayerConfigurator;
import com.main.Guild;
import com.menu.GuildMenu;

import org.bukkit.ChatColor;

public class PlayerEventGuildLeave implements PlayerEvent {

	@Override
	public boolean execute(Player p) {
		try {
			Guild playerGuild=PlayerConfigurator.getGuild(p);
			if(playerGuild!=null) {
				PlayerConfigurator.setGuild(p, "none");
				playerGuild.removePlayer(p);
				if(playerGuild.getAdminUuidString()==null) {
					GuildConfig.deleteGuild(playerGuild.getGuildUuidString());
				}
				GuildMenu.open(p);
			} else {
				p.closeInventory();
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			p.sendMessage(ChatColor.RED+"Возникла ошибка при выходе из легиона!");
			p.closeInventory();
			return false;
		}
	}
}
