package com.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.data.PlayerConfigurator;
import com.main.Guild;
import com.menu.GuildApplicationMenu;

public class GuildApplicationListener implements Listener {

	@EventHandler
	public void selectMenuPoint(InventoryClickEvent e) {
		if(e.getView().getTitle().equalsIgnoreCase(ChatColor.BLACK+""+ChatColor.BOLD+"Заявки в легион")) {
			e.setCancelled(true);
			ItemStack clicked=e.getCurrentItem();
			final Player p=(Player)e.getWhoClicked();
			Guild guild=PlayerConfigurator.getGuild(p);
			if(clicked==null||guild==null) {
				return;
			} else if(clicked.getType().equals(Material.PLAYER_HEAD)&&e.getView().getTitle().equalsIgnoreCase(ChatColor.BLACK+""+ChatColor.BOLD+"Заявки в легион")) {
				SkullMeta sm=(SkullMeta) clicked.getItemMeta();
				OfflinePlayer applicant=sm.getOwningPlayer();
				if(applicant==null) {
					return;
				}
				if(e.getClick().toString().contains("RIGHT")) {
					if(applicant.isOnline()) {
						applicant.getPlayer().sendMessage(ChatColor.RED+"Ваша заявка в "+guild.getGuildName()+" была отклонена.");;
					}
					guild.removeApplicant(applicant.getUniqueId().toString());
				} else if(!guild.isFull()) {
					guild.removeApplicant(applicant.getUniqueId().toString());
					guild.addPlayer(applicant);
				}
				GuildApplicationMenu.open(p);
			}
		}
	}
}
