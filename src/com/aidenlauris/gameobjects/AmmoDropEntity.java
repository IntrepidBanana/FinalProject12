package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.Team;
import com.aidenlauris.items.BulletAmmo;
import com.aidenlauris.items.EnergyCell;
import com.aidenlauris.items.ExplosiveAmmo;
import com.aidenlauris.items.Item;
import com.aidenlauris.items.ShotgunAmmo;
import com.aidenlauris.render.PaintHelper;
import com.aidenlauris.render.menu.CollectionText;

public class AmmoDropEntity extends Entity {

	private Ammo ammo = null;
	private int size = 15;
	private void initValues() {
		team = Team.PLAYER;
		HitBox box = new HitBox(this, 12, 12, false);
		box.addHint(Particle.class);
		box.addHint(this.getClass());
		// addCollisionBox(box);
		ForceAnchor f = new ForceAnchor(50f, this, WorldMap.getPlayer(), -1f);
		f.setLifeSpan(Integer.MAX_VALUE);
		getForceSet().addForce(f, 60);
		invincibility = 3;
	}

	AmmoDropEntity(float x, float y) {
		super(x, y);
		initValues();

		switch ((int) (Math.random() * 4)) {
		case 0:
			ammo = new BulletAmmo(20);
			break;
		case 1:
			ammo = new ExplosiveAmmo(12);
			break;
		case 2:
			ammo = new EnergyCell(5);
			break;
		case 3:
			ammo = new ShotgunAmmo(12);
			break;
		}
	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		if (box.isSolid) {
			collide(box, myBox);
		}

	}

	@Override
	public void update() {
		tickUpdate();
		if (distToPlayer() < 40) {
			if (ammo != null) {
				Player.getPlayer().getInventory().addAmmo(ammo);
				WorldMap.addMenu(new CollectionText(ammo.item() + " " + ammo.getCount()));
			}
			kill();
		}

	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);

		g2d.setColor(Color.ORANGE);
		if (this instanceof HealthDropEntity) {
			g2d.setColor(Color.MAGENTA);
		}
		AffineTransform transform = new AffineTransform();
		AffineTransform old = g2d.getTransform();
		transform.rotate(Math.toRadians(System.currentTimeMillis() / 3), drawX, drawY);
		g2d.transform(transform);

		g2d.fill(new Rectangle2D.Float(drawX - size/2, drawY - size/2, size, size));
		g2d.setTransform(old);

		if (this instanceof HealthDropEntity) {
			g2d.setFont(new Font(PaintHelper.font.getFontName(), Font.BOLD, 28));
			g2d.setColor(Color.WHITE);
			g2d.drawString("+", drawX - 8, drawY + 7);
			g2d.setFont(PaintHelper.font);
		}

		return g2d;
	}

	public static void drop(float x, float y, double chance, int low, int high) {
		double roll = Math.random();

		if (chance > roll) {
			int varianceOfItemCount = high - low;
			int rollVariance = low + (int) Math.random() * varianceOfItemCount;

			for (int i = 0; i < rollVariance; i++) {

				float randX, randY;
				randX = (float) (x + Math.random() * 20 - 10);
				randY = (float) (y + Math.random() * 20 - 10);

				WorldMap.addGameObject(new AmmoDropEntity(randX, randY));

			}

		}
	}
}
