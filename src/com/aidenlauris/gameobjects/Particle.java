/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Particle
 * used for effects with bullets and player
 */

package com.aidenlauris.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.aidenlauris.game.GameLogic;
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

	/**
	 * Used in the premade creator
	 * @param x coord
	 * @param y coord
	 * @param magnitude magnitude
	 * @param spread variance of angle
	 * @param theta angle of particle
	 */
	private Particle(float x, float y, float magnitude, float spread, float theta) {
		super(x, y);
		float a = (float) Math.toRadians(Math.random() * spread * 2 - spread);
		Force f = new Force(magnitude, (float) (theta + Math.PI + a));
		f.setReduction(0.3f);
		getForceSet().addForce(f);
		team = Team.PLAYER;
	
	}

	/**
	 * Creates a premade particle with a set of data
	 * @param x coord
	 * @param y coord
	 * @param magnitude magnitude
	 * @param theta angle
	 * @param spread the possible variance of angle
	 * @param count number of particles
	 * @param isSolid if we are adding a collider(NOTUSED)
	 * @param color color
	 * @param lifeSpan length of lifespan
	 * @param size size of particle
	 */
	public static void create(float x, float y, float magnitude, float theta, float spread, int count, boolean isSolid,
			Color color, int lifeSpan, int size) {
		for (int i = 0; i < count; i++) {
			Particle p = new Particle(x, y, magnitude, spread, theta);

			p.setSize(size);
			p.setColor(color);
			p.setLifeSpan(lifeSpan);
			GameLogic.addGameObject(p);
		}
	}

	/**
	 * Creates a premade particle with a set of data
	 * @param x coord
	 * @param y coord
	 * @param magnitude magnitude
	 * @param theta angle
	 * @param spread the possible variance of angle
	 * @param count number of particles
	 * @param isSolid if we are adding a collider(NOTUSED)
	 * @param color color
	 * @param lifeSpan length of lifespan
	 */
	public static void create(float x, float y, float magnitude, float theta, float spread, int count, boolean isSolid,
			Color color, int lifeSpan) {
		create(x, y, magnitude, theta, spread, count, isSolid, color, lifeSpan, 12);
	}
	/**
	 * Creates a premade particle with a set of data
	 * @param x coord
	 * @param y coord
	 * @param magnitude magnitude
	 * @param theta angle
	 * @param spread the possible variance of angle
	 * @param count number of particles
	 * @param isSolid if we are adding a collider(NOTUSED)
	 * @param color color
	 */
	public static void create(float x, float y, float magnitude, float theta, float spread, int count, Color color) {
		create(x, y, magnitude, theta, spread, count, true, color, 20);
	}

	/**
	 * Creates a premade particle with a set of data
	 * @param x coord
	 * @param y coord
	 * @param magnitude magnitude
	 * @param theta angle
	 * @param spread the possible variance of angle
	 * @param count number of particles
	 */
	public static void create(float x, float y, float magnitude, float theta, float spread, int count) {
		create(x, y, magnitude, theta, spread, count, Color.DARK_GRAY);
	}

	/**
	 * Creates a premade particle with a set of data
	 * @param x coord
	 * @param y coord
	 * @param magnitude magnitude
	 * @param theta angle
	 */
	public static void create(float x, float y, float magnitude, float theta) {
		create(x, y, magnitude, theta, 0, 1, Color.DARK_GRAY);
	}

	
	/**
	 * Creates a premade particle with a set of data
	 * @param x coord
	 * @param y coord
	 */
	public static void create(float x, float y) {
		create(x, y, 0, 0);
	}

	/**
	 * Initiates a manually made particle
	 * @param x
	 * @param y
	 */
	public Particle(float x, float y) {
		super(x, y);
	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
	}

	@Override
	public void update() {
		tickUpdate();
		if (time > getLifeSpan()) {
			kill();
		} else {
			//rotate the particle
			rotation += rotationSpeed;
			
			//update the alpha and size decay values 
			double alpha = 255 - ((time / (double) getLifeSpan()) * (255 - fadeMinimum));
			if (alpha < 0) {
				alpha = 0;
			}
			double newSize = size - ((time / (double) getLifeSpan()) * (size - sizeDecay));

			setColor(new Color(getColor().getRed(), getColor().getGreen(), getColor().getBlue(), (int) alpha));
			setDrawingSize(newSize);

		}
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

	/**
	 * @param s size
	 */
	private void setDrawingSize(double s) {
		drawingsize = s;
	}

	/**
	 * @param size size of particle
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return lifespan of particle
	 */
	public int getLifeSpan() {
		return lifeSpan;
	}

	/**
	 * @param lifeSpan set life span of particle
	 */
	public void setLifeSpan(int lifeSpan) {
		this.lifeSpan = lifeSpan;
	}


	/**
	 * @return color of particle
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color set color of particle
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @param rotationSpeed set the rotation speed of particle
	 */
	public void setRotationSpeed(int rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	/**
	 * @param rotation set initial rotation of particle
	 */
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	/**
	 * @param sizeDecay set end size of particle
	 */
	public void setSizeDecay(double sizeDecay) {
		this.sizeDecay = sizeDecay;
	}


	/**
	 * @param fadeMinimum set level of alpha at the end of particle life span
	 */
	public void setFadeMinimum(int fadeMinimum) {
		this.fadeMinimum = fadeMinimum;
	}
}
