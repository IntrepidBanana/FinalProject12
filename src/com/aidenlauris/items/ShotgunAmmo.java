/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * ShotgunAmmo
 * type of ammo for shotguns
 */

package com.aidenlauris.items;

public class ShotgunAmmo extends Ammo {

	public ShotgunAmmo() {
		setStackSize(24);
	}
	
	public ShotgunAmmo(int count){
		this();
		setCount(count);
	}
}
