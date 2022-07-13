package com.brewery;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class CustomAlco extends BrewAction {

	@Override
	public void brew(BrewerInventory inv, ItemStack item, ItemStack ingredient) {
		if(!(item.getType()==Material.LEATHER_CHESTPLATE)) {
			return;
		}
		
		LeatherArmorMeta armorMeta=(LeatherArmorMeta)item.getItemMeta();
		Color color=null;
		if(ingredient.getType()==Material.BLACK_DYE) {
			color=Color.BLACK;
		}
		armorMeta.setColor(color);
		item.setItemMeta(armorMeta);
	}
}
