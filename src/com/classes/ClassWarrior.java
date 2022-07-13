package com.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.items.ArmorCategory;
import com.classes.PlayerClassStats;

public class ClassWarrior extends BaseClass {

	public ClassWarrior() {
		registerSubclass(new SubclassKnight());
		registerSubclass(new SubclassPaladin());
	}
	
	@Override
	public String getClassName() {
		return "Воин";
	}
	
	@Override
	public String getClassDesc() {
		return "Бить, крушить, ломать.";
	}
	
	@Override
	public ChatColor getClassColor() {
		return ChatColor.BLUE;
	}
	
	@Override
	public ItemStack getClassItemStack() {
		return new ItemStack(Material.IRON_CHESTPLATE);
		//return CharacterIconHeads.getWarriorItem();
	}
	
	@Override
	public ArmorCategory getArmorCategory() {
		return ArmorCategory.HEAVY;
	}
	
	@Override
	public PlayerClassStats getStartingStats() {
		PlayerClassStats stats=new PlayerClassStats();
		stats.setAxeSkill(135000);
		stats.setAxeSkillMax(250000);
		stats.setStaffSkill(80000);
		return stats;
	}
	
	/*@Override
	public ItemStack getStartingWeapon() {
		PlayerSkill skill=new SkillTheChariot();
		return EquipmentHelper.getSkillgemWeapon(skill, Material.DIAMOND_AXE, false);
	}*/
}
