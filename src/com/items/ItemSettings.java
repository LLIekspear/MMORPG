package com.items;

import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemSettings {

	//Rarity: 0-base(white), 1-green, 2-blue, 3-yellow, 4-gold, 5-purple
	private static ChatColor getColor(int rarity) {
		if(rarity==0) {
			return ChatColor.WHITE;
		} else if(rarity==1) {
			return ChatColor.GREEN;
		} else if(rarity==2) {
			return ChatColor.BLUE;
		} else if(rarity==3) {
			return ChatColor.YELLOW;
		} else if(rarity==4) {
			return ChatColor.GOLD;
		} else if(rarity==5) {
			return ChatColor.LIGHT_PURPLE;
		}
		return ChatColor.WHITE;
	}
	
	public static ItemStack createCustomPotion(ItemStack item, String name, List<String> lore, int tradable, int rarity, int duration, int amplifier, List<PotionEffectType> effects) {
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.setDisplayName(name);
		if(tradable==0) {
			lore.add("");
			lore.add(ChatColor.GOLD+""+ChatColor.BOLD+"Нельзя передать");
		}
		lore.add("");
		meta.setLore(lore);
		for(int i=0; i<effects.size(); ++i) {
			meta.addCustomEffect(new PotionEffect(effects.get(i), duration, amplifier), true);
		}
		item.setItemMeta(meta);
		return item;
	}
	public static ItemStack createArtifact(ItemStack item, String name, List<String> lore, int tradable, int pvp, int pve, int physic, int magic, int crit, int health, int rarity, double speed) {
		ItemMeta meta=item.getItemMeta();
		AttributeModifier modifier_attack=new AttributeModifier(UUID.randomUUID(), "generic.attack_damage", physic, Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND);
		AttributeModifier modifier_health=new AttributeModifier(UUID.randomUUID(), "generic.max_health", health, Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND);
		AttributeModifier modifier_speed=new AttributeModifier(UUID.randomUUID(), "generic.movement_speed", speed, Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND);
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier_attack);
		meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, modifier_health);
		meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, modifier_speed);
		meta.setDisplayName(getColor(rarity)+""+ChatColor.BOLD+name);
		if(tradable==0) {
			lore.add("");
			lore.add(ChatColor.GOLD+""+ChatColor.BOLD+"Нельзя передать");
		}
		lore.add("");
		lore.add(ChatColor.WHITE+""+ChatColor.BOLD+"Атака: "+Integer.toString(physic));
		lore.add(ChatColor.WHITE+""+ChatColor.BOLD+"Физ. крит: "+Integer.toString(crit));
		lore.add(ChatColor.WHITE+""+ChatColor.BOLD+"HP: "+Integer.toString(health));
		if(pvp!=0) {
			lore.add(ChatColor.RED+""+ChatColor.BOLD+"Атака PVP: "+Integer.toString(pvp)+"%");
		}
		if(pve!=0) {
			lore.add(ChatColor.RED+""+ChatColor.BOLD+"Атака PVE: "+Integer.toString(pve)+"%");
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack createWeapon(ItemStack item, String name, List<String> lore, int tradable, int pvp, int pve, int physic, int magic, int speed, int crit, int rarity/*, int category*/) {
		ItemMeta meta=item.getItemMeta();
		meta.setDisplayName(getColor(rarity)+""+ChatColor.BOLD+name);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		AttributeModifier modifier_attack=new AttributeModifier(UUID.randomUUID(), "generic.attack_damage", physic, Operation.ADD_NUMBER, EquipmentSlot.HAND);
		AttributeModifier modifier_speed= new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", speed, Operation.ADD_NUMBER, EquipmentSlot.HAND);
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier_attack);
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, modifier_speed);
		meta.setUnbreakable(true);
		if(tradable==0) {
			lore.add("");
			lore.add(ChatColor.GOLD+""+ChatColor.BOLD+"Нельзя передать");
		}
		/*if(category==0) {
			lore.add(ChatColor.GOLD+""+ChatColor.BOLD+"Ткань");
		} else if(category==1) {
			lore.add(ChatColor.GOLD+""+ChatColor.BOLD+"Кожа");
		} else if(category==2) {
			lore.add(ChatColor.GOLD+""+ChatColor.BOLD+"Кольчуга");
		} else if(category==3) {
			lore.add(ChatColor.GOLD+""+ChatColor.BOLD+"Латы");
		}*/
		lore.add("");
		lore.add(ChatColor.WHITE+""+ChatColor.BOLD+"Атака: "+Integer.toString(physic));
		lore.add(ChatColor.WHITE+""+ChatColor.BOLD+"Сила магии: "+Integer.toString(magic));
		lore.add(ChatColor.WHITE+""+ChatColor.BOLD+"Скорость атаки: "+Integer.toString(speed)+"%");
		if(physic!=0) {
			lore.add(ChatColor.WHITE+""+ChatColor.BOLD+"Физ. крит: "+Integer.toString(crit));
		}
		if(magic!=0) {
			lore.add(ChatColor.WHITE+""+ChatColor.BOLD+"Маг. крит: "+Integer.toString(crit));
		}
		if(pvp!=0) {
			lore.add(ChatColor.RED+""+ChatColor.BOLD+"Атака PVP: "+Integer.toString(pvp)+"%");
		}
		if(pve!=0) {
			lore.add(ChatColor.RED+""+ChatColor.BOLD+"Атака PVE: "+Integer.toString(pve)+"%");
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack createArmor(ItemStack item, String name, List<String> lore, int tradable, int pvp, int pve, int physic, int magic, float speed, int rarity, int category, EquipmentSlot slot) {
		ItemMeta meta=item.getItemMeta();
		meta.setDisplayName(getColor(rarity)+""+ChatColor.BOLD+name);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		AttributeModifier modifier_armor=new AttributeModifier(UUID.randomUUID(), "generic.armor", physic, Operation.ADD_NUMBER, slot);
		AttributeModifier modifier_speed=new AttributeModifier(UUID.randomUUID(), "generic.movement_speed", (speed/100), Operation.ADD_NUMBER, slot);
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier_armor);
		meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, modifier_speed);
		meta.setUnbreakable(true);
		if(tradable==0) {
			lore.add("");
			lore.add(ChatColor.GOLD+""+ChatColor.BOLD+"Нельзя передать");
		}
		if(category==0) {
			lore.add(ChatColor.GOLD+""+ChatColor.BOLD+"Ткань");
		} else if(category==1) {
			lore.add(ChatColor.GOLD+""+ChatColor.BOLD+"Кожа");
		} else if(category==2) {
			lore.add(ChatColor.GOLD+""+ChatColor.BOLD+"Кольчуга");
		} else if(category==3) {
			lore.add(ChatColor.GOLD+""+ChatColor.BOLD+"Латы");
		}
		lore.add("");
		lore.add(ChatColor.WHITE+""+ChatColor.BOLD+"Физ. защита: "+Integer.toString(physic));
		lore.add(ChatColor.WHITE+""+ChatColor.BOLD+"Маг. защита: "+Integer.toString(magic));
		lore.add(ChatColor.WHITE+""+ChatColor.BOLD+"Скорость: "+Float.toString(speed)+"%");
		if(pvp!=0) {
			lore.add(ChatColor.RED+""+ChatColor.BOLD+"Защита PVP: "+Integer.toString(pvp)+"%");
		}
		if(pve!=0) {
			lore.add(ChatColor.RED+""+ChatColor.BOLD+"Защита PVE: "+Integer.toString(pve)+"%");
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public static ItemStack createItem(ItemStack item, String name, List<String> lore, int tradable, int rarity) {
		ItemMeta meta=item.getItemMeta();
		meta.setDisplayName(getColor(rarity)+""+ChatColor.BOLD+name);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		if(tradable==0) {
			lore.add("");
			lore.add(ChatColor.GOLD+""+ChatColor.BOLD+"Нельзя передать");
			meta.setLore(lore);
		} else if(tradable==1) {
			meta.setLore(lore);
		}
		item.setItemMeta(meta);
		return item;
	}
}
