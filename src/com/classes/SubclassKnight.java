package com.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SubclassKnight extends BaseSubclass {

	public static final String CLASS_NAME="Knight";
	
	public SubclassKnight() {
		//registerLearnable(new Learnable(new SkillPlaceholder(), 1));
	}
	
	@Override
	public String getSubclassName() {
		return CLASS_NAME;
	}
	
	@Override
	public String getSubclassDesc() {
		return "Рыцарь круглого стола.";
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
