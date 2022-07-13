package com.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.data.PlayerConfigurator;
import com.main.Main;
import com.main.PlayerDataPool;
import com.menu.ClassMenu;
import com.menu.RaceMenu;

public class PreJoinListener implements Listener {

	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent e) {
		if(!Main.isStarted()) {
			e.setLoginResult(Result.KICK_OTHER);
			e.setKickMessage("������ ����������, ���������� ��������� ����� ��������� �����!");
			//return;
		}
	//	if(Main.isStarted()) {
			//
		//}
	}
	
	@EventHandler
	public void onLogin(PlayerJoinEvent e) {
		PlayerDataPool.regPlayer(e.getPlayer());
//		e.getPlayer().getInventory().addItem(ItemSettings.createWeapon(new ItemStack(Material.DIAMOND_SWORD), "��� ��������", Lists.newArrayList("��� �������� �����"), 0, 0, 5, 15, 0, 14, 100, 4));
//		e.getPlayer().getInventory().addItem(ItemSettings.createWeapon(new ItemStack(Material.GOLDEN_SWORD), "��� �����", Lists.newArrayList("��� �������� �����"), 0, 10, 0, 15, 0, 14, 100, 4));
//		e.getPlayer().getInventory().addItem(ItemSettings.createArmor(new ItemStack(Material.GOLDEN_CHESTPLATE), "������ �����", Lists.newArrayList("������, ��������� � �����"), 1, 5, 0, 15, 7, 5, 3, 1, EquipmentSlot.CHEST));
//		e.getPlayer().getInventory().addItem(ItemSettings.createArtifact(new ItemStack(Material.GOLD_INGOT), "��������� �������", Lists.newArrayList("����� �� ���� �������"), 0, 4, 4, 6, 0, 20, 50, 5));
		if(PlayerConfigurator.getRace(e.getPlayer())==null||PlayerConfigurator.getRace(e.getPlayer()).getRaceName().equalsIgnoreCase("none")||PlayerConfigurator.getRace(e.getPlayer()).getRaceName().equalsIgnoreCase("banished")) {
			//��������� ����� ����, �� ��� ��� ��������� � ���� "�����"?
			RaceMenu.open(e.getPlayer());
		}
		if((PlayerConfigurator.getRace(e.getPlayer())!=null)&&PlayerConfigurator.getClass(e.getPlayer())==null||PlayerConfigurator.getClass(e.getPlayer()).equalsIgnoreCase("none")&&PlayerConfigurator.getRace(e.getPlayer())!=null) {
			ClassMenu.open(e.getPlayer());
			//��������� ����� ������
		}
		Scoreboard score=Bukkit.getScoreboardManager().getMainScoreboard();
		Team t=score.getTeam("nhide");
		if(t==null) {
			t=score.registerNewTeam("nhide");
			t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
			t.addEntry(e.getPlayer().getName());
//			e.getPlayer().sendMessage("�������� if � �����");
		} else {
			t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
			t.addEntry(e.getPlayer().getName());
//			e.getPlayer().sendMessage("�������� else � �����");
		}
	}
}
