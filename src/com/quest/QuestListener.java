package com.quest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.data.PlayerConfigurator;
import com.main.Main;
import com.main.PlayerData;
import com.main.PlayerDataPool;
import com.rep.Reputation;

public class QuestListener implements Listener {

	private static Main plugin=Main.getInstance();
	public List<Quest> quests=new ArrayList<Quest>();
	public HashMap<String, Inventory> quest_menues=new HashMap<String, Inventory>();
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		try {
			if(e.getView().getTitle().contains("Список заданий ")) {
				e.setCancelled(true);
			}
		} catch(Exception error) {
			//
		}
	}
	
	@EventHandler
	public void onDeathEntity(EntityDeathEvent e) {
		try {
			if(e.getEntity().getKiller().getType().equals(EntityType.PLAYER)) {
				Player killer=(Player)e.getEntity().getKiller();
				if(e.getEntity().getType().equals(EntityType.CREEPER)) {
					updateProgress(3, 1, killer);
				} else if(e.getEntity().hasMetadata("GhostMob")) {
					updateProgress(0, 1, killer);
				} else if(e.getEntity().hasMetadata("GhostElite")) {
					updateProgress(1, 1, killer);
				} else if(e.getEntity().hasMetadata("GhostBoss")) {
					updateProgress(2, 1, killer);
				}
			}
		} catch(Exception error) {
			//
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		try {
			if(e.getEntity().getKiller().getType().equals(EntityType.PLAYER)) {
				Player killer=(Player)e.getEntity().getKiller();
				Player killed=e.getEntity();
				int amount=Reputation.getKillReward(PlayerConfigurator.getHeroRep(killer), PlayerConfigurator.getHeroRep(killed));
				PlayerConfigurator.setHeroRep(killer, PlayerConfigurator.getHeroRep(killer)+amount);
				killer.sendMessage(ChatColor.GREEN+"Вы получили "+Integer.toString(amount)+" очков героя.");
				if(PlayerConfigurator.getHeroRep(killed)-amount>=0) {
					PlayerConfigurator.setHeroRep(killed, PlayerConfigurator.getHeroRep(killed)-amount);
					killed.sendMessage(ChatColor.RED+"Вы потеряли "+Integer.toString(amount)+" очков героя.");
				} else {
					PlayerConfigurator.setHeroRep(killed, 0);
					killed.sendMessage(ChatColor.RED+"Вы потеряли "+Integer.toString(amount)+" очков героя.");
				}
				updateProgress(5, 1, killer);
				//смотрим репу и квест
			}
		} catch(Exception error) {
			//
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		try {
			if(e.getBlock().getType().equals(Material.EMERALD_ORE)) {
				Player p=e.getPlayer();
				updateProgress(4, 1, p);
			}
		} catch(Exception error) {
			//
		}
	}
	
	public void setQuestList(Player p) {
		Inventory inv=Bukkit.createInventory(p, 9, "Список заданий "+p.getName());
		for(int i=0; i<quests.size(); ++i) {
			ItemStack quest=new ItemStack(Material.ENCHANTED_BOOK);
			ItemMeta quest_meta=quest.getItemMeta();
			quest_meta.setDisplayName(ChatColor.AQUA+quests.get(i).getName());
			List<String> lore=new ArrayList<String>();
			lore.add("ID: "+Integer.toString(quests.get(i).getId()));
			if(quests.get(i).getId()==5) {
				lore.add(ChatColor.GREEN+quests.get(i).getDesc()+Reputation.getStringRank(p));
			} else {
				lore.add(ChatColor.GREEN+quests.get(i).getDesc());
			}
			lore.add("Прогресс: 0");
			lore.add("Надо: "+quests.get(i).getNeed());
			quest_meta.setLore(lore);
			quest.setItemMeta(quest_meta);
			inv.addItem(quest);
			quest_menues.put(p.getName(), inv);
		}
	}
	
	public void updateProgress(int id, int progress, Player p) {
//		p.sendMessage("called method!");
		Inventory inv=quest_menues.get(p.getName());
		for(int i=0; i<quests.size(); ++i) {
			if(inv.getItem(i).getItemMeta().getLore().get(0).replace("ID: ", "").equalsIgnoreCase(Integer.toString(id))) {
				ItemStack quest=inv.getItem(i);
				if(quest.getItemMeta().getLore().get(2).equalsIgnoreCase("Выполнено!")) {
					break;
				}
				int progressNow=Integer.parseInt(quest.getItemMeta().getLore().get(2).replace("Прогресс: ", ""));
				int max=Integer.parseInt(quest.getItemMeta().getLore().get(3).replace("Надо: ", ""));
				boolean isCompleted=false;
				if((progressNow+progress)==max)
					isCompleted=true;
				if(isCompleted) {
					ItemMeta meta=quest.getItemMeta();
					List<String> lore=new ArrayList<String>();
					lore.add("ID: "+Integer.toString(id));
					lore.add(meta.getLore().get(1));
					lore.add("Выполнено!");
					meta.setLore(lore);
					quest.setItemMeta(meta);
					inv.setItem(i, quest);
					giveReward(p, id);
				} else {
					ItemMeta meta=quest.getItemMeta();
					List<String> lore=new ArrayList<String>();
					lore.add("ID: "+Integer.toString(id));
					lore.add(meta.getLore().get(1));
					lore.add("Прогресс: "+Integer.toString(progressNow+1));
					lore.add("Надо: "+Integer.toString(max));
					meta.setLore(lore);
					quest.setItemMeta(meta);
//					p.sendMessage("Op, fixed");
					inv.setItem(i, quest);
				}
			}
		}
	}
	
	public void giveReward(Player p, int id) {
		for(int i=0; i<quests.size(); ++i) {
			if(quests.get(i).getId()==id) {
				for(int j=0; j<quests.get(i).getRewards().size(); ++j) {
					p.getInventory().addItem(quests.get(i).getRewards().get(j));
				}
				p.giveExp(quests.get(i).getExp());
				int rep=PlayerConfigurator.getHeroRep(p);
				PlayerConfigurator.setHeroRep(p, rep+quests.get(i).getRep());
				p.sendMessage(ChatColor.GREEN+"Вы выполнили задание! Получено опыта: "+Integer.toString(quests.get(i).getExp())+". Получено очков героя: "+quests.get(i).getRep()+".");
			}
		}
		//+выдача экспы и репы
	}
	
	public void getQuests() {
		try {
			File directory=new File("plugins/MMORPG", "/Quests/");
			for(int i=0; i<directory.listFiles().length; ++i) {
				File dataFile=directory.listFiles()[i];
				FileConfiguration dataConfig=YamlConfiguration.loadConfiguration(dataFile);
				int type=dataConfig.getInt("type");
				int id=dataConfig.getInt("id");
				String name=dataConfig.getString("name");
				String desc=dataConfig.getString("desc");
				int need=dataConfig.getInt("need");
				int rep=dataConfig.getInt("rep");
				int exp=dataConfig.getInt("exp");
				int amount=dataConfig.getInt("items");
				List<ItemStack> reward=new ArrayList<ItemStack>();
				ByteArrayInputStream inputStream=new ByteArrayInputStream(Base64Coder.decodeLines(dataConfig.getString("reward")));
				BukkitObjectInputStream dataInput=new BukkitObjectInputStream(inputStream);
				for(int j=0; j<amount; ++j) {
					reward.add((ItemStack)dataInput.readObject());
				}
				dataInput.close();
				Quest quest=new Quest(type, id, name, desc, reward, need, rep, exp);
				quests.add(quest);
			}
		} catch(Exception error) {
			error.printStackTrace();
		}
	}
}
