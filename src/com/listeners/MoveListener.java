package com.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MoveListener implements Listener {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		try {
			Material m=e.getPlayer().getLocation().getBlock().getType();
			if(m==Material.LEGACY_STATIONARY_WATER||m==Material.WATER) {
				if(e.getPlayer().getLocation().getY()>60&&(e.getPlayer().getLocation().getBlock().getBiome()==Biome.SWAMP||e.getPlayer().getLocation().getBlock().getBiome()==Biome.SWAMP_HILLS)) {
					if(!e.getPlayer().hasPotionEffect(PotionEffectType.SLOW))
						e.getPlayer().sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Болото Вас затягивает...");
					e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 4), true);
				}
			}
			if(e.getPlayer().getLocation().getY()>60&&(e.getPlayer().getLocation().getBlock().getBiome()==Biome.DESERT||e.getPlayer().getLocation().getBlock().getBiome()==Biome.DESERT_HILLS)) {
				if(e.getPlayer().getInventory().getHelmet()==null) {
					if(!e.getPlayer().hasPotionEffect(PotionEffectType.CONFUSION))
						e.getPlayer().sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Солнце припекает, не помешал бы головной убор...");
					e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 120, 3), true);
				}
			}
			if(e.getPlayer().getLocation().getY()>60&&(e.getPlayer().getLocation().getBlock().getBiome()==Biome.MOUNTAIN_EDGE||e.getPlayer().getLocation().getBlock().getBiome()==Biome.SNOWY_BEACH||e.getPlayer().getLocation().getBlock().getBiome()==Biome.SNOWY_MOUNTAINS||e.getPlayer().getLocation().getBlock().getBiome()==Biome.SNOWY_TAIGA||e.getPlayer().getLocation().getBlock().getBiome()==Biome.SNOWY_TAIGA_HILLS||e.getPlayer().getLocation().getBlock().getBiome()==Biome.SNOWY_TAIGA_MOUNTAINS||e.getPlayer().getLocation().getBlock().getBiome()==Biome.SNOWY_TUNDRA)) {
				if(e.getPlayer().getWorld().hasStorm()) {
					if(!e.getPlayer().hasPotionEffect(PotionEffectType.BLINDNESS))
						e.getPlayer().sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Началась метель, лучше её переждать...");
					e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW,  120, 4), true);
					e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,  120, 1), true);
				}
			}
		} catch(Exception error) {
			//
		}
	}
}