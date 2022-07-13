package com.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import com.main.Group;
import com.main.PlayerData;
import com.main.PlayerDataPool;
import com.main.PlayerGroupPool;

public class GroupChatCommand implements CommandExecutor, TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p=(Player)sender;
		PlayerData playerData = PlayerDataPool.getPlayer(p);
		Group playerGroup=PlayerGroupPool.getGroup(playerData.getSelections().getGroupUuidString());
		if(playerData.getSelections().isInGroup()) {
			String text="";
				for(int i=0; i<args.length; ++i) {
					text=text+args[i];
					if(i+1<args.length)
						text=text+" ";
				}
				for(Player member : playerGroup.getPlayers()) {
					member.sendMessage(ChatColor.BLUE+"[Группа] "+p.getName()+": "+text);
				}
		} else {
			p.sendMessage(ChatColor.RED+"Вы должны состоять в группе!");
		}
		return false;
	}
}
