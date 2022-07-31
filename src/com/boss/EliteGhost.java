package com.boss;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.main.Main;

public class EliteGhost {

	private static Main plugin=Main.getInstance();
	BossBar bb;
	
	@SuppressWarnings("deprecation")
	public void spawn(World world, Location loc) {
		bb=Bukkit.createBossBar("Хранитель Марквлад", BarColor.RED, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
		
		PigZombie elite=world.spawn(loc.add(0.5, 0, 0.5), PigZombie.class);
		elite.setCustomName("§2§lХранитель Марквлад");
		elite.setCustomNameVisible(true);
		LivingEntity entity=(LivingEntity)elite;
		entity.setRemoveWhenFarAway(false);
		elite.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_SWORD));
		elite.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
		elite.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
		elite.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));
		elite.setMetadata("GhostElite", new FixedMetadataValue(plugin, "ghostelite"));
		elite.setMaxHealth(elite.getMaxHealth()*3);
		elite.setHealth(elite.getMaxHealth());
		
		bb.setProgress(1);
	}
}
