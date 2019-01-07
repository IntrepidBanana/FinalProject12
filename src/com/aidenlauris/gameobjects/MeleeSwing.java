package com.aidenlauris.gameobjects;

import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.gameobjects.util.Team;

public class MeleeSwing extends Projectile {
	
	public MeleeSwing(float damage) {
		setKnockback(1f);
		HurtBox box = new HurtBox(this, 6,6, damage);
		box.addHint(this.getClass());
		addCollisionBox(box);
	}
	
	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		
		if(box.getOwner() instanceof Entity) {
			Entity owner = (Entity) box.getOwner();
			if(owner.team != Team.PLAYER) {
				owner.damage((HurtBox) myBox);
				owner.knockBack(getKnockback(), getForceSet().getX(), getForceSet().getY());
				owner.stun(30);
				
			}
		}

	}
}