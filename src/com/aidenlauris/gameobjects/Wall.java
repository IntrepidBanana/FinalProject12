package com.aidenlauris.gameobjects;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.render.PaintHelper;

public class Wall extends Entity {

	float len = 0;
	float wid = 0;
	Player p = null;

	public Wall(float x, float y, int len, int wid) {
		super(x, y);
		// TODO Auto-generated constructor stub
		getForceSet().setImmovable();
		this.len = len;
		this.wid = wid;
		addCollisionBox(new HitBox(this, 0, 0, wid, len, true));
	}

	/**
	 * gets the coordinates of the appropriate vertex of the wall. 0 is the top left
	 * 1 is the top right 2 is the bottom right 3 is the bottom left
	 * 
	 * @param vertexIndex
	 *            index of the vertex
	 * @return the coordinates
	 */
	public float[] getVertex(int vertexIndex) {
		switch (vertexIndex) {
		case 0:
			return new float[] { x, y };
		case 1:
			return new float[] { x + wid, y };
		case 2:
			return new float[] { x + wid, y + len };
		case 3:
			return new float[] { x, y + len };
		default:
			return null;
		}
	}

	private float distToPlayer(int vertex) {
		float Xvertex = getVertex(vertex)[0];
		float Yvertex = getVertex(vertex)[1];

		float distToPlayer = (float) Math.hypot(p.x - Xvertex, p.y - Yvertex);
		return distToPlayer;
	}

	public int[] verticesByDistance() {
		int[] vByDist = new int[] { 0, 1, 2, 3 };
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4 - i - 1; j++) {
				if (distToPlayer(vByDist[j]) > distToPlayer(vByDist[j + 1])) {
					int t = vByDist[j];
					vByDist[j] = vByDist[j + 1];
					vByDist[j + 1] = t;
				}
			}
		}
		return vByDist;
	}

	public float angleBetween(int index1, int index2) {
		float b = distToPlayer(index1);
		float c = distToPlayer(index2);
		float a = (float) Math.hypot(getVertex(index1)[0] - getVertex(index2)[0],
				getVertex(index1)[1] - getVertex(index2)[1]);
		float cosineLaw = (float) (Math.pow(b, 2) + Math.pow(c, 2) - Math.pow(a, 2));
		cosineLaw *= 1 / (2 * b * c);
		cosineLaw = (float) Math.acos(cosineLaw);

		return cosineLaw;
	}

	public int[] widestAngle() {
		int[] widest = new int[] { 0, 0 };
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (i == j) {
					continue;
				}
				if (angleBetween(i, j) > angleBetween(widest[0], widest[1])) {
					widest = new int[] { i, j };
				}

			}
		}
		return widest;
	}

	@Override
	public void update() {
		if (p == null) {
			p = WorldMap.getPlayer();
		}

	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		g2d = PaintHelper.drawCollisionBox(g2d, this);
		return g2d;
	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		// TODO Auto-generated method stub

	}

}
