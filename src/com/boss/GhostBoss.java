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
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.main.Main;

public class GhostBoss {
	
	private static Main plugin=Main.getInstance();
	BossBar bb;
	
	@SuppressWarnings("deprecation")
	public void spawnBoss(World world, Location loc) {
		bb=Bukkit.createBossBar("Лорд Владмарк", BarColor.RED, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
		
		Skeleton skeleton=world.spawn(loc.add(0.5, 0, 0.5), Skeleton.class);
		skeleton.setCustomName("§8§lЛорд Владмарк");
		skeleton.setCustomNameVisible(true);
		LivingEntity entity=(LivingEntity)skeleton;
		entity.setRemoveWhenFarAway(false);
		skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_HOE));
		skeleton.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
		skeleton.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
		skeleton.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
		skeleton.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));
		skeleton.setMetadata("GhostBoss", new FixedMetadataValue(plugin, "ghostboss"));
		skeleton.setMaxHealth(skeleton.getMaxHealth()*4);
		skeleton.setHealth(skeleton.getMaxHealth());
		
		bb.setProgress(1);
	}
}
