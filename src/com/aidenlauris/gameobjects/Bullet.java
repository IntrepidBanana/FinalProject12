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
		Particle.create(x, y, 15f, getTheta(), 40, 1);
		if(part != null) {
			part.kill();
		}
		super.kill();
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		// g2d = super.draw(g2d);
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		if (team == Team.ENEMY) {
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
		} else {

			float theta = (float) (getForceSet().getNetTheta() + Math.PI);
			// int trail = (int) (24 * time + 48);
			int trail = 50;
			int width = 30;
			Shape s = new Rectangle2D.Float(drawX, drawY - width / 2, trail, width);
			AffineTransform transform = new AffineTransform();
			AffineTransform old = g2d.getTransform();
			transform.rotate(theta, drawX, drawY);
			g2d.transform(transform);
			g2d.setColor(Color.orange);
			if (getForceSet().getNetMagnitude() > 1) {
				g2d.fill(s);
			}
			g2d.setTransform(old);
		}

		// g2d = PaintHelper.drawCollisionBox(g2d, getCollisionBoxes());
		return g2d;
	}
}
