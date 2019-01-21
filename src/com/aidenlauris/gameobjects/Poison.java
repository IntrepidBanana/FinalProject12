/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Poison
 * PoisonWalker creates this when it walks to damage player
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.Time;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.gameobjects.util.Team;
import com.aidenlauris.render.PaintHelper;

public class Poison extends Entity {
	
	long alert = Time.alert(180);
	long damageTimer = Time.alert(10);

	public Poison(float x, float y) {
		super(x, y);
		getCollisionBoxes().clear();
//		addCollisionBox(new HitBox(this, 25, 25, false));
		addCollisionBox(new HurtBox(this, 30, 30, 0.000004f));
		this.z = -1;
		team = Team.ENEMY;
	}


	@Override
	public void collisionOccured(CollisionBox theirBox, CollisionBox myBox) {
		
		if(theirBox.getOwner() instanceof Player && Time.alertPassed(damageTimer)) {
			((Entity)theirBox.getOwner()).damage((HurtBox)myBox);
			damageTimer = Time.alert(20);
			
		}

	}

	@Override
	public void update() {
		
		if (Time.alertPassed(alert)) {
			kill();
		}
	}
@Override
public Graphics2D draw(Graphics2D g2d) {
	
	float drawX, drawY;
	drawX = PaintHelper.x(x);
	drawY = PaintHelper.y(y);
	
	Shape s = new Rectangle2D.Float(drawX - 15, drawY - 15, 30, 30);

	g2d.setColor(Color.GREEN);
	g2d.fill(s);
	
	return g2d;
}
}
