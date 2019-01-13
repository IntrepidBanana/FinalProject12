package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.render.PaintHelper;

public class PoisonCreep extends Projectile {

	public PoisonCreep(float damage){
		setKnockback(0);
		HurtBox box = new HurtBox(this, 50, 50, damage);
		box.addHint(this.getClass());
		addCollisionBox(box);
		health = Integer.MAX_VALUE;
	}

	public PoisonCreep(float x, float y, float moveSpeed, float damage, float theta, float gunOffset, float reduction) {
		super(x, y, 0, damage, theta, gunOffset, reduction);
	}
	
	
	@Override
	public Graphics2D draw(Graphics2D g2d) {
		// g2d = super.draw(g2d);
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		float theta = (float) (getForceSet().getNetTheta() + Math.PI);
//		int trail = (int) (24 * time + 48);
		int trail = 0;

		Shape s = new Rectangle2D.Float(drawX, drawY - 1.5f, 50, 50);

		AffineTransform transform = new AffineTransform();
		AffineTransform old = g2d.getTransform();
		transform.rotate(theta, drawX, drawY);
		g2d.transform(transform);
		g2d.setColor(Color.GREEN);
		//if (getForceSet().getNetMagnitude() > 1) {
			g2d.fill(s);
		//}
		g2d.setTransform(old);
		g2d = PaintHelper.drawCollisionBox(g2d, getCollisionBoxes());
		return g2d;
	}

}
