package com.aidenlauris.items;

import com.aidenlauris.gameobjects.Ammo;

public class ShotgunAmmo extends Ammo {

	public ShotgunAmmo() {
		setStackSize(24);
	}
	
	public ShotgunAmmo(int count){
		this();
		setCount(count);
	}
}
