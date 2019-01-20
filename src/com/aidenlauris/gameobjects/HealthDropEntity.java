package com.aidenlauris.gameobjects;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.items.Item;
import com.aidenlauris.render.menu.CollectionText;

public class HealthDropEntity extends AmmoDropEntity {

	public HealthDropEntity(float x, float y) {
		super(x, y);

	}

	@Override
	public void update() {
		tickUpdate();
		if (distToPlayer() < 40) {
			Player p = Player.getPlayer();
			p.health = Math.min(p.maxHealth, p.health + 25);
			WorldMap.addMenu( new CollectionText( "+25 Health"));

			kill();
		}

	}
	public static void drop(float x, float y, double chance, int low, int high) {
		double roll = Math.random();

		if (chance > roll) {
			int varianceOfItemCount = high - low;
			int rollVariance = low + (int) Math.random() * varianceOfItemCount;

			for (int i = 0; i < rollVariance; i++) {

				float randX, randY;
				randX = (float) (x + Math.random() * 20 - 10);
				randY = (float) (y + Math.random() * 20 - 10);

				WorldMap.addGameObject(new HealthDropEntity(randX, randY));
				
			}

		}
	}
	
	
}
