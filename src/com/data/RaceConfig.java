package com.data;

import java.util.List;

public class RaceConfig extends RaceDataConfiguration {

	public static List<String> getRaceUuidList() {
		return RaceDataConfiguration.getRaceUuidList();
	}
	
	public static void deleteRace(String race) {
		RaceDataConfiguration.deleteRace(race);
	}
	
	public static String getRaceName(String race) {
		return raceConfigGetString(race, "name");
	}
	
	public static void setRaceName(String race, String name) {
		raceConfigSet(race, "name", name);
	}
	
	public static String getRaceMotd(String race) {
		return raceConfigGetString(race, "motd");
	}
	
	public static void setRaceMotd(String race, String motd) {
		raceConfigSet(race, "motd", motd);
	}
	
	public static String getRaceDesc(String race) {
		return raceConfigGetString(race, "desc");
	}
	
	public static String getRaceLeaderUuidString(String race) {
		return raceConfigGetString(race, "leader");
	}
	
	public static void setRaceLeaderUuidString(String race, String leaderUuidString) {
		raceConfigSet(race, "leader", leaderUuidString);
	}
	
	public static List<String> getRaceAdvisersUuidStrings(String race) {
		return raceConfigGetStringList(race, "advisers");
	}
	
	public static void setRaceAdvisersUuidStrings(String race, List<String> advisersUuidStrings) {
		raceConfigSet(race, "advisers", advisersUuidStrings);
	}
	
	public static List<String> getRaceResidentsUuidStrings(String race) {
		return raceConfigGetStringList(race, "residents");
	}
	
	public static void setRaceResidentsUuidStrings(String race, List<String> residentsUuidStrings) {
		raceConfigSet(race, "residents", residentsUuidStrings);
	}
}
