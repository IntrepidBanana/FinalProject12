/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * GunDrop
 * object for the gun that drop on the floor for the player to pick up
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.game.util.Keys;
import com.aidenlauris.game.util.Time;
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
import com.aidenlauris.items.Minigun;
import com.aidenlauris.items.RocketLauncher;
import com.aidenlauris.items.Shotgun;
import com.aidenlauris.items.ShotgunAmmo;
import com.aidenlauris.items.Sword;
import com.aidenlauris.render.PaintHelper;

public class GunDrop extends Entity implements Interactable {

	
	// true if the player is interacting with this object
	private boolean interacting;
	
	//gun this this object has
	public Gun gun;
	private Particle p;

	
	
	/**
	 * initiates this gun drop with a randomized gun
	 * @param x
	 * @param y
	 */
	public GunDrop(float x, float y) {
		super(x, y);
		
		//randomizes gun drops
		ArrayList<Gun> guns = new ArrayList<>();
		guns.add(new MachineGun());
		guns.add(new Cannon());
		guns.add(new Shotgun());
		guns.add(new LaserGun());
		guns.add(new Sword());
		guns.add(new Minigun());
		guns.add(new RocketLauncher());

		int index = (int) (Math.random() * guns.size());
		gun = guns.get(index);

		
		//funky particle
		p = new Particle(x, y);
		p.setLifeSpan(Integer.MAX_VALUE);
		p.setRotation((int) (System.currentTimeMillis() / 3));
		p.setRotationSpeed(3);
		p.setColor(Color.LIGHT_GRAY);
		p.setFadeMinimum(255);
		p.setSize(20);
		p.setSizeDecay(20);
		p.init();
	}

	@Override
	public void interact() {
		
		//swap the player gun with this gun
		gun = Player.getPlayer().inv.addGun(gun);
	}

	/**
	 * checks whether the player can interact with this object
	 * if within 50 pixels, we can interact. add it to the list of things the player can interact with
	 */
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

		
		// if nearby and the key is pressed.
		if (interacting && Keys.isKeyPressed(KeyEvent.VK_E)) {
			interact();
		}

		
		//if no gun...
		if (gun == null) {
			Player.getPlayer().removeInteractable(this);
			kill();
		}

	}

	@Override
	public void kill() {
		p.kill();
		super.kill();
	}

	@Override
	public void collisionOccured(CollisionBox theirBox, CollisionBox myBox) {

	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {


		
		//adds the text for this objects
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		if (interacting) {

			String str = "[E] " + gun;
			int wid = g2d.getFontMetrics().stringWidth(str);

			g2d.setColor(Color.lightGray);
			g2d.setFont(PaintHelper.font);
			g2d.drawString(str, drawX - wid / 2 - 16, drawY - 24);
		}
		return g2d;
	}

	/**
	 * Spawns a gun drop at a coordinate with a certain chance to spawn
	 * @param x x coord
	 * @param y y coord
	 * @param chance chance this will drop
	 */
	public static void drop(float x, float y, double chance) {
		double roll = Math.random();

		if (chance > roll) {
			GameLogic.addGameObject(new GunDrop(x, y));

		}
	}

}
