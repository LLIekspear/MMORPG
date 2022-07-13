package com.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.OfflinePlayer;

import com.data.GuildConfig;
import com.data.PlayerConfigurator;
import com.menu.GuildMenu;
import com.utils.UnicodeUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Guild {

	private static Map<String, Guild> guildMap=new HashMap<>();
	
	private static Map<String, Guild> guildNameMap=new HashMap<>();
	private String guildUuidString;
	private String adminUuidString;
	private List<String> adjutantsUuidStrings=new ArrayList<>();
	private List<String> legionnairesUuidStrings=new ArrayList<>();
	private List<String> applicantUuidStrings=new ArrayList<>();
	private String guildName;
	private String guildDesc;
	private ItemStack guildBanner;
	private int upgradeAdditionalSlots;
	
	public static void init() {
		for(String guildUuidString : GuildConfig.getGuildUuidList()) {
			Guild guild=new Guild(guildUuidString);
			guildMap.put(guildUuidString, guild);
			guildNameMap.put(guild.getGuildName(), guild);
		}
		Main.getInstance().getLogger().info("Loaded "+guildMap.size()+" guilds!");
	}
	
	public static List<Guild> getGuilds() {
		List<Guild> guilds=new ArrayList<>(guildMap.values());
		Collections.shuffle(guilds);
		return guilds;
	}
	
	public static List<String> getGuildNames() {
		List<String> guilds=new ArrayList<>(guildNameMap.keySet());
		Collections.shuffle(guilds);
		return guilds;
	}
	
	public static Guild getGuild(String uuid) {
		return guildMap.get(uuid);
	}
	
	public static Guild getGuildByName(String guildName) {
		return guildNameMap.get(guildName);
	}
	
	public static boolean doShareGuild(OfflinePlayer player1, OfflinePlayer player2, boolean allowSame) {
		if(player1==player2) {
			return allowSame;
		}
		Guild guild1=PlayerConfigurator.getGuild(player1);
		Guild guild2=PlayerConfigurator.getGuild(player2);
		return guild1!=null&&guild2!=null&&StringUtils.equals(guild1.getGuildUuidString(), guild2.getGuildUuidString());
	}
	
	public static boolean createGuild(Player legate, String guildName) {
		try {
			Guild playerGuild=PlayerConfigurator.getGuild(legate);
			if(playerGuild!=null) {
				legate.sendMessage(ChatColor.RED+"Вы уже состоите в легионе!");
				return true;
			}
			//long money=PlayerCollectionConfigurator.getMoney(legate);
			long money=4000L;
			if(money<500) {
				legate.sendMessage(ChatColor.RED+"Для создания легиона требуется 500 монет! Вы имеете: "+" монет.");
				return true;
			}
			if(StringUtils.isBlank(guildName)) {
				legate.sendMessage(ChatColor.RED+"Введите название легиона!");
				return false;
			}
			if(guildName.length()>15) {
				legate.sendMessage(ChatColor.RED+"Название легиона не может превышать 15 символов!");
				return true;
			}
			if(isNameTaken(guildName)) {
				legate.sendMessage(ChatColor.RED+"Данное имя легиона уже занято!");
				return true;
			}
			String guildUuidString=UUID.randomUUID().toString();
			String adminUuidString=legate.getUniqueId().toString();
			Guild guild=new Guild(guildUuidString, adminUuidString, guildName);
			guildMap.put(guildUuidString, guild);
			guildNameMap.put(guild.getGuildName(), guild);
			//PlayerCollectionConfigurator.setMoney(legate, money-500);
			PlayerConfigurator.setGuild(legate, guildUuidString);
			GuildMenu.open(legate);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean applyForGuild(Player p, String guildName) {
		try {
			Guild playerGuild=PlayerConfigurator.getGuild(p);
			if(playerGuild!=null) {
				p.sendMessage(ChatColor.RED+"Вы уже состоите в легионе!");
				return true;
			}
			if(StringUtils.isBlank(guildName)) {
				p.sendMessage(ChatColor.RED+"Введите название легиона!");
				return false;
			}
			playerGuild=getGuildByName(guildName);
			if(playerGuild==null) {
				p.sendMessage(ChatColor.RED+"Неправильное название легиона!");
				return false;
			}
			String applicantUuidString=p.getUniqueId().toString();
			if(playerGuild.getApplicantUuidStrings().contains(applicantUuidString)) {
				p.sendMessage(ChatColor.RED+"Вы уже подали заявку в этот легион!");
				return true;
			}
			playerGuild.addApplicant(applicantUuidString);
			playerGuild.sendMessageToGuildLegionnaires(ChatColor.YELLOW+"Новая заявка на вступление от "+p.getName()+".");
			p.sendMessage(ChatColor.GREEN+"Вы подали заявку в "+playerGuild.getGuildName()+"!");
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean isNameTaken(String guildName) {
		return guildNameMap.keySet().contains(guildName);
	}
	
	public Guild(String guildUuidString) {
		this.guildUuidString=guildUuidString;
		this.guildName=GuildConfig.getGuildName(guildUuidString);
		this.guildDesc=GuildConfig.getGuildDesc(guildUuidString);
		this.guildBanner=GuildConfig.getGuildBanner(guildUuidString);
		this.adminUuidString=GuildConfig.getGuildLegateUuidString(guildUuidString);
		this.adjutantsUuidStrings=GuildConfig.getGuildAdjutantUuidStrings(guildUuidString);
		this.legionnairesUuidStrings=GuildConfig.getGuildLegionnairesUuidString(guildUuidString);
		this.applicantUuidStrings=GuildConfig.getGuildApplicantUuidStrings(guildUuidString);
		this.upgradeAdditionalSlots=GuildConfig.getUpgradeAdditionalSlots(guildUuidString);
	}
	
	public Guild(String guildUuidString, String adminUuidString, String guildName) {
		this.guildUuidString=guildUuidString;
		this.adminUuidString=adminUuidString;
		this.legionnairesUuidStrings.add(adminUuidString);
		this.guildName=guildName;
		this.guildDesc="Сообщение дня отсутствует.";
		this.guildBanner=new ItemStack(Material.WHITE_BANNER);
		
		GuildConfig.setGuildName(guildUuidString, guildName);
		GuildConfig.setGuildDesc(guildUuidString, guildDesc);
		GuildConfig.setGuildBanner(guildUuidString, guildBanner);
		GuildConfig.setGuildLegateUuidString(guildUuidString, adminUuidString);
		GuildConfig.setGuildAdjutantsUuidStrings(guildUuidString, adjutantsUuidStrings);
		GuildConfig.setGuildLegionnairesUuidString(guildUuidString, legionnairesUuidStrings);
		GuildConfig.setGuildApplicantUuidStrings(guildUuidString, applicantUuidStrings);
		GuildConfig.setUpgradeAdditionalSlots(guildUuidString, 0);
	}
	
	public String getGuildUuidString() {
		return guildUuidString;
	}
	
	public void setGuildUuidString(String guildUuidString) {
		this.guildUuidString=guildUuidString;
	}
	
	public String getAdminUuidString() {
		return adminUuidString;
	}
	
	public void setAdminUuidString(String adminUuidString) {
		this.adminUuidString=adminUuidString;
	}
	
	public boolean isGuildAdmin(OfflinePlayer p) {
		return adminUuidString.equals(p.getUniqueId().toString());
	}
	
	public List<String> getAdjutantsUuidStrings() {
		return adjutantsUuidStrings;
	}
	
	public void setAdjutantsUuidStrings(List<String> adjutantsUuidStrings) {
		this.adjutantsUuidStrings=adjutantsUuidStrings;
	}
	
	public boolean isGuildAdjutant(OfflinePlayer p) {
		return isGuildAdmin(p)||adjutantsUuidStrings.contains(p.getUniqueId().toString());
	}
	
	public List<String> getLegionnairesUuidStrings() {
		return legionnairesUuidStrings;
	}
	
	public void setLegionnairesUuidStrings(List<String> legionnairesUuidStrings) {
		this.legionnairesUuidStrings=legionnairesUuidStrings;
	}
	
	public boolean isGuildLegionnaire(OfflinePlayer p) {
		return legionnairesUuidStrings.contains(p.getUniqueId().toString());
	}
	
	public void sendMessageToGuildLegionnaires(String message) {
		for(String uuidString : legionnairesUuidStrings) {
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
	
	public int getLegionnaireAmount() {
		return legionnairesUuidStrings.size();
	}
	
	public int getMaxLegionnaireAmount() {
		return 5+upgradeAdditionalSlots;
	}
	
	public boolean isFull() {
		return getLegionnaireAmount()>=getMaxLegionnaireAmount();
	}
	
	public boolean isEmpty() {
		return getLegionnaireAmount()==0;
	}
	
	public void addPlayer(OfflinePlayer p) {
		legionnairesUuidStrings.add(p.getUniqueId().toString());
		GuildConfig.setGuildLegionnairesUuidString(guildUuidString, legionnairesUuidStrings);
		PlayerConfigurator.setGuild(p, guildUuidString);
		sendMessageToGuildLegionnaires(ChatColor.GREEN+p.getName()+" присоединился к "+guildName+"!");
	}
	
	public void removePlayer(OfflinePlayer p) {
		String legionnaireUuid=p.getUniqueId().toString();
		adjutantsUuidStrings.remove(legionnaireUuid);
		legionnairesUuidStrings.remove(legionnaireUuid);
		GuildConfig.setGuildAdjutantsUuidStrings(guildUuidString, adjutantsUuidStrings);
		GuildConfig.setGuildLegionnairesUuidString(guildUuidString, legionnairesUuidStrings);
		
		if(p.isOnline()) {
			p.getPlayer().sendMessage(ChatColor.RED+"Вы покинули легион!");
		}
		sendMessageToGuildLegionnaires(ChatColor.RED+p.getName()+" покинул ваш легион!");
		if(isGuildAdmin(p)) {
			if(isEmpty()) {
				GuildConfig.deleteGuild(guildUuidString);
			} else {
				String legateUuid=!adjutantsUuidStrings.isEmpty() ? adjutantsUuidStrings.get(0) : legionnairesUuidStrings.get(0);
				GuildConfig.setGuildLegateUuidString(guildUuidString, legateUuid);
				adminUuidString=legateUuid;
				OfflinePlayer legate=Bukkit.getOfflinePlayer(UUID.fromString(legateUuid));
				if(legate!= null) {
					sendMessageToGuildLegionnaires(ChatColor.GREEN+legate.getName()+" теперь легат!");
				}
			}
		}
	}
	
	public void demoteToLegionnaire(Player p, OfflinePlayer legionnaire) {
		String legionnaireUuid=legionnaire.getUniqueId().toString();
		if(!adjutantsUuidStrings.contains(legionnaireUuid)) {
			return;
		}
		adjutantsUuidStrings.remove(legionnaireUuid);
		GuildConfig.setGuildAdjutantsUuidStrings(guildUuidString, adjutantsUuidStrings);
		sendMessageToGuildLegionnaires(ChatColor.RED+legionnaire.getName()+" был разжалован в легионеры!");
	}
	
	public void promoteToAdjutant(Player p, OfflinePlayer legionnaire) {
		String legionnaireUuid=legionnaire.getUniqueId().toString();
		if(!legionnairesUuidStrings.contains(legionnaireUuid) || adjutantsUuidStrings.contains(legionnaireUuid)) {
			return;
		}
		adjutantsUuidStrings.add(legionnaireUuid);
		GuildConfig.setGuildAdjutantsUuidStrings(guildUuidString, adjutantsUuidStrings);
		sendMessageToGuildLegionnaires(ChatColor.GREEN+legionnaire.getName()+" был повышен до адъютанта!");
	}
	
	public void kickLegionnaire(Player p, OfflinePlayer legionnaire) {
		String legionnaireUuid=legionnaire.getUniqueId().toString();
		if(!legionnairesUuidStrings.contains(legionnaireUuid)) {
			return;
		}
		String noGuild="none";
		PlayerConfigurator.setGuild(legionnaire, noGuild);
		sendMessageToGuildLegionnaires(ChatColor.RED+p.getName()+" исключил "+legionnaire.getName()+" из легиона!");
		removePlayer(legionnaire);
	}
	
	public void promoteToLegate(Player p, OfflinePlayer legionnaire) {
		String legionnaireUuid=legionnaire.getUniqueId().toString();
		if(!adjutantsUuidStrings.contains(legionnaireUuid) || adjutantsUuidStrings.contains(p.getUniqueId().toString())) {
			return;
		}
		adjutantsUuidStrings.remove(legionnaireUuid);
		adjutantsUuidStrings.add(p.getUniqueId().toString());
		GuildConfig.setGuildAdjutantsUuidStrings(guildUuidString, adjutantsUuidStrings);
		adminUuidString=legionnaireUuid;
		GuildConfig.setGuildLegateUuidString(guildUuidString, adminUuidString);
		sendMessageToGuildLegionnaires(ChatColor.GREEN+legionnaire.getName()+" теперь легат легиона!");
	}
	
	public List<String> getApplicantUuidStrings() {
		updateApplications();
		return applicantUuidStrings;
	}
	
	public void setApplicantUuidStrings(List<String> applicantUuidStrings) {
		this.applicantUuidStrings=applicantUuidStrings;
		updateApplications();
	}
	
	public void addApplicant(String applicantUuidString) {
		applicantUuidStrings.add(applicantUuidString);
		updateApplications();
	}
	
	public void removeApplicant(String applicantUuidString) {
		applicantUuidStrings.remove(applicantUuidString);
		updateApplications();
	}
	
	public int getApplocationCount() {
		updateApplications();
		return applicantUuidStrings.size();
	}
	
	public void updateApplications() {
		List<String> applicants=new ArrayList<>();
		applicants.addAll(applicantUuidStrings);
		for(String applicantUuidString : applicants) {
			try {
				OfflinePlayer applicant=Bukkit.getOfflinePlayer(UUID.fromString(applicantUuidString));
				if(applicant==null||PlayerConfigurator.getGuild(applicant)!=null) {
					applicantUuidStrings.remove(applicantUuidString);
				}
			} catch(Exception e) {
				e.printStackTrace();
				applicantUuidStrings.remove(applicantUuidString);
			}
		}
		GuildConfig.setGuildApplicantUuidStrings(guildUuidString, applicantUuidStrings);
	}
	
	public String getGuildName() {
		return guildName;
	}
	
	public void setGuildName(String guildName) {
		this.guildName=guildName;
	}
	
	public String getGuildDesc() {
		return guildDesc;
	}
	
	public List<String> getWrappedGuildDesc() {
		return UnicodeUtils.wrapText(guildDesc);
	}
	
	public void setGuildDesc(Player p, String guildDesc) {
		this.guildDesc=guildDesc;
		if(p!=null) {
			sendMessageToGuildLegionnaires(ChatColor.GREEN+p.getName()+" установил сообщение дня: "+guildDesc);
		}
	}
	
	public ItemStack getGuildBanner() {
		return guildBanner;
	}
	
	public void setGuildBanner(Player p, ItemStack guildBanner) {
		this.guildBanner=guildBanner;
		if(p!=null) {
			sendMessageToGuildLegionnaires(ChatColor.GREEN+p.getName()+" изменил знамя легиона!");
		}
	}
	
	public int getUpgradeAdditionalSlots() {
		return upgradeAdditionalSlots;
	}
	
	public void setUpgradeAdditionalSlots(int upgradeAdditionalSlots) {
		this.upgradeAdditionalSlots=upgradeAdditionalSlots;
	}
}
