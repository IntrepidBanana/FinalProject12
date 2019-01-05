package com.aidenlauris.gameobjects;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.Team;
import com.aidenlauris.items.Item;
import com.aidenlauris.render.PaintHelper;

public class ItemDropEntity extends Entity {

	private Item item = null;

	private void initValues() {
		team = Team.PLAYER;
		HitBox box = new HitBox(this, 12, 12, false);
		box.addHint(Particle.class);
		box.addHint(this.getClass());
//		addCollisionBox(box);
		ForceAnchor f = new ForceAnchor(1f, this, WorldMap.getPlayer(), -1f);
		f.setLifeSpan(Integer.MAX_VALUE);
		getForceSet().addForce(f, 20);
		invincibility = 3;
	}

	ItemDropEntity(float x, float y) {
		super(x, y);
		initValues();
	}

	ItemDropEntity(float x, float y, Item item) {
		super(x, y);
		initValues();
		this.item = item;
	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		if (box.getOwner() instanceof Player) {
			Player p = (Player) box.getOwner();
			if (item != null) {
				p.getInventory().addItem(item);
			}
			((Player) box.getOwner()).score++;
			kill();
		}
		if (box.isSolid) {
			collide(box, myBox);
		}

	}

	@Override
	public void update() {
		tickUpdate();
		if(distToPlayer() < 40) {
			if (item != null) {
				Player.getPlayer().getInventory().addItem(item);
			}
			kill();
		}

	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);

		if (item != null) {
			switch (item.item()) {
			case "ShotgunAmmo":
				g2d.setColor(Color.red);
				break;
			case "BulletAmmo":
				g2d.setColor(Color.ORANGE);
				break;
			case "ExplosiveAmmo":
				g2d.setColor(Color.green);
				break;
			default:
				g2d.setColor(Color.magenta);
				break;
			}
		}

		AffineTransform transform = new AffineTransform();
		AffineTransform old = g2d.getTransform();
		transform.rotate(Math.toRadians(System.currentTimeMillis()/3), drawX, drawY);
		g2d.transform(transform);

		g2d.fill(new Rectangle2D.Float(drawX - 6, drawY - 6, 12, 12));
		g2d.setTransform(old);
		return g2d;
	}

	public static void drop(float x, float y, Item item, double chance, int low, int high) {
		double roll = Math.random();

		if (chance > roll) {
			int varianceOfItemCount = high - low;
			int rollVariance = low + (int) Math.random() * varianceOfItemCount;

			for (int i = 0; i < rollVariance; i++) {

				float randX, randY;
				randX = (float) (x + Math.random() * 20 - 10);
				randY = (float) (y + Math.random() * 20 - 10);

				WorldMap.addGameObject(new ItemDropEntity(randX, randY, item));

			}

		}
	}
}
