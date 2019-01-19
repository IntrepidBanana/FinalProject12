package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.game.util.Keys;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Interactable;
import com.aidenlauris.items.BulletAmmo;
import com.aidenlauris.items.Cannon;
import com.aidenlauris.items.EnergyCell;
import com.aidenlauris.items.ExplosiveAmmo;
import com.aidenlauris.items.Gun;
import com.aidenlauris.items.Item;
import com.aidenlauris.items.LaserGun;
import com.aidenlauris.items.MachineGun;
import com.aidenlauris.items.Shotgun;
import com.aidenlauris.items.ShotgunAmmo;
import com.aidenlauris.items.Sword;
import com.aidenlauris.render.PaintHelper;

public class GunDrop extends Entity implements Interactable {

	private boolean interacting;
	private Gun gun;

	public GunDrop(float x, float y) {
		super(x, y);

		switch ((int) (Math.random() * 5)) {
		case 0:
			gun = new MachineGun();
			break;
		case 1:
			gun = new LaserGun();
			break;
		case 2:
			gun = new Cannon();
			break;
		case 3:
			gun = new Shotgun();
			break;

		case 4:
			gun = new Sword();
			break;
		}

	}

	@Override
	public void interact() {
		gun = Player.getPlayer().inv.addGun(gun);
	}

	public void interactByProximity() {
		Player player = Player.getPlayer();
		if (distToPlayer() < 50) {
			interacting = true;
			player.addInteractable(this);
		} else {
			interacting = false;
			player.removeInteractable(this);
		}
	}

	public void update() {
		interactByProximity();

		if (interacting && Keys.isKeyPressed(KeyEvent.VK_E)) {
			interact();
		}

		if (gun == null) {
			kill();
		}

	}

	@Override
	public void collisionOccured(CollisionBox theirBox, CollisionBox myBox) {

	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		Shape s = new Rectangle2D.Float(drawX - 5, drawY - 5, 10, 10);
		g2d.setColor(Color.DARK_GRAY);
		g2d.fill(s);
		if (interacting && gun != null) {
			g2d.drawString(gun.item(), drawX, drawY - 16);
		}
		return g2d;
	}

	public static void drop(float x, float y, double chance) {
		double roll = Math.random();

		if (chance > roll) {
			WorldMap.addGameObject(new GunDrop(x, y));

		}
	}

}
