/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * MachineBullet
 * type of projectile for machine guns
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.render.PaintHelper;
import com.aidenlauris.render.SoundHelper;

public class MachineBullet extends Projectile {

	MachineBullet(float x, float y, float theta, float damage, float offset, float reduction) {
		super(x, y, 10f, damage, theta, offset, reduction);
		addCollisionBox(new HurtBox(this, -6f, -6f, 12, 12, damage));
	}

	public MachineBullet(float damage) {
		setKnockback(9f);
		HurtBox box = new HurtBox(this, -6f, -6f, 12, 12, damage);
		box.addHint(this.getClass());
		addCollisionBox(box);
		health = 1;
		//setSpawnSound("machine.wav");
	}

	@Override
	public void kill() {
		
		Particle.create(x, y, 15f, getTheta(), 40, 1);
		super.kill();
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		// g2d = super.draw(g2d);
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		float theta = (float) (getForceSet().getNetTheta() + Math.PI);
//		int trail = (int) (24 * time + 48);
		int trail = 1;

		Shape s = new Rectangle2D.Float(drawX, drawY - 1.5f, trail, 3f);

		AffineTransform transform = new AffineTransform();
		AffineTransform old = g2d.getTransform();
		transform.rotate(theta, drawX, drawY);
		g2d.transform(transform);
		g2d.setColor(Color.orange);
		if (getForceSet().getNetMagnitude() > 1) {
			g2d.fill(s);
		}
		g2d.setTransform(old);
		g2d = PaintHelper.drawCollisionBox(g2d, getCollisionBoxes());
		return g2d;
	}

}
