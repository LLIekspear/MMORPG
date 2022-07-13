package com.brewery;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.BrewingStand;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.main.Main;

public class BrewingRecipe {

	private final ItemStack ingredient;
	private final ItemStack fuel;
	private int fuelSet;
	private int fuelCharge;
	private BrewAction action;
	private BrewClock clock;
	private boolean perfect;
	
	public BrewingRecipe(ItemStack ingredient, ItemStack fuel, BrewAction action, boolean perfect, int fuelSet, int fuelCharge) {
		this.ingredient=ingredient;
		this.fuel=(fuel==null ? new ItemStack(Material.AIR) : fuel);
		this.setFuelSet(fuelSet);
		this.setFuelCharge(fuelCharge);
		this.action=action;
		this.perfect=perfect;
		Main.getInstance().recipes.add(this);
	}
	
	public BrewingRecipe(Material ingredient, BrewAction action) {
		this(new ItemStack(ingredient), null, action, true, 40, 0);
	}
	
	public ItemStack getIngredient() {
		return ingredient;
	}
	
	public ItemStack getFuel() {
		return fuel;
	}
	
	public BrewAction getAction() {
		return action;
	}
	
	public void setAction(BrewAction action) {
		this.action=action;
	}
	
	public BrewClock getClock() {
		return clock;
	}
	
	public void setClock(BrewClock clock) {
		//this.clock;
	}
	
	public boolean isPerfect() {
		return perfect;
	}
	
	public void setPerfect(boolean perfect) {
		this.perfect=perfect;
	}
	
	public static BrewingRecipe getRecipe(BrewerInventory inv) {
		for(BrewingRecipe recipe : Main.getInstance().recipes) {
			if(inv.getFuel()==null) {
				if(!recipe.isPerfect()&&inv.getIngredient().getType()==recipe.getIngredient().getType()) {
					return recipe;
				}
				if(recipe.isPerfect()&&inv.getIngredient().isSimilar(recipe.getIngredient())) {
					return recipe;
				}
			} else {
				if(!recipe.isPerfect()&&inv.getIngredient().getType()==recipe.getIngredient().getType()&&inv.getFuel().getType()==recipe.getIngredient().getType()) {
					return recipe;
				}
				if(recipe.isPerfect()&&inv.getIngredient().isSimilar(recipe.getIngredient()) &&inv.getFuel().isSimilar(recipe.getFuel())) {
					return recipe;
				}
			}
		}
		return null;
	}
	
	public void startBrewing(BrewerInventory inv) {
		clock=new BrewClock(this, inv, 400);
	}
	
	public int getFuelSet() {
		return fuelSet;
	}
	
	public void setFuelSet(int fuelSet) {
		this.fuelSet=fuelSet;
	}
	
	public int getFuelCharge() {
		return fuelCharge;
	}
	
	public void setFuelCharge(int fuelCharge) {
		this.fuelCharge=fuelCharge;
	}
	
	//Slot 0: 3 potion slot far left
	//Slot 1: 3 potion slot middle
	//slot 2: 3 potion slot far right
	//Slot 3: Ingredient
	//Slot 4: Fuel
	public class BrewClock extends BukkitRunnable {
		private BrewerInventory inv;
		private BrewingRecipe recipe;
		private ItemStack[] before;
		private BrewingStand stand;
		private int current;
		public BrewClock(BrewingRecipe recipe, BrewerInventory inv, int time) {
			this.recipe=recipe;
			this.inv=inv;
			this.stand=inv.getHolder();
			this.before=inv.getContents();
			this.current=time;
			runTaskTimer(Main.getInstance(), 0L, 1L);
		}
		
		@Override
		public void run() {
			if(current==0) {
				//set ingredient to 1 less than the current. Otherwise set to air
				if(inv.getIngredient().getAmount()>1) {
					ItemStack is=inv.getIngredient();
					is.setAmount(inv.getIngredient().getAmount()-1);
					inv.setIngredient(is);
				} else {
					inv.setIngredient(new ItemStack(Material.AIR));
				}
				//check the fuel in the recipe is more than 0, and exists
				ItemStack newFuel=recipe.getFuel();
				if(recipe.getFuel()!=null&&recipe.getFuel().getType()!=Material.AIR&&recipe.getFuel().getAmount()>0) {
					//count how much fuel should be taken away in order to fill the whole fuel bar
					int count=0;
					while(inv.getFuel().getAmount()>0&&stand.getFuelLevel()+recipe.fuelCharge<100) {
						stand.setFuelLevel(stand.getFuelLevel()+recipe.fuelSet);
						++count;
					}
					//if fuel in the inv is 0, set it to air
					if(inv.getFuel().getAmount()==0) {
						newFuel=new ItemStack(Material.AIR);
					} else {
						//otherwise, set the percent of fuel level to 100 and update the count of the fuel
						stand.setFuelLevel(100);
						newFuel.setAmount(inv.getFuel().getAmount()-count);
					}
				} else {
					newFuel=new ItemStack(Material.AIR);
				}
				inv.setFuel(newFuel);
				//brew recipe for each item put in
				for(int i=0; i<3; ++i) {
					if(inv.getItem(i)==null||inv.getItem(i).getType()==Material.AIR) {
						continue;
					}
					recipe.getAction().brew(inv, inv.getItem(i), ingredient);
				}
				//set the fuel level
				stand.setFuelLevel(stand.getFuelLevel()-recipe.fuelCharge);
				cancel();
				return;
			}
			
			//if a player drags an item, fuel, or any contents, reset it
			if(searchChanged(before, inv.getContents(), perfect)) {
				cancel();
				return;
			}
			
			//decrement, set brewing time and update the stand
			--current;
			stand.setBrewingTime(current);
			stand.update(true);
			
		}
		//check if any slots were changed
		public boolean searchChanged(ItemStack[] before, ItemStack[] after, boolean mode) {
			for(int i=0; i<before.length; ++i) {
				if((before[i]!=null&&after[i]==null)||(before[i]==null&&after[i]!=null)) {
					return false;
				} else {
					if(mode&&!before[i].isSimilar(after[i])) {
						return false;
					} else if(!mode&&!(before[i].getType()==after[i].getType())) {
						return false;
					}
				}
			}
			return true;
		}
	}
}
