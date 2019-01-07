package com.aidenlauris.gameobjects.util;

public class HitBox extends CollisionBox {

	public HitBox(GameObject owner, float x, float y, float len, float wid, boolean isSolid) {
		super(owner, x, y, len, wid, isSolid);
		// TODO Auto-generated constructor stub
	}

	public HitBox(GameObject owner, float len, float wid, boolean isSolid) {
		super(owner, len, wid, isSolid);
		// TODO Auto-generated constructor stub
	}

}
