package com.listeners;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.main.Main;

public class HorseListener implements Listener {

	private static Main plugin=Main.getInstance();
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
//		e.getPlayer().sendMessage("lol");
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR)&&e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.CHEST)&&e.getPlayer().getVehicle() instanceof Horse) {
			Inventory inv=Bukkit.createInventory(null, 27, "Сумка лошади");
			e.getPlayer().getVehicle().getUniqueId();
			try {
				File directory=new File(plugin.getDataFolder(), "/HorseChest/");
				File dataFile=new File(directory, e.getPlayer().getVehicle().getUniqueId().toString()+".yml");
				FileConfiguration dataConfig=YamlConfiguration.loadConfiguration(dataFile);
				ByteArrayInputStream inputStream=new ByteArrayInputStream(Base64Coder.decodeLines(dataConfig.getString("items")));
				BukkitObjectInputStream dataInput=new BukkitObjectInputStream(inputStream);
				for(int i=0; i<inv.getSize(); ++i) {
					inv.setItem(i, (ItemStack)dataInput.readObject());
				}
				dataInput.close();

//				List<String> itemsSaved=dataConfig.getStringList("items");
//				for(String item : itemsSaved) {
//					Material mat=Material.getMaterial(item.split(";")[0]);
//					int amount=Integer.parseInt(item.split(";")[1]);
//					inv.addItem(new ItemStack(mat, amount));
//				}
			} catch(Exception error) {
//				error.printStackTrace();
			}
			e.getPlayer().openInventory(inv);
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if(e.getView().getTitle().equalsIgnoreCase("Сумка лошади")) {
//			ItemStack[] items=e.getInventory().getContents();
//			List<String> itemsToSave=new ArrayList<String>();
//			for(ItemStack item : items) {
//				itemsToSave.add(item.getType().name()+";"+item.getAmount());
//			}
			try {
				ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
				BukkitObjectOutputStream dataOutput=new BukkitObjectOutputStream(outputStream);
				for(int i=0; i<e.getInventory().getSize(); ++i) {
					dataOutput.writeObject(e.getInventory().getItem(i));
				}
				dataOutput.close();
				
				File dataFile=new File(plugin.getDataFolder()+"/HorseChest/"+e.getPlayer().getVehicle().getUniqueId().toString()+".yml");
				FileConfiguration dataConfig=YamlConfiguration.loadConfiguration(dataFile);
				dataConfig.set("items", Base64Coder.encodeLines(outputStream.toByteArray()));
				dataConfig.save(dataFile);
			} catch(Exception error) {
//				error.printStackTrace();
			}
		}
	}
}
