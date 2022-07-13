package com.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.data.PlayerConfigurator;
import com.main.Guild;
import com.utils.LInventory;
import com.utils.MenuUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class BannerBuilder implements LInventory {

	private ItemStack banner;
	private String guildUuidString;
	private String page;
	private int layers=0;
	
	public BannerBuilder() {
		
	}
	
	@Override
	public String getInventoryId( ) {
		return "banner-builder";
	}
	
	@Override
	public void openInstance(Player p) {
		BannerBuilder.open(p);
	}
	
	public static void open(Player p) {
		Guild playerGuild=PlayerConfigurator.getGuild(p);
		if(!GuildMenu.validateAdjutantAccess(p, playerGuild)) {
			return;
		}
		ItemStack bannerItemStack=new ItemStack(Material.WHITE_BANNER);
		ItemMeta bannerItemMeta=bannerItemStack.getItemMeta();
		bannerItemMeta.setDisplayName(ChatColor.GREEN+"Знамя "+playerGuild.getGuildName());
		bannerItemStack.setItemMeta(bannerItemMeta);
		open(p, new BannerBuilder(bannerItemStack, playerGuild.getGuildUuidString()));
	}
	
	public static void open(Player p, BannerBuilder bannerBuilder) {
		String menuTitle=ChatColor.BLACK+""+ChatColor.BOLD+"Редактор знамени";
		Inventory menu=Bukkit.createInventory(p, 9, menuTitle);
		bannerBuilder.page="просмотр";
		
		ItemStack saveItemStack=GenericIconHeads.getConfirmItem();
		ItemMeta saveItemMeta=saveItemStack.getItemMeta();
		saveItemMeta.setDisplayName(ChatColor.GREEN+"Сохранить знамя легиона");
		saveItemStack.setItemMeta(saveItemMeta);
		menu.setItem(0, saveItemStack);
		
		if(bannerBuilder.getLayers()<10) {
			ItemStack layerItemStack=new ItemStack(Material.DRIED_KELP);
			ItemMeta layerItemMeta=layerItemStack.getItemMeta();
			layerItemMeta.setDisplayName(ChatColor.GOLD+"Добавить новый слой");
			layerItemStack.setItemMeta(layerItemMeta);
			menu.setItem(3, layerItemStack);
		}
		menu.setItem(4, bannerBuilder.getBanner());
		
		if(bannerBuilder.getLayers()<10) {
			ItemStack colorItemStack=new ItemStack(Material.CYAN_DYE);
			ItemMeta colorItemMeta=colorItemStack.getItemMeta();
			colorItemMeta.setDisplayName(ChatColor.AQUA+"Изменить цвет");
			colorItemStack.setItemMeta(colorItemMeta);
			menu.setItem(5, colorItemStack);
		}
		ItemStack closeItemStack=GenericIconHeads.getDeclineItem();
		ItemMeta closeItemMeta=closeItemStack.getItemMeta();
		closeItemMeta.setDisplayName(ChatColor.RED+"Закрыть редактор знамени");
		closeItemStack.setItemMeta(closeItemMeta);
		menu.setItem(8, closeItemStack);
		
		MenuUtil.setBorders(menu);
		p.openInventory(menu);
	}
	
	public BannerBuilder(ItemStack banner, String guildUuidString) {
		this.banner=banner;
		this.guildUuidString=guildUuidString;
	}
	
	public ItemStack getBanner() {
		return banner;
	}
	
	public void setBanner(ItemStack banner) {
		this.banner=banner;
	}
	
	public String getGuildUuidString() {
		return guildUuidString;
	}
	
	public void setGuildUuidString(String guildUuidString) {
		this.guildUuidString=guildUuidString;
	}
	
	public int getLayers() {
		return layers;
	}
	/*public static void select(InventoryClickEvent e) {
		selectMenuPoint(e);
	}*/
	public void selectMenuPoint(InventoryClickEvent e) {
		e.setCancelled(true);
		ItemStack clicked=e.getCurrentItem();
		final Player p=(Player) e.getWhoClicked();
		if(clicked==null) {
			return;
		} else if(page.equals("просмотр")) {
			
		}
	}
}
