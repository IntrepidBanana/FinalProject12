/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * ShotgunShell
 * type of projectile for shotguns
 */

package com.aidenlauris.gameobjects;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.render.PaintHelper;

public class ShotgunShell extends Projectile {


	/**
	 * creates the projectile with a set damage
	 * @param damage damage of projectile
	 */
	public ShotgunShell(float damage) {
		
		
		HurtBox box = new HurtBox(this,  15, 15, damage);
		box.addHint(this.getClass());
		addCollisionBox(box);
		
		
		setKnockback(5f);
		health = 2;
//		setSpawnSound("shotgun.wav");
	}
	
	@Override
	public void update() {
		
		
		//damage drop off
		setDamage(getDamage()*0.8f);
		
		
		super.update();
	}

	@Override
	public void kill() {
		if (getForceSet().getNetMagnitude() > 10f) {
			Particle.create(x, y, getForceSet().getNetMagnitude(), getTheta(), 90, 1);
		}
		super.kill();
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {

		
		//draw shotgun bullet
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		float theta = (float) (getForceSet().getNetTheta() + Math.PI);
		int trail = 16;
		int width = 10;
		Shape s = new Rectangle2D.Float(drawX, drawY - width/2, trail, width);

		AffineTransform transform = new AffineTransform();
		AffineTransform old = g2d.getTransform();
		transform.rotate(theta, drawX, drawY);
		g2d.transform(transform);
		g2d.setColor(Color.orange);
		g2d.fill(s);
		g2d.setTransform(old);
		return g2d;
	}
}
