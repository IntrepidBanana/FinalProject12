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
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PainterLoop extends JPanel {

	int camSize = 720;
	Camera camera;
	IOHandler io;
	boolean keys[] = new boolean[4];
	int timePressed[] = new int[4];
	double camSpeed = 1;
	Player player = new Player(50, 50, .3f);

	public PainterLoop(IOHandler io) {
		WorldMap.init();
		WorldMap.addEntity(player);
//		wm.addEntity(new Slug(wm, 90, 90, 1, 1, 1));
//		wm.addEntity(new Enemy(wm, 100, 100, 50, 10, 0.5f));
		WorldMap.addEntity(new Wall(0, 0, 40, 1000));
		WorldMap.addEntity(new Wall(0, 0, 1000, 40));
		WorldMap.addEntity(new Wall(800, 0, 1000, 40));
		WorldMap.addEntity(new Wall(0, 800, 40, 1000));
//		wm.addEntity(player);
//		wm.addEntity(new Slug(wm, 90, 90, 1, 1, 0.2f));
//		wm.addEntity(new Enemy(wm, 100, 100, 50, 10, 0.2f));

		
		this.io = io;
		this.camera = new Camera(camSize, player,io);
		WorldMap.setCamera(camera);
		this.io.setCamera(camera);
		addKeyListener(io);
		addMouseListener(io);
		addMouseMotionListener(io);
	}

	public void gameUpdate() {
		player.parseInput(io.getKeys(), io.getMouse());
		camera.update();
		WorldMap.update();
	}

	@Override
	protected synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		gameUpdate();

//		for (Tile t : WorldMap.tiles) {
//			g2d.setColor(t.color);
//			Shape l = new Rectangle2D.Double(camera.relX(t.x), camera.relY(t.y), Tile.size, Tile.size);
//			g2d.draw(l);
//		}
		for(int i = 0; i < WorldMap.getCollisionBoxes().size();i++) {
			CollisionBox cb = WorldMap.getCollisionBoxes().get(i);
			g2d.setColor(Color.red);
			float x1 = camera.relX(cb.getLeft());
			float y1 = camera.relY(cb.getTop());
			
			g2d.setColor(Color.red);
			Entity e = cb.getOwner();
			if(e instanceof Projectile) {
				g2d.setColor(Color.MAGENTA);
			}if(e instanceof Player) {
				g2d.setColor(Color.GREEN);
			}
			Shape hit = new Rectangle2D.Float(x1, y1, cb.wid, cb.len);
			g2d.draw(hit);

		}

		
		for(int i = 0; i < WorldMap.entities.size();i++) {
			Entity e = WorldMap.entities.get(i);
			Iterator<Force> iforce = e.forces.getForces().iterator();
			for (int j = 0; j < e.forces.getForces().size(); j++) {
				Force f = e.forces.getForces().get(j);
				
			
				
//				g2d.setColor(Color.green);
//				g2d.draw(new Line2D.Float(camera.relX(e.x), camera.relY(e.y), camera.relX(f.getDx() * 50 + e.x),
//						camera.relY(f.getDy() * 50 + e.y)));

			}
//			g2d.setColor(Color.black);
//
//			g2d.draw(new Line2D.Float(camera.relX(e.x), camera.relY(e.y), camera.relX(e.forces.getX() * 50 + e.x),
//					camera.relY(e.forces.getY() * 50 + e.y)));

		}

		

		repaint();
	}

}
