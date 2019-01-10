package com.aidenlauris.items;

import com.aidenlauris.gameobjects.Player;

public class HealthPickup extends Item {

	public HealthPickup(int stackSize) {
		super(1, stackSize);
	}
	
	@Override
	public void useItem() {
		if (Player.getPlayer().health < 75) {
		Player.getPlayer().health = Player.getPlayer().health + 25;
		}else {
			Player.getPlayer().health = 100;
		}
	}
	
	

}
