/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * ExplosiveAmmo
 * type of ammo for explosive weapons
 */

package com.aidenlauris.items;

public class ExplosiveAmmo extends Ammo {

	/**
	 * initiates empty ammo item
	 */
	public ExplosiveAmmo() {
	}

	
	/**
	 * Initiates ammo with a set count
	 * @param count
	 */
	public ExplosiveAmmo(int count) {
		this();
		setCount(count);
	}

}
