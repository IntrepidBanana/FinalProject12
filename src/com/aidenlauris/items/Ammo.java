/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Ammo
 * ammunition for weapons super abstract class
 */

package com.aidenlauris.items;

public abstract class Ammo extends Item implements Consumable {

	/**
	 * creates ammo item with a certain count
	 * @param count
	 */
	public Ammo(int count) {
		super();
		setCount(count);
	}

	/**
	 * basic empty ammo class
	 */
	public Ammo() {
		super();
		setCount(0);
	}

	@Override
	public void consume() {
		//reduce count
		setCount(getCount() - 1);
	}

	/**
	 * reduce ammo count by count
	 * @param count number to reduce by
	 */
	public void consume(int count) {

		setCount(getCount() - count);
	}

	@Override
	public String item() {
		return this.toString();
	}
}
