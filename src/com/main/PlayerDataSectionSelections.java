package com.main;

import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.StringUtils;

import com.events.PlayerEvent;

public class PlayerDataSectionSelections {

	private final PlayerData playerData;
	private String selectedPetId;
	private String selectedPetSlot;
	private String selectedCharacterSlot;
	private String selectedCharacterWorld;
	private String selectedCharacterClass;
	private String groupUuidString;
	private String PlayerEventName;
	private PlayerEvent PlayerEvent;
	private int activeConversations=0;
	//private SongPlayer songPlayer;
	//private Region region;
	
	public PlayerDataSectionSelections(PlayerData playerData) {
		this.playerData=playerData;
	}
	
	public PlayerData getPlayerData() {
		return playerData;
	}
	
	public String getSelectedPerId() {
		return selectedPetId;
	}
	
	public void setSelectedPetId(String selectedPetId) {
		this.selectedPetId=selectedPetId;
	}
	
	public String getSelectedPetSlot() {
		return selectedPetSlot;
	}
	
	public void setSelectedPetSlot(String selectedPetSlot) {
		this.selectedPetSlot=selectedPetSlot;
	}
	
	public String getSelectedCharacterSlot() {
		return selectedCharacterSlot;
	}
	
	public void setSelectedCharacterSlot(String selectedCharacterSlot) {
		this.selectedCharacterSlot=selectedCharacterSlot;
	}
	
	public boolean isCharacterSelected() {
		return StringUtils.isNotBlank(selectedCharacterSlot);
	}
	
	public void setSelectedCharacterWorld(String selectedCharacterWorld) {
		this.selectedCharacterWorld=selectedCharacterWorld;
	}
	
	public String getSelectedCharacterClass() {
		return selectedCharacterClass;
	}
	
	public void setSelectedCharacterClass(String selectedCharacterClass) {
		this.selectedCharacterClass=selectedCharacterClass;
	}
	
	public String getGroupUuidString() {
		return groupUuidString;
	}
	
	public void setGroupUuidString(String groupUuidString) {
		this.groupUuidString=groupUuidString;
	}
	
	public boolean isInGroup() {
		return StringUtils.isNotBlank(groupUuidString);
	}
	
	public String getPlayerEventName() {
		return PlayerEventName;
	}
	
	public void setPlayerEventName(String PlayerEventName) {
		this.PlayerEventName=PlayerEventName;
	}
	
	public PlayerEvent getPlayerEvent() {
		return PlayerEvent;
	}
	
	public void setPlayerEvent(PlayerEvent PlayerEvent) {
		this.PlayerEvent=PlayerEvent;
	}
	
	public int getActiveConversations() {
		return activeConversations;
	}
	
	public void increaseActiveConversations() {
		++activeConversations;
	}
	
	public void decreaseActiveConversations() {
		--activeConversations;
	}
	
	/*public SongPlayer getSongPlayer() {
		return songPlayer;
	}
	
	public void setSongPlayer(SongPlayer songPlayer) {
		this.songPlayer=songPlayer;
	}
	
	public Region getRegion() {
		return region;
	}
	
	public void setRegion(Region region) {
		this.region=region;
	}*/
}
