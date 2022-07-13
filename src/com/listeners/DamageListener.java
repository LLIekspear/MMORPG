package com.listeners;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

//TODO реализовать разновидность урона: маг и физ??? Привязка к предметам или скиллам?
//TODO убрать проверку ентити на игрока и добавить пве крит, если не игрок
public class DamageListener implements Listener {

	@EventHandler
	public void onAttack(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player&&e.getDamager() instanceof Player) {
			Player entity=(Player) e.getEntity();
			Player damager=(Player) e.getDamager();
			double attack=e.getDamage();
			double defense=entity.getAttribute(Attribute.GENERIC_ARMOR).getValue();
			damager.sendMessage("test: "+Arrays.toString(damager.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).split(ChatColor.WHITE+""+ChatColor.BOLD+":")));
			//
			for(int i=0; i<damager.getInventory().getItemInMainHand().getItemMeta().getLore().size(); ++i) {
				String[] param=ChatColor.stripColor(damager.getInventory().getItemInMainHand().getItemMeta().getLore().get(i).toString()).split(": ");
				damager.sendMessage(param[0]);
				try {
					damager.sendMessage(param[1]);
				} catch(Exception error) {
					//
				}
				if(param[0].equalsIgnoreCase("Атака")) {
					attack=Integer.parseInt(param[1]);
					damager.sendMessage("Attack1: "+Integer.parseInt(param[1]));
				}
				if(param[0].equalsIgnoreCase("Атака PVP")) {
					damager.sendMessage("Конечный урон: "+Double.toString(attack+((attack/100)*10)));
					attack+=(attack/100)*Integer.parseInt(param[1].toString().split("%")[0].toString());
					damager.sendMessage("% PVP dps: "+param[1].toString().split("%")[0]);
				}
			}
			//
			try {
				for(int i=0; i<damager.getInventory().getItemInMainHand().getItemMeta().getLore().size(); ++i) {
					String[] param=ChatColor.stripColor(damager.getInventory().getItemInMainHand().getItemMeta().getLore().get(i).toString()).split(": ");
					if(param[0].equalsIgnoreCase("Атака")) {
						attack=Integer.parseInt(param[1]);
						damager.sendMessage("Урон от оружия: "+Integer.parseInt(param[1]));
					}
					if(param[0].equalsIgnoreCase("Атака PVP")) {
						attack+=(attack*Integer.parseInt(param[1].toString().split("%")[0].toString()))/100;
						//damager.sendMessage("% PVP dps: "+Integer.parseInt(param[2]));
					}
					if((param[0].equalsIgnoreCase("Физ. крит")/*||(param[0].equalsIgnoreCase("Маг. крит"))*/)) {
						Random rg=new Random();
						int len=100;
						int randomInt=rg.nextInt(len);
						if(randomInt>70) {
							int randomCrit=rg.nextInt(Integer.parseInt(param[1]));
							attack+=randomCrit;
							damager.sendMessage(ChatColor.RED+"Крит. удар!");
						}
					}
				}
			} catch(Exception error) {
//				error.printStackTrace();
				if(damager.getInventory().getItemInMainHand()==null)
					attack=0.3;
				else
					attack=e.getDamage();
//				damager.sendMessage("Вы нанесли 0.3 единиц урона!");
			}
			try {
				damager.sendMessage("Чел в броне...");
				for(int i=0; i<4; ++i) {
					if(i==0&&entity.getInventory().getHelmet()!=null) {
						for(int j=0; j<entity.getInventory().getHelmet().getItemMeta().getLore().size(); ++j) {
							String[] param=ChatColor.stripColor(entity.getInventory().getHelmet().getItemMeta().getLore().get(j).toString()).split(": ");
							/*if(param[0].equalsIgnoreCase(ChatColor.WHITE+""+ChatColor.BOLD+"Физ. защита")) {
								defense+=Integer.parseInt(param[1]);
							}*/
							if(param[0].equalsIgnoreCase("Защита PVP")) {
								attack-=(defense*Integer.parseInt(param[1].toString().split("%")[0].toString()))/100;
								//entity.sendMessage("% PVP def helmet: "+Integer.parseInt(param[1]));
							}
							//
						}
					} else if(i==1&&entity.getInventory().getChestplate()!=null) {
						for(int j=0; j<entity.getInventory().getChestplate().getItemMeta().getLore().size(); ++j) {
							String[] param=ChatColor.stripColor(entity.getInventory().getChestplate().getItemMeta().getLore().get(j).toString()).split(": ");
							/*if(param[0].equalsIgnoreCase(ChatColor.WHITE+""+ChatColor.BOLD+"Физ. защита")) {
								defense+=Integer.parseInt(param[1]);
							}*/
							if(param[0].equalsIgnoreCase("Защита PVP")) {
								attack-=(defense*Integer.parseInt(param[1].toString().split("%")[0].toString()))/100;
								//entity.sendMessage("% PVP def chest: "+Integer.parseInt(param[1]));
							}
							//
						}
					} else if(i==2&&entity.getInventory().getLeggings()!=null) {
						for(int j=0; j<entity.getInventory().getLeggings().getItemMeta().getLore().size(); ++j) {
							String[] param=ChatColor.stripColor(entity.getInventory().getLeggings().getItemMeta().getLore().get(j).toString()).split(": ");
							/*if(param[0].equalsIgnoreCase(ChatColor.WHITE+""+ChatColor.BOLD+"Физ. защита")) {
								defense+=Integer.parseInt(param[1]);
							}*/
							if(param[0].equalsIgnoreCase("Защита PVP")) {
								attack-=(defense*Integer.parseInt(param[1].toString().split("%")[0].toString()))/100;
								//entity.sendMessage("% PVP def legs: "+Integer.parseInt(param[1]));
							}
							//
						}
					} else if(i==3&&entity.getInventory().getBoots()!=null) {
						for(int j=0; j<entity.getInventory().getBoots().getItemMeta().getLore().size(); ++j) {
							String[] param=ChatColor.stripColor(entity.getInventory().getBoots().getItemMeta().getLore().get(j).toString()).split(": ");
							/*if(param[0].equalsIgnoreCase(ChatColor.WHITE+""+ChatColor.BOLD+"Физ. защита")) {
								defense+=Integer.parseInt(param[1]);
							}*/
							if(param[0].equalsIgnoreCase("Защита PVP")) {
								attack-=(defense*Integer.parseInt(param[1].toString().split("%")[0].toString()))/100;
								//entity.sendMessage("% PVP def boots: "+Integer.parseInt(param[1]));
							}
							//
						}
					}
				}
	
				//TODO считаем броню и уменьшаем урон
			} catch(Exception error) {
				error.printStackTrace();
			}
			//if(damager.getInventory().getItemInMainHand().getItemMeta().getLore().contains(""))
			if(attack<0)
				attack=0;
			e.setDamage(attack);
			damager.sendMessage(ChatColor.YELLOW+"Противнику нанесен урон: "+Double.toString(attack));
			entity.sendMessage(ChatColor.RED+"Получен урон: "+Double.toString(attack));
		} else if(!(e.getEntity() instanceof Player)&&e.getDamager() instanceof Player) {
			Player damager=(Player) e.getDamager();
			//int attack=(int) e.getDamage();
			double attack=0;
			try {
				for(int i=0; i<damager.getInventory().getItemInMainHand().getItemMeta().getLore().size(); ++i) {
					String[] param=ChatColor.stripColor(damager.getInventory().getItemInMainHand().getItemMeta().getLore().get(i).toString()).split(": ");
					if(param[0].equalsIgnoreCase("Атака")) {
						attack=Integer.parseInt(param[1]);
						damager.sendMessage("Атака: "+Integer.parseInt(param[1]));
					}
					if(param[0].equalsIgnoreCase("Атака PVE")) {
						attack+=(attack*Integer.parseInt(param[1].toString().split("%")[0].toString()))/100;
						//damager.sendMessage("% PVE dps: "+Integer.parseInt(param[1]));
					}
					if((param[0].equalsIgnoreCase("Физ. крит")/*||(param[0].equalsIgnoreCase("Маг. крит"))*/)) {
						Random rg=new Random();
						int len=100;
						int randomInt=rg.nextInt(len);
						if(randomInt>70) {
							int randomCrit=rg.nextInt(Integer.parseInt(param[1]));
							attack+=randomCrit;
							damager.sendMessage(ChatColor.RED+"Крит. удар!");
						}
					}
				}
			} catch(Exception error) {
//				error.printStackTrace();
				if(damager.getInventory().getItemInMainHand()==null)
					attack=0.3;
				else 
					attack=e.getDamage();
			}
			e.setDamage(attack);
			damager.sendMessage(ChatColor.YELLOW+"Противнику нанесен урон: "+Double.toString(attack));
			//PVE DAMAGE
		} else if(e.getEntity() instanceof Player&&!(e.getDamager() instanceof Player)) {
			//PVE def
			Player entity=(Player) e.getEntity();
			double defense= entity.getAttribute(Attribute.GENERIC_ARMOR).getValue();
			double attack= e.getDamage();
			try {
				for(int i=0; i<4; ++i) {
					if(i==0) {
						for(int j=0; j<entity.getInventory().getHelmet().getItemMeta().getLore().size(); ++j) {
							String[] param=ChatColor.stripColor(entity.getInventory().getHelmet().getItemMeta().getLore().get(j).toString()).split(": ");
							/*if(param[0].equalsIgnoreCase(ChatColor.WHITE+""+ChatColor.BOLD+"Физ. защита")) {
								defense+=Integer.parseInt(param[1]);
							}*/
							if(param[0].equalsIgnoreCase("Защита PVE")) {
								attack-=(defense*Integer.parseInt(param[1].toString().split("%")[0].toString()))/100;
							}
							//
						}
					} else if(i==1) {
						for(int j=0; j<entity.getInventory().getChestplate().getItemMeta().getLore().size(); ++j) {
							String[] param=ChatColor.stripColor(entity.getInventory().getChestplate().getItemMeta().getLore().get(j).toString()).split(": ");
							/*if(param[0].equalsIgnoreCase(ChatColor.WHITE+""+ChatColor.BOLD+"Физ. защита")) {
								defense+=Integer.parseInt(param[1]);
							}*/
							if(param[0].equalsIgnoreCase("Защита PVE")) {
								attack-=(defense*Integer.parseInt(param[1].toString().split("%")[0].toString()))/100;
							}
							//
						}
					} else if(i==2) {
						for(int j=0; j<entity.getInventory().getLeggings().getItemMeta().getLore().size(); ++j) {
							String[] param=ChatColor.stripColor(entity.getInventory().getLeggings().getItemMeta().getLore().get(j).toString()).split(": ");
							/*if(param[0].equalsIgnoreCase(ChatColor.WHITE+""+ChatColor.BOLD+"Физ. защита")) {
								defense+=Integer.parseInt(param[1]);
							}*/
							if(param[0].equalsIgnoreCase("Защита PVE")) {
								attack-=(defense*Integer.parseInt(param[1].toString().split("%")[0].toString()))/100;
							}
							//
						}
					} else if(i==3) {
						for(int j=0; j<entity.getInventory().getBoots().getItemMeta().getLore().size(); ++j) {
							String[] param=ChatColor.stripColor(entity.getInventory().getBoots().getItemMeta().getLore().get(j).toString()).split(": ");
							/*if(param[0].equalsIgnoreCase(ChatColor.WHITE+""+ChatColor.BOLD+"Физ. защита")) {
								defense+=Integer.parseInt(param[1]);
							}*/
							if(param[0].equalsIgnoreCase("Защита PVE")) {
								attack-=(defense*Integer.parseInt(param[1].toString().split("%")[0].toString()))/100;
							}
							//
						}
					}
				}
	
				//TODO считаем броню и уменьшаем урон
			} catch(Exception error) {
//				error.printStackTrace();
			}
			Random rg=new Random();
			int len=100;
			int randomInt=rg.nextInt(len);
			if(randomInt>70) {
				int randomCrit=rg.nextInt((int) attack);
				attack+=randomCrit;
			}
			e.setDamage(attack);
			entity.sendMessage(ChatColor.RED+"Получен урон: "+Double.toString(attack));
		}
	}
}
