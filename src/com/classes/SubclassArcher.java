package com.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SubclassArcher extends BaseSubclass {

	public static final String CLASS_NAME="Archer";
	
	public SubclassArcher() {
		//registerLearnable(new Learnable(new SkillPlaceholder(), 1));
	}
	
	@Override
	public String getSubclassName() {
		return CLASS_NAME;
	}
	
	@Override
	public String getSubclassDesc() {
		return "С луком бегает.";
	}
	
	@Override
	public ChatColor getSubclassColor() {
		return ChatColor.GREEN;
	}
	
	@Override
	public ItemStack getSubclassItemStack() {
		return new ItemStack(Material.BOW);
	}
}
