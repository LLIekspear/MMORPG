package com.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SubclassAssassin extends BaseSubclass {

	public static final String CLASS_NAME="Assassin";
	
	public SubclassAssassin() {
		//registerLearnable(new Learnable(new SkillPlaceholder(), 1));
	}
	
	@Override
	public String getSubclassName() {
		return CLASS_NAME;
	}
	
	@Override
	public String getSubclassDesc() {
		return "Крыса.";
	}
	
	@Override
	public ChatColor getSubclassColor() {
		return ChatColor.GREEN;
	}
	
	@Override
	public ItemStack getSubclassItemStack() {
		return new ItemStack(Material.IRON_SWORD);
	}
}
