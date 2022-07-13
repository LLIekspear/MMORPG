package com.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SubclassMercenary extends BaseSubclass {

	public static final String CLASS_NAME="Mercenary";
	
	public SubclassMercenary() {
		//registerLearnable(new Learnable(new SkillPlaceholder(), 1));
	}
	
	@Override
	public String getSubclassName() {
		return CLASS_NAME;
	}
	
	@Override
	public String getSubclassDesc() {
		return "За деньги Родину продаст.";
	}
	
	@Override
	public ChatColor getSubclassColor() {
		return ChatColor.GREEN;
	}
	
	@Override
	public ItemStack getSubclassItemStack() {
		return new ItemStack(Material.GOLDEN_SWORD);
	}
}
