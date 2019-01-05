package com.aidenlauris.render.util;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.Iterator;

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.Player;
import com.aidenlauris.gameobjects.Ray;
import com.aidenlauris.gameobjects.Wall;
import com.aidenlauris.render.PaintHelper;

public class SightPolygon {

	ArrayList<Point2D> points = new ArrayList<>();
	ArrayList<Ray> rays = new ArrayList<>();

	private float getAngle(Point2D p) {
		float dx = (float) (p.getX() - WorldMap.getPlayer().x);
		float dy = (float) (p.getY() - WorldMap.getPlayer().y);
		float angle = (float) Math.atan2(dy, dx);
		return angle;

	}

	private void add(Point2D p) {
		float myAngle = getAngle(p);
		for (int i = 0; i < points.size(); i++) {
			Point2D selectedPoint = points.get(i);
			float selectedAngle = getAngle(selectedPoint);
			if (Math.toDegrees(myAngle) < Math.toDegrees(selectedAngle)) {
				points.add(i, p);
				return;
			}
		}
		points.add(p);
	}

	public void addPointsOfWall(Wall wall) {

		int[] wid = wall.widestAngle();
		float[] nearest = wall.getVertex(wall.verticesByDistance()[0]);

		float[] vertex = wall.getVertex(wid[0]);
		Point2D p1 = new Point2D.Float(vertex[0], vertex[1]);

		vertex = wall.getVertex(wid[1]);
		Point2D p2 = new Point2D.Float(vertex[0], vertex[1]);

		Point2D p3 = new Point2D.Float(nearest[0], nearest[1]);

		add(p1);
		add(p2);
		add(p3);

	}

	public void relCoords() {
		for (Point2D p : points) {
			p.setLocation(PaintHelper.x((float) p.getX()), PaintHelper.y((float) p.getY()));
		}
	}

	public void genRays() {
		ArrayList<Wall> walls = WorldMap.getAllWalls();
		Player p = Player.getPlayer();
		rays.clear();
		for (Wall wall : walls) {
			for (int i = 0; i < 4; i++) {
				float[] v = wall.getVertex(i);
				Ray ray = new Ray(p.x, p.y, v[0], v[1]);
				rays.add(ray);
				WorldMap.addGameObject(ray);
				Ray ray1 = new Ray(p.x, p.y, v[0], v[1]);
				rays.add(ray1);
				WorldMap.addGameObject(ray1);
				Ray ray2 = new Ray(p.x, p.y, v[0], v[1]);
				rays.add(ray2);
				WorldMap.addGameObject(ray2);
				Ray ray3 = new Ray(p.x, p.y, v[0], v[1]);
				rays.add(ray3);
				WorldMap.addGameObject(ray3);
			}
		}

		int wid = WorldMap.camx / 2;
		int len = WorldMap.camy / 2;
		Ray ray1 = new Ray(p.x, p.y, p.x - wid, p.y - len);
		Ray ray2 = new Ray(p.x, p.y, p.x - wid, p.y + len);
		Ray ray3 = new Ray(p.x, p.y, p.x + wid, p.y - len);
		Ray ray4 = new Ray(p.x, p.y, p.x + wid, p.y + len);

		rays.add(ray1);
		rays.add(ray2);
		rays.add(ray3);
		rays.add(ray4);

		WorldMap.addGameObject(ray1);
		WorldMap.addGameObject(ray2);
		WorldMap.addGameObject(ray3);
		WorldMap.addGameObject(ray4);

	}

	public void getPoints() {
		points.clear();
		for (Ray ray : rays) {
			Point2D point = new Point2D.Float(ray.x, ray.y);
			add(point);
		}

		relCoords();

	}

	public Path2D getPath() {
		getPoints();
		Path2D path = new Path2D.Float();
		path.moveTo(0, 0);
		if (points.size() > 0) {

			Point2D p1 = points.get(0);
			path.moveTo(p1.getX(), p1.getY());
		}
		for (Point2D p : points) {
			double x = p.getX();
			double y = p.getY();
			path.lineTo(x, y);

		}
		path.closePath();
		return path;

	}

	public Graphics2D draw(Graphics2D g2d) {
		getPath();
		int i = 0;
		for (Point2D p : points) {
			System.out.println(points.size());
			i++;
			g2d.drawRect((int) p.getX(), (int) p.getY(), 2, 2);
			g2d.drawString("" + i, (float) p.getX(), (float) p.getY() + 10);
		}
		return g2d;

	}

}
