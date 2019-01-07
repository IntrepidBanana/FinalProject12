package com.aidenlauris.gameobjects;

import com.aidenlauris.items.Consumable;
import com.aidenlauris.items.Item;

public class Ammo extends Item implements Consumable {

	public Ammo(int count) {
		super(0, 999);
		setCount(count);
	}

	public Ammo() {
		super(0, 100);
		setCount(0);
	}

	@Override
	public void consume() {
		setCount(getCount() - 1);
	}

	public void consume(int count) {

		setCount(getCount() - count);
	}

	@Override
	public String item() {
		return this.toString();
	}
}