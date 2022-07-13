package com.listeners;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.events.PlayerEventDemoteLegionnaire;
import com.events.PlayerEventGuildKick;
import com.events.PlayerEventGuildLeave;
import com.events.PlayerEventGuildPromoteAdjutant;
import com.events.PlayerEventGuildPromoteLegate;
import com.main.Guild;
import com.main.PlayerData;
import com.main.PlayerDataPool;
import com.menu.Dialog;
import com.menu.GuildApplicationMenu;
import com.utils.ItemUtils;

import net.md_5.bungee.api.ChatColor;

public class GuildListener implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getView().getTitle().equalsIgnoreCase(ChatColor.BLACK+""+ChatColor.BOLD+"������")||e.getView().getTitle().equalsIgnoreCase(ChatColor.BLACK+""+ChatColor.BOLD+"������ ��������")) {
			e.setCancelled(true);
			ItemStack clicked=e.getCurrentItem();
			final Player p=(Player)e.getWhoClicked();
			if(clicked==null) {
				return;
			} else if(ItemUtils.isSpecificItem(clicked, "������� �����")) {
				boolean isRightClick=e.getClick().toString().contains("RIGHT");
				if(isRightClick&&ItemUtils.hasLore(clicked)&&clicked.getItemMeta().getLore().contains("���")) {
					//BannerBuilder.open(p);
				} else {
					//BannerMenu.open(p);
				}
			} else if(ItemUtils.isSpecificItem(clicked, "����������� ������")) {
				GuildApplicationMenu.open(p);
			} //else if(HeadUtils.isHeadMenuItem(clicked, "Building: Guildhall"))
			else if(clicked.getType().equals(Material.BARRIER)) {
				PlayerData playerData=PlayerDataPool.getPlayer(p);
				playerData.getSelections().setPlayerEventName("��������");
				//PlayerEventGuildLeave PEGL=new PlayerEventGuildLeave();
				playerData.getSelections().setPlayerEvent(new PlayerEventGuildLeave());
				//playerData.getSelections().setPlayerEvent(PEGL);
				//PEGL.execute(p);
				Dialog.open(p);
			} else if(clicked.getType().toString().endsWith("_BANNER")&&!(clicked.getItemMeta().getDisplayName().isEmpty())) {
				Guild.applyForGuild(p, ChatColor.stripColor(clicked.getItemMeta().getDisplayName()));
				p.closeInventory();
			} else if(clicked.getType().equals(Material.PLAYER_HEAD)) {
				//p.sendMessage("���� ������, ��������� � ������...");
				tryToChangePlayerRank(e);
			}
		}
	}
	
	private void tryToChangePlayerRank(InventoryClickEvent e) {
		ItemStack clicked=e.getCurrentItem();
		final Player p=(Player)e.getWhoClicked();
		SkullMeta skullMeta=(SkullMeta) clicked.getItemMeta();
		OfflinePlayer legionnaire=skullMeta.getOwningPlayer();
		if(legionnaire==null||!ItemUtils.hasLore(clicked)) {
			return;
		}
		if(e.getClick().toString().contains("RIGHT")) {
			//p.sendMessage("��� ��������");
			if(clicked.getItemMeta().getLore().contains(ChatColor.GRAY+"��� ��� ��������� �� ���������")) {
				PlayerData playerData=PlayerDataPool.getPlayer(p);
				playerData.getSelections().setPlayerEventName("���������");
				playerData.getSelections().setPlayerEvent(new PlayerEventDemoteLegionnaire(legionnaire));
				//PlayerEventDemoteLegionnaire PEDL=new PlayerEventDemoteLegionnaire(legionnaire);
				//playerData.getSelections().setPlayerEvent(PEDL);
				//PEDL.execute(p);
				Dialog.open(p, clicked);
			} else if(skullMeta.getLore().contains(ChatColor.GRAY+"��� ��� ���������� �� �������")) {
				//p.sendMessage("������� ��������� �� �������");
				PlayerData playerData=PlayerDataPool.getPlayer(p);
				playerData.getSelections().setPlayerEventName("����������");
				playerData.getSelections().setPlayerEvent(new PlayerEventGuildKick(legionnaire));
				//PlayerEventGuildKick PEGK=new PlayerEventGuildKick(legionnaire);
				//playerData.getSelections().setPlayerEvent(PEGK);
				//PEGK.execute(p);
				Dialog.open(p, clicked);
			}
		} else {
			//p.sendMessage("��� ��������");
			if(clicked.getItemMeta().getLore().contains(ChatColor.GRAY+"��� ��� ��������� �� ���������")) {
				PlayerData playerData=PlayerDataPool.getPlayer(p);
				playerData.getSelections().setPlayerEventName("���������");
				playerData.getSelections().setPlayerEvent(new PlayerEventGuildPromoteAdjutant(legionnaire));
				//PlayerEventGuildPromoteAdjutant PEGPA=new PlayerEventGuildPromoteAdjutant(legionnaire);
				//playerData.getSelections().setPlayerEvent(PEGPA);
				//PEGPA.execute(p);
				Dialog.open(p, clicked);
			} else if(clicked.getItemMeta().getLore().contains(ChatColor.GRAY+"��� ��� ��������� �� ������")) { 
				PlayerData playerData=PlayerDataPool.getPlayer(p);
				playerData.getSelections().setPlayerEventName("���������");
				playerData.getSelections().setPlayerEvent(new PlayerEventGuildPromoteLegate(legionnaire));
				//PlayerEventGuildPromoteLegate PEGPL=new PlayerEventGuildPromoteLegate(legionnaire);
				//playerData.getSelections().setPlayerEvent(PEGPL);
				//PEGPL.execute(p);
				Dialog.open(p, clicked);
			}
		}
	}
}
