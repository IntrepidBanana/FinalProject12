package com.aidenlauris.render;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
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
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.FontUIResource;

import com.Tile;
import com.aidenlauris.game.IOHandler;
import com.aidenlauris.game.Time;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.game.util.GameState;
import com.aidenlauris.game.util.XY;
import com.aidenlauris.gameobjects.Camera;
import com.aidenlauris.gameobjects.Cursor;
import com.aidenlauris.gameobjects.AmmoDropEntity;
import com.aidenlauris.gameobjects.Player;
import com.aidenlauris.gameobjects.Wall;
import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.render.util.DrawCompare;
import com.aidenlauris.render.util.SpriteManager;

public class PainterLoop extends JPanel {

	int camSize = 720;
	Camera camera;
	IOHandler io;
	boolean keys[] = new boolean[4];
	int timePressed[] = new int[4];
	double camSpeed = 1;
	long startTime;
	private BufferedImage lightMap;
	public long fpsTimer;
	public GameState gameState = new GameState();
	public Tile[][] tiles = new Tile[118][118];
	
	
	public PainterLoop(IOHandler io) {
		WorldMap.init();

		this.io = io;
		this.camera = new Camera();
		WorldMap.setCamera(camera);
		this.io.setCamera(camera);
		addKeyListener(io);
		addMouseListener(io);
		addMouseMotionListener(io);
		addMouseWheelListener(io);
		startTime = System.currentTimeMillis();
		PaintHelper.initFont();

		SpriteManager.initSpriteSheets();

		lightMap = new BufferedImage(WorldMap.camx, WorldMap.camy, BufferedImage.TYPE_INT_ARGB);
		repaint();

		setBackground(new Color(0, 45, 48));

		// gameLoop();
	}

	@Override
	protected synchronized void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d = playState(g2d);
	}

	public Graphics2D playState(Graphics2D g2d) {

		ArrayList<GameObject> objects = WorldMap.objectsToDraw;

		WorldMap.sightPolygon.clear();

		
		int sizeMishap = 0;
		for (int i = 0; i < objects.size() - sizeMishap; i++) {
			if (i >= objects.size()) {
				break;
			}
			GameObject e = objects.get(i);
			if (e == null) {
				sizeMishap++;
				i--;
				continue;

			}
			if (Math.hypot(PaintHelper.x(e.x - WorldMap.camx / 2), PaintHelper.y(e.y - WorldMap.camy / 2)) > 1080) {
				continue;
			}
			g2d = e.draw(g2d);
			if (e instanceof Wall) {
				WorldMap.sightPolygon.addPath((Wall) e);
			}
		}
		g2d = WorldMap.sightPolygon.draw(g2d);
		g2d = WorldMap.menuLayer.draw(g2d);
		Cursor c = WorldMap.getCursor();
		g2d = c.draw(g2d);

		Time.setDelta(Math.min(Math.max(0.75f, fpsTimer / 60.0 + 0.08f), 1f));
g2d.setColor(Color.white);
		g2d.drawString("FPS: " + fpsTimer + " Delta: " + (Math.round(Time.delta()*100)/100.0), 16, 16);
		g2d.drawString("Level: " + WorldMap.globalDifficulty, WorldMap.camx/2 -48, 16);
		return g2d;
	}

}
