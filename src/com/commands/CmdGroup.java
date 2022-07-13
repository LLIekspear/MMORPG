package com.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.menu.GroupMenu;

public class CmdGroup implements LCommand {
	
	@Override
	public List<String> getCommandIds() {
		return Arrays.asList("group");
	}
	
	@Override
	public boolean executeCommand(CommandSender sender, String[] args) {
		Player p=(Player) sender;
		GroupMenu.open(p);
		return true;
	}

}
