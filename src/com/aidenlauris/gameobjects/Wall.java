/*Aiden Gimpel, Lauris Petlah
 * January 20th, 2019
 * Wall
 * literally a wall, blocks things from passing it
 */

package com.aidenlauris.gameobjects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
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
		addCollisionBox(new HitBox(this, 0, 0, len, wid, true));
	}

	/**
	 * gets the coordinates of the appropriate vertex of the wall. 0 is the top left
	 * 1 is the top right 2 is the bottom right 3 is the bottom left
	 * 
	 * @param vertexIndex
	 *            index of the vertex
	 * @return the coordinates
	 */
	public Point2D getVertex(int vertexIndex) {
		switch (vertexIndex) {
		default:
			return new Point2D.Float(x, y);
		case 1:
			return new Point2D.Float(x + wid, y);
		case 2:
			return new Point2D.Float(x + wid, y + len);
		case 3:
			return new Point2D.Float(x, y + len);
		}
	}

	private float distToPlayer(int vertex) {
		float Xvertex = (float) getVertex(vertex).getX();
		float Yvertex = (float) getVertex(vertex).getY();
		float distToPlayer = 0;
		if (p != null) {
			distToPlayer = (float) Math.hypot(p.x - Xvertex, p.y - Yvertex);
		}
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
		float a = (float) Math.hypot(getVertex(index1).getX() - getVertex(index2).getX(),
				getVertex(index1).getY() - getVertex(index2).getY());
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

	public ArrayList<Path2D> getShadow() {
		Player p = Player.getPlayer();
		float px = p.x;
		float py = p.y;
		float offset = 50;
		float shadow = 1000;
		int[] sortedVertices = verticesByDistance();

		// PATH 1 && PATH 2
		Path2D.Float path1 = new Path2D.Float();
		Path2D.Float path2 = new Path2D.Float();

		for (int i : new int[] { 0, 1, 3, 2 }) {

			Point2D vertex = getVertex(sortedVertices[i]);
			float a = (float) Math.atan2(vertex.getY() - py, vertex.getX() - px);
			float vx = PaintHelper.x(vertex.getX() + Math.cos(a) * offset);
			float vy = PaintHelper.y(vertex.getY() + Math.sin(a) * offset);
			float sx = (float) (vx + Math.cos(a) * shadow);
			float sy = (float) (vy + Math.sin(a) * shadow);

			if (i == 0) {
				path1.moveTo(vx, vy);
				path2.moveTo(sx, sy);

			}

			path1.lineTo(vx, vy);
			path2.lineTo(sx, sy);

		}
		path1.closePath();
		path2.closePath();
		// PATH 3
		Path2D.Float path3 = new Path2D.Float();
		boolean first = true;
		for (int i : widestAngle()) {
			Point2D vertex = getVertex(i);
			float a = (float) Math.atan2(vertex.getY() - py, vertex.getX() - px);
			float vx = PaintHelper.x(vertex.getX() + Math.cos(a) * offset);
			float vy = PaintHelper.y(vertex.getY() + Math.sin(a) * offset);
			float sx = (float) (vx + Math.cos(a) * shadow);
			float sy = (float) (vy + Math.sin(a) * shadow);
			if (first) {
				path3.moveTo(vx, vy);
				path3.lineTo(vx, vy);
				path3.lineTo(sx, sy);
				first = false;
			} else {
				path3.lineTo(sx, sy);
				path3.lineTo(vx, vy);
			}
		}

		path3.closePath();

		ArrayList<Path2D> paths = new ArrayList<>();

		paths.add(path1);
		paths.add(path2);
		paths.add(path3);
		return paths;
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {

		float drawX = PaintHelper.x(getVertex(0).getX());
		float drawY = PaintHelper.y(getVertex(0).getY());
		Shape s = new Rectangle2D.Float(drawX, drawY, wid, len);
		g2d.setColor(new Color(0,21,23));
		Stroke old = g2d.getStroke();
		g2d.setStroke(new BasicStroke(2));
		g2d.fill(s);
		g2d.draw(s);
		g2d.setStroke(old);

		return g2d;
	}

	@Override
	public void collisionOccured(CollisionBox box, CollisionBox myBox) {
		// TODO Auto-generated method stub

	}

}
