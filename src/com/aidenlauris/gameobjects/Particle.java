package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.Team;
import com.aidenlauris.render.PaintHelper;

public class Particle extends Entity {

	private int size = 50;
	private double drawingsize = size;
	private Color color = Color.DARK_GRAY;
	private int lifeSpan = 50;
	private int rotation = (int) (Math.random() * 360);
	private double sizeDecay = 10;
	private int rotationSpeed = 1;
	private int fadeMinimum = 55;

	public static void create(float x, float y, float magnitude, float theta, float spread, int count, boolean isSolid,
			Color color, int lifeSpan, int size) {
		for (int i = 0; i < count; i++) {
			Particle p = new Particle(x, y, magnitude, spread, theta);

			if (isSolid) {
				p.addCollider();
			}
			p.setSize(size);
			p.setColor(color);
			p.setLifeSpan(lifeSpan);
			WorldMap.addGameObject(p);
		}
	}

	public static void create(float x, float y, float magnitude, float theta, float spread, int count, boolean isSolid,
			Color color, int lifeSpan) {
		create(x, y, magnitude, theta, spread, count, isSolid, color, lifeSpan, 12);
	}

	public static void create(float x, float y, float magnitude, float theta, float spread, int count, Color color) {
		create(x, y, magnitude, theta, spread, count, true, color, 20);
	}

	public static void create(float x, float y, float magnitude, float theta, float spread, int count) {
		create(x, y, magnitude, theta, spread, count, Color.DARK_GRAY);
	}

	public static void create(float x, float y, float magnitude, float theta) {
		create(x, y, magnitude, theta, 0, 1, Color.DARK_GRAY);
	}

	public static void create(float x, float y) {
		create(x, y, 0, 0);
	}

	public Particle(float x, float y) {
		super(x, y);
		team = team.PLAYER;
	}

	Particle(float x, float y, float magnitude, float spread, float theta) {
		super(x, y);
		float a = (float) Math.toRadians(Math.random() * spread * 2 - spread);
		Force f = new Force(magnitude, (float) (theta + Math.PI + a));
		f.setReduction(0.3f);
		getForceSet().addForce(f);
		team = Team.PLAYER;

	}

	public void addCollider() {
		HitBox box = new HitBox(this, 4, 4, true);
		box.addHint(this.getClass());
//		addCollisionBox(box);
	}

	public void removeCollider() {
		getCollisionBoxes().clear();
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
		rotation += rotationSpeed;
		if (time > getLifeSpan()) {
			kill();
		} else {
			double alpha = 255 - ((time / (double) getLifeSpan()) * (255 - fadeMinimum));
			if (alpha < 0) {
				alpha = 0;
			}
			double newSize = size - ((time / (double) getLifeSpan()) * (size - sizeDecay));

			setColor(new Color(getColor().getRed(), getColor().getGreen(), getColor().getBlue(), (int) alpha));
			setDrawingSize(newSize);

		}
	}

	private void setDrawingSize(double s) {
		drawingsize = s;
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		g2d.setColor(getColor());

		AffineTransform transform = new AffineTransform();
		AffineTransform old = g2d.getTransform();
		transform.rotate(Math.toRadians(rotation), drawX, drawY);
		g2d.transform(transform);

		g2d.fill(
				new Rectangle2D.Double(drawX - (drawingsize / 2), drawY - (drawingsize / 2), drawingsize, drawingsize));

		g2d.setTransform(old);
		return g2d;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getLifeSpan() {
		return lifeSpan;
	}

	public void setLifeSpan(int lifeSpan) {
		this.lifeSpan = lifeSpan;
	}

	public int getSize() {
		return size;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getSizeDecay() {
		return sizeDecay;
	}

	public int getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(int rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public void setSizeDecay(double sizeDecay) {
		this.sizeDecay = sizeDecay;
	}

	public int getFadeMinimum() {
		return fadeMinimum;
	}

	public void setFadeMinimum(int fadeMinimum) {
		this.fadeMinimum = fadeMinimum;
	}
}
