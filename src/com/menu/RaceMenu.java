package com.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.data.PlayerConfigurator;
import com.main.Race;
import com.utils.LInventory;
import com.utils.MenuUtil;

public class RaceMenu implements LInventory {

	@Override
	public String getInventoryId() {
		return "races";
	}
	
	@Override
	public void openInstance(Player p) {
		RaceMenu.open(p);
	}
	
	public static void open(Player p) {
		Race playerRace=PlayerConfigurator.getRace(p);
		if(playerRace==null) {
			openRaceList(p);
			return;
		}
		if(PlayerConfigurator.getRaceString(p).equalsIgnoreCase("banished")) {
			p.sendMessage(ChatColor.RED+"�� ���� ������� �� ����� ����! ������ ��� �� ��������, ������� �� �������� � �����-���� ����.");
			return;
		}
		String menuTitle=ChatColor.BLACK+""+ChatColor.BOLD+"���� ����";
		Inventory menu=Bukkit.createInventory(p,  54, menuTitle);
		ItemStack raceItemStack=new ItemStack(Material.CRAFTING_TABLE);
		//ItemStack raceItemStack=MenuIconHeads.getRaceItem();
		ItemMeta raceItemMeta=raceItemStack.getItemMeta();
		raceItemMeta.setDisplayName(ChatColor.GREEN+playerRace.getRaceName());
		List<String> raceLores=new ArrayList<String>();
		raceLores.add(ChatColor.DARK_PURPLE+"�����: "+ChatColor.YELLOW+Bukkit.getOfflinePlayer(UUID.fromString(playerRace.getAdminUuidString())).getName());
		raceLores.add(ChatColor.DARK_PURPLE+"���������: "+ChatColor.YELLOW+playerRace.getResidentsAmount());
		raceLores.add("");
		//for(String lore : playerRace.getWrappedRaceMotd()) {
		//	raceLores.add(ChatColor.GRAY+lore);
		//}
		for(String lore : playerRace.getWrappedRaceDesc()) {
			raceLores.add(ChatColor.GRAY+lore);
		}
		for(String lore : playerRace.getWrappedRaceMotd()) {
			raceLores.add(ChatColor.GRAY+"��������� ���: "+lore);
		}
		
		raceLores.add("");
		raceLores.add(ChatColor.DARK_PURPLE+"�������:");
		raceLores.add(ChatColor.GREEN+"/rc [�����]"+ChatColor.WHITE+" - ���������� ��������� � ��� ����.");
		raceLores.add(ChatColor.GREEN+"/rmotd [�����]"+ChatColor.WHITE+" - ������������� ��������� ��� ����.");
		raceLores.add("");
		raceLores.add(ChatColor.DARK_GRAY+"UUID: "+playerRace.getRaceUuidString());
		raceItemMeta.setLore(raceLores);
		raceItemStack.setItemMeta(raceItemMeta);
		menu.setItem(0, raceItemStack);
		
		List<OfflinePlayer> residents=playerRace.getResidentsUuidStrings().stream().map(uuid -> Bukkit.getOfflinePlayer(UUID.fromString(uuid))).filter(offlinePlayer -> offlinePlayer!=null).collect(Collectors.toList());
		//������ ����������
		int residentsNumber=0;
		for(int slot=19; slot<44; ++slot) {
			if(StringUtils.equalsAny(""+slot, "26", "27", "35", "36")) {
				continue;
			} else if(residentsNumber+1>playerRace.getResidentsAmount()) {
				menu.setItem(slot, new ItemStack(Material.EMERALD_BLOCK));
				++residentsNumber;
				continue;
			}
			//TODO ������� �� ����. �������� � �����������
			OfflinePlayer resident=residents.get(residentsNumber);
			menu.setItem(slot, getRaceResidentItemStack(p, playerRace, resident));
			++residentsNumber;
		}
		
		MenuUtil.setBorders(menu);
		p.openInventory(menu);
	}
	
	private static void openRaceList(Player p) {
		List<Race> playerRaces=Race.getRaces();
		int size=MenuUtil.roundInventorySize(playerRaces.size()+2);
		String menuTitle=ChatColor.BLACK+""+ChatColor.BOLD+"������ ���";
		Inventory menu=Bukkit.createInventory(p, size, menuTitle);
		
		int raceNumber=0;
		for(Race listedRace : playerRaces) {
			if(raceNumber>=size) {
				break;
			}
			ItemStack raceItemStack=listedRace.getRaceItem();
			ItemMeta raceItemMeta=raceItemStack.getItemMeta();
			raceItemMeta.setDisplayName(ChatColor.GREEN+listedRace.getRaceName());
			List<String> raceLores=new ArrayList<>();
			raceLores.add(ChatColor.GRAY+"�������, ����� ������� ����.");
			if(listedRace.getAdminUuidString()==null) {
				raceLores.add(ChatColor.DARK_PURPLE+"�����: "+ChatColor.YELLOW+"�����������");
			} else {
				raceLores.add(ChatColor.DARK_PURPLE+"�����: "+ChatColor.YELLOW+Bukkit.getOfflinePlayer(UUID.fromString(listedRace.getAdminUuidString())).getName());
			}
			raceLores.add(ChatColor.DARK_PURPLE+"���������: "+ChatColor.YELLOW+listedRace.getResidentsAmount());
			raceLores.add("");
			for(String lore : listedRace.getWrappedRaceDesc()) {
				raceLores.add(ChatColor.GRAY+lore);
			}
			raceLores.add("");
			raceLores.add(ChatColor.DARK_GRAY+"UUID: "+listedRace.getRaceUuidString());
			raceItemMeta.setLore(raceLores);
			raceItemStack.setItemMeta(raceItemMeta);
			menu.setItem(raceNumber, raceItemStack);
			++raceNumber;
		}
		
		MenuUtil.setBorders(menu);
		p.openInventory(menu);
	}
	
	private static ItemStack getRaceResidentItemStack(Player p, Race playerRace, OfflinePlayer resident) {
		boolean isRaceLeader=playerRace.isRaceAdmin(resident);
		boolean isRaceAdviser=playerRace.isRaceAdviser(resident);
		String name=resident.getName();
		if(isRaceLeader) {
			name=ChatColor.GOLD+"[�����] "+name;
		} else if(isRaceAdviser) {
			name=ChatColor.YELLOW+"[��������] "+name;
		} else {
			name=ChatColor.GREEN+"[��������] "+name;
		}
		
		ItemStack skullItemStack=new ItemStack(Material.PLAYER_HEAD);
		SkullMeta skullItemMeta=(SkullMeta) skullItemStack.getItemMeta();
		skullItemMeta.setDisplayName(name);
		skullItemMeta.setOwningPlayer(resident);
		List<String> skullLores=new ArrayList<String>();
		if(!p.getUniqueId().equals(resident.getUniqueId()) && !isRaceLeader) {
			if(playerRace.isRaceAdmin(p)) {
				if(isRaceAdviser) {
					//
					skullLores.add(ChatColor.GRAY+"��� ��� ��������� �� ���������");
				} else {
					skullLores.add(ChatColor.GRAY+"��� ��� ��������� �� ���������");
					skullLores.add(ChatColor.GRAY+"��� ��� �������� �� ����");	
				}
				skullLores.add("");
			} else if(playerRace.isRaceAdviser(p)&&!isRaceAdviser) {
				//
				skullLores.add("");
			}
		}
		skullItemMeta.setLore(skullLores);
		skullItemStack.setItemMeta(skullItemMeta);
		return skullItemStack;
	}
	
	public static boolean validateAdviserAccess(Player p, Race playerRace) {
		if(playerRace==null||playerRace.getRaceName().equalsIgnoreCase("none")) {
			p.sendMessage(ChatColor.RED+"�� �� �������� � ����!");
			p.closeInventory();
			return false;
		} else if(playerRace.getRaceName().equalsIgnoreCase("banished")) {
			p.sendMessage(ChatColor.RED+"�� ���� ������� �� ����! �� ������ ���������!");
			p.closeInventory();
			return false;
		}
		if(!playerRace.isRaceAdviser(p)) {
			p.sendMessage(ChatColor.RED+"�� �� ��������!");
			p.closeInventory();
			return false;
		}
		return true;
	}
}
