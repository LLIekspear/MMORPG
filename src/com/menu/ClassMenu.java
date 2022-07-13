package com.menu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.utils.LInventory;
import com.utils.MenuUtil;
import com.utils.UnicodeUtils;
import com.classes.ClassAlchemist;
import com.classes.ClassMage;
import com.classes.ClassRanger;
import com.classes.ClassRogue;
import com.classes.ClassWarrior;
import com.classes.PlayerClass;
import com.classes.PlayerClassPool;
import com.classes.PlayerClassStats;
import com.classes.PlayerSubclass;

public class ClassMenu implements LInventory {

	@Override
	public String getInventoryId() {
		return "classes";
	}
	
	public static void open(Player p) {
		String menuTitle=ChatColor.BLACK+""+ChatColor.BOLD+"Выберите свой класс";
		//Inventory menu=Bukkit.createInventory(p, 9, menuTitle);
		Inventory menu=Bukkit.createInventory(p, 18, menuTitle);
		
		List<PlayerClass> characterClasses=PlayerClassPool.getAllClasses();
		characterClasses.add(new ClassAlchemist());
		characterClasses.add(new ClassMage());
		characterClasses.add(new ClassRanger());
		characterClasses.add(new ClassRogue());
		characterClasses.add(new ClassWarrior());
		for(int i=0; i<characterClasses.size(); ++i) {
			PlayerClass characterClass=characterClasses.get(i);
			PlayerClassStats stats=characterClass.getStartingStats();
			
			ItemStack classItemStack = characterClass.getClassItemStack();
			ItemMeta classItemMeta=classItemStack.getItemMeta();
			classItemMeta.setDisplayName(characterClass.getClassColor()+""+ChatColor.BOLD+characterClass.getClassName());
			
			List<String> classLores=new ArrayList<>();
			for(String textPart : UnicodeUtils.wrapText(characterClass.getClassDesc())) {
				classLores.add(ChatColor.GRAY+textPart);
			}
			classLores.add("");
			
			classLores.add(ChatColor.DARK_AQUA+""+ChatColor.BOLD+"Подклассы");
			for(PlayerSubclass subclass : characterClass.getSubclasses()) {
				String subclassDesc=subclass.getSubclassColor()+subclass.getSubclassDesc();
				classLores.add(ChatColor.BOLD+subclass.getSubclassName()+": "+subclassDesc);
			}
			classLores.add("");
			
			classLores.add(ChatColor.DARK_AQUA+""+ChatColor.BOLD+"Доступная броня");
			String armorType=characterClass.getArmorCategory().toString();
			classLores.add(ChatColor.GOLD+"Тип брони: "+ChatColor.BLUE+armorType);
			int staffSkill=(int) Math.ceil(stats.getStaffSkill()/1000);
			classLores.add(ChatColor.GOLD+"Магический скилл: "+ChatColor.RED+staffSkill);
			int axeSkill=(int) Math.ceil(stats.getAxeSkill()/1000);
			classLores.add(ChatColor.GOLD+"Скилл топора: "+ChatColor.RED+axeSkill);
			int swordSkill=(int) Math.ceil(stats.getSwordSkill()/1000);
			classLores.add(ChatColor.GOLD+"Скилл меча: "+ChatColor.RED+swordSkill);
			
			classItemMeta.setLore(classLores);
			classItemStack.setItemMeta(classItemMeta);
			menu.setItem(i*2+1, classItemStack);
		}
		
		MenuUtil.setBorders(menu);
		p.openInventory(menu);
	}
	
	
}
