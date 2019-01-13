package com.aidenlauris.items;

import com.aidenlauris.gameobjects.Ammo;

public class EnergyCell extends Ammo {

	public EnergyCell(int count) {
		this();
		setCount(count);
	}

	public EnergyCell() {
		setStackSize(15);
	}

}
