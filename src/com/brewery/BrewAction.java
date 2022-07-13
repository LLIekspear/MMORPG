package com.brewery;

import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;

public abstract class BrewAction {

	public abstract void brew(BrewerInventory inv, ItemStack item, ItemStack ingredient);
}
