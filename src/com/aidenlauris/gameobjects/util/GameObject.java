package com.aidenlauris.gameobjects.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.render.PaintHelper;

public abstract class GameObject {
	public float x;
	public float y;
	public int z = 0;
	private ForceSet forces = new ForceSet();
	public boolean forceAccurate = false;
	public boolean highSpeedAccess = false;
	protected long initTime = Time.global();

	ArrayList<CollisionBox> collisionBoxes = new ArrayList<>();

	public void addCollisionBox(CollisionBox box) {
		collisionBoxes.add(box);
	}

	public ArrayList<CollisionBox> getCollisionBoxes() {
		return collisionBoxes;
	}

	public abstract void update();

	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		// TODO Auto-generated method stub

	}

	public Graphics2D draw(Graphics2D g2d) {
		for (CollisionBox cb : collisionBoxes) {
			g2d = PaintHelper.drawCollisionBox(g2d, cb);
		}

		return g2d;
	}

	public void init() {
		WorldMap.addGameObject(this);
	}

	public float distToPlayer() {
		return (float) Math.hypot(x - WorldMap.player.x, y - WorldMap.player.y);
	}

	public void forceUpdate() {

		double narrowestSide = Math.min(CollisionHelper.width(this), CollisionHelper.length(this));
		if (getForceSet().getNetMagnitude() > narrowestSide && forceAccurate && narrowestSide > 0) {

			double divisions = (getForceSet().getNetMagnitude() / (narrowestSide / 2));

			double dx = getForceSet().getX() / divisions;
			double dy = getForceSet().getY() / divisions;
			for (int i = 0; i < divisions; i++) {
				if (CollisionHelper.chunkCollision(this)) {
				}
				x += dx;
				y += dy;
			}
		} else {
			x += getForceSet().getX();
			y += getForceSet().getY();
		}

		getForceSet().update();
		// x += forces.getX();
		// y += forces.getY();
	}

	public void kill() {
		removeSelf();
	}

	public void removeSelf() {
		collisionBoxes.clear();

		WorldMap.removeGameObject(this);
	}

	public ForceSet getForceSet() {
		return forces;
	}

	public void addForce(Force f) {
		getForceSet().addForce(f);
	}

}
