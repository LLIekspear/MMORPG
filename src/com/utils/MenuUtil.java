package com.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.menu.MenuIconHeads;

public class MenuUtil {

	private static List<Material> staticItems = Arrays.asList(Material.FILLED_MAP, Material.COMPASS, Material.NETHER_STAR, Material.BARRIER, Material.PLAYER_HEAD);
	
	public static void setMainMenuOpener(Inventory menu, int i) {
		ItemStack mainMenuItemStack=new ItemStack(Material.NETHER_STAR);
		ItemMeta mainMenuItemMeta=mainMenuItemStack.getItemMeta();
		mainMenuItemMeta.setDisplayName(ChatColor.GOLD+"Открыть главное меню");
		mainMenuItemStack.setItemMeta(mainMenuItemMeta);
		menu.setItem(i, mainMenuItemStack);
	}
	
	public static int roundInventorySize(int inventorySize) {
		if(inventorySize <= 9) {
			inventorySize = 9;
		}
		else if(inventorySize <= 18) {
			inventorySize = 18;
		}
		else if(inventorySize <= 27) {
			inventorySize = 27;
		}
		else if(inventorySize <= 36) {
			inventorySize = 36;
		}
		else if(inventorySize <= 45) {
			inventorySize = 45;
		}
		else {
			inventorySize = 54;
		}
		return inventorySize;
	}
	
	public static void setCurrencyDisplay(Inventory menu, Player p, int i) {
		ItemStack currencyItemStack=MenuIconHeads.getMoneyItem();
		ItemMeta currencyItemMeta=currencyItemStack.getItemMeta();
		currencyItemMeta.setDisplayName(ChatColor.GREEN+"Common Currency");
		List<String> lores=new ArrayList<String>();
		lores.add("Coins: 1000");
		lores.add("Medals: 1000");
		currencyItemMeta.setLore(lores);
		currencyItemStack.setItemMeta(currencyItemMeta);
		menu.setItem(i, currencyItemStack);
	}
	
	public static void setBorders(Inventory menu) {
		ItemStack borderItemStack = new ItemStack(Material.IRON_BARS);
		ItemMeta borderItemMeta = borderItemStack.getItemMeta();
		borderItemMeta.setDisplayName(" ");
		borderItemStack.setItemMeta(borderItemMeta);
		
		for(int slot = 0; slot < menu.getSize(); slot++) {
			if(menu.getItem(slot) == null)
				menu.setItem(slot, borderItemStack);
		}
	}
}
