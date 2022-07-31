package com.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

public class NPCCommand implements CommandExecutor {

	public void freezeEntity(Villager e) {
		e.setInvulnerable(true);
		e.setAI(false);
	}
	
	public void createNPC2(Location loc) {
		Villager villager=(Villager)Bukkit.getWorld("world").spawnEntity(loc, EntityType.VILLAGER);
		villager.setCustomName("Белукас");
		villager.setCustomNameVisible(true);
		villager.setProfession(Profession.SHEPHERD);
		freezeEntity(villager);
	}
	
	public void createNPC(Location loc) {
		Villager villager=(Villager)Bukkit.getWorld("world").spawnEntity(loc, EntityType.VILLAGER);
		villager.setCustomName("ТоварнаяБиржа");
		villager.setCustomNameVisible(true);
		villager.setProfession(Profession.MASON);
		freezeEntity(villager);
	}
	
	public void createNPC1(Location loc) {
		Villager villager=(Villager)Bukkit.getWorld("world").spawnEntity(loc, EntityType.VILLAGER);
		villager.setCustomName("Рыба");
		villager.setCustomNameVisible(true);
		villager.setProfession(Profession.LIBRARIAN);
		freezeEntity(villager);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p=(Player)sender;
		if(sender instanceof Player && args[0].equalsIgnoreCase("fish")&&p.hasPermission("lliekspear.fish_trader_creation")) {
			Location loc=p.getLocation();
			createNPC1(loc);
		} else if(sender instanceof Player && args[0].equalsIgnoreCase("gold")&&p.hasPermission("lliekspear.gold_trader_creation")) {
			Location loc=p.getLocation();
			createNPC(loc);
		} else if(sender instanceof Player &&args[0].equalsIgnoreCase("weapon")&&p.hasPermission("lliekspear.weapon_trader_creation")) {
			Location loc=p.getLocation();
			createNPC2(loc);
		} else {
			sender.sendMessage(ChatColor.RED+"Произошла ошибка на этапе создания торговца. У Вас достаточно прав для этого действия?");
		}
		return true;
	}
}
