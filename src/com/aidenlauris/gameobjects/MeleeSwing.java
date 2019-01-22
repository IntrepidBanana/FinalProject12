/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * MelleSwing
 * type of projectile for the sword and knife swings
 */

package com.aidenlauris.gameobjects;

import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.gameobjects.util.Team;

public class MeleeSwing extends Projectile {
	
	/**
	 * initiates all the values for this projectile
	 * 
	 * @param damage
	 *            of bullet
	 */
	public MeleeSwing(float damage) {
		setKnockback(2f);
		HurtBox box = new HurtBox(this, 10,10, damage);
		box.addHint(this.getClass());
		addCollisionBox(box);
		//setSpawnSound("sword.wav");
	}
	
	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		
		
		if(box.getOwner() instanceof Entity) {
			Entity owner = (Entity) box.getOwner();
			if(owner.team != Team.PLAYER) {
				owner.damage((HurtBox) myBox);
				owner.knockBack(getKnockback(), getTheta());
				
			}
		}

	}
}
