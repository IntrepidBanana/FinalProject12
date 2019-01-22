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

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.render.PaintHelper;

/**
 * @author Lauris & Aiden
 * Jan 22, 2019
 * 
 * a non-passsable entity that creates shadows
 *
 */
public class Wall extends Entity {

	//size of wall
	public float len = 0;
	public float wid = 0;
	
	//easy access to player
	private Player p = null;

	/**
	 * Creates a wall at a position with a certain size
	 * @param x start x
	 * @param y start y
	 * @param len length or height of wall 
	 * @param wid width of wall
	 */
	public Wall(float x, float y, int len, int wid) {
		super(x, y);

		//unable to move
		getForceSet().setImmovable();
		
		//starts variables
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

	/**
	 * Calculates the distance to the player from a certain vertex
	 * @param vertex index of vertex
	 * @return distance
	 */
	private float distToPlayer(int vertex) {
		
		//basic pythogoras
		float xVertex = (float) getVertex(vertex).getX();
		float yVertex = (float) getVertex(vertex).getY();
		float distToPlayer = 0;
		if (p != null) {
			distToPlayer = (float) Math.hypot(p.x - xVertex, p.y - yVertex);
		}
		return distToPlayer;
	}

	/**
	 * Sorts the indices of the vertices by the distance to the player, from closest to farthest
	 * @return integer array of vertices indicies
	 */
	public int[] verticesByDistance() {
		
		//default array
		int[] vByDist = new int[] { 0, 1, 2, 3 };

		//does a bubble sort because im lazy
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

	/**
	 * Angle in radians between 2 vertices and player using cosine law
	 * @param index1 first vertex
	 * @param index2 second vertex
	 * @return
	 */
	public float angleBetween(int index1, int index2) {
		
		//cosine law math to solve for angle:
		//p = player
		//v[1,2] = vertex
		//A = angle we calculate
		//[/,\]
		//V1
		//_\
		//__\
		//__AP
		//__/
		//_/
		//V2
		float b = distToPlayer(index1);
		float c = distToPlayer(index2);
		float a = (float) Math.hypot(getVertex(index1).getX() - getVertex(index2).getX(),
				getVertex(index1).getY() - getVertex(index2).getY());
		float cosineLaw = (float) (Math.pow(b, 2) + Math.pow(c, 2) - Math.pow(a, 2));
		cosineLaw *= 1 / (2 * b * c);
		cosineLaw = (float) Math.acos(cosineLaw);

		return cosineLaw;
	}

	/**
	 * returns the 2 vertices with the widest angle relative to the player
	 * @return integer array with the 2 angles that are widest
	 */
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
		
		///makes sure the player is always the appropriate value
		if (p == null) {
			p = GameLogic.getPlayer();
		}

	}

	/**
	 * Gets an arraylist of all shadow shapes
	 * shape 1: a quadrilateral shape that represents the wall with a slight offset according to the player
	 * shape 2: similar to shape 1 with a larger offset
	 * shape 3: a shape that fills in the space between 1 and 2
	 * 
	 * @return list of 2d paths to be drawn later
	 */
	public ArrayList<Path2D> getShadow() {
		
		
		//variables
		float px = p.x;
		float py = p.y;
		
		//offset of shape 1
		float offset = 50;
		
		//offset of shape 2
		float shadow = 1000;
		
		
		int[] sortedVertices = verticesByDistance();

		// PATH 1 && PATH 2
		Path2D.Float path1 = new Path2D.Float();
		Path2D.Float path2 = new Path2D.Float();

		
		//this order works to create a consistent shadow
		//derived from trial an error
		for (int i : new int[] { 0, 1, 3, 2 }) {

			//current vertex
			Point2D vertex = getVertex(sortedVertices[i]);
			
			//get angle between point and player
			float a = (float) Math.atan2(vertex.getY() - py, vertex.getX() - px);
			
			//gets the appropriate vertex for the shapes
			float shape1X = PaintHelper.x(vertex.getX() + Math.cos(a) * offset);
			float shape1Y = PaintHelper.y(vertex.getY() + Math.sin(a) * offset);

			float shape2X = (float) (shape1X + Math.cos(a) * shadow);
			float shape2Y = (float) (shape1Y + Math.sin(a) * shadow);

			
			//Path2D need a start position, this is the management for that
			if (i == 0) {
				path1.moveTo(shape1X, shape1Y);
				path2.moveTo(shape2X, shape2Y);

			}
			//creates a line from the previous coordinate to this one
			path1.lineTo(shape1X, shape1Y);
			path2.lineTo(shape2X, shape2Y);

		}
		path1.closePath();
		path2.closePath();
		
		
		// PATH 3
		Path2D.Float path3 = new Path2D.Float();
		boolean first = true;
		
		//connects the 2 points of the widest angle of shape 1 and shape 2.
		for (int i : widestAngle()) {
			
			//only requires 2 points because shape 2 is based off shape 1
			Point2D vertex = getVertex(i);
			float a = (float) Math.atan2(vertex.getY() - py, vertex.getX() - px);
			float vx = PaintHelper.x(vertex.getX() + Math.cos(a) * offset);
			float vy = PaintHelper.y(vertex.getY() + Math.sin(a) * offset);
			float sx = (float) (vx + Math.cos(a) * shadow);
			float sy = (float) (vy + Math.sin(a) * shadow);
			
			//ordering of line logic
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

		
		//return the paths
		ArrayList<Path2D> paths = new ArrayList<>();

		paths.add(path1);
		paths.add(path2);
		paths.add(path3);
		return paths;
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {

		
		//draws the wall
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
		//no collision event
	}

}
