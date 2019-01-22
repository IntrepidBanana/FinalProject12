/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * PoisonWalker
 * type of enemy that puts poison on the floor
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.game.util.Time;
import com.aidenlauris.gameobjects.util.Force;

public class PoisonWalker extends Enemy {

	
	private Particle part;

	/**
	 * Initiates the enemy at the coordinate
	 * @param x x coord
	 * @param y y coord
	 */
	public PoisonWalker(float x, float y) {
		super(x, y, 50, 10, 2);

		
		//sets the particle for the body
		part = new Particle(x, y);

		part.setRotation(3);
		part.setColor(Color.green);
		part.setSize(45);
		part.setSizeDecay(45);
		part.setLifeSpan(Integer.MAX_VALUE);
		part.init();

	}

	@Override
	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(GameLogic.getPlayer().x - x, 2) + Math.pow(GameLogic.getPlayer().y - y, 2));

		Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random() * 360));
		f.setId("PoisonMove");
		f.setLifeSpan(60);
		f.setReduction(0f);
		if (getForceSet().getForce("PoisonMove") == null) {
			addForce(f);
		}

	}

	public void update() {
		part.x = x;
		part.y = y;

		tickUpdate();

		
		// move logic
		float dist = distToPlayer();
		time++;

		move();

		if (time % 10 == 0) {

			GameLogic.addGameObject(new Poison(this.x, this.y));
		}
		if (dist < 500 && Time.alertPassed(alert)) {
			// WorldMap.addGameObject(new Poison(this.x, this.y));
			alert = Time.alert((long) (5));
		}

	}

	@Override
	public void kill() {
		part.kill();
		super.kill();
	}
	
}
