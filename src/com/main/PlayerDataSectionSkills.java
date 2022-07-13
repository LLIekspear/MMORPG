package com.main;

import java.util.HashMap;
import java.util.Map;

public class PlayerDataSectionSkills {

	private final PlayerData playerData;
	private int actionBar=0;
//	private List<Castable> selectedCastables=new ArrayList<>();
//	private Map<String, Castable> castableMap=new HashMap<>();
	private Map<String, Long> skillCooldownMap=new HashMap<>();
	private Map<String, Long> foodCooldownMap = new HashMap<>();
	private Map<String, Long> actionCooldownMap = new HashMap<>();
//	private Map<String, AbstractPassiveSkill> passiveSkillMap = new HashMap<>();
	
	public PlayerDataSectionSkills(PlayerData playerData) {
		this.playerData=playerData;
	}
	
	public PlayerData getPlayerData() {
		return playerData;
	}
	
	public int getActionBar() {
		return actionBar;
	}
	
	public void setActionBar(int actionBar) {
		this.actionBar=actionBar;
	}
	
/*	public List<Castable> getSelectedCastables() {
		return selectedCastables;
	}
	
	public List<Castable> getUnlockedCastables() {
		return unlockedCastables;
	}
	
	public Castable getCastable(String castableKey) {
		return castableMap.get(castableKey);
	}
	
	public void refreshUnlockedCastables() {
		unlockedCastables.clear();
		castableMap.clear();
		Player player=playerData.getPlayer();
		List<PlayerSubclass> subclasses=PlayerClassPool.getClass(player).getSubclasses();
		for(int i=0; i<subclasses.size(); ++i) {
			PlayerSubclass subclass=subclasses.get(i);
			int masterylvl=PlayerSkillConfigurator.getMasteryStatpoints(player, i+1);
			for(Learnable learnable : subclass.getLearned(masterylvl)) {
				Castable castable=new Castable(subclass.getSubclassItemStack(), learnable.getSkill());
				unlockedCastables.add(castable);
				castableMap.put("Skill :: "+learnable.getSkill().getSkillId(), castable);
			}
		}
		refreshSelectedCastables();
	}
	
	public void refreshSelectedCastables() {
		selectedCastables.clear();
	}*/
}
