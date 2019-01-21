/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Slug
 * small type of enemy that chases player
 */

package com.aidenlauris.gameobjects;

import java.awt.Graphics2D;

import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
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

	public Slug(float x, float y) {
		super(x, y, 20, 5, 0.5f);
		getCollisionBoxes().clear();
		addCollisionBox(new HitBox(this, 15, 15, true));
		addCollisionBox(new HurtBox(this, 20, 20, 10));
	}



	@Override
	public void kill() {

		SoundHelper.makeSound("slug.wav");
		super.kill();
	}


}
