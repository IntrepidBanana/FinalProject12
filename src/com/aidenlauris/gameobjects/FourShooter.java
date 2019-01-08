package com.aidenlauris.gameobjects;

import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.Force;

public class FourShooter extends Enemy {

	public FourShooter(float x, float y) {
		super(x, y, 25, 20, 2);
	}
	
	
	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));

		  
			Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random()*360));
			f.setId("FourMove");
			f.setLifeSpan(60);
			f.setReduction(0.05f);
			if (getForceSet().getForce("FourMove") == null){
			addForce(f);
			}	
	}
	
	public void update() {
		tickUpdate();

		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		time++;
		if (isStunned()) {
			return;
		}
		
		move();
		
		
		if (Time.alertPassed(alert)) {
			attack();
			alert = Time.alert((long) (60));
		}

	}
	
	public void attack(){
		for (int i = 0; i < 450; i += 90) {
			Bullet b = new Bullet(2);
			b.x = this.x;
			b.y = this.y;
			Player p = Player.getPlayer();
			b.setMoveSpeed(3);
			b.setLifeSpan(60);
			b.setGunOffset(50);
			b.team = team.ENEMY;
			float theta = (float)Math.toRadians(i);
			b.setTheta(theta);
			b.init();
		}

		
	}

}
