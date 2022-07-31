package com.boss;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.main.Main;

public class MobGhost {

	private static Main plugin=Main.getInstance();
	
	@SuppressWarnings("deprecation")
	public void spawn(World world, Location loc) {
		
		Skeleton mob=world.spawn(loc.add(0.5, 0, 0.5), Skeleton.class);
		mob.setCustomName("§6§lÄóõ");
		mob.setCustomNameVisible(true);
		LivingEntity entity=(LivingEntity)mob;
		entity.setRemoveWhenFarAway(false);
		mob.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
		mob.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		mob.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		mob.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
		mob.setMetadata("GhostMob", new FixedMetadataValue(plugin, "ghostmob"));
		mob.setMaxHealth(mob.getMaxHealth()*3);
		mob.setHealth(mob.getMaxHealth());
		
	}
}
