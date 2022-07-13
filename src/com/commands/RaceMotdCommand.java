package com.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.data.PlayerConfigurator;
import com.main.PlayerData;
import com.main.PlayerDataPool;
import com.main.Race;

public class RaceMotdCommand implements CommandExecutor, TabCompleter  {

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p=(Player) sender;
		PlayerData playerData=PlayerDataPool.getPlayer(p);
		Race playerRace=PlayerConfigurator.getRace(p);
		if(playerRace!=null&&!playerRace.getRaceName().equalsIgnoreCase("none")&&!playerRace.getRaceName().equalsIgnoreCase("banished")) {
			if(playerRace.isRaceAdmin(p)||playerRace.isRaceAdviser(p)) {
				String text="";
				for(int i=0; i<args.length; ++i) {
					text=text+args[i];
					if(i+1<args.length)
						text=text+" ";
				}
				playerRace.setRaceMotd(p, text);
			} else {
				p.sendMessage(ChatColor.RED+"Вы должны быть советником или лидером расы!");
			}
		} else {
			p.sendMessage(ChatColor.RED+"Вы должны состоять в расе!");
		}
		return false;
	}

}
