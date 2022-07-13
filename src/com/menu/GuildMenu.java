package com.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.data.PlayerConfigurator;
import com.main.Guild;
import com.utils.LInventory;
import com.utils.MenuUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.StringUtils;

public class GuildMenu implements LInventory {

	@Override
	public String getInventoryId() {
		return "guilds";
	}
	
	@Override
	public void openInstance(Player p) {
		GuildMenu.open(p);
	}
	
	public static void open(Player p) {
		Guild playerGuild=PlayerConfigurator.getGuild(p);
		if(playerGuild==null||PlayerConfigurator.getGuildString(p).equalsIgnoreCase("none")) {
			openGuildList(p);
			return;
		}
		String menuTitle=ChatColor.BLACK+""+ChatColor.BOLD+"������";
		//Inventory menu=Bukkit.createInventory(new GuildMenu(), 54, menuTitle);
		Inventory menu=Bukkit.createInventory(p, 54, menuTitle);
		ItemStack guildItemStack=MenuIconHeads.getGuildItem();
		ItemMeta guildItemMeta=guildItemStack.getItemMeta();
		guildItemMeta.setDisplayName(ChatColor.GREEN+playerGuild.getGuildName());
		List<String> guildLores=new ArrayList<String>();
		guildLores.add(ChatColor.DARK_PURPLE+"�����: "+ChatColor.YELLOW+Bukkit.getOfflinePlayer(UUID.fromString(playerGuild.getAdminUuidString())).getName());
		guildLores.add(ChatColor.DARK_PURPLE+"���������: "+ChatColor.YELLOW+playerGuild.getLegionnaireAmount()+"/"+playerGuild.getMaxLegionnaireAmount());
		guildLores.add("");
		for(String lore : playerGuild.getWrappedGuildDesc()) {
			guildLores.add(ChatColor.GRAY+lore);
		}
		guildLores.add("");
		guildLores.add(ChatColor.DARK_PURPLE+"�������:");
		guildLores.add(ChatColor.GREEN+"/lc [�����]"+ChatColor.WHITE+" - ���������� ��������� � ��� �������.");
		guildLores.add(ChatColor.GREEN+"/lmotd [�����]"+ChatColor.WHITE+" - ������������� ��������� ��� �������.");
		guildLores.add("");
		guildLores.add(ChatColor.DARK_GRAY+"UUID: "+playerGuild.getGuildUuidString());
		guildItemMeta.setLore(guildLores);
		guildItemStack.setItemMeta(guildItemMeta);
		menu.setItem(0, guildItemStack);
		
		ItemStack bannerItemStack=playerGuild.getGuildBanner();
		ItemMeta bannerItemMeta=bannerItemStack.getItemMeta();
		bannerItemMeta.setDisplayName(ChatColor.YELLOW+"�������� ����� �������");
		List<String> bannerLores=new ArrayList<String>();
		if(((BannerMeta) bannerItemMeta).getPatterns().size()>1) {
			bannerLores.add("");
		}
		bannerLores.add(ChatColor.GRAY+"���, ����� �������� (������ ��� ����������)");
		bannerItemStack.setItemMeta(bannerItemMeta);
		menu.setItem(1, bannerItemStack);
		
		//loadGuildBuildings(menu, playerGuild);
		
		ItemStack applicationsItemStack=playerGuild.getGuildBanner();
		ItemMeta applicationsItemMeta=applicationsItemStack.getItemMeta();
		applicationsItemMeta.setDisplayName(ChatColor.YELLOW+"����������� ������");
		List<String> applicationsLores=new ArrayList<String>();
		if(((BannerMeta) applicationsItemMeta).getPatterns().size()>1) {
			applicationsLores.add("");
		}
		applicationsLores.add(ChatColor.GRAY+"������� ��� ��������� "+ChatColor.LIGHT_PURPLE+playerGuild.getApplocationCount()+ChatColor.GRAY+" ������ (������ ��� ����������)");
		applicationsItemMeta.setLore(applicationsLores);
		applicationsItemStack.setItemMeta(applicationsItemMeta);
		menu.setItem(7, applicationsItemStack);
		
		ItemStack leaveItemStack=new ItemStack(Material.BARRIER);
		ItemMeta leaveItemMeta=leaveItemStack.getItemMeta();
		leaveItemMeta.setDisplayName(ChatColor.RED+"�������� ������");
		leaveItemStack.setItemMeta(leaveItemMeta);
		menu.setItem(8, leaveItemStack);
		
		List<OfflinePlayer> legionnaires=playerGuild.getLegionnairesUuidStrings().stream().map(uuid -> Bukkit.getOfflinePlayer(UUID.fromString(uuid))).filter(offlinePlayer -> offlinePlayer!=null).collect(Collectors.toList());
		ItemStack freeSlotItemStack = GenericIconHeads.getConfirmItem();
		ItemMeta freeSlotItemMeta=freeSlotItemStack.getItemMeta();
		freeSlotItemMeta.setDisplayName(ChatColor.DARK_GREEN+"��������� �����");
		freeSlotItemStack.setItemMeta(freeSlotItemMeta);
		
		ItemStack lockedSlotItemStack = GenericIconHeads.getDeclineItem();
		ItemMeta lockedSlotItemMeta=lockedSlotItemStack.getItemMeta();
		lockedSlotItemMeta.setDisplayName(ChatColor.DARK_RED+"������� �����");
		lockedSlotItemStack.setItemMeta(lockedSlotItemMeta);
		
		int legionnairesNumber=0;
		for(int slot=19; slot<44; ++slot) {
			if(StringUtils.equalsAny(""+slot, "26", "27", "35", "36")) {
				continue;
			} else if(legionnairesNumber+1>playerGuild.getMaxLegionnaireAmount()) {
				menu.setItem(slot, lockedSlotItemStack);
				++legionnairesNumber;
				continue;
			} else if(legionnairesNumber+1>playerGuild.getLegionnaireAmount()) {
				menu.setItem(slot, freeSlotItemStack);
				++legionnairesNumber;
				continue;
			}
			OfflinePlayer legionnaire=legionnaires.get(legionnairesNumber);
			menu.setItem(slot, getGuildLegionnaireItemStack(p, playerGuild, legionnaire));
			++legionnairesNumber;
		}
		
		MenuUtil.setBorders(menu);
		p.openInventory(menu);
	}
	
	private static void openGuildList(Player p) {
		List<Guild> playerGuilds=Guild.getGuilds();
		int size=MenuUtil.roundInventorySize(playerGuilds.size()+2);
		String menuTitle=ChatColor.BLACK+""+ChatColor.BOLD+"������ ��������";
		//Inventory menu=Bukkit.createInventory(new GuildMenu(), size, menuTitle);
		Inventory menu=Bukkit.createInventory(p, size, menuTitle);
		
		ItemStack createItemStack=MenuIconHeads.getGuildItem();
		ItemMeta createItemMeta=createItemStack.getItemMeta();
		createItemMeta.setDisplayName(ChatColor.BLUE+"������� ������");
		List<String> createLores=new ArrayList<String>();
		createLores.add(ChatColor.GRAY+"������� ��� ������� � ���, ����� ������� ������!");
		createLores.add(ChatColor.GRAY+"��� ��������� � 500 �����! �� ������: 4000(��������) �����");
		createLores.add("");
		createLores.add(ChatColor.BLUE+"/legion [��������]");
		createLores.add("");
		createLores.add(ChatColor.GRAY+"�� �� ������� �������� �������� � ����������.");
		createLores.add(ChatColor.GRAY+"������: /legion ViAntares");
		createItemMeta.setLore(createLores);
		createItemStack.setItemMeta(createItemMeta);
		menu.setItem(0,  createItemStack);
		
		ItemStack bannerItemStack=new ItemStack(Material.GREEN_BANNER);
		ItemMeta bannerItemMeta=bannerItemStack.getItemMeta();
		bannerItemMeta.setDisplayName(ChatColor.YELLOW+"������� �����");
		bannerItemStack.setItemMeta(bannerItemMeta);
		menu.setItem(1, bannerItemStack);
		
		int guildNumber=2;
		for(Guild listedGuild : playerGuilds) {
			if(guildNumber>=size) {
				break;
			}
			ItemStack guildItemStack=listedGuild.getGuildBanner();
			ItemMeta guildItemMeta=guildItemStack.getItemMeta();
			guildItemMeta.setDisplayName(ChatColor.GREEN+listedGuild.getGuildName());
			List<String> guildLores=new ArrayList<>();
			if(((BannerMeta) guildItemMeta).getPatterns().size()>1) {
				guildLores.add("");
			}
			guildLores.add(ChatColor.GRAY+"�������, ����� ������� ��� ������� ��� � ���.");
			guildLores.add(ChatColor.GREEN+"/apply "+listedGuild.getGuildName());
			guildLores.add("");
			guildLores.add(ChatColor.DARK_PURPLE+"�����: "+ChatColor.YELLOW+Bukkit.getOfflinePlayer(UUID.fromString(listedGuild.getAdminUuidString())).getName());
			guildLores.add(ChatColor.DARK_PURPLE+"���������: "+ChatColor.YELLOW+listedGuild.getLegionnaireAmount()+"/"+listedGuild.getMaxLegionnaireAmount());
			guildLores.add("");
			for(String lore : listedGuild.getWrappedGuildDesc()) {
				guildLores.add(ChatColor.GRAY+lore);
			}
			guildLores.add("");
			guildLores.add(ChatColor.DARK_GRAY+"UUID: "+listedGuild.getGuildUuidString());
			guildItemMeta.setLore(guildLores);
			guildItemStack.setItemMeta(guildItemMeta);
			menu.setItem(guildNumber, guildItemStack);
			++guildNumber;
		}
		
		MenuUtil.setBorders(menu);
		p.openInventory(menu);
	}
	
	private static ItemStack getGuildLegionnaireItemStack(Player p, Guild playerGuild, OfflinePlayer legionnaire) {
		boolean isGuildLegate=playerGuild.isGuildAdmin(legionnaire);
		boolean isGuildAdjutant=playerGuild.isGuildAdjutant(legionnaire);
		String name=legionnaire.getName();
		if(isGuildLegate) {
			name=ChatColor.GOLD+name+" [�����]";
		} else if(isGuildAdjutant) {
			name=ChatColor.YELLOW+name+" [��������]";
		} else {
			name=ChatColor.GREEN+name+" [��������]";
		}
		
		ItemStack skullItemStack=new ItemStack(Material.PLAYER_HEAD);
		SkullMeta skullItemMeta=(SkullMeta) skullItemStack.getItemMeta();
		skullItemMeta.setDisplayName(name);
		skullItemMeta.setOwningPlayer(legionnaire);
		List<String> skullLores=new ArrayList<String>();
		if(!p.getUniqueId().equals(legionnaire.getUniqueId()) && !isGuildLegate) {
			if(playerGuild.isGuildAdmin(p)) {
				if(isGuildAdjutant) {
					skullLores.add(ChatColor.GRAY+"��� ��� ��������� �� ������");
					skullLores.add(ChatColor.GRAY+"��� ��� ��������� �� ���������");
				} else {
					skullLores.add(ChatColor.GRAY+"��� ��� ��������� �� ���������");
					skullLores.add(ChatColor.GRAY+"��� ��� ���������� �� �������");
				}
				skullLores.add("");
			} else if(playerGuild.isGuildAdjutant(p)&&!isGuildAdjutant) {
				skullLores.add(ChatColor.GRAY+"��� ��� ���������� �� �������");
				skullLores.add("");
			}
		}
		skullItemMeta.setLore(skullLores);
		skullItemStack.setItemMeta(skullItemMeta);
		return skullItemStack;
	}
	
	//private static void loadGuildBuildings(Inventory menu, Guild playerGuild)
	public static boolean validateAdjutantAccess(Player p, Guild playerGuild) {
		if(playerGuild==null) {
			p.sendMessage(ChatColor.RED+"�� �� �������� � �������!");
			p.closeInventory();
			return false;
		}
		if(!playerGuild.isGuildAdjutant(p)) {
			p.sendMessage(ChatColor.RED+"�� �� ��������!");
			p.closeInventory();
			return false;
		}
		return true;
	}
}
