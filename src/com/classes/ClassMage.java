package com.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.items.ArmorCategory;

public class ClassMage extends BaseClass {

	public ClassMage() {
		registerSubclass(new SubclassSorcerer());
		registerSubclass(new SubclassWitcher());
	}
	
	@Override
	public String getClassName() {
		return "Маг";
	}
	
	@Override
	public String getClassDesc() {
		return "Ты на физику не смотри, я духом силен.";
	}
	
	@Override
	public ChatColor getClassColor() {
		return ChatColor.LIGHT_PURPLE;
	}
	
	@Override
	public ItemStack getClassItemStack() {
		return new ItemStack(Material.IRON_HOE);
		//return CharacterIconHeads.getMageItem();
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
		stats.setAxeSkill(80000);
		return stats;
	}
	
	/*@Override
	public ItemStack getStartingWeapon() {
		PlayerSkill skill=new SkillTheMagician();
		return EquipmentHelper.getSkillgemWeapon(skill, Material.DIAMOND_HOE, false);
	}*/
}
