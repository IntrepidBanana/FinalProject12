/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * HealthPickup
 * gives player health
 */

package com.aidenlauris.items;

import com.aidenlauris.gameobjects.Player;

public class HealthPickup extends Item {

	public HealthPickup() {
		super(1, 5);

		setCount(1);
	}
	
	@Override
	public void useItem() {
		Player p = Player.getPlayer();
		System.out.println(p.health + " " + p.maxHealth);
		p.health = Math.min(p.maxHealth, p.health + 25);
	}
	
	@Override
	public String item() {
		return this.toString();
	}
	
	

}
