package com.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.events.PlayerEventDemoteResident;
import com.events.PlayerEventRaceKick;
import com.events.PlayerEventRacePromoteAdviser;
import com.main.PlayerData;
import com.main.PlayerDataPool;
import com.main.Race;
import com.menu.ClassMenu;
import com.menu.Dialog;
import com.utils.ItemUtils;

public class RaceListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getView().getTitle().equalsIgnoreCase(ChatColor.BLACK+""+ChatColor.BOLD+"���� ����")||e.getView().getTitle().equalsIgnoreCase(ChatColor.BLACK+""+ChatColor.BOLD+"������ ���")) {
			e.setCancelled(true);
			ItemStack clicked=e.getCurrentItem();
			final Player p=(Player) e.getWhoClicked();
			if(clicked==null) {
				return;
			} else if(clicked.getType().equals(Material.PLAYER_HEAD)) {
				tryToChangePlayerRank(e);
			} else if(!clicked.getType().equals(Material.IRON_BARS)&&e.getView().getTitle().equalsIgnoreCase(ChatColor.BLACK+""+ChatColor.BOLD+"������ ���")) {
				Race.getRaceByName(ChatColor.stripColor(clicked.getItemMeta().getDisplayName())).addPlayer(p);
				p.closeInventory();
				ClassMenu.open(Bukkit.getPlayer(p.getName()));
			}
		}
	}
	
	private void tryToChangePlayerRank(InventoryClickEvent e) {
		ItemStack clicked=e.getCurrentItem();
		final Player p=(Player) e.getWhoClicked();
		SkullMeta skullMeta=(SkullMeta) clicked.getItemMeta();
		OfflinePlayer resident=skullMeta.getOwningPlayer();
		if(resident==null||!ItemUtils.hasLore(clicked)) {
			return;
		}
		if(e.getClick().toString().contains("RIGHT")) {
			if(clicked.getItemMeta().getLore().contains(ChatColor.GRAY+"��� ��� ��������� �� ���������")) {
				PlayerData playerData=PlayerDataPool.getPlayer(p);
				playerData.getSelections().setPlayerEventName("���������");
				playerData.getSelections().setPlayerEvent(new PlayerEventDemoteResident(resident));
				Dialog.open(p, clicked);
			} else if(skullMeta.getLore().contains(ChatColor.GRAY+"��� ��� �������� �� ����")) {
				p.sendMessage("������� ������� ������!");
				PlayerData playerData=PlayerDataPool.getPlayer(p);
				playerData.getSelections().setPlayerEventName("��������");
				playerData.getSelections().setPlayerEvent(new PlayerEventRaceKick(resident));
				Dialog.open(p, clicked);
			}
		} else {
			if(clicked.getItemMeta().getLore().contains(ChatColor.GRAY+"��� ��� ��������� �� ���������")) {
				PlayerData playerData=PlayerDataPool.getPlayer(p);
				playerData.getSelections().setPlayerEventName("���������");
				playerData.getSelections().setPlayerEvent(new PlayerEventRacePromoteAdviser(resident));
				Dialog.open(p, clicked);
			}
		}
	}
}
