/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * AmmoDropEntity
 * an entity which flies at the player and adds ammo for the player
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.Team;
import com.aidenlauris.items.Ammo;
import com.aidenlauris.items.BulletAmmo;
import com.aidenlauris.items.EnergyCell;
import com.aidenlauris.items.ExplosiveAmmo;
import com.aidenlauris.items.ShotgunAmmo;
import com.aidenlauris.render.PaintHelper;
import com.aidenlauris.render.menu.CollectionText;

public class AmmoDropEntity extends Entity {

	// type of ammo
	private Ammo ammo = null;

	/**
	 * initializes all values relating to teams and forces
	 */
	private void initValues() {
		team = Team.PLAYER;
		ForceAnchor f = new ForceAnchor(50f, this, GameLogic.getPlayer(), -1f);
		f.setLifeSpan(Integer.MAX_VALUE);
		getForceSet().addForce(f, 60);
	}

	/**
	 * initiates the entity at a certain x, y coord
	 * @param x x of entity
	 * @param y y of entity
	 */
	public AmmoDropEntity(float x, float y) {
		super(x, y);
		initValues();

		//set the type of ammo
		switch ((int) (Math.random() * 4)) {
		case 0:
			ammo = new BulletAmmo(20);
			break;
		case 1:
			ammo = new ExplosiveAmmo(3);
			break;
		case 2:
			ammo = new EnergyCell(5);
			break;
		case 3:
			ammo = new ShotgunAmmo(5);
			break;
		}
	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {

	}

	@Override
	public void update() {
		tickUpdate();
		
		//add the ammo to the player when near by
		//creates a popup above player
		//remove when finished
		if (distToPlayer() < 40) {
			if (ammo != null) {
				Player.getPlayer().getInventory().addAmmo(ammo);
				GameLogic.addMenu(new CollectionText(ammo.item() + " " + ammo.getCount()));
			}
			kill();
		}

	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {

		
		//DRAWS ALL ROTATIONS AND STUFF FOR THIS ENTITY
		int size = 15;
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

		g2d.fill(new Rectangle2D.Float(drawX - size / 2, drawY - size / 2, size, size));
		g2d.setTransform(old);

		//IF ITS A HEALTHDROP, CHANGE THE VALUES UP
		if (this instanceof HealthDropEntity) {
			g2d.setFont(new Font(PaintHelper.font.getFontName(), Font.BOLD, 28));
			g2d.setColor(Color.WHITE);
			g2d.drawString("+", drawX - 8, drawY + 7);
			g2d.setFont(PaintHelper.font);
		}

		return g2d;
	}

	/**
	 * creates a drop at a coordinate with a certain chance in a certain range
	 * @param x coordinate
	 * @param y coordinate
	 * @param chance chance the drop will spawn
	 * @param low minimum number of entities spawned
	 * @param high maximum number of entities spawned
	 */
	public static void drop(float x, float y, double chance, int low, int high) {
		double roll = Math.random();

		if (chance > roll) {
			int varianceOfItemCount = high - low;
			int rollVariance = low + (int) Math.random() * varianceOfItemCount;

			for (int i = 0; i < rollVariance; i++) {

				float randX, randY;
				randX = (float) (x + Math.random() * 20 - 10);
				randY = (float) (y + Math.random() * 20 - 10);

				GameLogic.addGameObject(new AmmoDropEntity(randX, randY));

			}

		}
	}
}
