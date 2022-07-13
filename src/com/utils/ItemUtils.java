package com.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {

	public static boolean hasLore(ItemStack itemStack) {
		return itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore();
	}
	
	public static String getStringFromLore(ItemStack itemStack, String content, int index) {
		for(String lore : itemStack.getItemMeta().getLore()) {
			if(lore.contains(content)) {
				return lore.split(" ")[index];
			}
		}
		return null;
	}
	
	public static boolean isMaterial(ItemStack itemStack, Material material) {
		return itemStack != null && itemStack.getType().equals(material);
	}
	
	public static boolean isSpecificItem(ItemStack itemStack, String itemName) {
		return hasDisplayName(itemStack) && ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()).equals(ChatColor.stripColor(itemName));
	}
	
	private static boolean hasDisplayName(ItemStack itemStack) {
		return itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName();
	}
}
