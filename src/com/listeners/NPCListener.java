package com.listeners;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;
import com.main.Main;

public class NPCListener implements Listener {

	Inventory inv=null;
	Inventory inv2=null;
	private static Main plugin=Main.getInstance();
	private HashMap<Integer, String> events=new HashMap<Integer, String>();
	private boolean isFirst=true;
	
	public void createInventory2() {
		inv2=Bukkit.createInventory(null, 45, "�������");
		
		List<ItemStack> items=new ArrayList<ItemStack>();
		items.add(new ItemStack(Material.DIAMOND_HELMET));
		items.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
		items.add(new ItemStack(Material.DIAMOND_LEGGINGS));
		items.add(new ItemStack(Material.DIAMOND_BOOTS));
		items.add(new ItemStack(Material.DIAMOND_SWORD));
		items.add(new ItemStack(Material.DIAMOND_PICKAXE));
		items.add(new ItemStack(Material.DIAMOND_AXE));
		items.add(new ItemStack(Material.DIAMOND_SHOVEL));
		items.add(new ItemStack(Material.GOLDEN_HELMET));
		items.add(new ItemStack(Material.GOLDEN_CHESTPLATE));
		items.add(new ItemStack(Material.GOLDEN_LEGGINGS));
		items.add(new ItemStack(Material.GOLDEN_BOOTS));
		items.add(new ItemStack(Material.GOLDEN_SWORD));
		items.add(new ItemStack(Material.GOLDEN_PICKAXE));
		items.add(new ItemStack(Material.GOLDEN_AXE));
		items.add(new ItemStack(Material.GOLDEN_SHOVEL));
		items.add(new ItemStack(Material.IRON_HELMET));
		items.add(new ItemStack(Material.IRON_CHESTPLATE));
		items.add(new ItemStack(Material.IRON_LEGGINGS));
		items.add(new ItemStack(Material.IRON_BOOTS));
		items.add(new ItemStack(Material.IRON_SWORD));
		items.add(new ItemStack(Material.IRON_PICKAXE));
		items.add(new ItemStack(Material.IRON_AXE));
		items.add(new ItemStack(Material.IRON_SHOVEL));
		items.add(new ItemStack(Material.BOW));
		items.add(new ItemStack(Material.CROSSBOW));
		
		List<String> prefixes=new ArrayList<String>();
		prefixes.add(ChatColor.WHITE+"�������");
		prefixes.add(ChatColor.GREEN+"������");
		prefixes.add(ChatColor.AQUA+"�����������");
		prefixes.add(ChatColor.GOLD+"����������");
		prefixes.add(ChatColor.RED+"���������");
		Collections.shuffle(items);
		Random rg=new Random();
		int amount=rg.nextInt(items.size());
		
		for(int i=0; i<amount; ++i) {
			ItemStack item=randomEnchantment(items.get(i));
			Collections.shuffle(prefixes);
			ItemMeta meta=item.getItemMeta();
			Random rg1=new Random();
			int price=0;
			if(item.getEnchantments().size()==0) {
				meta.setDisplayName(prefixes.get(0)+" "+item.getItemMeta().getDisplayName());
				price=(rg1.nextInt(15000-8000)+8000);
			} else if(item.getEnchantments().size()>0&&item.getEnchantments().size()<=2) {
				meta.setDisplayName(prefixes.get(1)+" "+item.getItemMeta().getDisplayName());
				price=(rg1.nextInt(17000-10000)+10000)+item.getEnchantments().size()*2000;
			} else if(item.getEnchantments().size()>2&&item.getEnchantments().size()<=4) {
				meta.setDisplayName(prefixes.get(2)+" "+item.getItemMeta().getDisplayName());
				price=(rg1.nextInt(30000-20000)+20000)+item.getEnchantments().size()*2000;
			} else if(item.getEnchantments().size()>4&&item.getEnchantments().size()<=6) {
				meta.setDisplayName(prefixes.get(3)+" "+item.getItemMeta().getDisplayName());
				price=(rg1.nextInt(35000-25000)+25000)+item.getEnchantments().size()*2000;
			} else if(item.getEnchantments().size()>6) {
				meta.setDisplayName(prefixes.get(4)+" "+item.getItemMeta().getDisplayName());
				price=(rg1.nextInt(60000-40000)+40000)+item.getEnchantments().size()*2000;
			}
			List<String> lore=new ArrayList<String>();
			lore.add(ChatColor.GOLD+""+ChatColor.BOLD+"��������� ��������: "+Integer.toString(price));
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv2.addItem(item);
		}
		
	}
	
	public ItemStack randomEnchantment(ItemStack item) {
		List<Enchantment> enchans=new ArrayList<Enchantment>();
		for(Enchantment ench : Enchantment.values()) {
			if(ench.canEnchantItem(item)) {
				enchans.add(ench);
			}
		}
		
		if(enchans.size()>=1) {
			Collections.shuffle(enchans);
			Random rg=new Random();
			int amount=rg.nextInt(enchans.size());
			for(int i=0; i<amount; ++i) {
				Enchantment chosen=enchans.get(i);
				if(!(chosen.equals(Enchantment.BINDING_CURSE))&&!(chosen.equals(Enchantment.VANISHING_CURSE)))
					item.addEnchantment(chosen, 1+(int) (Math.random()*((chosen.getMaxLevel()-1)+1)));
			}
		}
		return item;
	}
	
	public void createInventory() {
		inv=Bukkit.createInventory(null, 45, "�������������");
		
		ItemStack gold=createItem(ChatColor.GOLD+"������� ������", Material.GOLD_INGOT);
		ItemStack emerald=createItem(ChatColor.GREEN+"�������", Material.EMERALD);
		ItemStack wheat=createItem(ChatColor.YELLOW+"�������", Material.WHEAT);
		ItemStack sugar=createItem(ChatColor.WHITE+"�����", Material.SUGAR);
		ItemStack beef=createItem(ChatColor.RED+"��������", Material.BEEF);
		ItemStack pork=createItem(ChatColor.LIGHT_PURPLE+"�������", Material.PORKCHOP);
		ItemStack carrot=createItem(ChatColor.GREEN+"��������", Material.CARROT);
		ItemStack potato=createItem(ChatColor.GREEN+"���������", Material.POTATO);
		ItemStack leather=createItem(ChatColor.GOLD+"����", Material.LEATHER);
		ItemStack wood=createItem(ChatColor.DARK_GREEN+"���������", Material.OAK_LOG);
		ItemStack coal=createItem(ChatColor.BLACK+"�����", Material.COAL);
		ItemStack redstone=createItem(ChatColor.RED+"������� ����", Material.REDSTONE);
		isFirst=false;
		inv.setItem(0, gold);
		inv.setItem(1, emerald);
		inv.setItem(2, wheat);
		inv.setItem(3, sugar);
		inv.setItem(4, beef);
		inv.setItem(5, pork);
		inv.setItem(6, carrot);
		inv.setItem(7, potato);
		inv.setItem(8, leather);
		inv.setItem(9, wood);
		inv.setItem(10, coal);
		inv.setItem(11, redstone);
		events.put(0, "����������");
		events.put(1, "������������");
		events.put(2, "��������");
		events.put(3, "�����������");
		events.put(4, "��������C����");
		events.put(5, "����������");
//		events.put(6, "");
	}
	
	public void chooseEvent() {
		Random rg=new Random();
		int len=events.size()-1;
		int randomInt=rg.nextInt(len);
		
		setupEvent(randomInt);
	}
	
	public void setupEvent(int index) {
		Random rg=new Random();
		float randomFloat=rg.nextFloat()/10; //��� �������� �� 100, ������� �����������
		
		if(index==0) {
			Bukkit.broadcastMessage(ChatColor.GOLD+"[�������]"+ChatColor.RED+"���������� ������ �����������! ����������� �������� � ��������� ������, �������, �������, ���������!");
			//���� ��� �� ������������� LOG
			String info[]=configGet("SUGAR").split(" ");
			float price=Float.parseFloat(info[0]);
			int amount=Integer.parseInt(info[1]);
			price+=price*randomFloat;
			configSet("SUGAR", "price", price, amount);
			
			randomFloat=rg.nextFloat()/10;
			info=configGet("WHEAT").split(" ");
			price=Float.parseFloat(info[0]);
			amount=Integer.parseInt(info[1]);
			price+=price*randomFloat;
			configSet("WHEAT", "price", price, amount);
			
			randomFloat=rg.nextFloat()/10;
			info=configGet("CARROT").split(" ");
			price=Float.parseFloat(info[0]);
			amount=Integer.parseInt(info[1]);
			price+=price*randomFloat;
			configSet("CARROT", "price", price, amount);
			
			randomFloat=rg.nextFloat()/10;
			info=configGet("POTATO").split(" ");
			price=Float.parseFloat(info[0]);
			amount=Integer.parseInt(info[1]);
			price+=price*randomFloat;
			configSet("POTATO", "price", price, amount);
			
		} else if(index==1) {
			Bukkit.broadcastMessage(ChatColor.GOLD+"[�������]"+ChatColor.RED+"����������� ������ ������! ������� �������� � ��������� ���������!");
			//���� ��� �� ���������
			String info[]=configGet("OAK_LOG").split(" ");
			float price=Float.parseFloat(info[0]);
			int amount=Integer.parseInt(info[1]);
			price+=price*randomFloat;
			configSet("OAK_LOG", "price", price, amount);

		} else if(index==2) {
			Bukkit.broadcastMessage(ChatColor.GOLD+"[�������]"+ChatColor.RED+"���������� ������������ ������� ������� ��������!");
			//���� ��� �� �������� � ������
			String info[]=configGet("gold").split(" ");
			float price=Float.parseFloat(info[0]);
			int amount=Integer.parseInt(info[1]);
			price+=price*randomFloat;
			configSet("gold", "price", price, amount);
			
			randomFloat=rg.nextFloat()/10;
			info=configGet("emerald").split(" ");
			price=Float.parseFloat(info[0]);
			amount=Integer.parseInt(info[1]);
			price+=price*randomFloat;
			configSet("emerald", "price", price, amount);
		} else if(index==3) {
			Bukkit.broadcastMessage(ChatColor.GOLD+"[�������]"+ChatColor.GREEN+"������������� ������� �����������!");
			//������� ��� �� �����, �������, �������, ���������
			String info[]=configGet("SUGAR").split(" ");
			float price=Float.parseFloat(info[0]);
			int amount=Integer.parseInt(info[1]);
			price-=price*randomFloat;
			if(price<=0)
				price=10;
			configSet("SUGAR", "price", price, amount);
			
			randomFloat=rg.nextFloat()/10;
			info=configGet("WHEAT").split(" ");
			price=Float.parseFloat(info[0]);
			amount=Integer.parseInt(info[1]);
			price-=price*randomFloat;
			if(price<=0)
				price=10;
			configSet("WHEAT", "price", price, amount);
			
			randomFloat=rg.nextFloat()/10;
			info=configGet("CARROT").split(" ");
			price=Float.parseFloat(info[0]);
			amount=Integer.parseInt(info[1]);
			price-=price*randomFloat;
			if(price<=0)
				price=10;
			configSet("CARROT", "price", price, amount);
			
			randomFloat=rg.nextFloat()/10;
			info=configGet("POTATO").split(" ");
			price=Float.parseFloat(info[0]);
			amount=Integer.parseInt(info[1]);
			price-=price*randomFloat;
			if(price<=0)
				price=10;
			configSet("POTATO", "price", price, amount);
		} else if(index==4) {
			Bukkit.broadcastMessage(ChatColor.GOLD+"[�������]"+ChatColor.RED+"�������� ������� �������� �����!");
			//���� ��� �� ���� � ����
			String info[]=configGet("PORKCHOP").split(" ");
			float price=Float.parseFloat(info[0]);
			int amount=Integer.parseInt(info[1]);
			price+=price*randomFloat;
			configSet("PORKCHOP", "price", price, amount);
			
			randomFloat=rg.nextFloat()/10;
			info=configGet("BEEF").split(" ");
			price=Float.parseFloat(info[0]);
			amount=Integer.parseInt(info[1]);
			price+=price*randomFloat;
			configSet("BEEF", "price", price, amount);
			
			randomFloat=rg.nextFloat()/10;
			info=configGet("LEATHER").split(" ");
			price=Float.parseFloat(info[0]);
			amount=Integer.parseInt(info[1]);
			price+=price*randomFloat;
			configSet("LEATHER", "price", price, amount);
		} else if(index==5) {
			Bukkit.broadcastMessage(ChatColor.GOLD+"[�������]"+ChatColor.RED+"����������� �� ���� ��������� ��� ���������� ������!");
			//���� ��� �� �������� � �����
			String info[]=configGet("REDSTONE").split(" ");
			float price=Float.parseFloat(info[0]);
			int amount=Integer.parseInt(info[1]);
			price+=price*randomFloat;
			configSet("REDSTONE", "price", price, amount);
			
			randomFloat=rg.nextFloat()/10;
			info=configGet("COAL").split(" ");
			price=Float.parseFloat(info[0]);
			amount=Integer.parseInt(info[1]);
			price+=price*randomFloat;
			configSet("COAL", "price", price, amount);
		}
		update();
	}
	
	public ItemStack createItem(String name, Material mat) {
		ItemStack item=new ItemStack(mat);
		ItemMeta meta=item.getItemMeta();
		String desc=ChatColor.RED+""+ChatColor.BOLD+"��� - �������";
		String desc1=ChatColor.GREEN+""+ChatColor.BOLD+"��� - ������";
		String desc3=ChatColor.RED+""+ChatColor.BOLD+"������� � ���� ��� �������!";
		meta.setDisplayName(name);
		String[] info=null;
		String buyPrice="";
		String amount="";
		if(mat==Material.GOLD_INGOT) {
			info=configGetGoldInfo().split(" ");
			buyPrice="���������: "+info[0]+" �������.";
			amount="����� �������� ��� ������ "+info[1]+" ����.";
		} else if(mat==Material.EMERALD) {
			info=configGetEmeraldInfo().split(" ");
			buyPrice="��������� �� "+info[0]+" �������.";
			amount="����� �������� ��� ������ "+info[1]+" ����.";
		} else {
			info=configGet(mat.name()).split(" ");
			buyPrice="���������: "+info[0]+" �������.";
			amount="����� �������� ��� ������ "+info[1]+" ����.";
		}
		if(isFirst==true)
			configSet(mat.name(), "openPrice", Float.parseFloat(info[0]), Integer.parseInt(info[1]));
		else {
			String openPrice=configGet(mat.name()).split(" ")[2];
			if(Float.parseFloat(info[0])>Float.parseFloat(openPrice)) {
				float percent=((Float.parseFloat(info[0])-Float.parseFloat(openPrice))*100)/Float.parseFloat(info[0]);
				buyPrice=ChatColor.GREEN+buyPrice+" (+"+Float.toString(percent)+"%)";
			} else if(Float.parseFloat(info[0])<Float.parseFloat(openPrice)) {
				float percent=((Float.parseFloat(openPrice)-Float.parseFloat(info[0]))*100)/Float.parseFloat(openPrice);
				buyPrice=ChatColor.RED+buyPrice+" (-"+Float.toString(percent)+"%)";
			}
		}
		meta.setLore(Lists.newArrayList(buyPrice/*, sellPrice*/, amount, "-----------", desc, desc1, desc3));
		item.setItemMeta(meta);
		return item;
	}
	
	protected static void configSet(String mat, String path, Object value, Object amount) {
		try { 
			File dataFile=new File(plugin.getDataFolder()+"/Exchange/"+mat+".yml");
			FileConfiguration dataConfig=YamlConfiguration.loadConfiguration(dataFile);
			dataConfig.set(path, value);
			dataConfig.set("amount", amount);
			dataConfig.save(dataFile);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	protected static String configGet(String mat) {
		File directory=new File(plugin.getDataFolder(), "/Exchange/");
		File dataFile=new File(directory, mat+".yml");
		FileConfiguration dataConfig=YamlConfiguration.loadConfiguration(dataFile);
		float price=(float)dataConfig.getDouble("price");
		int amount=dataConfig.getInt("amount");
		float openPrice=(float)dataConfig.getDouble("openPrice");
		String result=Float.toString(price)+" "+Integer.toString(amount)+" "+Float.toString(openPrice);
		return result;
	}
	
	protected static String configGetGoldInfo() {
		File directory=new File(plugin.getDataFolder(), "/Exchange/");
		File dataFile=new File(directory, "gold.yml");
		FileConfiguration dataConfig=YamlConfiguration.loadConfiguration(dataFile);
		float buyPrice=(float)dataConfig.getDouble("price");
		int amount=dataConfig.getInt("amount");
		String result=Float.toString(buyPrice)+" "+Integer.toString(amount);
		return result;
	}
	
	protected static String configGetEmeraldInfo() {
		File directory=new File(plugin.getDataFolder(), "/Exchange/");
		File dataFile=new File(directory, "emerald.yml");
		FileConfiguration dataConfig=YamlConfiguration.loadConfiguration(dataFile);
		float buyPrice=(float)dataConfig.getDouble("price");
		int amount=dataConfig.getInt("amount");
		String result=Float.toString(buyPrice)+" "+Integer.toString(amount);
		return result;
	}
	
	@EventHandler
	public void DamagedVillager(EntityDamageByEntityEvent e) {
		try {
			if(e.getEntity().getCustomName()=="�������������"&&!(e.getDamager().isOp())) {
				e.setCancelled(true);
			} else if(e.getEntity().getCustomName()=="����"&&!(e.getDamager().isOp())) {
				e.setCancelled(true);
			} else if(e.getEntity().getCustomName()=="�������"&&!(e.getDamager().isOp())) {
				e.setCancelled(true);
			}
		} catch(Exception error) {
			//
		}
	}
	
	public void update() {
		ItemStack gold=createItem(ChatColor.GOLD+"������� ������", Material.GOLD_INGOT);
		ItemStack emerald=createItem(ChatColor.GREEN+"�������", Material.EMERALD);
		ItemStack wheat=createItem(ChatColor.YELLOW+"�������", Material.WHEAT);
		ItemStack sugar=createItem(ChatColor.WHITE+"�����", Material.SUGAR);
		ItemStack beef=createItem(ChatColor.RED+"��������", Material.BEEF);
		ItemStack pork=createItem(ChatColor.LIGHT_PURPLE+"�������", Material.PORKCHOP);
		ItemStack carrot=createItem(ChatColor.GREEN+"��������", Material.CARROT);
		ItemStack potato=createItem(ChatColor.GREEN+"���������", Material.POTATO);
		ItemStack leather=createItem(ChatColor.GOLD+"����", Material.LEATHER);
		ItemStack wood=createItem(ChatColor.DARK_GREEN+"���������", Material.OAK_LOG);
		ItemStack coal=createItem(ChatColor.BLACK+"�����", Material.COAL);
		ItemStack redstone=createItem(ChatColor.RED+"������� ����", Material.REDSTONE);
		
		inv.setItem(0, gold);
		inv.setItem(1, emerald);
		inv.setItem(2, wheat);
		inv.setItem(3, sugar);
		inv.setItem(4, beef);
		inv.setItem(5, pork);
		inv.setItem(6, carrot);
		inv.setItem(7, potato);
		inv.setItem(8, leather);
		inv.setItem(9, wood);
		inv.setItem(10, coal);
		inv.setItem(11, redstone);
	}
	
	public void updateGold() {
		ItemStack gold=createItem(ChatColor.GOLD+"������� ������", Material.GOLD_INGOT);
		ItemStack emerald=createItem(ChatColor.GREEN+"�������", Material.EMERALD);
		
		inv.setItem(0, gold);
		inv.setItem(1, emerald);
	}
	
	public void updateEmerald() {
		ItemStack gold=createItem(ChatColor.GOLD+"������� ������", Material.GOLD_INGOT);
		ItemStack emerald=createItem(ChatColor.GREEN+"�������", Material.EMERALD);
		
		inv.setItem(0, gold);
		inv.setItem(1, emerald);
	}
	
	@EventHandler
	public void onInvClick1(InventoryClickEvent e) {
		Player p=(Player)e.getWhoClicked();
		if(!(e.getView().getTitle().equals("�������")))
			return;
		if(e.getView().getTitle().equals("�������")) {
//			p.sendMessage("����");
			e.setCancelled(true);
			try {
				int price=Integer.parseInt(e.getCurrentItem().getItemMeta().getLore().get(0).replace(ChatColor.GOLD+""+ChatColor.BOLD+"��������� ��������: ", "").toString());
				double player_balance=Main.economy.getBalance(p);
				if(player_balance>=price) {
					Main.economy.withdrawPlayer(p, price);
					p.sendMessage(ChatColor.GREEN+"�� ������� ��������� �������!");
					ItemStack item=e.getCurrentItem();
					ItemMeta meta=item.getItemMeta();
					List<String> lore=new ArrayList<String>();
					lore.add(ChatColor.RED+"�������� "+p.getName()+" � ��������");
					meta.setLore(lore);
					item.setItemMeta(meta);
					p.getInventory().addItem(item);
				} else {
					p.sendMessage(ChatColor.RED+"� ��� ������������ �������!");
				}
			} catch(Exception error) {
				error.printStackTrace();
			}
		}
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p=(Player)e.getWhoClicked();
		if(!(e.getView().getTitle().equals("�������������")))
			return;
		if(e.getView().getTitle().equals("�������������")) {
			e.setCancelled(true);
		}
		
		if(e.getSlot()==0) {
			String info[]=configGetGoldInfo().split(" ");
			float price=Float.parseFloat(info[0]);
			int amount=Integer.parseInt(info[1]);
			double player_balance = Main.economy.getBalance(p);
			Random rg=new Random();
			int len=1000;
			int randomInt=rg.nextInt(len);
			Random rg_event=new Random();
			double randomEvent=rg_event.nextDouble();
			if(e.isRightClick()) {
				//buy
				if(amount>=50) {
					if((player_balance-price)>0) {
						player_balance=player_balance-price;
						--amount;
						p.sendMessage(ChatColor.GREEN+"�� ������� ��������� ������ ������! ������ ��� ������: "+Double.toString(player_balance));
						price+=(randomInt/100);
						configSet("gold", "price", price, amount);
						p.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
						Main.economy.withdrawPlayer(p, price);
						updateGold();
						if(randomEvent<=0.025)
							chooseEvent();
					} else {
						p.sendMessage(ChatColor.RED+"� ��� ������������ �������!");
					}
				} else {
					p.sendMessage(ChatColor.RED+"����������� �������� ������� �������, ������� ��������������!");
				}
			} else if(e.isLeftClick()) {
				//sell
				if(p.getInventory().getItemInMainHand().getType().equals(Material.GOLD_INGOT)) {
					p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
					/*if(amount<=30)
						price+=10;*/
					++amount;
					player_balance+=price;
					price-=randomInt/100;
					if(price<=10)
						price=30;
					configSet("gold", "price", price, amount);
					p.sendMessage(ChatColor.GREEN+"�� ������� ������� ������ ������! ������ ��� ������: "+Double.toString(player_balance));
					Main.economy.depositPlayer(p, price);
					updateGold();
					if(randomEvent<=0.025)
						chooseEvent();
				} else {
					p.sendMessage(ChatColor.RED+"�� �� ������� � ����� ������� ������!");
				}	
			}
		} else if(e.getSlot()==1) {
			String info[]=configGetEmeraldInfo().split(" ");
			//emer
			float price=Float.parseFloat(info[0]);
			int amount=Integer.parseInt(info[1]);
			double player_balance = Main.economy.getBalance(p);
			Random rg=new Random();
			int len=1000;
			int randomInt=rg.nextInt(len);
			Random rg_event=new Random();
			double randomEvent=rg_event.nextDouble();
			if(e.isRightClick()) {
				//buy
				if(amount>=10) {
					if((player_balance-price)>0) {
						player_balance=player_balance-price;
						--amount;
						p.sendMessage(ChatColor.GREEN+"�� ������� ��������� �������! ������ ��� ������: "+Double.toString(player_balance));
						price+=(randomInt/100);
						configSet("emerald", "price", price, amount);
						p.getInventory().addItem(new ItemStack(Material.EMERALD));
						Main.economy.withdrawPlayer(p, price);
						updateEmerald();
						if(randomEvent<=0.025)
							chooseEvent();
					} else {
						p.sendMessage(ChatColor.RED+"� ��� ������������ �������!");
					}
				} else {
					p.sendMessage(ChatColor.RED+"����������� �������� ���������, ������� ��������������!");
				}
			} else if(e.isLeftClick()) {
				//sell
				if(p.getInventory().getItemInMainHand().getType().equals(Material.EMERALD)) {
					p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
					/*if(amount<=10)
						price+=50;*/
					++amount;
					player_balance+=price;
					price-=randomInt/100;
					if(price<=100)
						price=200;
					configSet("emerald", "price", price, amount);
					p.sendMessage(ChatColor.GREEN+"�� ������� ������� �������! ������ ��� ������: "+Double.toString(player_balance));
					Main.economy.depositPlayer(p, price);
					updateEmerald();
					if(randomEvent<=0.025)
						chooseEvent();
				} else {
					p.sendMessage(ChatColor.RED+"�� �� ������� � ����� ��������!");
				}	
			}
		} else {
			try {
				String mat=e.getCurrentItem().getType().name();
				String info[]=configGet(mat).split(" ");
				float price=Float.parseFloat(info[0]);
				int amount=Integer.parseInt(info[1]);
				double player_balance = Main.economy.getBalance(p);
				Random rg=new Random();
				int len=1000;
				int randomInt=rg.nextInt(len);
				Random rg_event=new Random();
				double randomEvent=rg_event.nextDouble();
				if(e.isRightClick()) {
					if(amount>=20) {
						if(player_balance-price>0) {
							player_balance-=price;
							--amount;
							p.sendMessage(ChatColor.GREEN+"�� ������� ��������� ������, ��� ������: "+Double.toString(player_balance));
							price+=(randomInt/100);
							configSet(mat, "price", price, amount);
							p.getInventory().addItem(new ItemStack(e.getCurrentItem().getType()));
							Main.economy.withdrawPlayer(p, price);
							update();
							if(randomEvent<=0.025)
								chooseEvent();
						} else {
							p.sendMessage(ChatColor.RED+"� ��� ������������ ����� ��� ���������� ������!");
						}
					} else {
						p.sendMessage(ChatColor.RED+"����������� �������� ������� �������, ������� ��������������!");
					}
				} else if(e.isLeftClick()) {
					if(p.getInventory().getItemInMainHand().getType().equals(e.getCurrentItem().getType())) {
						p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
						++amount;
						player_balance+=price;
						price-=randomInt/100;
						if(price<=10)
							price=10;
						configSet(e.getCurrentItem().getType().name(), "price", price, amount);
						p.sendMessage(ChatColor.GREEN+"�� ������� ��������� ������! ������ ��� ������: "+Double.toString(player_balance));
						Main.economy.depositPlayer(p, price);
						update();
						if(randomEvent<=0.025)
							chooseEvent();
					} else {
						p.sendMessage(ChatColor.RED+"�� �� ������� � ����� ������ ������!");
					}
				}
			} catch(Exception error) {
				//
			}
		}
	}
	
	@EventHandler
	public void RightClick(PlayerInteractEntityEvent e) {
		Player p=e.getPlayer();
		if(e.getRightClicked().getType()==EntityType.VILLAGER) {
			Villager villager=(Villager)e.getRightClicked();
			if(villager.getProfession()==Villager.Profession.MASON&&villager.getCustomName().equals("�������������")) {
				e.setCancelled(true);
				if(inv==null) {
					createInventory();
				}
				p.openInventory(inv);
			} else if(villager.getProfession()==Villager.Profession.LIBRARIAN&&villager.getCustomName().equals("����")) {
				e.setCancelled(true);
				ItemStack stack=null;
				stack=new ItemStack(Material.COD);
				ItemMeta meta=stack.getItemMeta();
				meta.setDisplayName("����");
				meta.setLore(Lists.newArrayList(ChatColor.AQUA+""+ChatColor.BOLD+"����", "23��", "2��"));
				stack.setItemMeta(meta);
				
				try {
					if(p.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).equals(meta.getLore().get(0))) {
						ItemStack item=p.getInventory().getItemInMainHand();
						String size=p.getInventory().getItemInMainHand().getItemMeta().getLore().get(1);
						String weight=p.getInventory().getItemInMainHand().getItemMeta().getLore().get(2);
						int sizeI=Integer.parseInt(size.replaceAll(" ��", ""));
						int weightI=Integer.parseInt(weight.replaceAll(" ��", ""));
						double profit=sizeI*0.3+weightI*0.3;
						Main.economy.depositPlayer(p, profit);
						item.setAmount(item.getAmount()-1);
						p.sendMessage(ChatColor.GREEN+"�� ������� ������� ����! � ���������� "+ChatColor.GOLD+""+ChatColor.BOLD+Double.toString(profit)+".");
					} else {
						p.sendMessage(ChatColor.RED+"� ���� ������ ���� ����!");
					}
				} catch(NullPointerException error) {
					//
				}
			} else if(villager.getProfession()==Villager.Profession.SHEPHERD&&villager.getCustomName().equals("�������")) {
				e.setCancelled(true);
				if(inv2==null) {
					createInventory2();
				}
				p.openInventory(inv2);
			}
		}
	}
}
