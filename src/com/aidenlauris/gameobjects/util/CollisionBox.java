package com.aidenlauris.gameobjects.util;
import java.util.ArrayList;

import com.aidenlauris.gameobjects.Particle;

public abstract class CollisionBox {

	private GameObject owner;
	// box coords relative to owner
	public float x;
	public float y;
	public float len;
	public float wid;
	public boolean isSolid;
	private ArrayList<Class<? extends GameObject>> collisionHints = new ArrayList<>();

	public CollisionBox(GameObject owner, float x, float y, float len, float wid, boolean isSolid) {
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.len = len;
		this.wid = wid;
		this.isSolid = isSolid;
		collisionHints.add(Particle.class);
	}

	public CollisionBox(GameObject owner, float len, float wid, boolean isSolid) {
		this(owner, -wid / 2, -len / 2, len, wid, isSolid);
	}

	public void setOwner(GameObject owner) {
		this.owner = owner;
	}

	public GameObject getOwner() {
		return owner;
	}

	public float getLeft() {
		return owner.x + x;
	}

	public float getRight() {
		return owner.x + x + wid;
	}

	public float getTop() {
		return owner.y + y;
	}

	public float getBottom() {
		return owner.y + y + len;
	}

	public float getX() {
		return getLeft() + wid / 2;
	}

	public float getY() {
		return getTop() + len / 2;
	}

	public void addHint(Class<? extends GameObject> hint) {
		collisionHints.add(hint);
	}
	
	public ArrayList<Class<? extends GameObject>> getHints() {
		return collisionHints;
	}
	
	public void notifyOwner(CollisionBox box) {
		owner.collisionOccured(box, this);
	}

}
