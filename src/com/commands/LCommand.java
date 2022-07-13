package com.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

public interface LCommand {

	public List<String> getCommandIds();
	
	public default boolean allowConsoleExecution() {
		return false;
	}
	
	public boolean executeCommand(CommandSender sender, String[] args);
}
