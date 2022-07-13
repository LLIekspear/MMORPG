package com.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.items.ArmorCategory;

public class ClassRanger extends BaseClass {

	public ClassRanger() {
		registerSubclass(new SubclassArcher());
		registerSubclass(new SubclassCrossbowman());
	}
	
	@Override
	public String getClassName() {
		return "Стрелок";
	}
	
	@Override
	public String getClassDesc() {
		return "Стрельба - это искусство!";
	}
	
	@Override
	public ChatColor getClassColor() {
		return ChatColor.GREEN;
	}
	
	@Override
	public ItemStack getClassItemStack() {
		return new ItemStack(Material.BOW);
		//return CharacterIconHeads.getRangerItem();
	}
	
	@Override
	public ArmorCategory getArmorCategory() {
		return ArmorCategory.MEDIUM;
	}
	
	@Override
	public PlayerClassStats getStartingStats() {
		PlayerClassStats stats=new PlayerClassStats();
		stats.setStaffSkill(80000);
		//stats.setBowSkill(135000);
		//stats.setBowSkillMax(250000);
		return stats;
	}
	
	/*@Override
	public ItemStack getStartingWeapon() {
		PlayerSkill skill=new SkillTheShot();
		return EquipmentHelper.getSkillemWeapon(skill, Material.BOW, false);
	}*/
}
