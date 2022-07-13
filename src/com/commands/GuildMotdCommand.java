package com.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.data.PlayerConfigurator;
import com.main.Guild;
import com.main.PlayerData;
import com.main.PlayerDataPool;

public class GuildMotdCommand implements CommandExecutor, TabCompleter  {

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = (Player)sender;
		PlayerData playerData = PlayerDataPool.getPlayer(p);
		Guild playerGuild=PlayerConfigurator.getGuild(p);
		if(playerGuild!=null) {
			if(playerGuild.isGuildAdmin(p)) {
				String text="";
				for(int i=0; i<args.length; ++i) {
					text=text+args[i];
					if(i+1<args.length)
						text=text+" ";
				}
				playerGuild.setGuildDesc(p, text);
			} else {
				p.sendMessage(ChatColor.RED+"Вы должны быть легатом легиона!");
			}
		} else {
			p.sendMessage(ChatColor.RED+"Вы должны состоять в легионе!");			
		}
		return false;
	}
}
