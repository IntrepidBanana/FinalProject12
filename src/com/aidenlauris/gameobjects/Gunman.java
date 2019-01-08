package com.aidenlauris.gameobjects;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;

public class Gunman extends Enemy {

	public Gunman(int x, int y) {
		super(x, y, 50, 10, 0.2f);
	}
	
	
	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));

		  
			Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random() * 360));
			f.setId("Random");
			f.setLifeSpan(100);
			f.setReduction(0f);
			addForce(f);
	}
	
	public void update() {
		tickUpdate();

		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		time++;
		if (isStunned()) {
			return;
		}
		
		//if (time % 100 == 0){
		move();
		//}
		
		
		if (dist < 250) {
			attack();
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
		b.init();
	}

}
