package com.commands;

import java.io.File;
import java.io.FileInputStream;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;
import com.main.Main;

public class SchemCommand implements CommandExecutor {

	private String buildings[]= {"house", "Shop"};
	private static Main plugin=Main.getInstance();
	private ItemStack houseItem;
	
	private void setupItems() {
		houseItem=new ItemStack(Material.BRICK);
		ItemMeta meta=houseItem.getItemMeta();
		meta.setDisplayName("Дом");
		meta.setLore(Lists.newArrayList(ChatColor.LIGHT_PURPLE+"Домик"));
		houseItem.setItemMeta(meta);
	}
	
	/*public void pasteBuilding(String name, int x, int y, int z, org.bukkit.World world) {
		File dataDir=new File(plugin.getDataFolder(), "Schems");
		File file=new File(dataDir, name+".schem");
		Clipboard clipboard=null;
		ClipboardFormat format=ClipboardFormats.findByFile(file);
		try(ClipboardReader reader=format.getReader(new FileInputStream(file))) {
			clipboard=reader.read();
		} catch(Exception error) {
			//
		}
		World weWorld=new BukkitWorld(world);
		try(EditSession editSession=WorldEdit.getInstance().newEditSession(weWorld)) {
			Operation operation=new ClipboardHolder(clipboard)
					.createPaste(editSession)
					.to(BlockVector3.at(x, y, z))
					//conf here
					.build();
			Operations.complete(operation);
		} catch(Exception error) {
			//
		}
	}*/
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		setupItems();
		Player p=(Player)sender;
		if(sender instanceof Player && /*надо пробежаться по списку и проверить, есть ли совпадения*/args[0].equalsIgnoreCase("house")&&p.hasPermission("lliekspear.lol")) {
			Location loc=p.getLocation();
			int x=loc.getBlockX();
			int y=loc.getBlockY();
			int z=loc.getBlockZ();
			org.bukkit.World world=loc.getWorld();
			Main.economy.withdrawPlayer(p, 500);
			p.sendMessage(ChatColor.GREEN+"Вы успешно приобрели дом!");
			p.getInventory().addItem(houseItem);
		
		} else if(sender instanceof Player && args[0].equalsIgnoreCase("gold")&&p.hasPermission("lliekspear.gold_trader_creation")) {
			Location loc=p.getLocation();
		} else {
			sender.sendMessage(ChatColor.RED+"Произошла ошибка на этапе создания торговца. У Вас достаточно прав для этого действия?");
		}
		return true;
	}
}
