/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * HealthDropEntity
 * health that drops on the floor to heal the player
 */

package com.aidenlauris.gameobjects;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.items.Item;
import com.aidenlauris.render.menu.CollectionText;

public class HealthDropEntity extends AmmoDropEntity {

	/**
	 * initiates drop with coordinate x, y
	 * 
	 * @param x
	 *            coord x
	 * @param y
	 *            coord y
	 */
	public HealthDropEntity(float x, float y) {
		super(x, y);

	}

	@Override
	public void update() {
		tickUpdate();
		if (distToPlayer() < 40) {

			// adds to health when nearby
			Player p = Player.getPlayer();
			p.health = Math.min(p.maxHealth, p.health + 25);
			GameLogic.addMenu(new CollectionText("+25 Health"));

			kill();
		}

	}

	/**
	 * spawns a health drop with a chance to spawn
	 * 
	 * @param x x coord
	 * @param y y coord
	 * @param chance chance to drop
	 */
	public static void drop(float x, float y, double chance) {
		double roll = Math.random();

		if (chance > roll) {

			float randX, randY;
			randX = (float) (x + Math.random() * 20 - 10);
			randY = (float) (y + Math.random() * 20 - 10);

			GameLogic.addGameObject(new HealthDropEntity(randX, randY));

		}
	}

}
