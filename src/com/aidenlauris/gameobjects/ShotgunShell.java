package com.aidenlauris.gameobjects;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.render.PaintHelper;

public class ShotgunShell extends Projectile {

	public ShotgunShell(float x, float y, float theta, float damage, float offset, float reduction) {
		super(x + (float) Math.random() * 20 - 10, y + (float) Math.random() * 20 - 10, 12f, damage, theta, offset,
				reduction);
		setDamage(damage);
		addCollisionBox(new HurtBox(this, -7.5f, -7.5f, 15, 15, damage));
	}

	public ShotgunShell(float damage) {
		HurtBox box = new HurtBox(this, -7.5f, -7.5f, 15, 15, damage);
		box.addHint(this.getClass());
		addCollisionBox(box);
		setKnockback(5f);
		health = 2;
//		setSpawnSound("shotgun.wav");
	}
	
	@Override
	public void update() {
		setDamage(getDamage()*0.9f);
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
		g2d.setColor(Color.red);
		g2d.fill(s);
		g2d.setTransform(old);
		return g2d;
	}
}
