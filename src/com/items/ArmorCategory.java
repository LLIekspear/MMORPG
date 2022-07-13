package com.items;

public enum ArmorCategory {

	LIGHT("Легкая", 1),
	MEDIUM("Средняя", 2),
	HEAVY("Тяжелая", 3),
	UNKNOWN("Неизвестная", 0);
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
