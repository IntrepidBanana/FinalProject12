/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Spinner
 * Type of enemy that doesn't move and has bullets spinning around it
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.game.util.Time;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.HurtBox;

public class Spinner extends Enemy {

	private Particle part;

	private boolean spinnersCreated = false;

	public Spinner(float x, float y) {
		super(x, y, 50, 20, 2);
		part = new Particle(x, y);
		part.setColor(Color.orange);
		part.setSize(25);
		part.setSizeDecay(25);
		part.setFadeMinimum(255);
		part.setRotationSpeed(5);
		part.setLifeSpan(Integer.MAX_VALUE);
		part.init();
	}

	public void move() {
		// float dist = (float) Math
		// .sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) +
		// Math.pow(WorldMap.getPlayer().y - y, 2));
		//
		// Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random() *
		// 360));
		// f.setId("FourMove");
		// f.setLifeSpan(60);
		// f.setReduction(0);
		// if (getForceSet().getForce("FourMove") == null) {
		// addForce(f);
		// }
	}

	public void update() {
		part.x = x;
		part.y = y;

		tickUpdate();
		if (!spinnersCreated) {
			attack();
			spinnersCreated = true;
		}
	}

	public void attack() {

		SpinnerBlade a = new SpinnerBlade(this, 20, 0, 1, 50);
		a.color = Color.ORANGE;
		SpinnerBlade b = new SpinnerBlade(this, 20, 180, 1, 50);
		b.color = Color.orange;
	}

	@Override
	public void knockBack(float magnitude, float theta) {
	}

	@Override
	public void kill() {

		part.kill();
		super.kill();
	}
}