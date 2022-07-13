package com.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseClass implements PlayerClass {
	private Map<String, PlayerSubclass> subclassMap=new HashMap<>();
	private List<PlayerSubclass> subclasses=new ArrayList<>();
	protected void registerSubclass(PlayerSubclass subclass) {
		subclasses.add(subclass);
		subclassMap.put(subclass.getSubclassName(), subclass);
	}

	@Override
	public List<PlayerSubclass> getSubclasses() {
		return new ArrayList<>(subclasses);
	}
	
	@Override
	public PlayerSubclass getSubclass(String subclass) {
		return subclassMap.get(subclass);
	}
}
