package com.aidenlauris.game.util;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.gameobjects.Camera;
import com.aidenlauris.gameobjects.util.GameObject;

/**
 * @author Lauris & Aiden
 * Jan 21, 2019
 * All mouse activities and mouse logic including: presses, mousewheel, movement
 */
public class Mouse {
	
	
	//all variables
	private static float mouseX = 0;
	private static float mouseY = 0;
	private static boolean leftPressed = false;


	/**
	 * Gets the coordinate of the mouse relative to the top left of the screen
	 * 
	 * @return x coordinate of mouse
	 */
	public static float getX() {
		return getMouseX();
	}

	/**
	 * Gets the coordinate of the mouse relative to the top left of the screen
	 * 
	 * @return y coordinate of mouse
	 */
	public static float getY() {
		return getMouseY();
	}


	/**
	 * returns whether the left button is pressed
	 * @return left button is pressed
	 */
	public static boolean isLeftPressed() {
		return leftPressed;
	}


	/**
	 * sets the value of whether the mouse is pressed or not
	 * @param b boolean if button is pressed
	 */
	public static void setLeft(boolean b) {
		leftPressed = b;
	}


	/**
	 * gets the coordinate of the mouse relative to the world map
	 * 
	 * @return x coordinate
	 */
	public static float realX() {
		if (getCamera() != null) {
			return getMouseX() + getCamera().camX();
		}
		return 0;
	}

	/**
	 * gets the coordinate of the mouse relative to the world map
	 * 
	 * @return y coordinate
	 */
	public static float realY() {
		if (getCamera() != null) {
			return getMouseY() + getCamera().camY();
		}
		return 0;
	}

	/**
	 * gets the coordinate of the mouse relative to the center of the screen.
	 * 
	 * @return x coordinate
	 */
	public static float planeX() {
		if (getCamera() != null) {
			return getMouseX() - getCamera().getRadiusX();
		}
		return 0;
	}

	/**
	 * gets the coordinate of the mouse relative to the center of the screen.
	 * 
	 * @return y coordinate
	 */
	public static float planeY() {
		if (getCamera() != null) {
			return getMouseY() - getCamera().getRadiusY();
		}
		return 0;
	}

	/**
	 * gets the value of the camera
	 * @return the Camera class
	 */
	private static Camera getCamera() {
		return GameLogic.getCamera();
	}


	/**
	 * returns the radians from the center of the camera to given point
	 * @param originX x Coordinate of point
	 * @param originY y Coordinate of point
	 * @return angle between center and point
	 */
	public static float theta(float originX, float originY) {
		return (float) Math.atan2(realY() - originY, realX() - originX);
	}

	/**
	 * gets mouse x position
	 * @return mouse x position
	 */
	public static float getMouseX() {
		return mouseX;
	}

	/**
	 * sets the mouse x position
	 * @param x new x position
	 */
	public static void setMouseX(double x) {
		mouseX = (float) x;
	}

	/**
	 * gets mouse y position
	 * @return mouse y position
	 */
	public static float getMouseY() {
		return mouseY;
	}

	/**
	 * sets mouse y position
	 * @param y
	 */
	public static void setMouseY(double y) {
		mouseY = (float) y;
	}




}
