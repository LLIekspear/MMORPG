package com.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.data.PlayerConfigurator;
import com.inkzzz.spigot.armorevent.PlayerArmorEquipEvent;

public class EquipmentListener implements Listener {
	//TODO ���������� �������� ����� + �����������
	@EventHandler
	public void onArmorEquip(PlayerArmorEquipEvent e) {
		if(PlayerConfigurator.getClass(e.getPlayer()).equalsIgnoreCase("�������")||PlayerConfigurator.getClass(e.getPlayer()).equalsIgnoreCase("���")) {
			if(e.getItemStack().getType().equals(Material.LEATHER_HELMET)||e.getItemStack().getType().equals(Material.LEATHER_CHESTPLATE)||e.getItemStack().getType().equals(Material.LEATHER_LEGGINGS)||e.getItemStack().getType().equals(Material.LEATHER_BOOTS)) {
				
			}
		} else if(PlayerConfigurator.getClass(e.getPlayer()).equalsIgnoreCase("������")||PlayerConfigurator.getClass(e.getPlayer()).equalsIgnoreCase("�������")) {
			
		}
	}
}
