package com.aidenlauris.game.util;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.Camera;
import com.aidenlauris.gameobjects.GameObject;

public class Mouse {
	private static float mouseX = 0;
	private static float mouseY = 0;
	private static boolean leftPressed = false;
	private static boolean rightPressed = false;
	private static GameObject focus = null;
	private static int rotation = 0;

	public Mouse() {
		// TODO Auto-generated constructor stub
	}

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

	public static boolean isPressed() {
		return leftPressed || rightPressed;
	}

	public static boolean isLeftPressed() {
		return leftPressed;
	}

	public static boolean isRightPressed() {
		return rightPressed;
	}

	public static void setLeft(boolean b) {
		leftPressed = b;
	}

	public static void setRight(boolean b) {
		rightPressed = b;
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

	private static Camera getCamera() {
		return WorldMap.getCamera();
	}

	public float dist() {
		return (float) Math.hypot(planeX(), planeY());
	}

	public static float theta(float originX, float originY) {
		return (float) Math.atan2(realY() - originY, realX() - originX);
	}

	public static float getMouseX() {
		return mouseX;
	}

	public static void setMouseX(double d) {
		mouseX = (float) d;
	}

	public static float getMouseY() {
		return mouseY;
	}

	public static void setMouseY(double d) {
		mouseY = (float) d;
	}

	public static GameObject getFocus() {
		return focus;
	}

	public static void setFocus(GameObject focus) {
		Mouse.focus = focus;
	}

	public static float distToMouse(float x, float y) {
		return (float) Math.hypot(x - realX(), y - realY());
	}

	public static boolean withinBox(float x1, float y1, float wid, float len) {
		if (realX() >= x1 && realX() <= x1 + wid) {
			if (realY() >= y1 && realY() <= y1 + len) {
				return true;
			}
		}
		return false;
	}

	public static boolean withinBoxRelativeToCamera(float x1, float y1, float wid, float len) {
		if (getX() >= x1 && getX() <= x1 + wid) {
			if (getY() >= y1 && getY() <= y1 + len) {
				return true;
			}
		}
		return false;
	}

	public static int getWheelRotation() {
		int r = getRotation();
		if (r != 0) {
			setRotation(0);
		}
		return r;
	}

	private static int getRotation() {
		return rotation;
	}

	public static void setRotation(int rotation) {
		Mouse.rotation = rotation;
	}

}
