package com.classes;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.items.ArmorCategory;
import com.classes.PlayerClassStats;

public interface PlayerClass {
	
	public String getClassName();
	public String getClassDesc();
	public ChatColor getClassColor();
	public ItemStack getClassItemStack();
	public List<PlayerSubclass> getSubclasses();
	public PlayerSubclass getSubclass(String subclass);
	public ArmorCategory getArmorCategory();
	public PlayerClassStats getStartingStats();
	//public ItemStack getStartingWeapon();

}
