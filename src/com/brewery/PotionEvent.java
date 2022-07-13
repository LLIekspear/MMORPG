package com.brewery;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PotionEvent implements Listener {

	@EventHandler
	public void customPotionItemStackClick(InventoryClickEvent e) {
		Inventory inv=e.getClickedInventory();
		if(inv==null||inv.getType()!=InventoryType.BREWING) {
			return;
		}
		if(!(e.getClick()==ClickType.LEFT||e.getClick()==ClickType.RIGHT)) {
			return;
		}
		
		ItemStack is=e.getCurrentItem(); //gets item that is being clicked
		ItemStack is2=e.getCursor(); //gets current item held on moues
		if(e.getClick()==ClickType.RIGHT&&is.isSimilar(is2)) {
			return;
		}
		
		e.setCancelled(true);
		Player p=(Player)(e.getView().getPlayer());
		boolean compare=is.isSimilar(is2);
		ClickType type=e.getClick();
		int firstAmount=is.getAmount();
		int secondAmount=is2.getAmount();
		int stack=is.getMaxStackSize();
		int half=firstAmount/2;
		int clickedSlot=e.getSlot();
		if(type==ClickType.LEFT) {
			if(is==null||(is!=null&&is.getType()==Material.AIR)) {
				p.setItemOnCursor(is);
				inv.setItem(clickedSlot, is2);
			} else if(compare) {
				int used=stack-firstAmount;
				if(secondAmount<=used) {
					is.setAmount(firstAmount+secondAmount);
					p.setItemOnCursor(null);
				} else {
					is2.setAmount(secondAmount-used);
					is.setAmount(firstAmount+used);
					p.setItemOnCursor(is2);
				}
			} else if(!compare) {
				inv.setItem(clickedSlot, is2);
				p.setItemOnCursor(is);
			}
		} else if(type==ClickType.RIGHT) {
			if(is==null||(is!=null&&is.getType()==Material.AIR)) {
				p.setItemOnCursor(is);
				inv.setItem(clickedSlot, is2);
			} else if((is!=null&&is.getType()!=Material.AIR)&&(is2==null||(is2!=null&&is2.getType()==Material.AIR))) {
				ItemStack isClone=is.clone();
				isClone.setAmount(is.getAmount()%2==0 ? firstAmount-half : firstAmount-half-1);
				p.setItemOnCursor(isClone);
				
				is.setAmount(firstAmount-half);
			} else if(compare) {
				if((firstAmount+1)<=stack) {
					is2.setAmount(secondAmount-1);
					is.setAmount(firstAmount+1);
				}
			} else if(!compare) {
				inv.setItem(clickedSlot, is2);
				p.setItemOnCursor(is);
			}
		}
		if(((BrewerInventory) inv).getIngredient()==null) {
			return;
		}
		
		BrewingRecipe recipe=BrewingRecipe.getRecipe((BrewerInventory)inv);
		if(recipe==null) {
			return;
		}
		recipe.startBrewing((BrewerInventory)inv);
	}
}
