package com.aidenlauris.items;

import com.aidenlauris.gameobjects.Ammo;

public class ExplosiveAmmo extends Ammo {
	
	public ExplosiveAmmo() {
		setStackSize(12);
	}
	public ExplosiveAmmo(int count){
		this();
		setCount(count);
	}
	
}
