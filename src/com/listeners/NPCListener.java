package com.listeners;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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

//	private Economy economy;
	Inventory inv=null;
	private static Main plugin=Main.getInstance();
	private HashMap<Integer, String> events=new HashMap<Integer, String>();
	private boolean isFirst=true;
	
	public void createInventory() {
		inv=Bukkit.createInventory(null, 45, "ТоварнаяБиржа");
		
		ItemStack gold=createItem(ChatColor.GOLD+"Золотой слиток", Material.GOLD_INGOT);
		ItemStack emerald=createItem(ChatColor.GREEN+"Изумруд", Material.EMERALD);
		ItemStack wheat=createItem(ChatColor.YELLOW+"Пшеница", Material.WHEAT);
		ItemStack sugar=createItem(ChatColor.WHITE+"Сахар", Material.SUGAR);
		ItemStack beef=createItem(ChatColor.RED+"Говядина", Material.BEEF);
		ItemStack pork=createItem(ChatColor.LIGHT_PURPLE+"Свинина", Material.PORKCHOP);
		ItemStack carrot=createItem(ChatColor.GREEN+"Морковка", Material.CARROT);
		ItemStack potato=createItem(ChatColor.GREEN+"Картофель", Material.POTATO);
		ItemStack leather=createItem(ChatColor.GOLD+"Кожа", Material.LEATHER);
		ItemStack wood=createItem(ChatColor.DARK_GREEN+"Древесина", Material.OAK_LOG);
		ItemStack coal=createItem(ChatColor.BLACK+"Уголь", Material.COAL);
		ItemStack redstone=createItem(ChatColor.RED+"Красная пыль", Material.REDSTONE);
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
		events.put(0, "Разбойники");
		events.put(1, "ЛесныеПожары");
		events.put(2, "Инфляция");
		events.put(3, "Урожайность");
		events.put(4, "ЭпидемияCкота");
		events.put(5, "КризисШахт");
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
		double randomDouble=rg.nextDouble()/10; //уже поделили на 100, готовый коэффициент
		
		if(index==0) {
			Bukkit.broadcastMessage(ChatColor.GOLD+"[НОВОСТИ]"+ChatColor.RED+"Участились набеги разбойников! Наблюдаются проблемы с поставкой сахара, пшеницы, моркови, картофеля!");
			//рост цен на перечисленное LOG
			String info[]=configGet("SUGAR").split(" ");
			double price=Double.parseDouble(info[0]);
			int amount=Integer.parseInt(info[1]);
			price+=price*randomDouble;
			configSet("SUGAR", "price", price, amount);
			
			randomDouble=rg.nextDouble()/10;
			info=configGet("WHEAT").split(" ");
			price=Double.parseDouble(info[0]);
			amount=Integer.parseInt(info[1]);
			price+=price*randomDouble;
			configSet("WHEAT", "price", price, amount);
			
			randomDouble=rg.nextDouble()/10;
			info=configGet("CARROT").split(" ");
			price=Double.parseDouble(info[0]);
			amount=Integer.parseInt(info[1]);
			price+=price*randomDouble;
			configSet("CARROT", "price", price, amount);
			
			randomDouble=rg.nextDouble()/10;
			info=configGet("POTATO").split(" ");
			price=Double.parseDouble(info[0]);
			amount=Integer.parseInt(info[1]);
			price+=price*randomDouble;
			configSet("POTATO", "price", price, amount);
			
		} else if(index==1) {
			Bukkit.broadcastMessage(ChatColor.GOLD+"[НОВОСТИ]"+ChatColor.RED+"Наблюдаются лесные пожары! Имеются проблемы с поставкой древесины!");
			//рост цен на древесину
			String info[]=configGet("LOG").split(" ");
			double price=Double.parseDouble(info[0]);
			int amount=Integer.parseInt(info[1]);
			price+=price*randomDouble;
			configSet("OAK_LOG", "price", price, amount);

		} else if(index==2) {
			Bukkit.broadcastMessage(ChatColor.GOLD+"[НОВОСТИ]"+ChatColor.RED+"Экономисты прогнозируют высокий уровень инфляции!");
			//рост цен на изумруды и золото
			String info[]=configGet("gold").split(" ");
			double price=Double.parseDouble(info[0]);
			int amount=Integer.parseInt(info[1]);
			price+=price*randomDouble;
			configSet("gold", "price", price, amount);
			
			randomDouble=rg.nextDouble()/10;
			info=configGet("emerald").split(" ");
			price=Double.parseDouble(info[0]);
			amount=Integer.parseInt(info[1]);
			price+=price*randomDouble;
			configSet("emerald", "price", price, amount);
		} else if(index==3) {
			Bukkit.broadcastMessage(ChatColor.GOLD+"[НОВОСТИ]"+ChatColor.GREEN+"Зафиксирована высокая урожайность!");
			//падение цен на сахар, пшеницу, морковь, картофель
			String info[]=configGet("SUGAR").split(" ");
			double price=Double.parseDouble(info[0]);
			int amount=Integer.parseInt(info[1]);
			price-=price*randomDouble;
			if(price<=0)
				price=10;
			configSet("SUGAR", "price", price, amount);
			
			randomDouble=rg.nextDouble()/10;
			info=configGet("WHEAT").split(" ");
			price=Double.parseDouble(info[0]);
			amount=Integer.parseInt(info[1]);
			price-=price*randomDouble;
			if(price<=0)
				price=10;
			configSet("WHEAT", "price", price, amount);
			
			randomDouble=rg.nextDouble()/10;
			info=configGet("CARROT").split(" ");
			price=Double.parseDouble(info[0]);
			amount=Integer.parseInt(info[1]);
			price-=price*randomDouble;
			if(price<=0)
				price=10;
			configSet("CARROT", "price", price, amount);
			
			randomDouble=rg.nextDouble()/10;
			info=configGet("POTATO").split(" ");
			price=Double.parseDouble(info[0]);
			amount=Integer.parseInt(info[1]);
			price-=price*randomDouble;
			if(price<=0)
				price=10;
			configSet("POTATO", "price", price, amount);
		} else if(index==4) {
			Bukkit.broadcastMessage(ChatColor.GOLD+"[НОВОСТИ]"+ChatColor.RED+"Замечена вспышка эпидемии скота!");
			//рост цен на мясо и кожу
			String info[]=configGet("PORKCHOP").split(" ");
			double price=Double.parseDouble(info[0]);
			int amount=Integer.parseInt(info[1]);
			price+=price*randomDouble;
			configSet("PORKCHOP", "price", price, amount);
			
			randomDouble=rg.nextDouble()/10;
			info=configGet("BEEF").split(" ");
			price=Double.parseDouble(info[0]);
			amount=Integer.parseInt(info[1]);
			price+=price*randomDouble;
			configSet("BEEF", "price", price, amount);
			
			randomDouble=rg.nextDouble()/10;
			info=configGet("LEATHER").split(" ");
			price=Double.parseDouble(info[0]);
			amount=Integer.parseInt(info[1]);
			price+=price*randomDouble;
			configSet("LEATHER", "price", price, amount);
		} else if(index==5) {
			Bukkit.broadcastMessage(ChatColor.GOLD+"[НОВОСТИ]"+ChatColor.RED+"Специалисты всё чаще упоминают про шахтинский кризис!");
			//рост цен на редстоун и уголь
			String info[]=configGet("REDSTONE").split(" ");
			double price=Double.parseDouble(info[0]);
			int amount=Integer.parseInt(info[1]);
			price+=price*randomDouble;
			configSet("REDSTONE", "price", price, amount);
			
			randomDouble=rg.nextDouble()/10;
			info=configGet("COAL").split(" ");
			price=Double.parseDouble(info[0]);
			amount=Integer.parseInt(info[1]);
			price+=price*randomDouble;
			configSet("COAL", "price", price, amount);
		}
		update();
	}
	
	public ItemStack createItem(String name, Material mat) {
		ItemStack item=new ItemStack(mat);
		ItemMeta meta=item.getItemMeta();
		String desc=ChatColor.RED+""+ChatColor.BOLD+"ЛКМ - продать";
		String desc1=ChatColor.GREEN+""+ChatColor.BOLD+"ПКМ - купить";
		String desc3=ChatColor.RED+""+ChatColor.BOLD+"Держите в руке для продажи!";
		meta.setDisplayName(name);
		String[] info=null;
		String buyPrice="";
		String amount="";
		if(mat==Material.GOLD_INGOT) {
			info=configGetGoldInfo().split(" ");
			buyPrice="Стоимость: "+info[0]+" шекелей.";
			amount="Всего доступно для сделок "+info[1]+" штук.";
		} else if(mat==Material.EMERALD) {
			info=configGetEmeraldInfo().split(" ");
			buyPrice="Стоимость за "+info[0]+" шекелей.";
			amount="Всего доступно для сделок "+info[1]+" штук.";
		} else {
			info=configGet(mat.name()).split(" ");
			buyPrice="Стоимость: "+info[0]+" шекелей.";
			amount="Всего доступно для сделок "+info[1]+" штук.";
		}
		if(isFirst==true)
			configSet(mat.name(), "openPrice", Double.parseDouble(info[0]), Integer.parseInt(info[1]));
		else {
			String openPrice=configGet(mat.name()).split(" ")[2];
			if(Double.parseDouble(info[0])>Double.parseDouble(openPrice)) {
				float percent=((Float.parseFloat(info[0])-Float.parseFloat(openPrice))*100)/Float.parseFloat(info[0]);
				buyPrice=ChatColor.GREEN+buyPrice+" (+"+Float.toString(percent)+"%)";
			} else if(Double.parseDouble(info[0])<Double.parseDouble(openPrice)) {
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
		double price=dataConfig.getDouble("price");
		int amount=dataConfig.getInt("amount");
		double openPrice=dataConfig.getDouble("openPrice");
		String result=Double.toString(price)+" "+Integer.toString(amount)+" "+Double.toString(openPrice);
		return result;
	}
	
	protected static String configGetGoldInfo() {
		File directory=new File(plugin.getDataFolder(), "/Exchange/");
		File dataFile=new File(directory, "gold.yml");
		FileConfiguration dataConfig=YamlConfiguration.loadConfiguration(dataFile);
		double buyPrice=dataConfig.getDouble("price");
		int amount=dataConfig.getInt("amount");
		String result=Double.toString(buyPrice)+" "+Integer.toString(amount);
		return result;
	}
	
	protected static String configGetEmeraldInfo() {
		File directory=new File(plugin.getDataFolder(), "/Exchange/");
		File dataFile=new File(directory, "emerald.yml");
		FileConfiguration dataConfig=YamlConfiguration.loadConfiguration(dataFile);
		double buyPrice=dataConfig.getDouble("price");
		int amount=dataConfig.getInt("amount");
		String result=Double.toString(buyPrice)+" "+Integer.toString(amount);
		return result;
	}
	
	@EventHandler
	public void DamagedVillager(EntityDamageByEntityEvent e) {
		try {
			if(e.getEntity().getCustomName()=="ТоварнаяБиржа"&&!(e.getDamager().isOp())) {
				e.setCancelled(true);
			} else if(e.getEntity().getCustomName()=="Рыба"&&!(e.getDamager().isOp())) {
				e.setCancelled(true);
			}
		} catch(Exception error) {
			//
		}
	}
	
	public void update() {
		ItemStack gold=createItem(ChatColor.GOLD+"Золотой слиток", Material.GOLD_INGOT);
		ItemStack emerald=createItem(ChatColor.GREEN+"Изумруд", Material.EMERALD);
		ItemStack wheat=createItem(ChatColor.YELLOW+"Пшеница", Material.WHEAT);
		ItemStack sugar=createItem(ChatColor.WHITE+"Сахар", Material.SUGAR);
		ItemStack beef=createItem(ChatColor.RED+"Говядина", Material.BEEF);
		ItemStack pork=createItem(ChatColor.LIGHT_PURPLE+"Свинина", Material.PORKCHOP);
		ItemStack carrot=createItem(ChatColor.GREEN+"Морковка", Material.CARROT);
		ItemStack potato=createItem(ChatColor.GREEN+"Картофель", Material.POTATO);
		ItemStack leather=createItem(ChatColor.GOLD+"Кожа", Material.LEATHER);
		ItemStack wood=createItem(ChatColor.DARK_GREEN+"Древесина", Material.OAK_LOG);
		ItemStack coal=createItem(ChatColor.BLACK+"Уголь", Material.COAL);
		ItemStack redstone=createItem(ChatColor.RED+"Красная пыль", Material.REDSTONE);
		
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
		ItemStack gold=createItem(ChatColor.GOLD+"Золотой слиток", Material.GOLD_INGOT);
		ItemStack emerald=createItem(ChatColor.GREEN+"Изумруд", Material.EMERALD);
		
		inv.setItem(0, gold);
		inv.setItem(1, emerald);
	}
	
	public void updateEmerald() {
		ItemStack gold=createItem(ChatColor.GOLD+"Золотой слиток", Material.GOLD_INGOT);
		ItemStack emerald=createItem(ChatColor.GREEN+"Изумруд", Material.EMERALD);
		
		inv.setItem(0, gold);
		inv.setItem(1, emerald);
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p=(Player)e.getWhoClicked();
		if(!(e.getView().getTitle().equals("ТоварнаяБиржа")))
			return;
		if(e.getView().getTitle().equals("ТоварнаяБиржа")) {
			e.setCancelled(true);
		}
		
		if(e.getSlot()==0) {
			String info[]=configGetGoldInfo().split(" ");
			double price=Double.parseDouble(info[0]);
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
						p.sendMessage(ChatColor.GREEN+"Вы успешно приобрели слиток золота! Теперь Ваш баланс: "+Double.toString(player_balance));
						price+=(randomInt/100);
						configSet("gold", "price", price, amount);
						p.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
						Main.economy.withdrawPlayer(p, price);
						updateGold();
						if(randomEvent<=0.025)
							chooseEvent();
					} else {
						p.sendMessage(ChatColor.RED+"У Вас недостаточно средств!");
					}
				} else {
					p.sendMessage(ChatColor.RED+"Наблюдается нехватка золотых слитков, продажа приостановлена!");
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
					p.sendMessage(ChatColor.GREEN+"Вы успешно продали слиток золота! Теперь Ваш баланс: "+Double.toString(player_balance));
					Main.economy.depositPlayer(p, price);
					updateGold();
					if(randomEvent<=0.025)
						chooseEvent();
				} else {
					p.sendMessage(ChatColor.RED+"Вы не держите в руках золотые слитки!");
				}	
			}
		} else if(e.getSlot()==1) {
			String info[]=configGetEmeraldInfo().split(" ");
			//emer
			double price=Double.parseDouble(info[0]);
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
						p.sendMessage(ChatColor.GREEN+"Вы успешно приобрели изумруд! Теперь Ваш баланс: "+Double.toString(player_balance));
						price+=(randomInt/100);
						configSet("emerald", "price", price, amount);
						p.getInventory().addItem(new ItemStack(Material.EMERALD));
						Main.economy.withdrawPlayer(p, price);
						updateEmerald();
						if(randomEvent<=0.025)
							chooseEvent();
					} else {
						p.sendMessage(ChatColor.RED+"У Вас недостаточно средств!");
					}
				} else {
					p.sendMessage(ChatColor.RED+"Наблюдается нехватка изумрудов, продажа приостановлена!");
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
					p.sendMessage(ChatColor.GREEN+"Вы успешно продали изумруд! Теперь Ваш баланс: "+Double.toString(player_balance));
					Main.economy.depositPlayer(p, price);
					updateEmerald();
					if(randomEvent<=0.025)
						chooseEvent();
				} else {
					p.sendMessage(ChatColor.RED+"Вы не держите в руках изумруды!");
				}	
			}
		} else {
			try {
				String mat=e.getCurrentItem().getType().name();
				String info[]=configGet(mat).split(" ");
				double price=Double.parseDouble(info[0]);
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
							p.sendMessage(ChatColor.GREEN+"Вы успешно совершили сделку, Ваш баланс: "+Double.toString(player_balance));
							price+=(randomInt/100);
							configSet(mat, "price", price, amount);
							p.getInventory().addItem(new ItemStack(e.getCurrentItem().getType()));
							Main.economy.withdrawPlayer(p, price);
							update();
							if(randomEvent<=0.025)
								chooseEvent();
						} else {
							p.sendMessage(ChatColor.RED+"У Вас недостаточно денег для совершения сделки!");
						}
					} else {
						p.sendMessage(ChatColor.RED+"Наблюдается нехватка данного ресурса, продажа приостановлена!");
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
						p.sendMessage(ChatColor.GREEN+"Вы успешно совершили сделку! Теперь Ваш баланс: "+Double.toString(player_balance));
						Main.economy.depositPlayer(p, price);
						update();
						if(randomEvent<=0.025)
							chooseEvent();
					} else {
						p.sendMessage(ChatColor.RED+"Вы не держите в руках нужный ресурс!");
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
			if(villager.getProfession()==Villager.Profession.MASON&&villager.getCustomName().equals("ТоварнаяБиржа")) {
				e.setCancelled(true);
				if(inv==null) {
					createInventory();
				}
				p.openInventory(inv);
			} else if(villager.getProfession()==Villager.Profession.LIBRARIAN&&villager.getCustomName().equals("Рыба")) {
				e.setCancelled(true);
				ItemStack stack=null;
				stack=new ItemStack(Material.COD);
				ItemMeta meta=stack.getItemMeta();
				meta.setDisplayName("Щука");
				meta.setLore(Lists.newArrayList(ChatColor.AQUA+""+ChatColor.BOLD+"Рыба", "23см", "2кг"));
				stack.setItemMeta(meta);
				
				try {
					if(p.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).equals(meta.getLore().get(0))) {
						ItemStack item=p.getInventory().getItemInMainHand();
						String size=p.getInventory().getItemInMainHand().getItemMeta().getLore().get(1);
						String weight=p.getInventory().getItemInMainHand().getItemMeta().getLore().get(2);
						int sizeI=Integer.parseInt(size.replaceAll(" см", ""));
						int weightI=Integer.parseInt(weight.replaceAll(" кг", ""));
						double profit=sizeI*0.3+weightI*0.3;
						Main.economy.depositPlayer(p, profit);
						item.setAmount(item.getAmount()-1);
						p.sendMessage(ChatColor.GREEN+"Вы успешно продали рыбу! И заработали "+ChatColor.GOLD+""+ChatColor.BOLD+Double.toString(profit)+".");
					} else {
						p.sendMessage(ChatColor.RED+"В руке должна быть рыба!");
					}
				} catch(NullPointerException error) {
					//
				}
			}
		}
	}
}
