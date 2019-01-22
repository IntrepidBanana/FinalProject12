/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Slug
 * small type of enemy that chases player
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.game.util.Time;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.render.PaintHelper;
import com.aidenlauris.render.SoundHelper;
import com.aidenlauris.render.util.SpriteManager;
import com.aidenlauris.render.util.SpriteManager.State;

public class Slug extends Enemy {
	protected Particle part;

	public Slug(float x, float y) {
		super(x, y, 20, 5, 0.5f);
		getCollisionBoxes().clear();
		addCollisionBox(new HitBox(this, 15, 15, true));
		addCollisionBox(new HurtBox(this, 20, 20, 10));
		part = new Particle(x, y);
		part.setColor(Color.yellow);
		part.setSize(15);
		part.setSizeDecay(15);
		part.setFadeMinimum(255);
		part.setRotationSpeed(12);
		part.setLifeSpan(Integer.MAX_VALUE);
		part.init();
	}

	@Override
	public void update() {
		part.x = x;
		part.y = y;
		super.update();
	}
	@Override
	public void kill() {

		SoundHelper.makeSound("slug.wav");
		part.kill();
		super.kill();
	}

}
