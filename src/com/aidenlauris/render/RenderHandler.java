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

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.game.util.IOHandler;
import com.aidenlauris.game.util.Keys;
import com.aidenlauris.game.util.Time;
import com.aidenlauris.game.util.XY;
import com.aidenlauris.gameobjects.Camera;
import com.aidenlauris.gameobjects.Cursor;
import com.aidenlauris.gameobjects.AmmoDropEntity;
import com.aidenlauris.gameobjects.Player;
import com.aidenlauris.gameobjects.Wall;
import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.render.util.SpriteManager;

/**
 * @author Aiden & Lauris
 * Jan 22, 2019
 * 
 * Handles all rendering done by game
 */
public class RenderHandler extends JPanel {

	

	public long fpsTimer;
	
	
	public RenderHandler(IOHandler io) {

		//initialize values
		Camera camera = new Camera();
		GameLogic.setCamera(camera);
		io.setCamera(camera);
		addKeyListener(io);
		addMouseListener(io);
		addMouseMotionListener(io);
		PaintHelper.initFont();
		Keys k = new Keys();
		SpriteManager.initSpriteSheets();

		repaint();
		
		
		//background color

		setBackground(new Color(0, 45, 48));

	}

	@Override
	protected synchronized void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// render the game
		g2d = playState(g2d);
	}
	
	
	/**
	 * Renders all components and things to draw
	 * @param g2d graphics unit
	 * @return updated graphics unit
	 */
	public Graphics2D playState(Graphics2D g2d) {

		//all objects to draw
		ArrayList<GameObject> objects = GameLogic.objectsToDraw;

		//reset viewcones
		GameLogic.sightPolygon.clear();

		
		//since this method is called really quickly, concurrent modification may occur
		//this attempts to prevent that
		int sizeMishap = 0;
		for (int i = 0; i < objects.size() - sizeMishap; i++) {
			
			//validates everything
			if (i >= objects.size()) {
				break;
			}
			GameObject e = objects.get(i);
			if (e == null) {
				sizeMishap++;
				i--;
				continue;

			}
			
			//if distance to object is too far, dont draw
			if (Math.hypot(PaintHelper.x(e.x - GameLogic.camx / 2), PaintHelper.y(e.y - GameLogic.camy / 2)) > 1080) {
				continue;
			}
			
			//draw the entity
			g2d = e.draw(g2d);
			
			
			//if its a wall add the value to the sight polygon manager
			if (e instanceof Wall) {
				GameLogic.sightPolygon.addPath((Wall) e);
			}
		}
		
		//draw sight polygon
		g2d = GameLogic.sightPolygon.draw(g2d);
		
		//draw menu
		g2d = GameLogic.menuLayer.draw(g2d);
		
		//draw the cursor
		g2d = GameLogic.getCursor().draw(g2d);

		//calculate the valid delta time variable
		Time.setDelta(Math.min(Math.max(0.75f, fpsTimer / 60.0 + 0.08f), 1f));
		
		
		//adds the level and fps 
		g2d.setColor(Color.white);
		g2d.drawString("FPS: " + fpsTimer + " Delta: " + (Math.round(Time.delta()*100)/100.0), 16, 16);
		g2d.drawString("Level: " + GameLogic.globalDifficulty, GameLogic.camx/2 -48, 16);
		return g2d;
	}

}
