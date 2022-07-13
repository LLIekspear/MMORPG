package com.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.main.Group;
import com.main.PlayerData;
import com.main.PlayerDataPool;
import com.main.PlayerGroupPool;
import com.menu.GroupMenu;
import com.utils.ItemUtils;

import net.md_5.bungee.api.ChatColor;

public class GroupListener implements Listener {

	@EventHandler
	public void removePlayer(PlayerQuitEvent e) {
		try {
			PlayerData playerData=PlayerDataPool.getPlayer(e.getPlayer());
			Group playerGroup=PlayerGroupPool.getGroup(playerData.getSelections().getGroupUuidString());
			playerGroup.removePlayer(e.getPlayer());
			playerData.getSelections().setGroupUuidString(null);
			playerGroup.removePlayer(e.getPlayer());
	
		} catch(Exception error) {
			//
		}
	}
	@EventHandler
	public void selectMenuPoint(InventoryClickEvent e) {
		if(e.getView().getTitle().equalsIgnoreCase(ChatColor.BLACK+""+ChatColor.BOLD+"Группа")||e.getView().getTitle().equalsIgnoreCase(ChatColor.BLACK+""+ChatColor.BOLD+"Поиск группы")) {
			e.setCancelled(true);
			ItemStack clicked=e.getCurrentItem();
			final Player p=(Player)e.getWhoClicked();
			if(clicked==null) {
				return;
			}
			//create group
			else if(clicked.getType().equals(Material.LIGHT_BLUE_CONCRETE) ) {
				if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.BLUE+"Создать открытую группу")/*.contains("Создать")*/) {
					//p.sendMessage("Пошло создание группы");
					Group playerGroup = new Group(p);
					PlayerData playerData=PlayerDataPool.getPlayer(p);
					playerData.getSelections().setGroupUuidString(playerGroup.getGroupUuidString());
					//p.sendMessage("UUID: "+playerGroup.getGroupUuidString());
					PlayerGroupPool.regGroup(playerGroup);
					GroupMenu.open(p);
				} else {
					GroupMenu.passwordInput(p, null, "_");
				}
			}
			//join password group
			else if(clicked.getType().equals(Material.YELLOW_CONCRETE)) {
				String groupUuidString=ItemUtils.getStringFromLore(clicked, "UUID", 1);
				//лог
				GroupMenu.passwordInput(p, groupUuidString, "_");
			}
			//join open group
			else if(clicked.getType().equals(Material.LIME_CONCRETE)&&clicked.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.BLUE+"Группа")) {
				//p.sendMessage("Попытка зайти в группу...");
				String groupUuidString = ItemUtils.getStringFromLore(clicked, "UUID", 1);
				Group playerGroup = PlayerGroupPool.getGroup(groupUuidString);
				PlayerData playerData =PlayerDataPool.getPlayer(p);
				playerGroup.addPlayer(p);
				playerData.getSelections().setGroupUuidString(groupUuidString);
				GroupMenu.open(p);
				
				Scoreboard score=Bukkit.getScoreboardManager().getMainScoreboard();
				Team t=score.getTeam("group-"+Bukkit.getOfflinePlayer(UUID.fromString(playerGroup.getAdminUuidString())).getName());
				t.addEntry(p.getName());
			}
			//password input
			else if(clicked.getType().equals(Material.GRAY_CONCRETE)) {
				String passwordString=p.getOpenInventory().getTitle().split(" ")[2].replace(""+ChatColor.DARK_RED, "");
				passwordString+=clicked.getItemMeta().getDisplayName().replace(""+ChatColor.RESET, "");
				if(ItemUtils.hasLore(clicked)) {
					String groupUuidString=ItemUtils.getStringFromLore(clicked, "UUID", 1);
					if(passwordString.length()==5) {
						Group playerGroup=PlayerGroupPool.getGroup(groupUuidString);
						if(passwordString.equals(playerGroup.getGroupPassword())) {
							PlayerData playerData=PlayerDataPool.getPlayer(p);
							playerGroup.addPlayer(p);
							playerData.getSelections().setGroupUuidString(groupUuidString);
							GroupMenu.open(p);
						} else {
							p.sendMessage(ChatColor.RED+"Неправильный пароль!");
							p.closeInventory();
							return;
						}
					} else {
						GroupMenu.passwordInput(p, groupUuidString, passwordString);
					}
				} else {
					//лог с паролем и его длинной
					if(passwordString.length()==5) {
						Group playerGroup=new Group(p);
						PlayerData playerData=PlayerDataPool.getPlayer(p);
						playerData.getSelections().setGroupUuidString(playerGroup.getGroupUuidString());
						playerGroup.setGroupPassword(passwordString);
						PlayerGroupPool.regGroup(playerGroup);
						GroupMenu.open(p);
					} else {
						GroupMenu.passwordInput(p, null, passwordString);
					}
				}
			} else {
				GroupMenu.handlePlayerInteractions(p, clicked);
			}
		}
	}
}
