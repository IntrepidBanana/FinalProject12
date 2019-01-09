package com.aidenlauris.gameobjects;

import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;

public class GlaiveThrower extends Enemy {

	public GlaiveThrower(float x, float y) {
		super(x, y, 50, 20, 3);
	}
	
	@Override
	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));

		  
			Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random()*360));
			f.setId("GunMove");
			f.setLifeSpan(60);
			f.setReduction(0f);
			if (getForceSet().getForce("GunMove") == null){
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
		
		
		if (dist < 500 && Time.alertPassed(alert)) {
			attack();
			alert = Time.alert((long) (120));
		}

	}
	
	public void attack(){
		Bullet b = new Bullet(1f);
		b.x = this.x;
		b.y = this.y;
		Player p = Player.getPlayer();
		b.setMoveSpeed(6);
		b.setLifeSpan(180f);
		b.setGunOffset(50);
		b.team = team.ENEMY;
		float theta = (float) Math.atan2(p.y - this.y, p.x - this.x);
		b.setTheta(theta);
		Bullet b2 = new Bullet(1);
		b2.setMoveSpeed(1);
		ForceAnchor f = new ForceAnchor(100f, b2, b, -1f);
		f.setOffset(50);
		b2.getForceSet().addForce(f);
		b2.setGunOffset(50);
		b2.team = team.ENEMY;
		float theta2 = (float)Math.toRadians(Math.random()*360);
		b2.setTheta(theta2);
		b.init();
		b2.init();
		
	}

}
