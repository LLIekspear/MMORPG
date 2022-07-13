package com.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.main.Guild;
import com.menu.GuildMenu;

public class GuildCommand implements CommandExecutor, TabCompleter {
	
	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p=(Player) sender;
		if(args[0].equalsIgnoreCase("menu")) {
			GuildMenu.open(p);
		} else {
			Guild.createGuild((Player) sender, /*StringUtils.join(" ")*/args[0]);
		}
		return false;
	}
}
