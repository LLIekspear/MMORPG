package com.main;

import org.bukkit.entity.Player;

public class PlayerData {

	private final Player player;
	private final int Id;
	//private final PlayerDataSectionStats stats;
	//private final PlayerDataSectionSkills skills;
	private final PlayerDataSectionSelections selections;
	
	public PlayerData(Player player, int Id) {
		this.player=player;
		this.Id=Id;
		//this.stats=new PlayerDataSectionStats(this);
		//this.skills=new PlayerDataSectionSkills(this);
		this.selections=new PlayerDataSectionSelections(this);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getId() {
		return Id;
	}
	
	/*public PlayerDataSectionStats getStats() {
		return stats;
	}*/
	
	/*public PlayerDataSectionSkills getSkills() {
		return skills;
	}*/
	
	public PlayerDataSectionSelections getSelections() {
		return selections;
	}
}
