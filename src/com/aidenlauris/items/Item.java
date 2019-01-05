package com.aidenlauris.items;

public abstract class Item {

	private double weight;
	private int stackSize;
	private int count;
	
	public Item(double weight, int stackSize) {
		this.weight = weight;
		this.stackSize = stackSize;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getStackSize() {
		return stackSize;
	}

	public void setStackSize(int stackSize) {
		this.stackSize = stackSize;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void addToCount(int count) {
		setCount(getCount() + count); 
	}
	
	public void removeFromCount(int count) {
		addToCount(-count);
	}
	
	public String item() {
		return toString();
	}

	public void useItem() {
		System.out.println("there doesnt seem to be a use for this item...");
	}

	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
