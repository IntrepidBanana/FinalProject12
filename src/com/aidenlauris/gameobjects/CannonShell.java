package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.render.PaintHelper;

public class CannonShell extends Projectile{


	
	public CannonShell(int damage) {
//		weight = 10;
		HurtBox box = new HurtBox(this, 8, 8, damage);
		box.addHint(this.getClass());
		addCollisionBox(box);
		setKnockback(10f);
//		setSpawnSound("cannon.wav");
	}

	
	@Override
	public void kill() {
		WorldMap.addGameObject(new Explosion(x, y, 200, 30f, 150f));
		removeSelf();
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		
		Shape s = new Rectangle2D.Float(drawX- 5,drawY-5,10,10);
		
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(s);
		
		return g2d;
	}

}
