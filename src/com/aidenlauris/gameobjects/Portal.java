package com.aidenlauris.gameobjects;

import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.gameobjects.util.HitBox;

public class Portal extends Entity {

	private boolean timeHasStarted = false;
	private long alert = 0;

	public Portal(Enemy lastEnemy) {
		super(0, 0);
		addCollisionBox(new HitBox(this, 30, 30, false));
		ForceAnchor fa = new ForceAnchor(10f, Player.getPlayer(), this, -1);
		fa.setId("portal");
		Player.getPlayer().getForceSet().addForce(fa, 30);
		x = lastEnemy.x;
		y = lastEnemy.y;

	}

	@Override
	public void update() {
		if (distToPlayer() < 30) {
			if (Time.alertPassed(alert) && timeHasStarted) {
				startGeneration();
				Player.getPlayer().getForceSet().removeForce("portal");
			}
			if (!timeHasStarted) {
				alert = Time.alert(90);
				timeHasStarted = true;
			}
		}
	}

	public void startGeneration() {
		WorldMap.init();
	}

	@Override
	public void collisionOccured(CollisionBox theirBox, CollisionBox myBox) {
		if (theirBox.getOwner() instanceof Wall) {
			collide(theirBox, myBox);
		}
	}
}
