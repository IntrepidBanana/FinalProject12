package com.aidenlauris.gameobjects.util;
import java.util.ArrayList;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.game.util.XY;

/**
 * @author Lauris & Aiden
 * Jan 21, 2019
 * 
 * helper class for collision detection
 */
public class CollisionHelper {

	/**
	 * send a reply to both boxes to indicate a collision occured
	 * @param a box1
	 * @param b box2
	 * @return false if no collision occurred
	 */
	public static boolean sendReply(CollisionBox a, CollisionBox b) {

		//check hints
		if (a.getHints().contains(b.getOwner().getClass())) {
			return false;
		}

		if (b.getHints().contains(a.getOwner().getClass())) {
			return false;
		}

		
		//check collision
		if (checkCollision(a, b)) {
			a.notifyOwner(b);
			b.notifyOwner(a);
			return true;
		}
		return false;

	}

	/**
	 * checks if the two boxes are colliding
	 * @param a box1
	 * @param b box2
	 * @return true if collision occurred
	 */
	public static boolean checkCollision(CollisionBox a, CollisionBox b) {
		
		//uses Axis Aligned collision detection method
		if (!(a.getLeft() > b.getRight() || a.getRight() < b.getLeft() || a.getTop() > b.getBottom()
				|| a.getBottom() < b.getTop())) {
			return true;
		}
		return false;
	}

	/**
	 * checks all collision for 2 sets of collision boxes
	 * @param a set1
	 * @param b set2
	 * @return true if any collisions occured
	 */
	public static boolean collideSets(ArrayList<CollisionBox> a, ArrayList<CollisionBox> b) {

		for (int i = 0; i < a.size(); i++) {
			CollisionBox box1 = a.get(i);
			for (int j = 0; j < b.size(); j++) {
				CollisionBox box2 = b.get(j);

				if (box1.getOwner() == box2.getOwner()) {
					continue;
				}
				if (checkCollision(box1, box2)) {
					box1.notifyOwner(box2);
					box2.notifyOwner(box1);
					return true;
				}

			}
		}
		return false;
	}

	/**
	 * checks all collisions for an object in its chunk. This method is used to check high speed accesses
	 * @param obj game object
	 * @return true if any collision occured
	 */
	public static boolean chunkCollision(GameObject obj) {

		//for each chunk this object occupies...
		for (XY xy : GameLogic.getMap().locateChunkOfObject(obj)) {
			ArrayList<GameObject> chunk = GameLogic.getMap().getMap().get(xy);
			
			if (chunk == null) {
				continue;
			}
			
			for (GameObject e : chunk) {
				
				// if collision occurs, send reply
				if (sendReply(obj, e)) {
					obj.highSpeedAccess = true;
					return true;
				}
			}

		}

		return false;

	}

	/**
	 * get width of the entire set of collision boxes for an object
	 * @param obj gameobject
	 * @return float value of width
	 */
	public static float width(GameObject obj) {

		if (obj.getCollisionBoxes().size() == 0) {
			return 0;
		}

		
		//finds the right and leftmost coordinates
		float leftMost = Integer.MAX_VALUE;
		float rightMost = Integer.MIN_VALUE;

		for (CollisionBox c : obj.getCollisionBoxes()) {
			if (c.getLeft() < leftMost) {
				leftMost = c.getLeft();
			}
			if (c.getRight() > rightMost) {
				rightMost = c.getRight();
			}
		}

		
		//differnce of the two
		return rightMost - leftMost;

	}

	/**
	 * get total height of collision boxes for an object
	 * @param obj gameobject
	 * @return float value of length
	 */
	public static float length(GameObject obj) {

		if (obj.getCollisionBoxes().size() == 0) {
			return 0;
		}

		// finds the upper and lowermost coordinates
		float upper = Integer.MAX_VALUE;
		float lower = Integer.MIN_VALUE;
		
		for (CollisionBox c : obj.getCollisionBoxes()) {
			if (c.getTop() < upper) {
				upper = c.getTop();
			}
			if (c.getBottom() > lower) {
				lower = c.getBottom();
			}
		}

		//return difference
		return lower - upper;

	}

	/**
	 * check collision and send reply for 2 objects
	 * @param a object1
	 * @param b object2
	 * @return true if collision occured
	 */
	public static boolean sendReply(GameObject a, GameObject b) {
		if (a == b) {
			return false;
		}
		for (CollisionBox box1 : a.getCollisionBoxes()) {
			for (CollisionBox box2 : b.getCollisionBoxes()) {
				if (sendReply(box1, box2)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * gets the bound of object in a certain direction
	 * @param obj gameobject
	 * @param d direction of collider
	 * @return float of coordinate of said object
	 */
	public static float getColliderBound(GameObject obj, Direction d) {

		float furthest = 0;

		//for each collider, find the greatest distance in said direction
		for (int i = 0; i < obj.getCollisionBoxes().size(); i++) {
			CollisionBox box = obj.getCollisionBoxes().get(i);
			
			
			switch (d) {
			case TOP:
				if (box.getTop() < furthest || i == 0) {
					furthest = box.getTop();
				}
				break;
			case BOTTOM:
				if (box.getBottom() > furthest || i == 0) {
					furthest = box.getBottom();
				}
				break;
			case LEFT:
				if (box.getLeft() < furthest || i == 0) {
					furthest = box.getLeft();
				}
				break;
			case RIGHT:
				if (box.getRight() > furthest || i == 0) {
					furthest = box.getRight();
				}
				break;
			default:
				break;
			}
		}
		return furthest;

	}

}
