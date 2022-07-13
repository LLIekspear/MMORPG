package com.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.items.ArmorCategory;

public class ClassRogue extends BaseClass {

	public ClassRogue() {
		registerSubclass(new SubclassAssassin());
		registerSubclass(new SubclassMercenary());
	}
	
	@Override
	public String getClassName() {
		return "Убийца";
	}
	
	@Override
	public String getClassDesc() {
		return "В спину бить и резать - наш девиз.";
	}
	
	@Override
	public ChatColor getClassColor() {
		return ChatColor.GREEN;
	}
	
	@Override
	public ItemStack getClassItemStack() {
		return new ItemStack(Material.IRON_SWORD);
		//return CharacterIconHeads.getRogueItem();
	}
	
	@Override
	public ArmorCategory getArmorCategory() {
		return ArmorCategory.MEDIUM;
	}
	
	@Override
	public PlayerClassStats getStartingStats() {
		PlayerClassStats stats=new PlayerClassStats();
		stats.setSwordSkill(135000);
		stats.setSwordSkillMax(250000);
		stats.setStaffSkill(80000);
		return stats;
	}
	
	/*@Override
	public ItemStack getStartingWeapon() {
		PlayerSkill skill=new SkillTheHierophant();
		return EquipmentHelper.getSkillgemWeapon(skill, Material.DIAMOND_SWORD, false);
	}*/
}
