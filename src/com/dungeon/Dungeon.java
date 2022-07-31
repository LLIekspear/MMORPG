package com.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.boss.Mob;

public class Dungeon {

	private String dungeonUuidString="";
	private Map<Location, Mob> mobMap=new HashMap<>();
	private List<String> participantsUuidStrings=new ArrayList<>();
	private String dungeonName="";
	private int maxAmount=1;
	
	public String getDUuid() {
		return dungeonUuidString;
	}
	
	public void setDUuid(String uuid) {
		dungeonUuidString=uuid;
	}
	
	public String getDName() {
		return dungeonName;
	}
	
	public void setDName(String name) {
		dungeonName=name;
	}
	
	public boolean isParticipant(OfflinePlayer p) {
		return participantsUuidStrings.contains(p.getUniqueId().toString());
	}
	
	public void sendMessageToParticipants(String message) {
		for(String uuidString : participantsUuidStrings) {
			try {
				OfflinePlayer offlinePlayer=Bukkit.getOfflinePlayer(UUID.fromString(uuidString));
				Player p=offlinePlayer.getPlayer();
				if(p!=null) {
					p.sendMessage(message);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setMaxAmount(int amount) {
		maxAmount=amount;
	}
	
	public int getMaxAmount() {
		return maxAmount;
	}
	
	public int amount() {
		return participantsUuidStrings.size();
	}
	
	public void addParticipant(OfflinePlayer p) {
		participantsUuidStrings.add(p.getUniqueId().toString());
	}
	
	public void removeParticipant(OfflinePlayer p) {
		participantsUuidStrings.remove(p.getUniqueId().toString());
	}
	
	public void createWorld() {
		//
	}
	
	public void setSpawnPosition() {
		//
	}
}
