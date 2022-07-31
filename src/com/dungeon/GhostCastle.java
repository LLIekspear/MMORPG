package com.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.boss.EliteGhost;
import com.boss.GhostBoss;
import com.boss.MobGhost;
import com.google.common.collect.Lists;
import com.main.Main;

import io.netty.util.internal.ThreadLocalRandom;

public class GhostCastle {
	Dungeon castle=new Dungeon();
	GhostBoss boss=new GhostBoss();
	EliteGhost elite=new EliteGhost();
	MobGhost mob=new MobGhost();
	
	public void initCastle() {
		castle.setDUuid(UUID.randomUUID().toString());
		castle.setDName("Призрачная крепость");
		castle.setMaxAmount(30);
		String folderName="plugins/MMORPG/Dungeons/GhostCastle";
		World world=new WorldCreator(folderName).createWorld();
		world.setStorm(true);
		world.setDifficulty(Difficulty.PEACEFUL);
		world.setSpawnLocation(157, 65, -68);
		world.getWorldBorder().setCenter(124, -13);
		world.getWorldBorder().setSize(250);
		world.setTime(15000);
		world.setAutoSave(false);
		world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
		world.setGameRule(GameRule.KEEP_INVENTORY, true);
		world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
		world.setGameRule(GameRule.MOB_GRIEFING, false);
		world.setPVP(false);
		for(Entity entity : world.getEntities()) {
			if(!(entity instanceof Player)) {
				entity.remove();
			}
		}
		
		new BukkitRunnable() {
			@Override
			public void run() {
				world.setDifficulty(Difficulty.NORMAL);
				boss.spawnBoss(world, new Location(world, 91, 70, 79));
				elite.spawn(world, new Location(world, 127, 64, 52));
				mob.spawn(world, new Location(world, 132, 64, 78));
				mob.spawn(world, new Location(world, 124, 64, 65));
				mob.spawn(world, new Location(world, 140, 64, 29));
				mob.spawn(world, new Location(world, 115, 64, 23));
				mob.spawn(world, new Location(world, 124, 63, -5));
				mob.spawn(world, new Location(world, 108, 65, -10));
				mob.spawn(world, new Location(world, 126, 64, -45));
				mob.spawn(world, new Location(world, 131, 64, -71));
			}
		}.runTaskLater(Main.getInstance(), 280);
		
		chestGenerate(world);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				chestGenerate(Bukkit.getServer().getWorld("plugins/MMORPG/Dungeons/GhostCastle"));
			}
		}, 144000, 144000);
	}
	
	public void chestGenerate(World world) {
		Location chestLoc1=new Location(world, 128, 77, 15);
		Location chestLoc2=new Location(world, 100, 77, 18);
		
		chestLoc1.getBlock().setType(Material.CHEST);
		chestLoc2.getBlock().setType(Material.CHEST);
		
		if(chestLoc1.getBlock().getState() instanceof Chest && chestLoc2.getBlock().getState() instanceof Chest) {
			Chest chest1=(Chest)chestLoc1.getBlock().getState();
			Chest chest2=(Chest)chestLoc2.getBlock().getState();
			
			Inventory inv1=chest1.getBlockInventory();
			Inventory inv2=chest2.getBlockInventory();
			inv1=lootGenerate(inv1);
			inv2=lootGenerate(inv2);	
		}
	}
	
	public Inventory lootGenerate(Inventory inv) {
		ItemStack silverMedal=new ItemStack(Material.IRON_NUGGET);
		ItemMeta silverMeta=silverMedal.getItemMeta();
		silverMeta.setDisplayName(ChatColor.GRAY+""+ChatColor.BOLD+"Серебряная медаль");
		silverMeta.setLore(Lists.newArrayList(ChatColor.AQUA+""+ChatColor.BOLD+"Драгоценная медаль"));
		silverMedal.setItemMeta(silverMeta);
		
		inv.clear();
		inv.addItem(new ItemStack(Material.EMERALD));
		int rand=ThreadLocalRandom.current().nextInt(3);
		for(int i=0; i<rand; ++i) {
			inv.addItem(silverMedal);
		}
		inv.addItem(new ItemStack(Material.GOLD_INGOT));
		
		return inv;
	}

}
