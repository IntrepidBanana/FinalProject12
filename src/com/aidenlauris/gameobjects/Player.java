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
import java.util.ArrayList;
import java.util.Map;

import com.aidenlauris.game.IOHandler;
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
import com.aidenlauris.items.EnergyCell;

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

	public Player(float x, float y, float moveSpeed) {
		super(x, y, moveSpeed, 100);
		maxHealth = 100;
		z = 1;
		team = Team.PLAYER;
		addCollisionBox(new HitBox(this, 15, 15, false));

		inv.addGun(new Pistol());
		inv.addAmmo(new BulletAmmo(125));

		WorldMap.addMenu(healthBar);
		// WorldMap.addMenu(menu);
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
			inv.getGun().useItem();
			
		}

		if (!idle) {
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
		System.out.println(time);
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
		// menu = inventory.getMenu(32);
		if (time % 1 == 0) {

			int options = 10;
			if (effectType % options == 0) {
				double theta = Math.toRadians(Math.random() * 360);
				for (int i = 1; i <= 3; i++) {
					theta += Math.toRadians(120);
					float dx = (float) (x + Math.cos(theta) * 120);
					float dy = (float) (y + Math.sin(theta) * 120);

					Particle part = new Particle(dx, dy);
					part.setLifeSpan(120);
					part.setSize(6);
					part.setSizeDecay(3);
					part.setRotationSpeed(8);
					part.setFadeMinimum(0);

					ForceAnchor fa = new ForceAnchor((float) (1f + Math.random() * 3f), part, this, -1f);
					fa.setOffset(85 * (int) (Math.random() * 3));
					fa.setLifeSpan(Integer.MAX_VALUE);
					// fa.hasVariableSpeed(false);

					part.getForceSet().addForce(fa);

					part.init();

				}
			}
			if (effectType % options == 1) {
				double theta = Math.toRadians(Math.random() * 360);
				for (int i = 1; i <= 3; i++) {
					theta += Math.toRadians(120);
					float dx = (float) (x + Math.cos(theta) * 120);
					float dy = (float) (y + Math.sin(theta) * 120);

					Particle part = new Particle(dx, dy);
					part.setLifeSpan(120);
					part.setSize(9);
					part.setSizeDecay(5);
					part.setRotationSpeed(8);
					part.setFadeMinimum(0);

					ForceAnchor fa = new ForceAnchor((float) (1f + Math.random() * 3f), part, this, -1f);
					fa.setOffset(85 + 180 * (int) Math.random());
					fa.setLifeSpan(Integer.MAX_VALUE);
					// fa.hasVariableSpeed(false);

					part.getForceSet().addForce(fa);

					part.init();

				}
			}
			if (effectType % options == 2) {
				double theta = Math.toRadians(Math.random() * 360);
				for (int i = 1; i <= 3; i++) {
					theta += Math.toRadians(120);
					float dx = (float) (x + Math.cos(theta) * 120);
					float dy = (float) (y + Math.sin(theta) * 50);

					Particle part = new Particle(dx, dy);
					part.setLifeSpan(120);
					part.setSize(6);
					part.setSizeDecay(1);
					part.setRotationSpeed(8);
					part.setFadeMinimum(0);
					part.setColor(Color.ORANGE);
					ForceAnchor fa = new ForceAnchor((float) (1f + Math.random() * 3f), part, this, -1f);
					fa.setOffset(85 * (int) (Math.random() * 3));
					fa.setLifeSpan(Integer.MAX_VALUE);
					// fa.hasVariableSpeed(false);

					part.getForceSet().addForce(fa);

					part.init();

				}
			}

			if (effectType % options == 3) {
				double theta = Math.toRadians(Math.random() * 360);
				for (int i = 1; i <= 3; i++) {
					theta += Math.toRadians(120);
					float dx = (float) (x + Math.cos(theta) * 60);
					float dy = (float) (y + Math.sin(theta) * 60);

					Particle part = new Particle(dx, dy);
					part.setLifeSpan(120);
					part.setSize(9);
					part.setSizeDecay(5);
					part.setRotationSpeed(8);
					part.setFadeMinimum(0);
					part.setColor(Color.ORANGE);
					ForceAnchor fa = new ForceAnchor(100f, part, this, -1f);
					fa.setOffset(85);
					fa.setLifeSpan(Integer.MAX_VALUE);
					// fa.hasVariableSpeed(false);

					part.getForceSet().addForce(fa);

					part.init();

				}
			}
			if (effectType % options == 4) {
				double theta = Math.toRadians(Math.random() * 360);
				for (int i = 1; i <= 2; i++) {
					theta += Math.toRadians(120);
					float dx = (float) (x + Math.cos(theta) * 120 + Math.random() * 30 - 15);
					float dy = (float) (y + Math.sin(theta) * 120 + Math.random() * 30 - 15);

					Particle part = new Particle(dx, dy);
					part.setLifeSpan(120);
					part.setSize(20);
					part.setSizeDecay(1);
					part.setRotationSpeed(12);
					part.setFadeMinimum(0);
					part.setColor(Color.black);
					ForceAnchor fa = new ForceAnchor(1f, part, this, -1f);
					fa.setOffset(85);
					fa.setLifeSpan(Integer.MAX_VALUE);
					// fa.hasVariableSpeed(false);

					part.getForceSet().addForce(fa);

					part.init();

				}
			}
			if (effectType % options == 5) {
				double theta = Math.toRadians(Math.random() * 360);
				for (int i = 1; i <= 2; i++) {
					theta += Math.toRadians(120);
					float dx = (float) (x + Math.cos(theta) * 20 + Math.random() * 30 - 15);
					float dy = (float) (y + Math.sin(theta) * 20 + Math.random() * 30 - 15);

					Particle part = new Particle(dx, dy);
					part.setLifeSpan(120);
					part.setSize(20);
					part.setSizeDecay(0);
					part.setRotationSpeed(12);
					part.setFadeMinimum(0);
					part.setColor(Color.black);
					ForceAnchor fa = new ForceAnchor(10f, part, this, -1f);
					fa.setOffset(180);
					fa.setLifeSpan(Integer.MAX_VALUE);
					fa.hasVariableSpeed(false);

					part.getForceSet().addForce(fa);

					part.init();

				}
			}
			if (effectType % options == 6) {
				if (time % 4 == 0) {
					double theta = Math.toRadians(Math.random() * 360);
					for (int i = 1; i <= 5; i++) {
						theta += Math.toRadians(120);
						float dx = (float) (x + Math.random() * 6 - 3);
						float dy = (float) (y - Math.random() * 10 - 5);

						Particle part = new Particle(dx, dy);
						part.setLifeSpan(120);
						part.setSize(6);
						part.setSizeDecay(0);
						part.setRotationSpeed(30);
						part.setFadeMinimum(180);
						part.setColor(Color.red);
						ForceAnchor fa = new ForceAnchor(3f, part, this, -1f);
						fa.setOffset(180);
						fa.setLifeSpan(Integer.MAX_VALUE);
						fa.hasVariableSpeed(true);

						part.getForceSet().addForce(fa);

						part.init();

					}
					for (int i = 1; i <= 1; i++) {
						theta += Math.toRadians(120);
						float dx = (float) (x + Math.random() * 6 - 3);
						float dy = (float) (y - Math.random() * 10 - 5);

						Particle part = new Particle(dx, dy);
						part.setLifeSpan(120);
						part.setSize(6);
						part.setSizeDecay(0);
						part.setRotationSpeed(30);
						part.setFadeMinimum(180);
						part.setColor(Color.orange);
						ForceAnchor fa = new ForceAnchor(3f, part, this, -1f);
						fa.setOffset(180);
						fa.setLifeSpan(Integer.MAX_VALUE);
						fa.hasVariableSpeed(true);

						part.getForceSet().addForce(fa);

						part.init();
					}
					for (int i = 1; i <= 1; i++) {
						theta += Math.toRadians(120);
						float dx = (float) (x + Math.random() * 6 - 3);
						float dy = (float) (y - Math.random() * 10 - 5);

						Particle part = new Particle(dx, dy);
						part.setLifeSpan(120);
						part.setSize(6);
						part.setSizeDecay(0);
						part.setRotationSpeed(30);
						part.setFadeMinimum(180);
						part.setColor(Color.yellow);
						ForceAnchor fa = new ForceAnchor(3f, part, this, -1f);
						fa.setOffset(180);
						fa.setLifeSpan(Integer.MAX_VALUE);
						fa.hasVariableSpeed(true);

						part.getForceSet().addForce(fa);

						part.init();
					}
				}
			}
			if (effectType % options == 7) {
				if (time % 1 == 0) {
					for (int i = 1; i <= 1; i++) {
						float dx = (float) (x + Math.random() * 2000 - 1000);
						float dy = (float) (y + Math.random() * 2000 - 1000);

						Particle part = new Particle(dx, dy);
						part.setLifeSpan(300);
						part.setSize(0);
						part.setSizeDecay(15);
						part.setRotationSpeed(1);
						part.setFadeMinimum(0);
						part.setColor(Color.DARK_GRAY);

						part.init();
					}
				}
			}

		}

	}


	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		if (box.isSolid) {
			collide(box, myBox);
		}
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		g2d = super.draw(g2d);

		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		float gunLength = 0;
		if (inv.getGuns()[0] instanceof Gun) {
			gunLength = inv.getGuns()[0].getLength();
		}

		float theta = Mouse.theta(x, y);

		Shape s = new Rectangle2D.Float(drawX, drawY - 5, gunLength, 10);

		AffineTransform transform = new AffineTransform();
		AffineTransform old = g2d.getTransform();
		transform.rotate(theta, drawX, drawY);
		g2d.transform(transform);
		g2d.setColor(Color.DARK_GRAY);
		g2d.fill(s);
		g2d.setTransform(old);

		// darkness??
		Point2D center = new Point2D.Float(drawX, drawY);
		float radius = 500;
		float[] dist = { 0.0f, 0.3f, 1.0f };
		Color[] colors = { new Color(0, 0, 0, 0), new Color(0, 0, 0, 0), Color.darkGray };
		RadialGradientPaint radial = new RadialGradientPaint(center, radius, dist, colors);
		Paint oldpaint = g2d.getPaint();

		// Shape barOutline = new Rectangle2D.Float(100, 100, maxHealth*2 + 5, 30);
		// g2d.setColor(Color.BLACK);
		// g2d.fill(barOutline);
		//
		// Shape healthBar = new Rectangle2D.Float(100, 100, health*2, 25);
		// g2d.setColor(Color.RED);
		// g2d.fill(healthBar);

		g2d.setPaint(radial);
		// g2d.fillRect(0, 0, WorldMap.camx, WorldMap.camy);
		g2d.setPaint(oldpaint);

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
		SoundHelper.makeSound("death.wav");
		super.kill();
	}

}
