package com.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.main.Group;
import com.main.Main;
import com.main.PlayerData;
import com.main.PlayerDataPool;
import com.main.PlayerGroupPool;
import com.utils.LInventory;
import com.utils.MenuUtil;

import org.bukkit.ChatColor;

//TODO ПЕРЕДЕЛАТЬ НА ПКМ И ЛКМ УДАЛЕНИЕ И ПОВЫШЕНИЕ В ГРУППЕ!
public class GroupMenu implements LInventory {
	@Override
	public String getInventoryId() {
		return "groups";
	}
	
	@Override
	public void openInstance(Player p) {
		GroupMenu.open(p);
	}
	
	public static void open(Player p) {
		Inventory menu;
		PlayerData playerData = PlayerDataPool.getPlayer(p);
		if(playerData.getSelections().isInGroup()) {
			p.sendMessage("Состоит в группе!");
		}
		else {
			p.sendMessage("Не состоит в группе!");
		}
		if(playerData.getSelections().isInGroup()) {
			String menuTitle=ChatColor.BLACK+""+ChatColor.BOLD+"Группа";
			menu=Bukkit.createInventory(p, 9, menuTitle);
			//menu=Components.inventory(new GroupMenu(), menuTitle, 9);
			Group playerGroup=PlayerGroupPool.getGroup(playerData.getSelections().getGroupUuidString());
			
			ItemStack groupItemStack=MenuIconHeads.getGroupItem();
			setGroupItemMeta(groupItemStack, playerGroup, true);
			menu.setItem(0, groupItemStack);
			
			int playerNumber=1;
			for(Player member : playerGroup.getPlayers()) {
				boolean isGroupLeader=playerGroup.isGroupAdmin(member);
				String name=isGroupLeader
						? ChatColor.GOLD+member.getName()+" [Лидер]"
						: ChatColor.YELLOW+member.getName()+" [Участник]";
				ItemStack skullItemStack=new ItemStack(Material.PLAYER_HEAD);
				SkullMeta skullItemMeta=(SkullMeta) skullItemStack.getItemMeta();
				skullItemMeta.setDisplayName(name);
				skullItemMeta.setOwningPlayer(Bukkit.getOfflinePlayer(member.getUniqueId()));
				//skullItemMeta.setOwningPlayer(member);
				List<String> skullLores=new ArrayList<String>();
				//skullLores.add(ChatColor.GRAY+"Нажмите для телепортации!");
				skullItemMeta.setLore(skullLores);
				skullItemStack.setItemMeta(skullItemMeta);
				menu.setItem(playerNumber, skullItemStack);
				++playerNumber;
			}
			if(playerGroup.isGroupAdmin(p)) {
				ItemStack promoteItemStack = new ItemStack(Material.GOLDEN_HELMET);
				ItemMeta promoteItemMeta=promoteItemStack.getItemMeta();
				promoteItemMeta.setDisplayName(ChatColor.RED+"Сделать лидером группы");
				promoteItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				promoteItemStack.setItemMeta(promoteItemMeta);
				menu.setItem(6, promoteItemStack);
				
				ItemStack kickItemStack=new ItemStack(Material.IRON_BOOTS);
				ItemMeta kickItemMeta=kickItemStack.getItemMeta();
				kickItemMeta.setDisplayName(ChatColor.RED+"Исключить из группы");
				kickItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				kickItemStack.setItemMeta(kickItemMeta);
				menu.setItem(7, kickItemStack);
			}
			
			ItemStack leaveItemStack = new ItemStack(Material.BARRIER);
			ItemMeta leaveItemMeta=leaveItemStack.getItemMeta();
			leaveItemMeta.setDisplayName(ChatColor.RED+"Покинуть группу");
			leaveItemStack.setItemMeta(leaveItemMeta);
			menu.setItem(8, leaveItemStack);
		} else {
			List<Group> groups=PlayerGroupPool.getGroups();
			int size=MenuUtil.roundInventorySize(groups.size()+2);
			String menuTitle=ChatColor.BLACK+""+ChatColor.BOLD+"Поиск группы";
			menu=Bukkit.createInventory(p, size, menuTitle);
			//menu=Components.inventory(new GroupMenu(), menuTitle, size);
			
			ItemStack createItemStack = new ItemStack(Material.LIGHT_BLUE_CONCRETE);
			ItemMeta createItemMeta = createItemStack.getItemMeta();
			createItemMeta.setDisplayName(ChatColor.BLUE + "Создать открытую группу");
			createItemStack.setItemMeta(createItemMeta);
			menu.setItem(0, createItemStack);
			
			ItemStack createWithPasswordItemStack = new ItemStack(Material.LIGHT_BLUE_CONCRETE);
			ItemMeta createWithPasswordItemMeta = createWithPasswordItemStack.getItemMeta();
			createWithPasswordItemMeta.setDisplayName(ChatColor.YELLOW + "Создать закрытую группу (с паролем)");
			createWithPasswordItemStack.setItemMeta(createWithPasswordItemMeta);
			menu.setItem(1, createWithPasswordItemStack);
			
			int groupNumber=2;
			for(Group playerGroup : groups) {
				if(groupNumber>=size) {
					break;
				}
				ItemStack groupItemStack = new ItemStack(playerGroup.isFull() ? Material.BLACK_CONCRETE :
					(playerGroup.isPasswordProtected() ? Material.YELLOW_CONCRETE : Material.LIME_CONCRETE));
				setGroupItemMeta(groupItemStack, playerGroup, false);
				menu.setItem(groupNumber, groupItemStack);
				++groupNumber;
			}
		}
		MenuUtil.setBorders(menu);
		p.openInventory(menu);
	}
	
	public static void setGroupItemMeta(ItemStack groupItemStack, Group playerGroup, boolean insideGroup) {
		ItemMeta groupItemMeta = groupItemStack.getItemMeta();
		groupItemMeta.setDisplayName(ChatColor.BLUE + "Группа");
		List<String> lores = new ArrayList<String>();
		lores.add(ChatColor.DARK_PURPLE + "Лидер: " + ChatColor.YELLOW
				+ Bukkit.getPlayer(UUID.fromString(playerGroup.getAdminUuidString())).getName());
		lores.add(ChatColor.DARK_PURPLE + "Игроки: " + ChatColor.YELLOW
				+ playerGroup.getPlayerAmount() + " / 5");
		lores.add("");
		for(String lore : playerGroup.getWrappedGroupDescription()) {
			lores.add(ChatColor.GRAY + lore);
		}
		lores.add("");
		if(insideGroup) {
			lores.add(playerGroup.isPasswordProtected() ? ChatColor.RED + "Пароль: "
					+ playerGroup.getGroupPassword() : ChatColor.GREEN + "Пароля нет");
			lores.add("");
			lores.add(ChatColor.DARK_PURPLE + "Команды:");
			lores.add(ChatColor.YELLOW + "/" + ChatColor.WHITE + "group menu " + ChatColor.GRAY + "Открывает меню группы");
			lores.add(ChatColor.YELLOW + "/" + ChatColor.WHITE + "grc [текст] " + ChatColor.GRAY + "Отправляет сообщение в групповой чат");
			lores.add(ChatColor.GOLD + "/" + ChatColor.WHITE + "gdesc [текст] " + ChatColor.GRAY + "Изменяет сообщение группы");
		}
		else {
			lores.add(playerGroup.isPasswordProtected() ? ChatColor.RED
					+ "Пароль" : ChatColor.GREEN + "Пароля нет");
		}
		lores.add("");
		lores.add(ChatColor.DARK_GRAY + "UUID: " + playerGroup.getGroupUuidString());
		groupItemMeta.setLore(lores);
		groupItemStack.setItemMeta(groupItemMeta);
	}

	public static void passwordInput(Player p, String groupUuidString, String passwordString) {
		String menuTitle=ChatColor.BLACK+""+ChatColor.BOLD+"Пароль группы: " + ChatColor.DARK_RED+passwordString;
		Inventory menu=Bukkit.createInventory(p, 9, menuTitle);
		
		int slot=0;
		while(slot<9) {
			ItemStack passwordNumberItemStack=new ItemStack(Material.GRAY_CONCRETE);
			ItemMeta passwordNumberItemMeta = passwordNumberItemStack.getItemMeta();
			passwordNumberItemMeta.setDisplayName(ChatColor.RESET+""+(slot+1));
			if(groupUuidString!=null) {
				List<String> lores=new ArrayList<String>();
				lores.add("");
				lores.add(ChatColor.DARK_GRAY + "UUID: " + groupUuidString);
				passwordNumberItemMeta.setLore(lores);
			}
			passwordNumberItemStack.setItemMeta(passwordNumberItemMeta);
			menu.setItem(slot, passwordNumberItemStack);
			++slot;
		}
		p.openInventory(menu);
	}
	
	public static void playerSelection(Player p, boolean promote) {
		String menuTitle=ChatColor.BLACK+""+ChatColor.BOLD+"Группа: "+ (promote ? "Повысить" : "Исключить");
		Inventory menu=Bukkit.createInventory(p, 9, menuTitle);
		PlayerData playerData=PlayerDataPool.getPlayer(p);
		Group playerGroup=PlayerGroupPool.getGroup(playerData.getSelections().getGroupUuidString());
		int playerNumber=1;
		for(Player member : playerGroup.getPlayers()) {
			if(playerGroup.isGroupAdmin(member)) {
				continue;
			}
			ItemStack skullItemStack=new ItemStack(Material.PLAYER_HEAD);
			SkullMeta skullItemMeta =(SkullMeta) skullItemStack.getItemMeta();
			skullItemMeta.setDisplayName(ChatColor.YELLOW+member.getName());
			skullItemMeta.setOwningPlayer(member);
			skullItemStack.setItemMeta(skullItemMeta);
			menu.setItem(playerNumber, skullItemStack);
			playerNumber+=2;
		}
		MenuUtil.setBorders(menu);
		p.openInventory(menu);
	}
	
	/*@Override
	public void selectMenuPoint(InventoryClickEvent e) {
		e.setCancelled(true);
		ItemStack clicked=e.getCurrentItem();
		final Player p=(Player)e.getWhoClicked();
		if(clicked==null) {
			return;
		}
		//create group
		else if(clicked.getType().equals(Material.LIGHT_BLUE_CONCRETE) ) {
			if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.BLUE+"Создать открытую группу")) {
				Group playerGroup = new Group(p);
				PlayerData playerData=PlayerDataPool.getPlayer(p);
				playerData.getSelections().setGroupUuidString(playerGroup.getGroupUuidString());
				PlayerGroupPool.regGroup(playerGroup);
				open(p);
			} else {
				passwordInput(p, null, "_");
			}
		}
		//join password group
		else if(clicked.getType().equals(Material.YELLOW_CONCRETE)) {
			String groupUuidString=ItemUtils.getStringFromLore(clicked, "UUID", 1);
			//лог
			passwordInput(p, groupUuidString, "_");
		}
		//join open group
		else if(clicked.getType().equals(Material.GREEN_CONCRETE)) {
			String groupUuidString = ItemUtils.getStringFromLore(clicked, "UUID", 1);
			Group playerGroup = PlayerGroupPool.getGroup(groupUuidString);
			PlayerData playerData =PlayerDataPool.getPlayer(p);
			playerGroup.addPlayer(p);
			playerData.getSelections().setGroupUuidString(groupUuidString);
			open(p);
		}
		//password input
		else if(clicked.getType().equals(Material.GRAY_CONCRETE)) {
			String passwordString=p.getOpenInventory().getTitle().split(" ")[2].replace(""+ChatColor.DARK_RED, "");
			passwordString+=clicked.getItemMeta().getDisplayName().replace(""+ChatColor.RESET, "");
			if(ItemUtils.hasLore(clicked)) {
				String groupUuidString=ItemUtils.getStringFromLore(clicked, "UUID", 1);
				if(passwordString.length()==5) {
					Group playerGroup=PlayerGroupPool.getGroup(groupUuidString);
					if(passwordString.equals(playerGroup.getGroupPassword())) {
						PlayerData playerData=PlayerDataPool.getPlayer(p);
						playerGroup.addPlayer(p);
						playerData.getSelections().setGroupUuidString(groupUuidString);
						open(p);
					} else {
						p.sendMessage(ChatColor.RED+"Неправильный пароль!");
						p.closeInventory();
						return;
					}
				} else {
					passwordInput(p, groupUuidString, passwordString);
				}
			} else {
				//лог с паролем и его длинной
				if(passwordString.length()==5) {
					Group playerGroup=new Group(p);
					PlayerData playerData=PlayerDataPool.getPlayer(p);
					playerData.getSelections().setGroupUuidString(playerGroup.getGroupUuidString());
					playerGroup.setGroupPassword(passwordString);
					PlayerGroupPool.regGroup(playerGroup);
					open(p);
				} else {
					passwordInput(p, null, passwordString);
				}
			}
		} else {
			handlePlayerInteractions(p, clicked);
		}
	}*/
	
	public static void handlePlayerInteractions(Player p, ItemStack clicked) {
		if(clicked.getType().equals(Material.PLAYER_HEAD)) {
			SkullMeta sm=(SkullMeta) clicked.getItemMeta();
			//p.sendMessage("SM: "+sm.getOwningPlayer().getName()+"/"+sm.getDisplayName());
			//sm.setOwningPlayer(Bukkit.getPlayer(sm.getDisplayName()));
			if(sm.getOwningPlayer()==null) {
				return;
			}
			Player target=Main.getOnlinePlayer((sm.getOwningPlayer().getName()));
			if(target==null||p.equals(target)||PlayerDataPool.getPlayer(target)==null) {
				return;
			}
			String playerGroupUuid=PlayerDataPool.getPlayer(p).getSelections().getGroupUuidString();
			String targetGroupUuid=PlayerDataPool.getPlayer(target).getSelections().getGroupUuidString();
			if(!StringUtils.equals(playerGroupUuid, targetGroupUuid)) {
				return;
			}
			String inventoryName=p.getOpenInventory().getTitle();
			if(/*inventoryName.equalsIgnoreCase("Группа: Повысить")*//*inventoryName.contains("Повысить")*/inventoryName.contains(ChatColor.BLACK+""+ChatColor.BOLD+"Группа: Повысить")) {
				promotePlayer(p, target);
			} else if(/*inventoryName.equalsIgnoreCase("Группа: Исключить")*//*inventoryName.contains("Исключить")*/inventoryName.contains(ChatColor.BLACK+""+ChatColor.BOLD+"Группа: Исключить")) {
				kickPlayer(p, target);
			} else {
				//Teleporter.playerTeleportManual(p, target);
			}
		} else if(clicked.getType().equals(Material.GOLDEN_HELMET)) {
			playerSelection(p, true);
		} else if(clicked.getType().equals(Material.IRON_BOOTS)) {
			playerSelection(p, false);
		} else if(clicked.getType().equals(Material.BARRIER)) {
			PlayerData playerData=PlayerDataPool.getPlayer(p);
			Group playerGroup=PlayerGroupPool.getGroup(playerData.getSelections().getGroupUuidString());
			if(playerGroup!=null) {
				playerGroup.removePlayer(p);
				playerData.getSelections().setGroupUuidString(null);
				open(p);
			} else {
				p.closeInventory();
			}
		}
	}
	
	private static void promotePlayer(Player p, Player target) {
		PlayerData playerData=PlayerDataPool.getPlayer(target);
		Group playerGroup=PlayerGroupPool.getGroup(playerData.getSelections().getGroupUuidString());
		if(playerGroup!=null) {
			playerGroup.setAdminUuidString(target.getUniqueId().toString());
			for(Player member : playerGroup.getPlayers()) {
				member.sendMessage(ChatColor.GREEN+target.getName()+" теперь лидер группы.");
			}
			open(p);
		} else {
			p.closeInventory();
		}
	}
	
	private static void kickPlayer(Player p, Player target) {
		PlayerData playerData=PlayerDataPool.getPlayer(target);
		Group playerGroup=PlayerGroupPool.getGroup(playerData.getSelections().getGroupUuidString());
		if(playerGroup!=null) {
			playerGroup.removePlayer(target);
			playerData.getSelections().setGroupUuidString(null);
			target.sendMessage(ChatColor.RED+"Вас исключили из группы.");
			for(Player member : playerGroup.getPlayers()) {
				member.sendMessage(ChatColor.RED+target.getName()+" был исключен из группы.");
			}
			open(p);
		} else {
			p.closeInventory();
		}
	}
}
