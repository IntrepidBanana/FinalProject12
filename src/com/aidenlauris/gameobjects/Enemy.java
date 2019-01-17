package com.aidenlauris.gameobjects;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.gameobjects.util.Team;
import com.aidenlauris.items.BulletAmmo;
import com.aidenlauris.items.EnergyCell;
import com.aidenlauris.items.ExplosiveAmmo;
import com.aidenlauris.items.ShotgunAmmo;
import com.aidenlauris.render.PaintHelper;

public class Enemy extends Entity {
	protected long alert = Time.alert(60);
	private boolean isHovering;
	private long lastMoveCall = Time.alert(60);

	public Enemy(float x, float y, int health, int strength, float speed) {
		super(x, y, 1, 1);

		addCollisionBox(new HitBox(this, 25, 25, true));
		addCollisionBox(new HurtBox(this, 30, 30, 20));

		this.health = health;
		this.maxHealth = health;
		setMoveSpeed(speed);
		team = Team.ENEMY;
	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		if(myBox instanceof HurtBox && box.getOwner() instanceof Player){
			((Entity)box.getOwner()).damage((HurtBox) myBox);
			float theta = (float) Math.atan2(Player.getPlayer().y - this.y, Player.getPlayer().x - this.x);
			Player.getPlayer().knockBack(25, (float) (theta+Math.PI));
			return;
		}
		if (box instanceof HurtBox && box.getOwner() instanceof Entity) {
			Entity owner = (Entity) box.getOwner();
		}
		if (box.isSolid) {
			collide(box, myBox);
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

		
		
		if (dist < 80) {
			attack();
		}

	}

	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
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

	public void attack() {
	}

	@Override
	public void kill() {

		ItemDropEntity.drop(x, y, new BulletAmmo(1), 0.25, 10, 15);
		ItemDropEntity.drop(x, y, new ShotgunAmmo(1), 0.2, 5, 8);
		ItemDropEntity.drop(x, y, new ExplosiveAmmo(1), 0.05, 1, 1);
		ItemDropEntity.drop(x, y, new EnergyCell(1), 0.05, 1, 2);
		HealthDropEntity.drop(x, y, 0.15, 1, 1);
		WorldMap.addGameObject(new Corpse(x, y, this));
System.out.println(getForceSet().getNetMagnitude());
		removeSelf();
	}




	@Override
	public Graphics2D draw(Graphics2D g2d) {
		float drawX, drawY;
		drawX = PaintHelper.x(x);
		drawY = PaintHelper.y(y);

		g2d = super.draw(g2d);
		g2d.drawString(this.getClass().getSimpleName(), drawX, drawY);
		return g2d;
	}
}
