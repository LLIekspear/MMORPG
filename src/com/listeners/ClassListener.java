package com.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.data.PlayerConfigurator;
import com.main.PlayerData;
import com.main.PlayerDataPool;

public class ClassListener implements Listener {

	@EventHandler
	public void selectMenuPoint(InventoryClickEvent e) {
		if(e.getView().getTitle().equalsIgnoreCase(ChatColor.BLACK+""+ChatColor.BOLD+"Выберите свой класс")) {
			//e.getWhoClicked().sendMessage("Класс прожал!");
			e.setCancelled(true);
			ItemStack clicked=e.getCurrentItem();
			final Player p=(Player) e.getWhoClicked();
			PlayerData playerData=PlayerDataPool.getPlayer(p);
			
			/*if(playerData==null||!ItemUtils.isMaterial(clicked, Material.PLAYER_HEAD)) {
				return;
			}*/
			
			String className=ChatColor.stripColor(clicked.getItemMeta().getDisplayName());
			//e.getWhoClicked().sendMessage("Класс: "+className);
			//e.getWhoClicked().sendMessage(p.getName());
			//if(PlayerClassPool.getClass(className)!=null) {
			
			//TODO допилить выгрузку информации о классе игрока + установку класса во время работы	
			playerData.getSelections().setSelectedCharacterClass(className);
			PlayerConfigurator.setPlayerClass(p, className);
			p.closeInventory();
			//}
		}
	}
}
