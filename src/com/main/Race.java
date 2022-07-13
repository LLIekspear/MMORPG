package com.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.data.PlayerConfigurator;
import com.data.RaceConfig;
import com.utils.UnicodeUtils;

import net.md_5.bungee.api.ChatColor;

public class Race {

	private static Map<String, Race> raceMap=new HashMap<>();
	private static Map<String, Race> raceNameMap=new HashMap<>();
	private String raceUuidString;
	private String adminUuidString;
	private List<String> advisersUuidStrings=new ArrayList<>();
	private List<String> residentsUuidStrings=new ArrayList<>();
	private String raceName;
	private String raceMotd;
	
	public static void init() {
		for(String raceUuidString : RaceConfig.getRaceUuidList()) {
			Race race=new Race(raceUuidString);
			raceMap.put(raceUuidString, race);
			raceNameMap.put(race.getRaceName(), race);
		}
		Main.getInstance().getLogger().info("Loaded "+raceMap.size()+" races!");
	}
	
	public static List<Race> getRaces() {
		List<Race> races=new ArrayList<>(raceMap.values());
		Collections.shuffle(races);
		return races;
	}
	
	public static List<String> getRaceNames() {
		List<String> races=new ArrayList<>(raceNameMap.keySet());
		Collections.shuffle(races);
		return races;
	}
	
	public static Race getRace(String uuid) {
		return raceMap.get(uuid);
	}
	
	public static Race getRaceByName(String raceName) {
		return raceNameMap.get(raceName);
	}
	
	public Race(String raceUuidString) {
		this.raceUuidString=raceUuidString;
		this.raceName=RaceConfig.getRaceName(raceUuidString);
		this.raceMotd=RaceConfig.getRaceMotd(raceUuidString);
		this.adminUuidString=RaceConfig.getRaceLeaderUuidString(raceUuidString);
		this.advisersUuidStrings=RaceConfig.getRaceAdvisersUuidStrings(raceUuidString);
		this.residentsUuidStrings=RaceConfig.getRaceResidentsUuidStrings(raceUuidString);
	}
	
	public Race(String raceUuidString, String adminUuidString, String raceName) {
		this.raceUuidString=raceUuidString;
		this.adminUuidString=adminUuidString;
		this.residentsUuidStrings.add(adminUuidString);
		this.raceName=raceName;
		this.raceMotd="Сообщение дня отсутствует.";
		
		RaceConfig.setRaceName(raceUuidString, raceName);
		RaceConfig.setRaceMotd(raceUuidString, raceMotd);
		RaceConfig.setRaceLeaderUuidString(raceUuidString, adminUuidString);
		RaceConfig.setRaceAdvisersUuidStrings(raceUuidString, advisersUuidStrings);
		RaceConfig.setRaceResidentsUuidStrings(raceUuidString, residentsUuidStrings);
	}
	
	public String getRaceUuidString() {
		return raceUuidString;
	}
	
	public void setRaceUuidString(String raceUuidString) {
		this.raceUuidString=raceUuidString;
	}
	
	public String getAdminUuidString() {
		return adminUuidString;
	}
	
	public void setAdminUuidString(String adminUuidString) {
		this.adminUuidString=adminUuidString;
	}
	
	public boolean isRaceAdmin(OfflinePlayer p) {
		return adminUuidString.equals(p.getUniqueId().toString());
	}
	
	public List<String> getAdvisersUuidStrings() {
		return advisersUuidStrings;
	}
	
	public void setAdvisersUuidStrings(List<String> advisersUuidStrings) {
		this.advisersUuidStrings=advisersUuidStrings;
	}
	
	public boolean isRaceAdviser(OfflinePlayer p) {
		return isRaceAdmin(p)||advisersUuidStrings.contains(p.getUniqueId().toString());
	}
	
	public List<String> getResidentsUuidStrings() {
		return residentsUuidStrings;
	}
	
	public void setResidentsUuidStrings(List<String> residentsUuidStrings) {
		this.residentsUuidStrings=residentsUuidStrings;
	}
	
	public boolean isRaceResident(OfflinePlayer p) {
		return residentsUuidStrings.contains(p.getUniqueId().toString());
	}
	
	public void sendMessageToRaceResidents(String message) {
		for(String uuidString : residentsUuidStrings) {
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
	
	public int getResidentsAmount() {
		return residentsUuidStrings.size();
	}
	
	public void addPlayer(OfflinePlayer p) {
		residentsUuidStrings.add(p.getUniqueId().toString());
		RaceConfig.setRaceResidentsUuidStrings(raceUuidString, residentsUuidStrings);
		PlayerConfigurator.setRace(p, raceUuidString);
		sendMessageToRaceResidents(ChatColor.GOLD+"[Раса]: "+p.getName()+" присоединился к нашей расе!");
	}
	
	
	public void removePlayer(OfflinePlayer p) {
		String residentUuid=p.getUniqueId().toString();
		advisersUuidStrings.remove(residentUuid);
		residentsUuidStrings.remove(residentUuid);
		RaceConfig.setRaceAdvisersUuidStrings(raceUuidString, advisersUuidStrings);
		RaceConfig.setRaceResidentsUuidStrings(raceUuidString, residentsUuidStrings);
		
		if(p.isOnline()) {
			p.getPlayer().sendMessage(ChatColor.RED+"[Раса]: Вы покинули расу!");
		}
		sendMessageToRaceResidents(ChatColor.RED+"[Раса]: "+p.getName()+" покинул вашу расу!");
	}
	
	public int getMaxAdvisersAmount() {
		return 3;
	}
	
	public void demoteToResident(Player p, OfflinePlayer resident) {
		String residentUuid=resident.getUniqueId().toString();
		if(!advisersUuidStrings.contains(residentUuid)) {
			return;
		}
		advisersUuidStrings.remove(residentUuid);
		RaceConfig.setRaceAdvisersUuidStrings(raceUuidString, advisersUuidStrings);
		sendMessageToRaceResidents(ChatColor.RED+"[Раса]: "+resident.getName()+" был снят с поста советника!");
	}
	
	public void promoteToAdviser(Player p, OfflinePlayer resident) {
		String residentUuid=resident.getUniqueId().toString();
		if(!residentsUuidStrings.contains(residentUuid) ||advisersUuidStrings.contains(residentUuid)) {
			return;
		}
		advisersUuidStrings.add(residentUuid);
		RaceConfig.setRaceAdvisersUuidStrings(raceUuidString, advisersUuidStrings);
		sendMessageToRaceResidents(ChatColor.GOLD+"[Раса]: "+resident.getName()+" был назначен на пост советника!");
	}
	
	public void kickPlayer(Player p, OfflinePlayer resident) {
		String residentUuid=resident.getUniqueId().toString();
		if(!residentsUuidStrings.contains(residentUuid)) {
			return;
		}
		String noRace="banished";
		PlayerConfigurator.setRace(resident, noRace);
		sendMessageToRaceResidents(ChatColor.RED+"[Раса]: "+p.getName()+" исключил "+resident.getName()+" из расы!");
		removePlayer(resident);
	}
	
	public void promoteToLeader(Player p, OfflinePlayer resident) {
		String residentUuid=resident.getUniqueId().toString();
		adminUuidString=residentUuid;
		RaceConfig.setRaceLeaderUuidString(raceUuidString, adminUuidString);
		sendMessageToRaceResidents(ChatColor.GOLD+"[Раса]: "+p.getName()+" был снят с поста лидера. Поблагодарим его за труд!"+resident.getName()+" был назначен на пост лидера расы! Пожелаем ему удачи!");
	}
	
	public String getRaceName() {
		return raceName;
	}
	
	public void setRaceName(String raceName) {
		this.raceName=raceName;
	}
	
	public String getRaceMotd() {
		return raceMotd;
	}
	
	public List<String> getWrappedRaceMotd() {
		return UnicodeUtils.wrapText(raceMotd);
	}
	
	public ItemStack getRaceItem() {
		if(this.raceName.equalsIgnoreCase("Тёмные")) {
			return new ItemStack(Material.COAL_BLOCK);
		} else if(this.raceName.equalsIgnoreCase("Люди")) {
			return new ItemStack(Material.COOKED_BEEF);
		} else if(this.raceName.equalsIgnoreCase("Эльфы")) {
			return new ItemStack(Material.BOOK);
		} else if(this.raceName.equalsIgnoreCase("Зверолюди")) {
			return new ItemStack(Material.WOLF_SPAWN_EGG);
		} else if(this.raceName.equalsIgnoreCase("Водяные")) {
			return new ItemStack(Material.WATER_BUCKET);
		} else if(this.raceName.equalsIgnoreCase("Гномы")) {
			return new ItemStack(Material.OAK_PRESSURE_PLATE);
		} else {
			return new ItemStack(Material.DRAGON_EGG);
		}
	}
	
	public List<String> getWrappedRaceDesc() {
		if(this.raceName.equalsIgnoreCase("Тёмные")) {
			return UnicodeUtils.wrapText("Нигеры этого мира.");
		} else if(this.raceName.equalsIgnoreCase("Люди")) {
			return UnicodeUtils.wrapText("Люди научились адаптироваться и выживать в этом жестоком мире. Здоровье +4.");
		} else if(this.raceName.equalsIgnoreCase("Эльфы")) {
			return UnicodeUtils.wrapText("Высокомерное ЧМО.");
		} else if(this.raceName.equalsIgnoreCase("Зверолюди")) {
			return UnicodeUtils.wrapText("Эволюция затронула многие разновидные живности, но эту затронула больше. Скорость бега +4%");
		} else if(this.raceName.equalsIgnoreCase("Водяные")) {
			return UnicodeUtils.wrapText("Жизнь моя жестянка... ");
		} else if(this.raceName.equalsIgnoreCase("Гномы")) {
			return UnicodeUtils.wrapText("Не карлик, а гном!");
		} else {
			return UnicodeUtils.wrapText("Хто я?");
		}
	}
	
	public void setRaceMotd(Player p, String raceMotd) {
		this.raceMotd=raceMotd;
		if(p!=null) {
			sendMessageToRaceResidents(ChatColor.GOLD+"[Раса]: "+p.getName()+" установил сообщение дня: "+raceMotd);
		}
	}
}
