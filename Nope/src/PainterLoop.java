import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PainterLoop extends JPanel {

	int camSize = 720;
	static Camera camera;
	IOHandler io;
	boolean keys[] = new boolean[4];
	int timePressed[] = new int[4];
	double camSpeed = 1;
	WorldMap wm = new WorldMap();
	Player player = new Player(0, 0, 0.65f, wm);

	public PainterLoop(IOHandler io) {
		wm.addEntity(player);
		this.camera = new Camera(camSize, player);
		this.io = io;
		this.io.setCamera(camera);
		addKeyListener(io);
		addMouseListener(io);
		addMouseMotionListener(io);
	}

	public void gameUpdate() {
		player.giveInput(io.getKeys(), io.mouse);
		camera.update();
		wm.update();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		gameUpdate();

		for (Tile t : wm.tiles) {
			g2d.setColor(t.color);
			Shape l = new Rectangle2D.Double(camera.relX(t.x), camera.relY(t.y), Tile.size, Tile.size);
			g2d.draw(l);
		}
		for (CollisionBox cb : wm.collisionBoxes) {

			g2d.setColor(Color.red);
			float x1 = camera.relX(cb.getLeft());
			float y1 = camera.relY(cb.getTop());

			Shape hit = new Rectangle2D.Float(x1, y1, cb.len, cb.wid);
			g2d.draw(hit);

		}

		g2d.setColor(Color.green);
		for (Entity e : wm.entities) {
			for (Force f : e.forces.getForces()) {
				g2d.setColor(Color.green);
				g2d.draw(new Line2D.Float(camera.relX(e.x), camera.relY(e.y), camera.relX(f.getDx() * 50 + e.x),
						camera.relY(f.getDy() * 50 + e.y)));

			}
			g2d.setColor(Color.black);

			g2d.draw(new Line2D.Float(camera.relX(e.x), camera.relY(e.y), camera.relX(e.forces.getX() * 50 + e.x),
					camera.relY(e.forces.getY() * 50 + e.y)));

		}

		

		g2d.setColor(Color.red);
		Shape deltaPoint = new Rectangle2D.Double(camera.relX(player.x) - 2.5, camera.relY(player.y) - 2.5, 5.0, 5.0);
		g2d.draw(deltaPoint);

		repaint();
	}

}
