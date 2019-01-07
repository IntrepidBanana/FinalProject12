package com.aidenlauris.gameobjects.util;
import java.util.ArrayList;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.game.util.XY;

public class CollisionHelper {

	public static boolean sendReply(CollisionBox a, CollisionBox b) {

		if (a.getHints().contains(b.getOwner().getClass())) {
			return false;
		}

		if (b.getHints().contains(a.getOwner().getClass())) {
			return false;
		}

		if (checkCollision(a, b)) {
			a.notifyOwner(b);
			b.notifyOwner(a);
			return true;
		}
		return false;

	}

	public static boolean checkCollision(CollisionBox a, CollisionBox b) {
		if (!(a.getLeft() > b.getRight() || a.getRight() < b.getLeft() || a.getTop() > b.getBottom()
				|| a.getBottom() < b.getTop())) {
			return true;
		}
		return false;
	}

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

	public static boolean chunkCollision(GameObject obj) {

		for (XY xy : WorldMap.getMap().locateChunkOfObject(obj)) {
			ArrayList<GameObject> chunk = WorldMap.getMap().getMap().get(xy);
			
			if (chunk == null) {
				continue;
			}
			for (GameObject e : chunk) {
				
				if (sendReply(obj, e)) {
					obj.highSpeedAccess = true;
					return true;
				}
			}

		}

		return false;

	}

	public static float width(GameObject obj) {

		if (obj.getCollisionBoxes().size() == 0) {
			return 0;
		}

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

		return rightMost - leftMost;

	}

	public static float length(GameObject obj) {

		if (obj.getCollisionBoxes().size() == 0) {
			return 0;
		}

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

		return lower - upper;

	}

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

	public static float get(GameObject obj, Direction d) {

		float furthest = 0;

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
