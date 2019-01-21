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

import com.aidenlauris.game.IOHandler;
import com.aidenlauris.game.RocketLauncher;
import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.game.util.KeyType;
import com.aidenlauris.game.util.Keys;
import com.aidenlauris.game.util.Mouse;
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

	protected long alert = Time.alert(10);
	int score = 0;
	boolean idle = true;
	public Inventory inv = new Inventory();
	Menu menu = new Menu();
	MenuItemLabel[] itemLabels = new MenuItemLabel[20];
	int selectedItem = 0;
	ArrayList<Interactable> interactables = new ArrayList<>();
	private int effectType = 8;
	private HealthBar healthBar = new HealthBar();
	public long animation = 0;
	public ArrayList<Particle> effects = new ArrayList<>();

	public Player(float x, float y, float moveSpeed) {
		super(x, y, moveSpeed, 100);
		health = 100;
		maxHealth = 100;
		z = 1;
		team = Team.PLAYER;
		addCollisionBox(new HitBox(this, 15, 15, false));
		addCollisionBox(new HurtBox(this, 20, 20, 20));
		//inv.addGun(new Pistol());
		inv.addAmmo(new BulletAmmo(125));
		inv.addAmmo(new ExplosiveAmmo(5));
		inv.addAmmo(new EnergyCell(5));
		inv.addAmmo(new ShotgunAmmo(25));

		WorldMap.addMenu(healthBar);
	}

	public void parseInput() {
		idle = true;
		float dx = 0;
		float dy = 0;
		if (isStunned()) {
			return;
		}

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
		if (Keys.isKeyPressed(KeyEvent.VK_R)) {
			effectType++;
		}
		if (Keys.isKeyPressed(KeyEvent.VK_O)) {
			Time.setDelta(Time.delta() + 0.1f);
		}
		if (Keys.isKeyPressed(KeyEvent.VK_P)) {
			Time.setDelta(Time.delta() - 0.1f);
		}
		if (Keys.isKeyPressed(KeyEvent.VK_Z)) {
			for (int i = 0; i < Math.random() * 10; i++) {

				WorldMap.addGameObject(new Giant((float) Math.random() * 900, (float) Math.random() * 900));
			}
		}
		int mouseRotation = Mouse.getWheelRotation();
		if (mouseRotation > 0) {
			if (Mouse.getFocus() instanceof ItemContainer) {
			} else {
			}
		}
		if (mouseRotation < 0) {
			if (Mouse.getFocus() instanceof ItemContainer) {
			} else {
			}
		}
		if (Keys.isKeyPressed(KeyEvent.VK_SPACE)) {
			inv.swapGun();
		}

		if (Keys.isKeyPressed(KeyEvent.VK_E)) {
			interactWith();

		}
		if (dx != 0 || dy != 0) {
			setMoveSpeed(5);
			Force f = new Force((float) (getMoveSpeed() * Time.delta()), dx, dy);
			if (dx != 0 && dy != 0) {
				f.setMagnitude((float) (getMoveSpeed() * 1.4f * Time.delta()));
			}
			f.setLifeSpan(-1);
			getForceSet().addForce(f);
		}

		if (Mouse.isLeftPressed()) {
			if(inv.getGun() != null){
			inv.getGun().useItem();
			}

		}

	}

	private void interactWith() {
		for (int i = 0; i < interactables.size(); i++) {
			if (i <= interactables.size()) {
				interactables.get(i).interact();
			}
		}

	}

	public void screenShake(Camera c, int time) {
		if (time <= 0)
			return;
		if (time % 50 == 0) {
			Force f = new Force(10f, (float) Math.toRadians(Math.random() * 360));
			f.setReduction(0.8f);
			c.getForceSet().addForce(f);
		}
		screenShake(c, time - 1);
	}

	public void update() {
		if (Time.alertPassed(alert)) {
			SoundHelper.makeSound("music.wav");
			alert = Time.alert(720);
		}

		parseInput();
		tickUpdate();
		healthBar.update(this);

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
		} else {
			// mySprite = State.Idle;
		}
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
		if (effects.size() > 500) {
			effects.remove(0);
		}
	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		if (box.isSolid) {
			collide(box, myBox);
		}

		if (box.getOwner() instanceof Enemy && myBox instanceof HurtBox) {
			((Entity) box.getOwner()).damage((HurtBox) myBox);
			((Entity) box.getOwner()).knockBack(-10f, x, y, box.getOwner().x, box.getOwner().y);
		}
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {

		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		float gunLength = 0;
		if (inv.getGuns()[0] instanceof Gun) {
			gunLength = inv.getGuns()[0].getLength();
		}

		float theta = Mouse.theta(x, y);

		Shape s = new Rectangle2D.Float(drawX, drawY - 12, gunLength * 1.3f, 6);

		AffineTransform transform = new AffineTransform();
		AffineTransform old = g2d.getTransform();
		transform.rotate(theta, drawX, drawY);
		g2d.transform(transform);
		g2d.setColor(Color.DARK_GRAY);
		g2d.fill(s);
		g2d.drawImage(SpriteManager.playerSprites.get(mySprite), null, (int) drawX - 14, (int) drawY - 12);
		g2d.setTransform(old);
		
		if (health < 1){
			g2d.drawString("YOU'RE DEAD!", 1000, 1000);
		}

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
		if (WorldMap.getPlayer() != null) {
			return WorldMap.getPlayer();
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
		if (invincibility <= 0) {
			health -= (box.damage);
			int num = (int) (Math.random() * 8) + 1;
			String choice = "" + num;
			SoundHelper.makeSound("pain" + choice + ".wav");
		}

	}

	public void damage(int damage) {
		if (invincibility <= 0) {
			health -= (damage);
			int num = (int) (Math.random() * 8) + 1;
			String choice = "" + num;
			SoundHelper.makeSound("pain" + choice + ".wav");
		}

	}

	@Override
	public void kill() {
		health = 0;
		new Explosion(x, y, 250, 700, 5000).init();;
		
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
		SoundHelper.makeSound("death.wav");
		removeSelf();
		WorldMap.restart();
	}

}
