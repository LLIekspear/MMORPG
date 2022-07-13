package com.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SubclassPaladin extends BaseSubclass {

	public static final String CLASS_NAME="Paladin";
	
	public SubclassPaladin() {
		//registerLearnable(new Learnable(new SkillPlaceholder(), 1));
	}
	
	@Override
	public String getSubclassName() {
		return CLASS_NAME;
	}
	
	@Override
	public String getSubclassDesc() {
		return " то это?";
	}
	
	@Override
	public ChatColor getSubclassColor() {
		return ChatColor.BLUE;
	}
	
	@Override
	public ItemStack getSubclassItemStack() {
		return new ItemStack(Material.DIAMOND_SWORD);
	}
}
