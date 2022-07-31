package com.quest;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.main.Main;

public class QuestCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p=(Player)sender;
		if(sender instanceof Player && args[0].equalsIgnoreCase("open")&&p.hasPermission("lliekspear.quest_open")) {
			Inventory inv=Main.getInstance().getQuests().quest_menues.get(p.getName());
			p.openInventory(inv);
		} else {
			sender.sendMessage(ChatColor.RED+"Произошла ошибка на этапе открытия списка заданий. У Вас достаточно прав для этого действия?");
		}
		return true;
	}
}
