package com.classes;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public interface PlayerSubclass {

	public String getSubclassName();
	public String getSubclassDesc();
	public ChatColor getSubclassColor();
	public ItemStack getSubclassItemStack();
	//public List<Learnable> getLearnables();
	//public List<Learnable> getLearned(int masteryLevel);
	//public Learnable getNextLearnable(int masteryLevel);
}
