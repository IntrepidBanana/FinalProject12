package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;

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
		
		double theta = Math.toRadians(Math.random() * 360);
		for (int i = 1; i <= 3; i++) {
			theta += Math.toRadians(120);
			float dx = (float) (x + Math.cos(theta) * 30);
			float dy = (float) (y + Math.sin(theta) * 30);

			Particle part = new Particle(dx, dy);
			part.setLifeSpan(120);
			part.setSize(6);
			part.setSizeDecay(3);
			part.setRotationSpeed(8);
			part.setFadeMinimum(0);
			part.setColor(Color.pink);
			
			
			ForceAnchor fa = new ForceAnchor((float) (1f + Math.random() * 3f), part, this, -1f);
			fa.setOffset(85 * (int) (Math.random() * 3));
			fa.setLifeSpan(Integer.MAX_VALUE);
			// fa.hasVariableSpeed(false);

			part.getForceSet().addForce(fa);

			part.init();

		}
		
		for (int i = 1; i <= 3; i++) {
			theta += Math.toRadians(120);
			float dx = (float) (x + Math.cos(theta) * 30);
			float dy = (float) (y + Math.sin(theta) * 30);

			Particle part = new Particle(dx, dy);
			part.setLifeSpan(120);
			part.setSize(6);
			part.setSizeDecay(3);
			part.setRotationSpeed(8);
			part.setFadeMinimum(0);
			part.setColor(new Color(107,45,103));
			
			
			ForceAnchor fa = new ForceAnchor((float) (1f + Math.random() * 3f), part, this, -1f);
			fa.setOffset(85 * (int) (Math.random() * 3));
			fa.setLifeSpan(Integer.MAX_VALUE);
			// fa.hasVariableSpeed(false);

			part.getForceSet().addForce(fa);

			part.init();

		}
		
		
	}
	
	@Override
	public Graphics2D draw(Graphics2D g2d) {
		return g2d;
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
