/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Enemy
 * a super class for all types of enemies
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.game.util.Time;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.gameobjects.util.Team;
import com.aidenlauris.render.PaintHelper;

public class Enemy extends Entity {

	//chance to drop things
	private final float DROP_CHANCE = 0.2f;
	
	//move alert
	protected long alert = Time.alert(60);

	/**
	 * initiates an enemy with a set of values
	 * @param x x coord of enemy
	 * @param y y coord of enemy
	 * @param health health of enemy
	 * @param strength strength of enemy (not really used)
	 * @param speed movement speed
	 */
	public Enemy(float x, float y, int health, int strength, float speed) {
		super(x, y, 1, 1);

		addCollisionBox(new HitBox(this, 25, 25, false));
		addCollisionBox(new HurtBox(this, 30, 30, 20));

		this.health = health;
		this.maxHealth = health;
		setMoveSpeed(speed);
		team = Team.ENEMY;
		
		
	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		
		//damage player if enemy and player collide
		if (myBox instanceof HurtBox && box.getOwner() instanceof Player) {
			((Entity) box.getOwner()).damage((HurtBox) myBox);
			float theta = (float) Math.atan2(Player.getPlayer().y - this.y, Player.getPlayer().x - this.x);
			Player.getPlayer().knockBack(25, (float) (theta));
			return;
		}

		//collide with any solid box
		if (box.isSolid) {
			collide(box, myBox);
		}

	}

	@Override
	public void update() {

		//default operations
		tickUpdate();

		float dist = distToPlayer();
		time++;
		move();

		if (dist < 80) {
			attack();
		}

	}

	/**
	 * the move event that is called by an enemy
	 */
	public void move() {
		
		//default operation with 2 states. moving randomly and following player
		float dist = (float) Math
				.sqrt(Math.pow(GameLogic.getPlayer().x - x, 2) + Math.pow(GameLogic.getPlayer().y - y, 2));
		if (dist < 400) {
			ForceAnchor f = new ForceAnchor(3f, this, Player.getPlayer(), -1);

			f.setId("PlayerFollow");
			f.hasVariableSpeed(false);
			if (getForceSet().getForce("PlayerFollow") == null) {
				getForceSet().removeForce("Random");
				getForceSet().addForce(f);
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

	/**
	 * attack event called by enemy
	 */
	public void attack() {
	}

	@Override
	public void kill() {
		
		//on kill drop items
		AmmoDropEntity.drop(x, y, DROP_CHANCE*2, 1, 3);
		HealthDropEntity.drop(x, y, DROP_CHANCE);
		GunDrop.drop(x, y, DROP_CHANCE/2);
		
		//on kill make a corpse
		GameLogic.addGameObject(new Corpse(x, y, this));
		
		//make particles
		for(int i = 0; i < 10; i++) {
			float theta = (float) Math.toRadians(Math.random()*360);
			
			Particle p = new Particle(x, y);
			p.setColor(Color.red);
			p.setRotation(1);
			p.setSize(12);
			p.setSizeDecay(1);
			p.setLifeSpan(80);
			
			Force f = new Force(8, theta);
			f.setReduction(0.1f);
			
			p.addForce(f);
			p.init();
			
		}
		
		removeSelf();
	}

}
