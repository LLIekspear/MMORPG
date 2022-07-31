package com.quest;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class Quest {

	private int type;
	private int id;
	private String name;
	private String desc;
	private List<ItemStack> reward;
	private int progress;
	private int need;
	private int rep;
	private int exp;

	public Quest(int type, int id, String name, String desc, List<ItemStack> rewards, int need, int rep, int exp) {
		this.type=type;
		this.id=id;
		this.name=name;
		this.desc=desc;
		this.reward=rewards;
		this.progress=0;
		this.need=need;
		this.rep=rep;
		this.exp=exp;
	}
	
	public int getNeed() {
		return need;
	}
	
	public void setNeed(int need) {
		this.need=need;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type=type;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id=id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc=desc;
	}
	
	public List<ItemStack> getRewards() {
		return reward;
	}
	
	public void setRewards(List<ItemStack> reward) {
		this.reward=reward;
	}
	
	public double getProgress() {
		return progress;
	}
	
	public void setProgress(int progress) {
		this.progress=progress;
	}
	
	public int getRep() {
		return rep;
	}
	
	public void setRep(int rep) {
		this.rep=rep;
	}
	
	public int getExp() {
		return exp;
	}
	
	public void setExp(int exp) {
		this.exp=exp;
	}
}
