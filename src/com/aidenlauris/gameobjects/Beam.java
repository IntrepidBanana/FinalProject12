/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Beam
 * a type of projectile that creates a laser beam
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;
import com.aidenlauris.gameobjects.util.CollisionHelper;
import com.aidenlauris.gameobjects.util.HurtBox;

public class Beam extends Projectile {

	/**
	 * initiates all the values for this projectile
	 * 
	 * @param damage
	 *            of bullet
	 * @param x
	 *            start of bullet position
	 * @param y
	 *            start of bullet position
	 */
	public Beam(float damage, float x, float y) {
		setKnockback(0);
		HurtBox box = new HurtBox(this, 20, 20, damage);
		box.addHint(this.getClass());
		addCollisionBox(box);
		health = Integer.MAX_VALUE;
		// setSpawnSound("beam.wav");

	}

	@Override
	public void kill() {
		Particle.create(x, y, 15f, getTheta(), 40, 1);
		super.kill();
	}

	@Override
	public void forceUpdate() {

		// overrides the force update so that it draws particles in the intra tick
		// calculations
		double narrowestSide = Math.min(CollisionHelper.width(this), CollisionHelper.length(this));
		if (getForceSet().getNetMagnitude() > narrowestSide && forceAccurate && narrowestSide > 0) {

			double divisions = (getForceSet().getNetMagnitude() / (narrowestSide));

			double dx = getForceSet().getDX() / divisions;
			double dy = getForceSet().getDY() / divisions;
			for (int i = 0; i < divisions; i++) {
				if (CollisionHelper.chunkCollision(this)) {
				}

				Particle p = new Particle(x, y);
				p.setLifeSpan(30);
				p.setSize(20);
				p.setSizeDecay(0);
				p.setColor(new Color(255, 0, 125, 0));
				p.setRotationSpeed(30);
				p.setRotation((int) Math.toDegrees(getForceSet().getNetTheta()));

				p.setFadeMinimum(0);
				p.init();

				x += dx;
				y += dy;
			}
		} else {
			x += getForceSet().getDX();
			y += getForceSet().getDY();
		}

		getForceSet().update();

	}

}
