package com.aidenlauris.gameobjects;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.HurtBox;

public class Giant extends Enemy {

	public Giant(float x, float y) {
		super(x, y, 100, 10, 0.5f);
		getCollisionBoxes().clear();
		addCollisionBox(new HitBox(this, 100, 100, true));
		addCollisionBox(new HurtBox(this, 105, 105, 15));
		
	}
	
	@Override
	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		if (dist < 300) {
			ForceAnchor f = new ForceAnchor(1.5f, this, Player.getPlayer(), -1);

			f.setId("PlayerFollow");
			f.hasVariableSpeed(false);
			if (getForceSet().getForce("PlayerFollow") == null) {
				getForceSet().removeForce("Random");
				getForceSet().addForce(f);
				// System.out.println("Running Attack");
			}
		} else {
			Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random() * 360));
			f.setId("Random");
			f.setLifeSpan(60);
			f.setReduction(0f);
			if (getForceSet().getForce("Random") == null) {
				getForceSet().removeForce("PlayerFollow");
				getForceSet().addForce(f);
			}
		}
	}
	
	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		if(myBox instanceof HurtBox && box.getOwner() instanceof Player){
			((Entity)box.getOwner()).damage((HurtBox) myBox);
			float theta = (float) Math.atan2(Player.getPlayer().y - this.y, Player.getPlayer().x - this.x);
			Player.getPlayer().knockBack(50, (float) (theta+Math.PI));
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
