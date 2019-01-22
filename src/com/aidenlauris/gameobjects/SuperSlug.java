/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * SuperSlug
 * type of enemy that splits into 4 slugs on death
 */
package com.aidenlauris.gameobjects;

import java.awt.Color;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.render.SoundHelper;

public class SuperSlug extends Slug {

	/**
	 * creates the enemy at a start position
	 * @param x start x
	 * @param y start y
	 */
	public SuperSlug(float x, float y) {
		super(x, y);

		this.health = 75;

		//bigger slug
		getCollisionBoxes().clear();
		addCollisionBox(new HitBox(this, 35, 35, true));
		addCollisionBox(new HurtBox(this, 40, 40, 20));
		body.setSize(35);
		body.setSizeDecay(35);
	}

	@Override
	public void kill() {

		super.kill();

		SoundHelper.makeSound("slug.wav");
		
		
		//create 4 slugs on kill
		for (int i = 0; i < 4; i++) {
			GameLogic.addGameObject(new Slug(this.x, this.y));
		}
	}

}
