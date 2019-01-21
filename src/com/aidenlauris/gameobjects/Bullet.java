/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Bullet
 * a type of projectile for pistols and machine guns
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceSet;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.gameobjects.util.Team;
import com.aidenlauris.render.PaintHelper;

public class Bullet extends Projectile {

	Bullet(float x, float y, float theta, float damage, float offset, float reduction) {
		super(x, y, 10f, damage, theta, offset, reduction);
		addCollisionBox(new HurtBox(this, -6f, -6f, 12, 12, damage));
	}

	Particle part;

	public Bullet(float damage) {
		setKnockback(9f);
		HurtBox box = new HurtBox(this, -6f, -6f, 12, 12, damage);
		// box.addHint(this.getClass());
		addCollisionBox(box);
		health = 1;
		
		
		part = new Particle(x, y);
		part.setColor(Color.orange);

		part.setSize(25);
		part.setRotationSpeed(12);
		part.setSizeDecay(25);
		part.setFadeMinimum(255);
		part.setLifeSpan(Integer.MAX_VALUE);
		part.init();
	}

	@Override
	public void init() {
		super.init();
		if (team == team.ENEMY) {
			part = new Particle(x, y);
			part.setColor(Color.red);
			part.setSize(16);
			part.setRotationSpeed(3);
			part.setSizeDecay(12);
			part.setFadeMinimum(255);
			part.setLifeSpan(400);
			part.init();
		}

	}

	@Override
	public void update() {
		super.update();
		if (part != null) {
			part.x = x;
			part.y = y;
		}
	}

	@Override
	public void kill() {
		Particle.create(x, y, 15f, getTheta(), 40, 1, Color.LIGHT_GRAY);
		if (part != null) {
			part.kill();
		}
		super.kill();
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		// g2d = super.draw(g2d);
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		if (team == team.ENEMY) {
			if (time % 5 == 0) {
				Particle p = new Particle(x, y);
				p.setColor(Color.red);

				p.setSize(16);
				p.setRotationSpeed(3);
				p.setSizeDecay(0);
				p.setFadeMinimum(0);
				p.setLifeSpan(30);
				p.init();
			}
		}

		return g2d;
	}
}
