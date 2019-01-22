package com.aidenlauris.gameobjects.util;

/**
 * @author Lauris & Aiden
 * Jan 21, 2019
 * 
 * type of collision box that can carry damage. is expected to be areas where hitboxes can be affected by damage
 */
public class HurtBox extends CollisionBox {

	public float damage;
	
	/**
	 * creates hit box with set data
	 * @param owner object that owns this box
	 * @param x relative top left corner of this box
	 * @param y relative top left corner of this box
	 * @param len length of the box
	 * @param wid width of the box
	 * @param damage damage of box
	 */
	public HurtBox(GameObject owner, float x, float y, float len, float wid, float damage) {
		super(owner, x, y, len, wid, false);
		this.damage = damage;
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * creates hit box with set data
	 * @param owner object that owns this box
	 * @param len length of the box
	 * @param wid width of the box
	 * @param damage damage of box
	 */
	public HurtBox(GameObject owner, float len, float wid, float damage) {
		super(owner, len, wid, false);
		this.damage = damage;
		// TODO Auto-generated constructor stub
	}

}
