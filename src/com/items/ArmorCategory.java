package com.items;

public enum ArmorCategory {

	LIGHT("������", 1),
	MEDIUM("�������", 2),
	HEAVY("�������", 3),
	UNKNOWN("�����������", 0);
	private String name;
	private int weight;
	ArmorCategory(String name, int weight) {
		this.name=name;
		this.weight=weight;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public int getWeight() {
		return weight;
	}
}
