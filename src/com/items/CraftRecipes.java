package com.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import com.google.common.collect.Lists;
import com.main.Main;

public class CraftRecipes {
	private static Main plugin=Main.getInstance();

	@SuppressWarnings("deprecation")
	public void recipesInit() {
		ItemStack juice=new ItemStack(Material.POTION, 1);
		PotionMeta meta_juice=(PotionMeta) juice.getItemMeta();
		meta_juice.setColor(Color.RED);
		meta_juice.setDisplayName(ChatColor.RED+"Ягодный сок");
		List<String> juice_lore=new ArrayList<String>();
		juice_lore.add(ChatColor.GREEN+"Можно выпить.");
		juice_lore.add(ChatColor.GREEN+"Можно превратить в брагу.");
		meta_juice.setLore(juice_lore);
//		meta_juice.setBasePotionData(new PotionData(PotionType.REGEN, true, false));
		juice.setItemMeta(meta_juice);
		
		ItemStack braga=new ItemStack(Material.POTION, 1);
		PotionMeta meta_braga=(PotionMeta) braga.getItemMeta();
		meta_braga.setColor(Color.ORANGE);
		meta_braga.setDisplayName(ChatColor.RED+"Брага");
		List<String> braga_lore=new ArrayList<String>();
		braga_lore.add(ChatColor.GREEN+"Можно выпить.");
		braga_lore.add(ChatColor.GREEN+"Можно превратить в дистиллят.");
		braga_lore.add(ChatColor.GREEN+"Можно превратить в шнапс.");
		meta_braga.setLore(braga_lore);
		meta_braga.setBasePotionData(new PotionData(PotionType.REGEN, true, false));
		meta_braga.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 0), true);
		braga.setItemMeta(meta_braga);
		
		ItemStack dist=new ItemStack(Material.POTION, 1);
		PotionMeta meta_dist=(PotionMeta) dist.getItemMeta();
		meta_dist.setColor(Color.WHITE);
		meta_dist.setDisplayName(ChatColor.RED+"Дистиллят");
		List<String> dist_lore=new ArrayList<String>();
		dist_lore.add(ChatColor.GREEN+"Можно выпить.");
		dist_lore.add(ChatColor.GREEN+"Можно превратить в виски.");
		dist_lore.add(ChatColor.GREEN+"Можно превратить в водку.");
		dist_lore.add(ChatColor.RED+"Забористая штука.");
		meta_dist.setLore(dist_lore);
		meta_dist.setBasePotionData(new PotionData(PotionType.WEAKNESS, true, false));
		meta_dist.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 500, 3), true);
		dist.setItemMeta(meta_dist);
		
		//20 ticks = 1 second
		ItemStack shnaps=new ItemStack(Material.POTION, 1);
		PotionMeta meta_shnaps=(PotionMeta) shnaps.getItemMeta();
		meta_shnaps.setColor(Color.YELLOW);
		meta_shnaps.setDisplayName(ChatColor.RED+"Шнапс");
		List<String> shnaps_lore=new ArrayList<String>();
		shnaps_lore.add(ChatColor.GREEN+"Можно выпить.");
		shnaps_lore.add(ChatColor.RED+"Забористая штука.");
		meta_shnaps.setLore(shnaps_lore);
		meta_shnaps.setBasePotionData(new PotionData(PotionType.STRENGTH, true, false));
		meta_shnaps.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 500, 2), true);
		meta_shnaps.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 400, 1), true);
		shnaps.setItemMeta(meta_shnaps);
		
		ItemStack med=new ItemStack(Material.POTION, 1);
		PotionMeta meta_med=(PotionMeta) med.getItemMeta();
		meta_med.setColor(Color.YELLOW);
		meta_med.setDisplayName(ChatColor.RED+"Медовуха");
		List<String> med_lore=new ArrayList<String>();
		med_lore.add(ChatColor.GREEN+"Можно выпить.");
		med_lore.add(ChatColor.GREEN+"Можно первратить в самогон.");
		meta_med.setLore(med_lore);
		meta_med.setBasePotionData(new PotionData(PotionType.REGEN, true, false));
		meta_med.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 160, 0), true);
		med.setItemMeta(meta_med);
		
		ItemStack sam=new ItemStack(Material.POTION, 1);
		PotionMeta meta_sam=(PotionMeta) sam.getItemMeta();
		meta_sam.setColor(Color.WHITE);
		meta_sam.setDisplayName(ChatColor.RED+"Самогон");
		List<String> sam_lore=new ArrayList<String>();
		sam_lore.add(ChatColor.GREEN+"Можно выпить.");
		sam_lore.add(ChatColor.RED+"Забористая штука.");
		meta_sam.setLore(sam_lore);
		meta_sam.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE, true, false));
		meta_sam.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 0), true);
		meta_sam.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 500, 2), true);
		sam.setItemMeta(meta_sam);
		
		ItemStack juice1=new ItemStack(Material.POTION, 1);
		PotionMeta meta_juice1=(PotionMeta) juice1.getItemMeta();
		meta_juice1.setColor(Color.YELLOW);
		meta_juice1.setDisplayName(ChatColor.RED+"Яблочный сок");
		List<String> juice_lore1=new ArrayList<String>();
		juice_lore1.add(ChatColor.GREEN+"Можно выпить.");
		juice_lore1.add(ChatColor.GREEN+"Можно превратить в брагу.");
		meta_juice1.setLore(juice_lore1);
//		meta_juice.setBasePotionData(new PotionData(PotionType.REGEN, true, false));
		juice1.setItemMeta(meta_juice1);
		
		ItemStack ghost=ItemSettings.createItem(new ItemStack(Material.ENCHANTED_BOOK), "Подозрительная книга", Lists.newArrayList("При открытии отправляет в призрачную крепость"), 1, 5);
		
		NamespacedKey ghostKey=new NamespacedKey(plugin, "ghost");
		ShapedRecipe ghostRecipe=new ShapedRecipe(ghostKey, ghost);
		ghostRecipe.shape("RER", "PCP", "RER");
		ghostRecipe.setIngredient('C', Material.CLOCK);
		ghostRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
		ghostRecipe.setIngredient('E', Material.EMERALD);
		ghostRecipe.setIngredient('P', Material.ENDER_PEARL);
		
		NamespacedKey juiceKey=new NamespacedKey(plugin, "juice");
		ShapedRecipe juiceRecipe=new ShapedRecipe(juiceKey, juice);
		juiceRecipe.shape("BBB", "BBB", "BGB");
		juiceRecipe.setIngredient('B', Material.SWEET_BERRIES);
		juiceRecipe.setIngredient('G', Material.GLASS_BOTTLE);
		
		NamespacedKey juiceKey1=new NamespacedKey(plugin, "juice1");
		ShapedRecipe juiceRecipe1=new ShapedRecipe(juiceKey1, juice1);
		juiceRecipe1.shape("BBB", "BBB", "BGB");
		juiceRecipe1.setIngredient('B', Material.APPLE);
		juiceRecipe1.setIngredient('G', Material.GLASS_BOTTLE);
		
		NamespacedKey bragaKey=new NamespacedKey(plugin, "braga");
		ShapedRecipe bragaRecipe=new ShapedRecipe(bragaKey, braga);
		bragaRecipe.shape("DCD", "SSS", "JJJ");
		bragaRecipe.setIngredient('D', Material.COCOA_BEANS);
		bragaRecipe.setIngredient('C', Material.CLOCK);
		bragaRecipe.setIngredient('S', Material.SUGAR);
//		bragaRecipe.setIngredient('J', juice.getType());
		bragaRecipe.setIngredient('J', new RecipeChoice.ExactChoice(juice));
		
		NamespacedKey braga1Key=new NamespacedKey(plugin, "braga1");
		ShapedRecipe braga1Recipe=new ShapedRecipe(braga1Key, braga);
		braga1Recipe.shape("DCD", "SSS", "JJJ");
		braga1Recipe.setIngredient('D', Material.COCOA_BEANS);
		braga1Recipe.setIngredient('C', Material.CLOCK);
		braga1Recipe.setIngredient('S', Material.SUGAR);
		braga1Recipe.setIngredient('J', new RecipeChoice.ExactChoice(juice1));
		
		NamespacedKey medKey=new NamespacedKey(plugin, "med");
		ShapedRecipe medRecipe=new ShapedRecipe(medKey, med);
		medRecipe.shape("DCD", "SSS", "MMM");
		medRecipe.setIngredient('D', Material.COCOA_BEANS);
		medRecipe.setIngredient('C', Material.CLOCK);
		medRecipe.setIngredient('S', Material.SUGAR);
		medRecipe.setIngredient('M', Material.HONEY_BOTTLE);
		
		NamespacedKey samKey=new NamespacedKey(plugin, "sam");
		ShapedRecipe samRecipe=new ShapedRecipe(samKey, sam);
		samRecipe.shape("CCC", "DMD", "STS");
		samRecipe.setIngredient('D', Material.COCOA_BEANS);
		samRecipe.setIngredient('C', Material.CLOCK);
		samRecipe.setIngredient('S', Material.SUGAR);
		samRecipe.setIngredient('M', new RecipeChoice.ExactChoice(med));
		samRecipe.setIngredient('T', Material.TORCH);
		
		NamespacedKey distKey=new NamespacedKey(plugin, "dist");
		ShapedRecipe distRecipe=new ShapedRecipe(distKey, dist);
		distRecipe.shape("CCC", "WBW", "WTW");
		distRecipe.setIngredient('C', Material.CLOCK);
		distRecipe.setIngredient('W', Material.WHEAT);
		distRecipe.setIngredient('B', new RecipeChoice.ExactChoice(braga));
		distRecipe.setIngredient('T', Material.TORCH);
		
		NamespacedKey shnapsKey=new NamespacedKey(plugin, "shnaps");
		ShapedRecipe shnapsRecipe=new ShapedRecipe(shnapsKey, shnaps);
		shnapsRecipe.shape("CCC", "ABA", "ATA");
		shnapsRecipe.setIngredient('C', Material.CLOCK);
		shnapsRecipe.setIngredient('A', Material.APPLE);
		shnapsRecipe.setIngredient('B', new RecipeChoice.ExactChoice(braga));
		shnapsRecipe.setIngredient('T', Material.TORCH);
		
		//пиво, вино, виски, водка
		
		Bukkit.addRecipe(juiceRecipe);
		Bukkit.addRecipe(juiceRecipe1);
		Bukkit.addRecipe(bragaRecipe);
		Bukkit.addRecipe(braga1Recipe);
		Bukkit.addRecipe(medRecipe);
		Bukkit.addRecipe(samRecipe);
		Bukkit.addRecipe(distRecipe);
		Bukkit.addRecipe(shnapsRecipe);
		Bukkit.addRecipe(ghostRecipe);
	}
}
