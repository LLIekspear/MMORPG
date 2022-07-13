package com.data;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class GuildConfig extends GuildDataConfiguration {

	public static List<String> getGuildUuidList() {
		return GuildDataConfiguration.getGuildUuidList();
	}
	
	public static void deleteGuild(String guild) {
		GuildDataConfiguration.deleteGuild(guild);
	}
	
	public static String getGuildName(String guild) {
		return guildConfigGetString(guild, "name");
	}
	
	public static void setGuildName(String guild, String name) {
		guildConfigSet(guild, "name", name);
	}
	
	public static String getGuildDesc(String guild) {
		return guildConfigGetString(guild, "desc");
	}
	
	public static void setGuildDesc(String guild, String desc) {
		guildConfigSet(guild, "desc", desc);
	}
	
	public static ItemStack getGuildBanner(String guild) {
		return guildConfigGetItemStack(guild, "banner");
	}
	
	public static void setGuildBanner(String guild, ItemStack banner) {
		guildConfigSet(guild, "banner", banner);
	}
	
	public static String getGuildLegateUuidString(String guild) {
		return guildConfigGetString(guild, "legate");
	}
	
	public static void setGuildLegateUuidString(String guild, String legateUuidString) {
		guildConfigSet(guild, "legate", legateUuidString);
	}
	
	public static List<String> getGuildAdjutantUuidStrings(String guild) {
		return guildConfigGetStringList(guild, "adjutants");
	}
	
	public static void setGuildAdjutantsUuidStrings(String guild, List<String> adjutantsUuidStrings) {
		guildConfigSet(guild, "adjutants", adjutantsUuidStrings);
	}
	
	public static List<String> getGuildLegionnairesUuidString(String guild) {
		return guildConfigGetStringList(guild, "legionnaires");
	}
	
	public static void setGuildLegionnairesUuidString(String guild, List<String> legionnairesUuidStrings) {
		guildConfigSet(guild, "legionnaires", legionnairesUuidStrings);
	}
	
	public static List<String> getGuildApplicantUuidStrings(String guild) {
		return guildConfigGetStringList(guild, "applicants");
	}
	
	public static void setGuildApplicantUuidStrings(String guild, List<String> applicantUuidStrings) {
		guildConfigSet(guild, "applicants", applicantUuidStrings);
	}
	
	public static int getUpgradeAdditionalSlots(String guild) {
		return guildConfigGetInt(guild, "upgrades_slots");
	}
	
	public static void setUpgradeAdditionalSlots(String guild, int lvl) {
		guildConfigSet(guild, "upgrades_slots", lvl);
	}
}
