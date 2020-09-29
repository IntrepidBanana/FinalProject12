package com.aidenlauris.gameobjects.util;

/**
 * @author Lauris & Aiden
 * Jan 21, 2019
 * 
 * type of collision box that can be solid. is expected to be used as a physical "body" for a game object
 */
public class HitBox extends CollisionBox {

	/**
	 * creates hit box with set data
	 * @param owner object that owns this box
	 * @param x relative top left corner of this box
	 * @param y relative top left corner of this box
	 * @param len length of the box
	 * @param wid width of the box
	 * @param isSolid whether solid or not
	 */
	public HitBox(GameObject owner, float x, float y, float len, float wid, boolean isSolid) {
		super(owner, x, y, len, wid, isSolid);
	}

	/**
	 * creates hit box with set data
	 * @param owner object that owns this box
	 * @param len length of the box
	 * @param wid width of the box
	 * @param isSolid whether solid or not
	 */
	public HitBox(GameObject owner, float len, float wid, boolean isSolid) {
		super(owner, len, wid, isSolid);
	}

}
