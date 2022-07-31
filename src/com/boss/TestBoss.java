package com.boss;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.PigZombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.google.common.collect.Lists;
import com.items.ItemSettings;
import com.main.Main;

import io.netty.util.internal.ThreadLocalRandom;

public class TestBoss implements Listener {

	private static Main plugin=Main.getInstance();
	BossBar bb;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreakBlock(BlockBreakEvent e) {
		if(e.getBlock().getType()==Material.SPONGE) {
			bb=Bukkit.createBossBar("Владос", BarColor.RED, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
			e.setCancelled(true);
			e.getBlock().setType(Material.AIR);
			Skeleton skeleton=e.getBlock().getWorld().spawn(e.getBlock().getLocation().add(0.5, 0, 0.5), Skeleton.class);
			skeleton.setCustomName("§8§lВладос");
			skeleton.setCustomNameVisible(true);
			skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_HOE));
			skeleton.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
			skeleton.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
			skeleton.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
			skeleton.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));
			skeleton.setMetadata("Vlados", new FixedMetadataValue(plugin, "vlados"));
			skeleton.setMaxHealth(skeleton.getMaxHealth()*4);
			skeleton.setHealth(skeleton.getMaxHealth());
			
			bb.setProgress(1);
			bb.addPlayer(e.getPlayer());
			
			
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if((e.getEntity() instanceof Skeleton ||e.getEntity() instanceof PigZombie)&& e.getDamager() instanceof Player) {
			if(e.getEntity().hasMetadata("Vlados")||e.getEntity().hasMetadata("GhostBoss")||e.getEntity().hasMetadata("GhostElite")) {
				int rand=ThreadLocalRandom.current().nextInt(10);
				if(rand<5) {
					e.setCancelled(true);
					Player p=(Player)e.getDamager();
//					p.playSound(p.getLocation(), Sound.valueOf("ANVIL_LAND"), 10, 10);
					p.sendMessage(ChatColor.RED+"Атака была заблокирована.");
				}
//				bb.removePlayer((Player)e.getDamager());
				LivingEntity entity=(LivingEntity)e.getEntity();
				bb.setProgress(entity.getHealth()/entity.getMaxHealth());
//				bb.addPlayer((Player)e.getDamager());
			}
		}
		if(e.getDamager() instanceof Skeleton && e.getEntity() instanceof Player) {
			if(e.getDamager().hasMetadata("Vlados")||e.getEntity().hasMetadata("GhostBoss")) {
				int rand=ThreadLocalRandom.current().nextInt(10);
				if(rand<5) {
					e.setCancelled(true);
					Player p=(Player)e.getEntity();
					p.setVelocity(new Vector(0,2,0));
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 2));
					p.sendMessage(ChatColor.RED+"Вас подбросили вверх!");
				}
			}
		}
	}
	
	@EventHandler
	public void onBossKilled(EntityDeathEvent e) {
		if(e.getEntity() instanceof Skeleton && (e.getEntity().hasMetadata("Vlados")||e.getEntity().hasMetadata("GhostBoss"))) {
			ItemStack goldMedal=new ItemStack(Material.GOLD_NUGGET);
			ItemMeta goldMeta=goldMedal.getItemMeta();
			goldMeta.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"Золотая медаль");
			goldMeta.setLore(Lists.newArrayList(ChatColor.AQUA+""+ChatColor.BOLD+"Драгоценная медаль"));
			goldMedal.setItemMeta(goldMeta);
			
			int rand=ThreadLocalRandom.current().nextInt(100);
			
			e.getDrops().clear();
			if(rand<=5)
				e.getDrops().add(ItemSettings.createArmor(new ItemStack(Material.CHAINMAIL_HELMET), "Маска тени", Lists.newArrayList(ChatColor.GREEN+"Тень забвения"), 1, 0, 3, 7, 7, 0, 4, 2, EquipmentSlot.HEAD));
			rand=ThreadLocalRandom.current().nextInt(100);
			if(rand<=3)
				ItemSettings.createArmor(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "Мантия тени", Lists.newArrayList(ChatColor.GREEN+"Тень забвения"), 1, 0, 5, 15, 7, 0, 4, 2, EquipmentSlot.CHEST);
			rand=ThreadLocalRandom.current().nextInt(100);
			if(rand<=4)
				ItemSettings.createArmor(new ItemStack(Material.CHAINMAIL_LEGGINGS), "Витки тени", Lists.newArrayList(ChatColor.GREEN+"Тень забвения"), 1, 0, 4, 9, 7, 0, 4, 2, EquipmentSlot.LEGS);
			rand=ThreadLocalRandom.current().nextInt(100);
			if(rand<=5)
				ItemSettings.createArmor(new ItemStack(Material.CHAINMAIL_BOOTS), "Сапоги тени", Lists.newArrayList(ChatColor.GREEN+"Тень забвения"), 1, 0, 3, 7, 7, 12, 4, 2, EquipmentSlot.FEET);
			e.getDrops().add(new ItemStack(Material.EMERALD));
			e.getDrops().add(ItemSettings.createArtifact(new ItemStack(Material.BLUE_DYE), "Вихрь", Lists.newArrayList("Необычный артефакт, увеличивающий скорость передвижения носителя"), 0, 4, 4, 6, 0, 20, 5, 5, 0.2));
			e.getDrops().add(ItemSettings.createArtifact(new ItemStack(Material.RED_DYE), "Барьер", Lists.newArrayList("Необычный артефакт, защищает от взрывов"), 0, 4, 4, 6, 0, 20, 5, 5, 0));
			e.getDrops().add(ItemSettings.createArtifact(new ItemStack(Material.ORANGE_DYE), "Огонёк", Lists.newArrayList("Необычный артефакт, защищает от лавы"), 0, 4, 4, 6, 0, 20, 5, 5, 0));
			e.getDrops().add(ItemSettings.createArtifact(new ItemStack(Material.WHITE_DYE), "Мантия", Lists.newArrayList("Необычный артефакт, существа менее агрессивны к носителю"), 0, 4, 4, 6, 0, 20, 5, 5, 0));
			e.getDrops().add(goldMedal);
			e.getDrops().add(goldMedal);
			Player p=(Player)e.getEntity().getKiller();
			p.giveExp(100);
			p.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"Поздравляем! Вы уничтожили босса!");
//			p.playSound(p.getLocation(), Sound.valueOf("ANVIL_LAND"), 10, 10);
			Bukkit.broadcastMessage(ChatColor.GOLD+"Босс был уничтожен "+p.getName()+"!");
			bb.removeAll();
			if(e.getEntity().hasMetadata("GhostBoss")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						new GhostBoss().spawnBoss(Bukkit.getServer().getWorld("plugins/MMORPG/Dungeons/GhostCastle"), new Location(Bukkit.getServer().getWorld("plugins/MMORPG/Dungeons/GhostCastle"), 91, 70, 79));
						Bukkit.broadcastMessage(ChatColor.AQUA+"Лорд Владмарк вновь восстал из пустоты!");
					}
				}.runTaskLater(plugin, 432000);
			}
		} else if(e.getEntity() instanceof PigZombie && e.getEntity().hasMetadata("GhostElite")) {
			e.getDrops().clear();
			e.getDrops().add(new ItemStack(Material.DIAMOND));
			Player p=(Player)e.getEntity().getKiller();
			p.giveExp(70);
			p.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"Поздравляем! Вы уничтожили элитного моба!");
			new BukkitRunnable() {
				@Override
				public void run() {
					new EliteGhost().spawn(Bukkit.getServer().getWorld("plugins/MMORPG/Dungeons/GhostCastle"), new Location(Bukkit.getServer().getWorld("plugins/MMORPG/Dungeons/GhostCastle"), 127, 64, 52));
				}
			}.runTaskLater(plugin, 144000);
		} else if(e.getEntity() instanceof Skeleton && e.getEntity().hasMetadata("GhostMob")) {
			e.getDrops().clear();
			e.getDrops().add(new ItemStack(Material.GOLD_INGOT));
			new BukkitRunnable() {
				@Override
				public void run() {
					List<Location> locs=new ArrayList<Location>();
					locs.add(new Location(Bukkit.getServer().getWorld("plugins/MMORPG/Dungeons/GhostCastle"), 132, 64, 78));
					locs.add(new Location(Bukkit.getServer().getWorld("plugins/MMORPG/Dungeons/GhostCastle"), 124, 64, 65));
					locs.add(new Location(Bukkit.getServer().getWorld("plugins/MMORPG/Dungeons/GhostCastle"), 140, 64, 29));
					locs.add(new Location(Bukkit.getServer().getWorld("plugins/MMORPG/Dungeons/GhostCastle"), 115, 64, 23));
					locs.add(new Location(Bukkit.getServer().getWorld("plugins/MMORPG/Dungeons/GhostCastle"), 124, 63, -5));
					locs.add(new Location(Bukkit.getServer().getWorld("plugins/MMORPG/Dungeons/GhostCastle"), 108, 65, -10));
					locs.add(new Location(Bukkit.getServer().getWorld("plugins/MMORPG/Dungeons/GhostCastle"), 126, 64, -45));
					locs.add(new Location(Bukkit.getServer().getWorld("plugins/MMORPG/Dungeons/GhostCastle"), 131, 64, -71));
					int rand=ThreadLocalRandom.current().nextInt(locs.size());
					new MobGhost().spawn(Bukkit.getServer().getWorld("plugins/MMORPG/Dungeons/GhostCastle"), locs.get(rand));
				}
			}.runTaskLater(plugin, 6000);
		}
	}
}
