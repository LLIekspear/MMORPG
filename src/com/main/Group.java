package com.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.utils.UnicodeUtils;

public class Group {

	private HashMap<Integer, List<String>> groups=new HashMap<Integer, List<String>>();
	
	public Group(Player leader) {
		groupUuidString = UUID.randomUUID().toString();
		adminUuidString=leader.getUniqueId().toString();
		players.add(leader);
		
		//10.03.2022 тестим теги внутри группы //СДЕЛАТЬ ОЧИСТКУ И УДАЛЕНИЕ ГРУПП!!!!!
		Scoreboard score=Bukkit.getScoreboardManager().getMainScoreboard();
		Team t=score.getTeam("group-"+leader.getName());
		if(t==null) {
			t=score.registerNewTeam("group-"+leader.getName());
			t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
			t.setAllowFriendlyFire(false);
			t.setColor(ChatColor.BLUE);
			t.addEntry(leader.getName());
		} else {
			t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
			t.setAllowFriendlyFire(false);
			t.setColor(ChatColor.BLUE);
			t.addEntry(leader.getName());
		}
	}
	
	String groupUuidString;
	String adminUuidString;
	List<Player> players = new ArrayList<>();
	String groupPassword;
	String groupDescription="Без описания";
	
	public String getGroupUuidString() {
		return groupUuidString;
	}
	
	public void setGroupUuidString(String groupUuidString) {
		this.groupUuidString=groupUuidString;
	}
	
	public String getAdminUuidString() {
		return adminUuidString;
	}
	
	public void setAdminUuidString(String adminUuidString) {
		this.adminUuidString=adminUuidString;
	}
	
	public boolean isGroupAdmin(Player player) {
		return adminUuidString.equals(player.getUniqueId().toString());
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public void setPlayers(List<Player> players) {
		this.players=players;
	}
	
	public int getPlayerAmount( ) {
		return players.size();
	}
	
	public boolean isFull() {
		return getPlayerAmount()>=5;
	}
	
	public boolean isEmpty() {
		return getPlayerAmount()==0;
	}
	
	public void addPlayer(Player player) {
		players.add(player);
		for(Player member : players) {
			member.sendMessage(ChatColor.GREEN + player.getName()+" присоединился к группе.");
		}
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
		Scoreboard score=Bukkit.getScoreboardManager().getMainScoreboard();
		Team t=score.getTeam("nhide");
		t.addEntry(player.getName());
		player.sendMessage(ChatColor.RED+"Вы покинули группу!");
		for(Player member : players) {
			member.sendMessage(ChatColor.RED+player.getName()+" покинул группу!");
		}
		if(isEmpty()) {
			PlayerGroupPool.unregGroup(groupUuidString);
		} else if(isGroupAdmin(player)) {
			Player leader=players.get(0);
			adminUuidString=leader.getUniqueId().toString();
			for(Player member : players) {
				member.sendMessage(ChatColor.GREEN + leader.getName()+" теперь лидер группы!");
			}
		}
	}
	
	public String getGroupPassword() {
		return groupPassword;
	}
	
	public void setGroupPassword(String groupPassword) {
		this.groupPassword=groupPassword;
	}
	
	public boolean isPasswordProtected() {
		return StringUtils.isNotBlank(groupPassword);
	}
	
	public String getGroupDescription() {
		return groupDescription;
	}
	
	public void setGroupDescription(String groupDescription) {
		this.groupDescription=groupDescription;
	}
	
	public List<String> getWrappedGroupDescription() {
		return UnicodeUtils.wrapText(groupDescription);
	}
	
	public void setGroupDescription(Player p, String groupDescription) {
		this.groupDescription=groupDescription;
		for(Player member : players) {
			member.sendMessage(ChatColor.GREEN+p.getName()+" изменил сообщение группы на: "+groupDescription);
		}
	}
	/*public int getGroup(String name) {
		try {
			for(Map.Entry<Integer, List<String>> entry : groups.entrySet()) {
				for(int i=0; i<entry.getValue().size(); ++i) {
					if(entry.getValue().get(i)==name) {
						return entry.getKey();
					}
				}
			}
			return 0;
		} catch(Exception e) {
			//вывод ошибки
			return 0;
		}
	}
	
	public void setGroup(String leader, String name) {
		int group = getGroup(leader);
		//сделать проверку на наличие группы
		if(group!=0) {
			if(groups.get(group).size()<6) {
				groups.get(group).add(name);
			} else {
				//Группа полная
			}
		}
		else {
			int number=1;
			for(int i=0; i<groups.size(); ++i) {
				if(i>0)
					++number;
			}
			groups.put(number, Lists.newArrayList(leader, name));
		}
	}
	
	public List<String> getGroupPlayers(int group) {
		return groups.get(group);
	}
	
	public void exitGroup(int group, String name) {
		groups.get(group).remove(name);
		//вывести сообщение о том, что игрок вышел из группы
	}*/
}
