package com.classes;

public abstract class BaseSubclass implements PlayerSubclass {

	//private List<Learnable> learnables=new ArrayList<>();
	
	/*protected void registerLearnable(Learnable learnable) {
		learnables.add(learnable);
	}*/
	/*@Override
	public List<Learnable> getLearnables() {
		return new ArrayList<>(learnables);
	}
	
	@Override
	public List<Learnable> getLearned(int masteryLevel) {
		List<Learnable> learned = new ArrayList<>();
		for(Learnable learnable : learnables) {
			if(learnable.getLevel() <= masteryLevel) {
				learned.add(learnable);
			}
		}
		return learned;
	}
	
	@Override
	public Learnable getNextLearnable(int masteryLevel) {
		for(Learnable learnable : learnables) {
			if(learnable.getLevel() > masteryLevel) {
				return learnable;
			}
		}
		return null;
	}*/
}
