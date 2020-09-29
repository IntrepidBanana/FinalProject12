/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * EnergyCell
 * type of ammo for LaserGuns
 */

package com.aidenlauris.items;

public class EnergyCell extends Ammo {

	/**
	 * Initiates ammo with a set number of bullets
	 * @param count
	 */
	public EnergyCell(int count) {
		this();
		setCount(count);
	}

	/**
	 * Inititiates empty ammo 
	 */
	public EnergyCell() {
	}

}
