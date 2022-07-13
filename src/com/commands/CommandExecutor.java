package com.commands;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;

public class CommandExecutor {

	private static Map<String, LCommand> commandMap=new HashMap<>();
	
	public static LCommand getCommand(String commandId) {
		return commandMap.get(commandId);
	}
	
	public static void regCommand(LCommand cmd) {
		for(String commandId : cmd.getCommandIds()) {
			commandMap.put(commandId.toLowerCase(), cmd);
		}
	}
	
	public static boolean execute(CommandSender sender, Command cmd, String[] args) {
		LCommand command=getCommand(cmd.getName().toLowerCase());
		if(command==null) {
			return false;
		}
		if(sender instanceof Player) {
			//отправлять лог
		}
		else if(!command.allowConsoleExecution()) {
			sender.sendMessage(ChatColor.RED+"Только игроки могут выполнять эту команду!");
			return true;
		}
		try {
			return command.executeCommand(sender,  args);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
