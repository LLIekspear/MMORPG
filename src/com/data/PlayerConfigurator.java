package com.data;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.main.Guild;
import com.main.Race;

public class PlayerConfigurator extends PlayerDataConfiguration {

	public static Double getBalance(OfflinePlayer p) {
		return playerConfigGetDouble(p, "balance");
	}
	
	public static void setBalance(OfflinePlayer p, Double money) {
		playerConfigSet(p, "balance", money);
	}
	
	public static String getRaceString(OfflinePlayer p) {
		return playerConfigGetString(p, "race");
	}
	
	public static Race getRace(OfflinePlayer p) {
		return Race.getRace(playerConfigGetString(p, "race"));
	}
	
	public static void setRace(OfflinePlayer p, String raceUuidString) {
		playerConfigSet(p, "race", raceUuidString);
	}
	
	public static String getRank(OfflinePlayer p) {
		return playerConfigGetString(p, "rank");
	}
	
	public static void setHeroRep(OfflinePlayer p, int rep) {
		playerConfigSet(p, "rep", rep);
	}
	
	public static int getHeroRep(OfflinePlayer p) {
		return playerConfigGetInt(p, "rep");
	}
	/*public static void setRank(OfflinePlayer player, Rank rank) {
		playerConfigSet(player, "rank", rank.getRankName());
	}*/
	
	/*public static String getLastPlayed(OfflinePlayer p) {
		return playerConfigGetLong(p, "lastplayed");
	}*/
	
	/*public static void setLastPlayed(OfflinePlayer player) {
		playerConfigSet(player, "lastplayed", System.currentTimeMillis(), false);
	}*/
	
	
	public static String getGuildString(Player p) {
		return playerConfigGetString(p,"guild");
	}
	
	public static String getGuildString(OfflinePlayer p) {
		return playerConfigGetString(p, "guild");
	}
	
	public static Guild getGuild(Player p) {
		return Guild.getGuild(playerConfigGetString(p, "guild"));
	}
	
	public static Guild getGuild(OfflinePlayer p) {
		return Guild.getGuild(playerConfigGetString(p, "guild"));
	}
	
	public static void setGuild(OfflinePlayer p, String guildUuidString) {
		playerConfigSet(p, "guild", guildUuidString);
	}
	
	public static String getClass(OfflinePlayer p) {
		return playerConfigGetString(p, "class");
	}
	
//	public static void setClass(OfflinePlayer p, PlayerClass playerClass) {
//		playerConfigSet(p, "class", playerClass.getClassName());
//	}
	
	public static void setPlayerClass(Player p, String playerClass) {
		playerConfigSet(p, "class", playerClass);
	}
	
	public static String getCharacterWorldString(Player p) {
		return playerConfigGetString(p, "pos_world");
	}
	
	public static boolean isCaharacterCooldownReady(Player p, String actionId) {
		Long cooldown=playerConfigGetLong(p, "cooldown_"+actionId);
		return cooldown==null || cooldown<=System.currentTimeMillis();
	}
	
	public static void updateCharacterCooldown(Player p, String actionId, Long cooldown) {
		playerConfigSet(p, "cooldown_"+actionId, cooldown+System.currentTimeMillis());
	}
	
	public static List<String> getFriendsList(OfflinePlayer p) {
		return playerConfigGetStringList(p, "friends");
	}
	
	public static void setFriendsList(OfflinePlayer p, List<String> friendsList) {
		playerConfigSet(p, "friends", friendsList);
	}
	
	/*public static String getLevelString(OfflinePlayer p) {
		return playerConfigGetString(p, "lvl");
	}*/
}
