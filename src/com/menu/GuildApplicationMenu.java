package com.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.data.PlayerConfigurator;
import com.main.Guild;
import com.utils.LInventory;
import com.utils.MenuUtil;

public class GuildApplicationMenu implements LInventory {

	@Override
	public String getInventoryId() {
		return "applications";
	}
	
	@Override
	public void openInstance(Player p) {
		GuildApplicationMenu.open(p);
	}
	
	public static void open(Player p) {
		Guild playerGuild=PlayerConfigurator.getGuild(p);
		if(!GuildMenu.validateAdjutantAccess(p, playerGuild)) {
			return;
		}
		String menuTitle=ChatColor.BLACK+""+ChatColor.BOLD+"Заявки в легион";
		Inventory menu=Bukkit.createInventory(p, 27, menuTitle);
		List<OfflinePlayer> applicants=playerGuild.getApplicantUuidStrings().stream().map(uuid -> Bukkit.getOfflinePlayer(UUID.fromString(uuid))).filter(offlinePlayer -> offlinePlayer!=null).collect(Collectors.toList());
		for(int slot=0; slot<27&&slot<applicants.size();++slot) {
			OfflinePlayer applicant=applicants.get(slot);
			ItemStack skullItemStack=new ItemStack(Material.PLAYER_HEAD);
			SkullMeta skullItemMeta=(SkullMeta) skullItemStack.getItemMeta();
			skullItemMeta.setDisplayName(ChatColor.LIGHT_PURPLE+applicant.getName());
			skullItemMeta.setOwningPlayer(applicant);
			List<String> skullLores=new ArrayList<String>();
			skullLores.add(ChatColor.GRAY+"ЛКМ для принятия, "+"Свободные места: "+(playerGuild.getMaxLegionnaireAmount()-playerGuild.getLegionnaireAmount()));
			skullLores.add(ChatColor.GRAY+"ПКМ для отказа");
			skullLores.add("");
			skullLores.add(ChatColor.GRAY+"Последний онлайн: "+(applicant.isOnline() ? ChatColor.GREEN+"Сейчас" : ChatColor.BLUE+"TODO"+" назад"));
			skullItemMeta.setLore(skullLores);
			skullItemStack.setItemMeta(skullItemMeta);
			menu.setItem(slot, skullItemStack);
		}
		MenuUtil.setBorders(menu);
		p.openInventory(menu);
	}
}
