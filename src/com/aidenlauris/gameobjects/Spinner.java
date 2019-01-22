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

	private Particle body;

	
	//is run once to spawn the spinners
	private boolean spinnersCreated = false;

	public Spinner(float x, float y) {
		super(x, y, 50, 20, 2);
		
		//starts body particle
		body = new Particle(x, y);
		body.setColor(Color.orange);
		body.setSize(40);
		body.setSizeDecay(25);
		body.setFadeMinimum(255);
		body.setRotationSpeed(5);
		body.setLifeSpan(Integer.MAX_VALUE);
		body.init();
	}

	public void move() {
		//no move capability
	}

	public void update() {
		body.x = x;
		body.y = y;

		tickUpdate();
		if (!spinnersCreated) {
			attack();
		}
	}

	public void attack() {

		//creates spinner blades
		SpinnerBlade a = new SpinnerBlade(this, 20, 0, 1, 50);
		a.color = Color.ORANGE;
		SpinnerBlade b = new SpinnerBlade(this, 20, 180, 1, 50);
		b.color = Color.orange;
		
		SpinnerBlade c = new SpinnerBlade(this, 20, 0, 1, 100);
		c.color = Color.ORANGE;
		SpinnerBlade d = new SpinnerBlade(this, 20, 180, 1, 100);
		d.color = Color.orange;
		spinnersCreated = true;
	}

	@Override
	public void knockBack(float magnitude, float theta) {
		//unable to take knockback
	}

	@Override
	public void kill() {

		body.kill();
		super.kill();
	}
}