/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * BulletAmmo
 * type of ammo for pistols
 */

package com.aidenlauris.items;

import com.aidenlauris.gameobjects.Ammo;

public class BulletAmmo extends Ammo {
	
	public BulletAmmo() {
		setStackSize(100);
	}
	public BulletAmmo(int count){
		this();
		setCount(count);

	}

}
