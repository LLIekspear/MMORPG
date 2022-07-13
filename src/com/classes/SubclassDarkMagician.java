package com.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SubclassDarkMagician extends BaseSubclass {

	public static final String CLASS_NAME="DarkMagician";
	
	public SubclassDarkMagician() {
		//registerLearnable(new Learnable(new SkillPlaceholder(), 1));
	}
	
	@Override
	public String getSubclassName() {
		return CLASS_NAME;
	}
	
	@Override
	public String getSubclassDesc() {
		return "Темная магия";
	}
	
	@Override
	public ChatColor getSubclassColor() {
		return ChatColor.LIGHT_PURPLE;
	}
	
	@Override
	public ItemStack getSubclassItemStack() {
		return new ItemStack(Material.BOOK);
	}
}
