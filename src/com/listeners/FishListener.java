package com.listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

import net.md_5.bungee.api.ChatColor;

public class FishListener implements Listener {
	private ArrayList<String> cold_fish=Lists.newArrayList("Стерлядь", "Налим", "Щука", "Радужная форель", "Чукучан");
	private ArrayList<String> medium_fish=Lists.newArrayList("Сом", "Карп", "Щука", "Лещ", "Плотва");
	private ArrayList<String> warm_fish=Lists.newArrayList("Багариус", "Тор", "Веслонос", "Тилапия", "Пайяра");
	private ArrayList<String> ocean_fish=Lists.newArrayList("Тунец", "Луфарь", "Барракуда", "Дорадо", "Треска");
	private ArrayList<ItemStack> garbage=Lists.newArrayList(new ItemStack(Material.SEAGRASS), new ItemStack(Material.DEAD_BUSH), new ItemStack(Material.CHARCOAL), new ItemStack(Material.FLINT), new ItemStack(Material.SADDLE), new ItemStack(Material.SLIME_BALL), new ItemStack(Material.BONE), new ItemStack(Material.GOLD_NUGGET), new ItemStack(Material.IRON_NUGGET), new ItemStack(Material.NAUTILUS_SHELL), new ItemStack(Material.STICK));
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerFish(PlayerFishEvent e) {
		String fish_name="";
		if(e.getState()== PlayerFishEvent.State.CAUGHT_FISH) {
			Biome b=e.getPlayer().getLocation().getBlock().getBiome();
			Player p=e.getPlayer();
			Random rg=new Random();
			int randomInt=1;
			if(b==Biome.SNOWY_BEACH||b==Biome.FROZEN_RIVER||b==Biome.FROZEN_OCEAN||b==Biome.ICE_SPIKES||b==Biome.SNOWY_MOUNTAINS||b==Biome.TAIGA||b==Biome.SNOWY_TAIGA||b==Biome.SNOWY_TAIGA_HILLS||b==Biome.TAIGA_HILLS||b==Biome.SNOWY_TAIGA_MOUNTAINS) {
				int len=cold_fish.size();
				randomInt=rg.nextInt(len);
				fish_name=cold_fish.get(randomInt);
			}
			else if(b==Biome.BIRCH_FOREST||b==Biome.BIRCH_FOREST_HILLS||b==Biome.WOODED_HILLS||b==Biome.FOREST||b==Biome.BIRCH_FOREST_HILLS||b==Biome.PLAINS||b==Biome.RIVER||b==Biome.DARK_FOREST||b==Biome.DARK_FOREST_HILLS||b==Biome.RIVER) {
				int len=medium_fish.size();
				randomInt=rg.nextInt(len);
				fish_name=medium_fish.get(randomInt);
			}
			else if(b==Biome.JUNGLE||b==Biome.JUNGLE_EDGE||b==Biome.JUNGLE_HILLS||b==Biome.SAVANNA||b==Biome.SAVANNA_PLATEAU) {
				int len=warm_fish.size();
				randomInt=rg.nextInt(len);
				fish_name=warm_fish.get(randomInt);
			}
			else if(b==Biome.DEEP_OCEAN||b==Biome.OCEAN) {
				int len=ocean_fish.size();
				randomInt=rg.nextInt(len);
				fish_name=ocean_fish.get(randomInt);
			}
			else if(b==Biome.NETHER_WASTES) {
				e.setCancelled(true);
				p.sendMessage(ChatColor.YELLOW+""+ChatColor.BOLD+"Адовый карась или адовый окунь?)");
				return;
			}
			else if(b==Biome.MUSHROOM_FIELDS||b==Biome.MUSHROOM_FIELD_SHORE) {
				e.setCancelled(true);
				p.sendMessage(ChatColor.GRAY+""+ChatColor.BOLD+"Грибной сом или грибная пиявка?)");
				return;
			}
			/*else if(b==Biome.SKY) {
				p.sendMessage(ChatColor.AQUA+""+ChatColor.BOLD+"Летучих рыб ещё не завезли.");
				return;
			}*/
			else if(b==Biome.SWAMP||b==Biome.SWAMP_HILLS) {
				//мусор вытягивает
				int len=garbage.size();
				randomInt=rg.nextInt(len);
				e.getCaught().remove();
				p.getWorld().dropItem(p.getLocation(), garbage.get(randomInt));
				p.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"Болотный ротан и карп, что может быть лучше?");
				return;
			}
			
			if(randomInt!=1) {
				int weight=20;
				int randomWeight=rg.nextInt(weight);
				String Weight=Integer.toString(randomWeight);
			
				int size=120;
				int randomSize=rg.nextInt(size);
				String Size=Integer.toString(randomSize);
			
				ItemStack stack = null;
				stack=new ItemStack(Material.COD);
				ItemMeta meta=stack.getItemMeta();
				meta.setDisplayName(ChatColor.BOLD+""+ChatColor.BLUE+fish_name);
				meta.setLore(Lists.newArrayList(ChatColor.AQUA+""+ChatColor.BOLD+"Рыба", Size+" см", Weight+" кг"));
				stack.setItemMeta(meta);
				
				e.getCaught().remove();
				p.getWorld().dropItem(p.getLocation(), stack);
			}
			else {
				p.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Упс, что-то пошло не так... Сообщите в тех. поддержку!");
			}
		}
	}
}
