package com.events;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.data.PlayerConfigurator;
import com.main.Guild;
import com.menu.GuildMenu;

public class PlayerEventGuildPromoteLegate implements PlayerEvent {

	private OfflinePlayer legionnaire;
	
	public PlayerEventGuildPromoteLegate(OfflinePlayer legionnaire) {
		this.legionnaire=legionnaire;
	}
	
	@Override
	public boolean execute(Player p) {
		try {
			Guild playerGuild=PlayerConfigurator.getGuild(p);
			if(playerGuild!=null) {
				playerGuild.promoteToLegate(p, legionnaire);
				GuildMenu.open(p);
			} else {
				p.closeInventory();
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			p.sendMessage(ChatColor.RED+"�������� ������ ��� ��������� ������!");
			p.closeInventory();
			return false;
		}
	}
}
