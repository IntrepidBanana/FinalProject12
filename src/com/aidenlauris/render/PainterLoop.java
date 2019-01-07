package com.aidenlauris.render;
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
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.FontUIResource;

import com.aidenlauris.game.IOHandler;
import com.aidenlauris.game.WorldMap;
import com.aidenlauris.game.util.XY;
import com.aidenlauris.gameobjects.Camera;
import com.aidenlauris.gameobjects.Cursor;
import com.aidenlauris.gameobjects.ItemDropEntity;
import com.aidenlauris.gameobjects.Player;
import com.aidenlauris.gameobjects.Wall;
import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.render.util.DrawCompare;

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
		lightMap = new BufferedImage(WorldMap.camx, WorldMap.camy, BufferedImage.TYPE_INT_ARGB);
		repaint();

		// gameLoop();
	}

	@Override
	protected synchronized void paintComponent(Graphics g) {

		super.paintComponent(g);
		long start = System.currentTimeMillis();
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawString("FPS: " + fpsTimer, 16, 16);

		ArrayList<GameObject> objects = WorldMap.objectsToDraw;
		try {
			objects.sort(new DrawCompare());
		} catch (IllegalArgumentException e) {
			System.out.println("Comparison Error");
		}
		int sizeMishap = 0;

		try {
		for(XY xy : WorldMap.getMap().getChunkCoords()) {
			
			float  size = WorldMap.getMap().chunkSize();
			float dx =PaintHelper.x(xy.x*size);
			float dy =PaintHelper.y(xy.y*size);
			
			g2d.setColor(Color.green);
			g2d.draw(new Rectangle2D.Float(dx, dy, size, size));
			
		}}
		catch(ConcurrentModificationException e) {
			System.out.println("YOURE TOO SPEEDY FOR THE FUCKING MACHINE TO RUN HOLY SHIT SLOW DOWN MAN");
		}
		
		Composite oldComp = g2d.getComposite();
//		Graphics2D gl = lightMap.createGraphics();
//		gl.setColor(new Color(0, 0, 0, 50));
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
			if (Math.hypot(PaintHelper.x(e.x - WorldMap.camx / 2), PaintHelper.y(e.y - WorldMap.camy / 2)) > 1260) {
				continue;
			}
			g2d = e.draw(g2d);
//			if (e instanceof LightSource) {
//				LightSource light = (LightSource) e;
//				 gl = light.renderLight(gl);
//			}
		}
//		g2d.drawImage(lightMap, null, 0, 0);
		Cursor c = WorldMap.getCursor();
		g2d = c.draw(g2d);

		long end = System.currentTimeMillis();
		int updateTime = (int) (end - start);

	}

}