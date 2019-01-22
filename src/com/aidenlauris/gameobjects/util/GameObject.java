package com.aidenlauris.gameobjects.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.game.util.Time;
import com.aidenlauris.render.PaintHelper;
import com.aidenlauris.render.util.SpriteManager.State;

/**
 * @author Lauris & Aiden Jan 21, 2019
 * 
 *         represents all objects that are holding data and on the screen. These
 *         objects can have forces invoked on them, have collision boxes. All
 *         entities, enemies, item drops, player, etc. are examples of game
 *         objects
 * 
 * 
 */
/**
 * @author Lauris & Aiden
 * Jan 21, 2019
 */
public abstract class GameObject {

	// x,y position of object
	public float x;
	public float y;

	// UNUSED CURRENTLY
	// the draw order of this object
	// e.g. if z = 1 for object A, and z = 0; for object B
	// object A will draw above object B
	public int z = 0;

	// all forces for this object
	private ForceSet forces = new ForceSet();

	// whether there is a need for this object to have pixel perfect collision
	// detection
	public boolean forceAccurate = false;

	// whether this item was accessed in a high speed check
	public boolean highSpeedAccess = false;

	// time of initiation
	protected long initTime = Time.global();

	// current state of animation and animation alert timer.
	public State mySprite = State.Idle;
	public long animation = 0;

	// all collision boxes
	public ArrayList<CollisionBox> collisionBoxes = new ArrayList<>();

	/**
	 * Debug purposes. prints all the forces associated with this object
	 */
	public void printAllForces() {
		System.out.println("\n Total Forces: " + getForceSet().getForces().size());
		for (Force f : getForceSet().getForces()) {
			System.out.println(f.getId() + ": " + f.getMagnitude() + "/tick,  " + (-f.getReduction()) + "/tick/tick");
		}
	}

	/**
	 * returns the distance between this object to the player
	 * 
	 * @return distance
	 */
	public float distToPlayer() {
		return (float) Math.hypot(x - GameLogic.player.x, y - GameLogic.player.y);
	}

	/**
	 * adding this object to the game world.
	 */
	public void init() {
		GameLogic.addGameObject(this);
	}

	/**
	 * update method is called every tick. This is used as a 'main method' for game
	 * objects.
	 */
	public abstract void update();

	/**
	 * updates all force. If force accurate is true, performs force calculations in
	 * an intratick basis with multiple calls per tick
	 */
	public void forceUpdate() {

		//narrowest side of the collision boxes
		double narrowestSide = Math.min(CollisionHelper.width(this), CollisionHelper.length(this));
		
		// if this object is force accurate and the net force is greater than the
		// narrowest side, perform intratick calculations
		if (getForceSet().getNetMagnitude() > narrowestSide && forceAccurate && narrowestSide > 0) {

			
			//how many intratick calculations we need to make
			double divisions = (getForceSet().getNetMagnitude() / (narrowestSide));

			//gets a fraction of the total per tick delta change
			double dx = getForceSet().getDX() / divisions;
			double dy = getForceSet().getDY() / divisions;
			
			//for each division check all collisions and progress the current x and y value
			for (int i = 0; i < divisions; i++) {
				CollisionHelper.chunkCollision(this);
				x += dx;
				y += dy;
			}
		} else {
			//if not force accurate, add the forces to the x normally
			x += getForceSet().getDX();
			y += getForceSet().getDY();
		}

		//update all forces
		getForceSet().update();
	}

	/**
	 * this method is called whenever this object recieves a collision notification. Can be overridden but not necassery
	 * @param box box collider which collided with this object
	 * @param myBox this objects box collider which was collided with
	 */
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		// TODO Auto-generated method stub

	}

	/**
	 * if a collision occured, figure out the placement of my coordinate
	 * 
	 * @param box
	 *            the collider this collision box collided with
	 * @param myBox
	 *            this collision box
	 */
	public void collide(CollisionBox box, CollisionBox myBox) {

		// if no collision occured, dont check
		if (!CollisionHelper.checkCollision(box, myBox)) {
			return;
		}

		// LEGACY CODE;
		// it works and im scared to touch this part of the code

		// 0 mybox is above
		// 1 mybox is to the right
		// 2 mybox is below
		// 3 mybox is to the left

		// gets the smallest difference for each direction
		int direction = 0;
		float smallestDifference = myBox.getBottom() - box.getTop();
		if (box.getRight() - myBox.getLeft() < smallestDifference) {
			smallestDifference = box.getRight() - myBox.getLeft();
			direction = 1;
		}

		if (box.getBottom() - myBox.getTop() < smallestDifference) {
			smallestDifference = box.getBottom() - myBox.getTop();
			direction = 2;
		}

		if (myBox.getRight() - box.getLeft() < smallestDifference) {
			smallestDifference = myBox.getRight() - box.getLeft();
			direction = 3;
		}

		GameObject owner = myBox.getOwner();

		// for the given direction, do the math to figure out my new coordinate
		switch (direction) {
		case 0:
			owner.y = -2 + box.getTop() - myBox.len / 2 + (myBox.getY() - owner.y);

			break;
		case 1:

			owner.x = 2 + box.getRight() + myBox.wid / 2 + (myBox.getX() - owner.x);
			break;
		case 2:
			owner.y = 2 + box.getBottom() + myBox.len / 2 + (myBox.getY() - owner.y);
			break;
		case 3:

			owner.x = -2 + box.getLeft() - myBox.wid / 2 + (myBox.getX() - owner.x);
			break;
		}

	}

	/**
	 * called when the render logic can view this object. Meant to perform all graphical manipulations here
	 * @param g2d graphics logic unit
	 * @return the updated graphics 
	 */
	public Graphics2D draw(Graphics2D g2d) {

		return g2d;
	}

	/**
	 * On call, this method will remove itself. This method can be overriden to override how the object will disappear
	 */
	public void kill() {
		removeSelf();
	}

	/**
	 * removes this object from the game
	 */
	public void removeSelf() {
		collisionBoxes.clear();

		GameLogic.removeGameObject(this);
	}

	/**
	 * adds a collisionbox to this object
	 * @param box the box collider to add
	 */
	public void addCollisionBox(CollisionBox box) {
		collisionBoxes.add(box);
	}

	/**
	 * gets list of all collision boxes for this object
	 * @return list of box colliders
	 */
	public ArrayList<CollisionBox> getCollisionBoxes() {
		return collisionBoxes;
	}

	/**
	 * add a force to this object
	 * @param f force
	 */
	public void addForce(Force f) {
		getForceSet().addForce(f);
	}
	

	/**
	 * gets the set of forces for this object
	 * @return the forceset class
	 */
	public ForceSet getForceSet() {
		return forces;
	}

}
