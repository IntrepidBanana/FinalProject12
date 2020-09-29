/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Camera
 * Camera to show the game and move the screen
 */

package com.aidenlauris.gameobjects;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.game.util.Mouse;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.Force;
import com.aidenlauris.gameobjects.util.ForceAnchor;

public class Camera extends Entity {

	// offsets caused by mouse
	private float mouseOffsetX = 0;
	private float mouseOffsetY = 0;
	
	//size of camera initially
	public int sizeX;
	public int sizeY;
	
	//player location
	public Player player = Player.getPlayer();

	/**
	 * Initialize camera
	 */
	public Camera() {
		super(0, 0);
		this.sizeX = GameLogic.camx;
		this.sizeY = GameLogic.camy;
	}

	/**
	 * @return x radius of camera
	 */
	public int getRadiusX() {
		return sizeX / 2;
	}

	/**
	 * @return y radius of camera
	 */
	public int getRadiusY() {
		return sizeY / 2;
	}

	@Override
	public void update() {
		//updates mouseoffsets and the next location to move to
		
		player = Player.getPlayer();
		mouseOffsetX = Mouse.planeX();
		mouseOffsetY = Mouse.planeY();
		moveTo(player.x + mouseOffsetX / 5, player.y + mouseOffsetY / 5);
		tickUpdate();
	}

	/**
	 * @return left most coordinate of camera
	 */
	public float camX() {
		return x - getRadiusX();
	}

	/**
	 * @return upper most coordinate of camera
	 */
	public float camY() {
		return y - getRadiusY();
	}

	/**
	 * returns the inputed coordinate as a value relative to the cameras position
	 * @param x x input
	 * @return transformed coordinate
	 */
	public float relX(double x) {
		return (float) (x - camX());
	}
	
	/**
	 * returns the inputed coordinate as a value relative to the cameras position
	 * @param y y input
	 * @return transformed coordinate
	 */
	public float relY(double y) {
		return (float) (y - camY());
	}

	/**
	 * moves the camera to a specified position with a smoothed transition
	 * @param dx destination x coordinate
	 * @param dy destination y coordinate
	 */
	private void moveTo(float dx, float dy) {
		
		
		float distToX = dx - this.x;
		float distToY = dy - this.y;
		
		//goto that point at a speed of 20 percent per update
		this.x += distToX * 0.2;
		this.y += distToY * 0.2;

	}

	/**
	 * induce a camera shake 
	 * @param theta angle of the originated location
	 * @param angle randomized level of spread
	 * @param power power of the camera shake
	 */
	public void cameraShake(double theta, float angle, float power) {

		angle = (float) Math.toRadians(Math.random() * angle * 2 - angle);
		Force f = new Force(power * 1f, (float) (theta + Math.PI + angle));
		f.setReduction(0.1f);

		if (getForceSet().getNetMagnitude() < 10) {
			getForceSet().addForce(f);
		}

	}

	@Override
	public void collisionOccured(CollisionBox theirBox, CollisionBox myBox) {
		// TODO Auto-generated method stub
		
	}



}
