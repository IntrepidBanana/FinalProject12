import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class SightPolygon {

	ArrayList<Point2D> points = new ArrayList<>();

	private float getAngle(Point2D p) {
		float dx = (float) (p.getX() - WorldMap.getPlayer().x);
		float dy = (float) (p.getY() - WorldMap.getPlayer().y);
		float angle = (float) Math.atan2(dy, dx);
		return angle;

	}

	private float getPainterAngle(Point2D p) {
		float dx = (float) (p.getX() - PaintHelper.x(WorldMap.getPlayer().x));
		float dy = (float) (p.getY() - PaintHelper.y(WorldMap.getPlayer().y));
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

	public Path2D getPath() {
		points.clear();
		ArrayList<Wall> walls = WorldMap.getAllWalls();
		for (Wall w : walls) {
			float x, y;

			Point2D p1 = new Point2D.Float();
			x = w.getVertex(w.widestAngle()[0])[0];
			y = w.getVertex(w.widestAngle()[0])[1];
			p1.setLocation(x, y);

			int shadow = 720;
			Point2D p1Shadow = new Point2D.Float();
			float theta = getAngle(p1)+0.01f;
			x = (float) (x + Math.cos(theta) * shadow);
			y = (float) (y + Math.sin(theta) * shadow);
			p1Shadow.setLocation(x, y);

			Point2D p2 = new Point2D.Float();
			x = w.getVertex(w.widestAngle()[1])[0];
			y = w.getVertex(w.widestAngle()[1])[1];
			p2.setLocation(x, y);

			Point2D p2Shadow = new Point2D.Float();
			theta = getAngle(p2)+0.01f;
			x = (float) (x + Math.cos(theta) * shadow);
			y = (float) (y + Math.sin(theta) * shadow);
			p2Shadow.setLocation(x, y);

			Point2D p3 = new Point2D.Float();
			x = w.getVertex(w.verticesByDistance()[0])[0];
			y = w.getVertex(w.verticesByDistance()[0])[1];
			p3.setLocation(x, y);

			add(p1);
			add(p1Shadow);
			add(p2);
			add(p3);
			add(p2Shadow);
		}
		add(new Point2D.Float(0, 0));
		add(new Point2D.Float(WorldMap.camx, 0));
		add(new Point2D.Float(0, WorldMap.camy));
		add(new Point2D.Float(WorldMap.camx, WorldMap.camy));

		Path2D path = new Path2D.Float();
		float sx = (float) points.get(0).getX();
		float sy = (float) points.get(0).getY();
		path.moveTo(sx, sy);
		for (Point2D p : points) {
			path.lineTo(PaintHelper.x((float) p.getX()), PaintHelper.y((float) p.getY()));
		}
		path.closePath();

		return path;

	}

}
