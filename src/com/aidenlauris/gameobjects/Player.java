/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Player
 * the playable character
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.game.util.IOHandler;
import com.aidenlauris.game.util.KeyType;
import com.aidenlauris.game.util.Keys;
import com.aidenlauris.game.util.Mouse;
import com.aidenlauris.game.util.Time;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.HurtBox;
import com.aidenlauris.gameobjects.util.Interactable;
import com.aidenlauris.gameobjects.util.Inventory;
import com.aidenlauris.gameobjects.util.Inventory;
import com.aidenlauris.gameobjects.util.ItemContainer;
import com.aidenlauris.gameobjects.util.Team;
import com.aidenlauris.items.BulletAmmo;
import com.aidenlauris.items.Cannon;
import com.aidenlauris.items.Gun;
import com.aidenlauris.items.HealthPickup;
import com.aidenlauris.items.Knife;
import com.aidenlauris.items.LaserGun;
import com.aidenlauris.items.MachineGun;
import com.aidenlauris.items.Minigun;
import com.aidenlauris.items.Pistol;
import com.aidenlauris.items.RocketLauncher;
import com.aidenlauris.items.Shotgun;
import com.aidenlauris.items.ShotgunAmmo;
import com.aidenlauris.items.Sword;
import com.aidenlauris.render.PaintHelper;
import com.aidenlauris.render.SoundHelper;
import com.aidenlauris.render.menu.HealthBar;
import com.aidenlauris.render.menu.Menu;
import com.aidenlauris.render.menu.MenuItemLabel;
import com.aidenlauris.render.util.LightSource;
import com.aidenlauris.render.util.SightPolygon;
import com.aidenlauris.render.util.SpriteManager;
import com.aidenlauris.render.util.SpriteManager.State;
import com.aidenlauris.items.EnergyCell;
import com.aidenlauris.items.ExplosiveAmmo;

public class Player extends Entity implements LightSource, ItemContainer {

	private long musicAlert = Time.alert(10);

	// whether player is idle or not
	private boolean idle = true;

	public Inventory inv = new Inventory();

	// list of all nearby objects that player can interact with
	public ArrayList<Interactable> interactables = new ArrayList<>();

	// healthbar menu
	private HealthBar healthBar = new HealthBar();

	// animation alert - used to change animation states
	public long animation = 0;

	// responsible for dust that appears in the game world
	public ArrayList<Particle> effects = new ArrayList<>();

	/**
	 * Initializes the player
	 * 
	 * @param x
	 *            start position x
	 * @param y
	 *            start position y
	 * @param moveSpeed
	 *            move speed
	 */
	public Player(float x, float y, float moveSpeed) {
		super(x, y, moveSpeed, 100);

		health = 100;
		maxHealth = 100;
		team = Team.PLAYER;

		addCollisionBox(new HitBox(this, 15, 15, false));
		addCollisionBox(new HurtBox(this, 20, 20, 20));

		inv.addAmmo(new BulletAmmo(125));
		inv.addAmmo(new ExplosiveAmmo(5));
		inv.addAmmo(new EnergyCell(5));
		inv.addAmmo(new ShotgunAmmo(25));

		GameLogic.addMenu(healthBar);
	}

	/**
	 * parses all inputs of character
	 */
	public void parseInput() {

		// idle until proven other wise
		idle = true;

		float dx = 0;
		float dy = 0;

		// MOVEMENT
		if (Keys.isKeyHeld(KeyEvent.VK_W)) {
			dy += -1;
			idle = false;
		}
		if (Keys.isKeyHeld(KeyEvent.VK_S)) {
			dy += 1;
			idle = false;
		}
		if (Keys.isKeyHeld(KeyEvent.VK_A)) {
			dx += -1;
			idle = false;
		}
		if (Keys.isKeyHeld(KeyEvent.VK_D)) {
			dx += 1;
			idle = false;
		}

		// ADDING MOVEMENT FORCES
		if (dx != 0 || dy != 0) {
			setMoveSpeed(5);
			Force f = new Force((float) (getMoveSpeed() * Time.delta()), dx, dy);
			if (dx != 0 && dy != 0) {
				f.setMagnitude((float) (getMoveSpeed() * 1.4f * Time.delta()));
			}
			f.setLifeSpan(-1);
			getForceSet().addForce(f);
		}

		// GUNSWAP
		if (Keys.isKeyPressed(KeyEvent.VK_SPACE)) {
			inv.swapGun();
		}

		// INTERACT
		if (Keys.isKeyPressed(KeyEvent.VK_E)) {
			interactWith();

		}

		// SHOOT
		if (Mouse.isLeftPressed()) {
			if (inv.getGun() != null) {
				inv.getGun().useItem();
			}

		}

	}

	private void interactWith() {
		if (interactables.size() != 0) {
			interactables.get(0).interact();
		}

	}

	@Override
	public void update() {

		// play music
		if (Time.alertPassed(musicAlert)) {
			SoundHelper.makeSound("music.wav");
			musicAlert = Time.alert(720);
		}

		// update everything
		parseInput();
		tickUpdate();
		healthBar.update(this);

		// parse animation
		if (!idle) {
			if (Time.alertPassed(animation)) {
				switch (mySprite) {
				case Move1:
					mySprite = State.Move2;
					break;
				default:
					mySprite = State.Move1;
					break;
				}
				animation = Time.alert(10);
			}
		}

		// ADD DUST PARTICLES TO MAP
		float dx = (float) (x + Math.random() * 1500 - 750);
		float dy = (float) (y + Math.random() * 1500 - 750);

		Particle part = new Particle(dx, dy);
		part.setLifeSpan(300);
		part.setSize(0);
		part.setSizeDecay(15);
		part.setRotationSpeed(1);
		part.setFadeMinimum(0);
		part.setColor(Color.DARK_GRAY);
		part.init();
		effects.add(part);
		if (effects.size() > 300) {
			effects.get(0).kill();
			effects.remove(0);

		}
	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		// collide with solids
		if (box.isSolid) {
			collide(box, myBox);
		}

		// hurt enemies that come into contact with player
		if (box.getOwner() instanceof Enemy && myBox instanceof HurtBox) {
			((Entity) box.getOwner()).damage((HurtBox) myBox);
			((Entity) box.getOwner()).knockBack(-10f, x, y);
		}
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {

		// draw gun and sprite of character with a rotation
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		float gunLength = 0;
		if (inv.getGuns()[0] instanceof Gun) {
			gunLength = inv.getGuns()[0].getLength();
		}

		float theta = Mouse.theta(x, y);

		Shape s = new Rectangle2D.Float(drawX, drawY - 12, gunLength * 1.3f, 6);

		// rotation math
		AffineTransform transform = new AffineTransform();
		AffineTransform old = g2d.getTransform();
		transform.rotate(theta, drawX, drawY);
		g2d.transform(transform);
		g2d.setColor(Color.lightGray);
		g2d.fill(s);
		g2d.drawImage(SpriteManager.playerSprites.get(mySprite), null, (int) drawX - 14, (int) drawY - 12);
		g2d.setTransform(old);


		return g2d;
	}

	@Override
	public Graphics2D renderLight(Graphics2D gl) {
		SightPolygon sight = new SightPolygon();
		gl.setColor(new Color(0, 0, 0, 0));
		// gl.draw(sight.getPath());
		return gl;
	}

	@Override
	public Inventory getInventory() {
		return inv;
	}

	public static Player getPlayer() {
		if (GameLogic.getPlayer() != null) {
			return GameLogic.getPlayer();
		} else {
			return new Player(0, 0, 2f);
		}
	}

	public void addInteractable(Interactable e) {
		if (!interactables.contains(e)) {
			interactables.add(e);
		}
	}

	public void removeInteractable(Interactable e) {
		interactables.remove(e);
	}

	public void damage(HurtBox box) {
		health -= (box.damage);
		int num = (int) (Math.random() * 8) + 1;
		String choice = "" + num;
		SoundHelper.makeSound("pain" + choice + ".wav");

	}

	public void damage(int damage) {
		health -= (damage);
		int num = (int) (Math.random() * 8) + 1;
		String choice = "" + num;
		SoundHelper.makeSound("pain" + choice + ".wav");

	}

	@Override
	public void kill() {
		health = 0;
		new Explosion(x, y, 250, 700, 5000).init();
		;

		for (int i = 0; i < 10; i++) {
			float theta = (float) Math.toRadians(Math.random() * 360);

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
		SoundHelper.makeSound("death.wav");
		removeSelf();
		GameLogic.restart();
	}

}
