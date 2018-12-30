import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
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
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.FontUIResource;

public class PainterLoop extends JPanel {

	int camSize = 720;
	Camera camera;
	IOHandler io;
	boolean keys[] = new boolean[4];
	int timePressed[] = new int[4];
	double camSpeed = 1;
	Player player = new Player(500, 500, 2f);
	long startTime;
	private BufferedImage lightMap;

	public PainterLoop(IOHandler io) {
		WorldMap.init();
		WorldMap.addGameObject(player);
		// wm.addEntity(new Slug(wm, 90, 90, 1, 1, 1));
		// wm.addEntity(new Enemy(wm, 100, 100, 50, 10, 0.5f));
		WorldMap.addGameObject(new Wall(0, 0, 100, 1000));
		// WorldMap.addGameObject(new Wall(0, 0, 1000, 100));
		// WorldMap.addGameObject(new Wall(800, 0, 1000, 100));
		// WorldMap.addGameObject(new Wall(0, 800, 100, 1000));
		// wm.addEntity(player);
		// wm.addEntity(new Slug(wm, 90, 90, 1, 1, 0.2f));
		// wm.addEntity(new Enemy(wm, 100, 100, 50, 10, 0.2f));

		WorldMap.addGameObject(new ItemDropEntity(150, 150));
		this.io = io;
		this.camera = new Camera(camSize, player, io);
		WorldMap.setCamera(camera);
		this.io.setCamera(camera);
		addKeyListener(io);
		addMouseListener(io);
		addMouseMotionListener(io);
		startTime = System.currentTimeMillis();
		PaintHelper.initFont();
		lightMap = new BufferedImage(WorldMap.camx, WorldMap.camy, BufferedImage.TYPE_INT_ARGB);
		repaint();

		// gameLoop();
	}

	@Override
	protected synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		long start = System.currentTimeMillis();
		Graphics2D g2d = (Graphics2D) g;
		ArrayList<GameObject> objects = (ArrayList<GameObject>) WorldMap.gameObjects.clone();
		objects.sort(new DrawCompare());
		int sizeMishap = 0;

		Composite oldComp = g2d.getComposite();
//		Graphics2D gl = lightMap.createGraphics();
//		gl.setColor(new Color(0, 0, 0, 255));
//		gl.fillRect(0, 0, WorldMap.camx, WorldMap.camy);
//		gl.setComposite(AlphaComposite.DstOut);
		for (int i = 0; i < objects.size() - sizeMishap; i++) {
			if (i >= objects.size()) {
				return;
			}
			GameObject e = objects.get(i);
			if (e == null) {
				sizeMishap++;
				i--;
				continue;

			}
			if (Math.hypot(PaintHelper.x(e.x-WorldMap.camx/2), PaintHelper.y(e.y-WorldMap.camy/2)) > 1260) {
				continue;
			}
			g2d = e.draw(g2d);
//			if (e instanceof LightSource) {
//				LightSource light = (LightSource) e;
////				gl = light.renderLight(gl);
//			}
		}
		// g2d.drawImage(lightMap, null, 0, 0);
		Cursor c = WorldMap.getCursor();
		g2d = c.draw(g2d);

		long end = System.currentTimeMillis();
		int updateTime = (int) (end - start);

	}

}
