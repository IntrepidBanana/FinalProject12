package com.aidenlauris.gameobjects.util;
import java.util.ArrayList;

import com.aidenlauris.gameobjects.Particle;

/**
 * @author Lauris & Aiden
 * Jan 21, 2019
 * 
 * handles box collider data and logic
 */
public abstract class CollisionBox {

	private GameObject owner;
	// box coords relative to owner
	//important fields
	public float x;
	public float y;
	public float len;
	public float wid;
	public boolean isSolid;

	//collection of all classes to ignore when checking collisions
	private ArrayList<Class<? extends GameObject>> collisionHints = new ArrayList<>();

	
	/**
	 * constructor for collisionbox
	 * @param owner gameobject owner of collider
	 * @param x top left corner x position
	 * @param y top left corner y position
	 * @param len length of box
	 * @param wid width of box
	 * @param isSolid whether to count as solid or not
	 */
	public CollisionBox(GameObject owner, float x, float y, float len, float wid, boolean isSolid) {
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.len = len;
		this.wid = wid;
		this.isSolid = isSolid;
		collisionHints.add(Particle.class);
	}

	/**
	 * constructor for collisionbox; centers around owner x and y position
	 * @param owner gameobject owner of collider
	 * @param len length of box
	 * @param wid width of box
	 * @param isSolid whether to count as solid or not
	 */
	public CollisionBox(GameObject owner, float len, float wid, boolean isSolid) {
		this(owner, -wid / 2, -len / 2, len, wid, isSolid);
	}

	/**
	 * sets the new owner
	 * @param owner gameobject
	 */
	public void setOwner(GameObject owner) {
		this.owner = owner;
	}

	/**
	 * gets the owner
	 * @return gameobject owner
	 */
	public GameObject getOwner() {
		return owner;
	}

	/**
	 * get leftmost coordinate
	 * @return leftmost coord
	 */
	public float getLeft() {
		return owner.x + x;
	}

	/**
	 * get right coord
	 * @return right coord
	 */
	public float getRight() {
		return owner.x + x + wid;
	}

	/**
	 * get top coord
	 * @return top coord
	 */
	public float getTop() {
		return owner.y + y;
	}

	/**
	 * get bottom coord
	 * @return bottom coord
	 */
	public float getBottom() {
		return owner.y + y + len;
	}

	/**
	 * get x center of box
	 * @return
	 */
	public float getX() {
		return getLeft() + wid / 2;
	}

	/**
	 * get y center of box 
	 * @return
	 */
	public float getY() {
		return getTop() + len / 2;
	}

	/**
	 * add a object to ignore in collisions
	 * @param hint class of object to ignore
	 */
	public void addHint(Class<? extends GameObject> hint) {
		collisionHints.add(hint);
	}
	
	/**
	 * @return list of all classes to ignore
	 */
	public ArrayList<Class<? extends GameObject>> getHints() {
		return collisionHints;
	}
	
	/**
	 * sends a notification to the owner that a collision occured with another box
	 * @param box that this collider had a collision with
	 */
	public void notifyOwner(CollisionBox box) {
		owner.collisionOccured(box, this);
	}

}
