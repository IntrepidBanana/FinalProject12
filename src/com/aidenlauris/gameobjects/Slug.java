package com.aidenlauris.gameobjects;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.HurtBox;

public class Slug extends Enemy {

	public Slug(float x, float y) {
		super(x, y, 20, 5, 0.5f);
		getCollisionBoxes().clear();
		addCollisionBox(new HitBox(this, 15, 15, true));
		addCollisionBox(new HurtBox(this, 20, 20, 5));
	}
	
	
	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		if(myBox instanceof HurtBox && box.getOwner() instanceof Player){
			((Entity)box.getOwner()).damage((HurtBox) myBox);
			float theta = (float) Math.atan2(Player.getPlayer().y - this.y, Player.getPlayer().x - this.x);
			Player.getPlayer().knockBack(15, (float) (theta+Math.PI));
			return;
		}
		if (box instanceof HurtBox && box.getOwner() instanceof Entity) {
			Entity owner = (Entity) box.getOwner();
		}
		if (box.isSolid) {
			collide(box, myBox);
		}

	}
	
	

}
