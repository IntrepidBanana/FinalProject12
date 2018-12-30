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

public class Wall extends Entity {

	float len = 0;
	float wid = 0;
	Player p = null;

	Wall(float x, float y, int len, int wid) {
		super(x, y);
		// TODO Auto-generated constructor stub
		forces.setImmovable();
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
		g2d = PaintHelper.drawCollisionBox(g2d, collisionBoxes.get(0));

		Point2D.Float[][] points = new Point2D.Float[4][2];

		for (int i = 0; i < 4; i++) {

			// gets the vertex of the wall;
			float Xvertex = getVertex(i)[0];
			float Yvertex = getVertex(i)[1];

			// start of coordinates relative to screen;
			float Xstart = PaintHelper.x(Xvertex);
			float Ystart = PaintHelper.y(Yvertex);

			// player coordinates relative to screen;
			float playerX = PaintHelper.x(p.x);
			float playerY = PaintHelper.y(p.y);

			// angle between player and vertex;
			float theta = (float) (Math.atan2(Yvertex - p.y, Xvertex - p.x));

			// end point of the shadow line;
			float hypot = 100;
			float Xend = PaintHelper.x((float) (Xvertex + (hypot * Math.cos(theta))));
			float Yend = PaintHelper.y((float) (Yvertex + (hypot * Math.sin(theta))));

			// the start and end point of the line segments
			Point2D p1 = new Point2D.Float(Xstart, Ystart);
			Point2D p2 = new Point2D.Float(Xend, Yend);

			points[i][0] = (Float) p1;
			points[i][1] = (Float) p2;

			// drawing the line;
			Shape s = new Line2D.Float(p1, p2);
			// g2d.draw(s);

		}

		// vertices sorted by distance, closest - furthest;
		int[] distances = verticesByDistance();

		// starting a path;
		Path2D path = new Path2D.Float();

		// start at the closest point to player.
		path.moveTo(points[distances[0]][0].getX(), points[distances[0]][0].getY());

		// goto the first vertex that is part of the widest pair of vertex;
		path.lineTo(points[widestAngle()[0]][0].getX(), points[widestAngle()[0]][0].getY());

		// goto the shadow of said vertex;
		path.lineTo(points[widestAngle()[0]][1].getX(), points[widestAngle()[0]][1].getY());

		// goto shadow of second widest vertex
		path.lineTo(points[widestAngle()[1]][1].getX(), points[widestAngle()[1]][1].getY());

		// goto last widest vertex and end the path
		path.lineTo(points[widestAngle()[1]][0].getX(), points[widestAngle()[1]][0].getY());
		path.closePath();

		Point2D.Float shadow1 = points[widestAngle()[0]][1];
		Point2D.Float shadow2 = points[widestAngle()[1]][1];

		float theta = (float) Math.atan2(shadow2.getY() - shadow1.getY(), shadow2.getX() - shadow1.getX());

		Point2D.Float ctrl = new Point2D.Float();

		float ellipseX = (float) (shadow1.getX() + shadow2.getX()) / 2;
		float ellipseY = (float) (shadow1.getY() + shadow2.getY()) / 2;
		float dist = (float) Math.hypot(shadow1.getX() - shadow2.getX(), shadow1.getY() - shadow2.getY());
		// float dist = 100;
		dist *= 3;
		dist = 100;
		ctrl.setLocation(ellipseX + dist * Math.cos(theta - Math.PI / 2),
				ellipseY + dist * Math.sin(theta - Math.PI / 2));

//		System.out.println(Math.toDegrees(theta) + " " + Math.toDegrees(theta - Math.PI / 2));

		QuadCurve2D curve = new QuadCurve2D.Float((float) shadow1.getX(), (float) shadow1.getY(), (float) ctrl.getX(),
				(float) ctrl.getY(), (float) shadow2.getX(), (float) shadow2.getY());

		Shape rect = new Rectangle2D.Float((float) ctrl.getX(), (float) ctrl.getY(), 2, 2);

//		g2d.draw(rect);

		g2d.setColor(Color.gray);
//		g2d.draw(path);
//		g2d.draw(curve);

		return g2d;
	}

	@Override
	public void contactReply(CollisionBox box, CollisionBox myBox) {
		// TODO Auto-generated method stub

	}

}
