package com.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.main.PlayerData;
import com.main.PlayerDataPool;
import com.utils.LInventory;
import com.utils.MenuUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Dialog implements LInventory {
	@Override
	public String getInventoryId() {
		return "dialog";
	}
	
	public static void open(Player p) {
		open(p, null);
	}
	
	public static void open(Player p, ItemStack infoItemStack) {
		PlayerData playerData=PlayerDataPool.getPlayer(p);
		String menuTitle=ChatColor.BLACK+""+ChatColor.BOLD+"œÓ‰Ú‚ÂÊ‰ÂÌËÂ: "+playerData.getSelections().getPlayerEventName();
		//Inventory menu=Bukkit.createInventory(new Dialog(), 9, menuTitle);
		Inventory menu=Bukkit.createInventory(p, 9, menuTitle);
		ItemStack confirmItemStack=GenericIconHeads.getConfirmItem();
		ItemMeta confirmItemMeta=confirmItemStack.getItemMeta();
		confirmItemMeta.setDisplayName(ChatColor.GREEN+"œŒƒ“¬≈–ƒ»“‹");
		confirmItemStack.setItemMeta(confirmItemMeta);
		menu.setItem(0, confirmItemStack);
		
		ItemStack declineItemStack=GenericIconHeads.getDeclineItem();
		ItemMeta declineItemMeta=declineItemStack.getItemMeta();
		declineItemMeta.setDisplayName(ChatColor.RED+"Œ“ ÀŒÕ»“‹");
		declineItemStack.setItemMeta(declineItemMeta);
		menu.setItem(8, declineItemStack);
		
		if(infoItemStack!=null) {
			menu.setItem(4, infoItemStack);
		}
		
		MenuUtil.setBorders(menu);
		p.openInventory(menu);
	}

}
