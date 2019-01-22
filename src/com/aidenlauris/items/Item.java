/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Item
 * super class for all types of items such as guns or ammo
 */

package com.aidenlauris.items;

public abstract class Item {
	
	//amount of this item
	private int count;
	
	/**
	 * initializes this item
	 */
	public Item() {
	}


	
	
	

	/**
	 * @return the amount of this item
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the amount of this item
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @param count adds by this amount from the count
	 */
	public void addToCount(int count) {
		setCount(getCount() + count); 
	}
	
	/**
	 * @param count removes by this amount from the count
	 */
	public void removeFromCount(int count) {
		addToCount(-count);
	}
	
	/**
	 * @return the name of this item
	 */
	public String item() {
		return toString();
	}

	/**
	 * this event is called whenever this item used
	 */
	public void useItem() {
		
		//default stuff
		System.out.println("there doesnt seem to be a use for this item...");
	}

	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
