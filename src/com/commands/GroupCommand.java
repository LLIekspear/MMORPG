package com.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.menu.GroupMenu;

public class GroupCommand implements Listener, CommandExecutor, TabCompleter {
	private static final List<String> groupTabCompletes=Arrays.asList("group");
	//private Inventory menu=Bukkit.createInventory(null, 27, "AA");
	//private ItemStack agree = new ItemStack(Material.EMERALD_BLOCK);
	//private ItemStack disagree = new ItemStack(Material.REDSTONE_BLOCK);
	//private Group group=new Group();
	//private List<String> players=new ArrayList<>();
	//private HashMap<String, List<Integer>> players=new HashMap<String, List<Integer>>();

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p=(Player) sender;
		if(args[0].equalsIgnoreCase("menu")) {
			GroupMenu.open(p);
		}
		return false;
	}
}
