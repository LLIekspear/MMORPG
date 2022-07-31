package com.listeners;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.main.Main;

public class BlockPutListener implements Listener {

	private static Main plugin=Main.getInstance();
//	private RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
	
	public void createRegion(Player p, int x, int y, int z) {
	/*	BlockVector3 min=BlockVector3.at(x,y-1000,z);
		BlockVector3 max=BlockVector3.at(x+47, y+1000, z+47);
		ProtectedRegion region=new ProtectedCuboidRegion(p.getName()+"_house", min, max);
		
		DefaultDomain owners=region.getOwners();
		owners.addPlayer(p.getUniqueId());
		region.setOwners(owners);
		
		RegionManager regions=container.get(new BukkitWorld(p.getLocation().getWorld()));
		regions.addRegion(region);
		try {
			regions.save();
		} catch(Exception error) {
			p.sendMessage("Возникла ошибка при сохранении региона!");
		}*/
	}
	
	public void pasteBuilding(String name, int x, int y, int z, org.bukkit.World world, Player p) {
		createRegion(p, x, y, z);
		
		File dataDir=new File(plugin.getDataFolder(), "Schems");
		File file=new File(dataDir, name+".schem");
	/*	Clipboard clipboard=null;
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
		}*/
	}
	
	@EventHandler
	public void onBlockPutGroundDungeon(BlockPlaceEvent e) {
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase("plugins/MMORPG/Dungeons/GhostCastle")) {
			e.getPlayer().sendMessage(ChatColor.RED+"Здесь нельзя строить!");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockBreakDungeon(BlockBreakEvent e) {
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase("plugins/MMORPG/Dungeons/GhostCastle")) {
			e.getPlayer().sendMessage(ChatColor.RED+"Здесь нельзя ломать!");
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPutGround(PlayerInteractEvent e) {
		try {
			ItemStack item=e.getItem();
			if((e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK))&&item.getItemMeta().getLore().get(0).equals(ChatColor.LIGHT_PURPLE+"Домик")) {
				e.setCancelled(true);
				if(item.getAmount()>0) {
					//TODO ошибка, нужно убавлять кол-во, а не удалять предмет, иначе все разы используется!!!
					int amount=item.getAmount() -1;
					e.getPlayer().getInventory().remove(item);
					item.setAmount(amount);
					e.getPlayer().getInventory().addItem(item);
				} else if(item.getAmount()==0) {
					e.getPlayer().getInventory().remove(item);
				}
				Location loc=e.getPlayer().getLocation();
				int x=loc.getBlockX();
				int y=loc.getBlockY();
				int z=loc.getBlockZ();
				org.bukkit.World world=loc.getWorld();
				pasteBuilding("house", x, y, z, world, e.getPlayer());
				e.getPlayer().sendMessage(ChatColor.GREEN+"Вы успешно установили дом!");
			}
		} catch(Exception error) {
			//
		}
	}
}
