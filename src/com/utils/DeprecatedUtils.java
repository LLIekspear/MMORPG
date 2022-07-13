package com.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class DeprecatedUtils {

	public static ItemStack addPlayerHeadTexture(ItemStack headItemStack, String base64, UUID hashAsId) {
		return Bukkit.getUnsafe().modifyItemStack(headItemStack,
				"{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}"
		);
	}
}
