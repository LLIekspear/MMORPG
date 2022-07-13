package com.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.items.ArmorCategory;

public class ClassAlchemist extends BaseClass {

	public ClassAlchemist() {
		registerSubclass(new SubclassSummoner());
		registerSubclass(new SubclassDarkMagician());
	}
	
	@Override
	public String getClassName() {
		return "Алхимик";
	}
	
	@Override
	public String getClassDesc() {
		return "Типо маг, но не совсем, зато банки и свитки делать умею.";
	}
	
	@Override
	public ChatColor getClassColor() {
		return ChatColor.YELLOW;
	}
	
	@Override
	public ItemStack getClassItemStack() {
		return new ItemStack(Material.BOOK);
		//return CharacterIconHeads.getAlchemistItem();
	}
	
	@Override
	public ArmorCategory getArmorCategory() {
		return ArmorCategory.LIGHT;
	}
	
	@Override
	public PlayerClassStats getStartingStats() {
		PlayerClassStats stats=new PlayerClassStats();
		stats.setStaffSkill(135000);
		stats.setStaffSkillMax(250000);
		stats.setSwordSkill(80000);
		return stats;
	}
	
	/*@Override
	public ItemStack getStartingWeapon() {
		PlayerSkill skill=new SkillTheHighPriestess();
		return EquipmentHelper.getSkillgemWeapon(skill, Material.DIAMOND_HOE, false);
	}*/
}
