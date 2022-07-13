package com.menu;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.utils.DeprecatedUtils;
import com.utils.ItemUtils;


public class HeadUtils {

	public static ItemStack getPlayerHead(String base64) {
		ItemStack headItemStack = new ItemStack(Material.PLAYER_HEAD);
		UUID hashAsId = new UUID(base64.hashCode(), base64.hashCode());
		return DeprecatedUtils.addPlayerHeadTexture(headItemStack, base64, hashAsId);
	}
	public static boolean isHeadMenuItem(ItemStack itemStack, String itemName) {
		return ItemUtils.isMaterial(itemStack, Material.PLAYER_HEAD) && ItemUtils.isSpecificItem(itemStack, itemName);
	}
}
