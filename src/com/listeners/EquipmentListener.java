package com.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.entity.EntityTargetEvent;

import com.inkzzz.spigot.armorevent.PlayerArmorEquipEvent;
import com.inkzzz.spigot.armorevent.PlayerArmorUnequipEvent;

public class EquipmentListener implements Listener {
	//TODO нормальный листенер брони + ограничения
	@EventHandler
	public void onArmorUnequip(PlayerArmorUnequipEvent e) {
		try {
			if(e.getItemStack().getItemMeta().getLore().get(0).equalsIgnoreCase(ChatColor.GREEN+"Тень забвения")) {
				e.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
				e.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
				e.getPlayer().removePotionEffect(PotionEffectType.SATURATION);
				e.getPlayer().sendMessage(ChatColor.RED+"Вы вновь ощущаете теплый воздух...");
			}
		} catch(Exception error) {
			//
		}
	}
	
	@EventHandler
	public void onArmorEquip(PlayerArmorEquipEvent e) {
		try {
			if(e.getPlayer().getInventory().getChestplate().getType().equals(Material.DIAMOND_CHESTPLATE))
				if(e.getPlayer().getInventory().getLeggings().getType().equals(Material.DIAMOND_LEGGINGS))
					if(e.getPlayer().getInventory().getHelmet().getType().equals(Material.DIAMOND_HELMET))
						if(e.getPlayer().getInventory().getBoots().getType().equals(Material.DIAMOND_BOOTS))
							e.getPlayer().sendMessage(ChatColor.GREEN+"Сила чувствуется...");
			if(e.getItemStack().getItemMeta().getLore().get(0).equalsIgnoreCase(ChatColor.GREEN+"Тень забвения"))
				if(e.getPlayer().getInventory().getHelmet().getItemMeta().getLore().get(0).equalsIgnoreCase(ChatColor.GREEN+"Тень забвения"))
					if(e.getPlayer().getInventory().getChestplate().getItemMeta().getLore().get(0).equalsIgnoreCase(ChatColor.GREEN+"Тень забвения"))
						if(e.getPlayer().getInventory().getLeggings().getItemMeta().getLore().get(0).equalsIgnoreCase(ChatColor.GREEN+"Тень забвения"))
							if(e.getPlayer().getInventory().getBoots().getItemMeta().getLore().get(0).equalsIgnoreCase(ChatColor.GREEN+"Тень забвения")) {
								e.getPlayer().sendMessage(ChatColor.GREEN+"Вы ощущаете прикосновение холодного ветра...");
								//add new effects, but need listeners for that
								e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
								e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
								e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0));
								
							}
		} catch(Exception error) {
			//
		}
//		if(PlayerConfigurator.getClass(e.getPlayer()).equalsIgnoreCase("Алхимик")||PlayerConfigurator.getClass(e.getPlayer()).equalsIgnoreCase("Маг")) {
//			if(e.getItemStack().getType().equals(Material.LEATHER_HELMET)||e.getItemStack().getType().equals(Material.LEATHER_CHESTPLATE)||e.getItemStack().getType().equals(Material.LEATHER_LEGGINGS)||e.getItemStack().getType().equals(Material.LEATHER_BOOTS)) {
//				
//			}
//		} else if(PlayerConfigurator.getClass(e.getPlayer()).equalsIgnoreCase("Убийца")||PlayerConfigurator.getClass(e.getPlayer()).equalsIgnoreCase("Стрелок")) {
//			
//		}
	}
	
	@EventHandler
	public void onLavaDamage(EntityDamageEvent e ) {
		if(e.getEntity().getType()==EntityType.PLAYER) {
			Player p=(Player)e.getEntity();
			if(e.getCause()==DamageCause.FIRE || e.getCause()==DamageCause.FIRE_TICK || e.getCause()==DamageCause.LAVA) {
				if(p.getInventory().getItemInOffHand().getItemMeta().getLore().get(0).equalsIgnoreCase("Необычный артефакт, защищает от лавы")) {
					e.setCancelled(true);
					p.setFireTicks(0);
//					p.sendMessage("Вас оберегает таинственный артефакт....");
				}
			}
		}
	}
	
	@EventHandler
	public void onExplosionDamage(EntityDamageEvent e) {
		if(e.getEntity().getType()==EntityType.PLAYER) {
			Player p=(Player)e.getEntity();
			if(e.getCause()==DamageCause.BLOCK_EXPLOSION || e.getCause()==DamageCause.ENTITY_EXPLOSION) {
				if(p.getInventory().getItemInOffHand().getItemMeta().getLore().get(0).equalsIgnoreCase("Необычный артефакт, защищает от взрывов")) {
					e.setDamage(0);
//					p.sendMessage("Вас оберегает таинственный артефакт...");
				}
			}
		}
	}
	
	@EventHandler
	public void onTarget(EntityTargetEvent e) {
		try {
			if(e.getTarget().getType()==EntityType.PLAYER) {
				Player p=(Player)e.getTarget();
				try {
					LivingEntity entity=(LivingEntity)e.getEntity();
					if(!p.getInventory().getItemInOffHand().getType().equals(Material.AIR)) {
						if(p.getInventory().getItemInOffHand().getItemMeta().getLore().get(0).equalsIgnoreCase("Необычный артефакт, существа менее агрессивны к носителю")) {
							if(entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).getValue()/2>=(e.getTarget().getLocation().distance(e.getEntity().getLocation()))) {
//								p.sendMessage("Сагрился, с артефактом, follow: "+Double.toString(entity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).getValue())+" distance: "+Double.toString(e.getTarget().getLocation().distance(e.getEntity().getLocation())));
							} else {
//								e.setCancelled(true);
								e.setTarget(null);
								e.setCancelled(true);
//								p.sendMessage("Захотел сагриться, спас артефакт");
							}
						} else {
//							p.sendMessage("Сагрился, без артефакта");
						}
					}
				} catch(Exception error) {
					error.printStackTrace();
				}
			}
		} catch(Exception error) {
			//
		}
	}
}
