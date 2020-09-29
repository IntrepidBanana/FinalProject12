package com.aidenlauris.render.util;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.Iterator;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.gameobjects.Player;
import com.aidenlauris.gameobjects.Ray;
import com.aidenlauris.gameobjects.Wall;
import com.aidenlauris.render.PaintHelper;

/**
 * @author Aiden & Lauris
 * Jan 22, 2019
 * 
 * collection of all view cones created by walls
 */
public class SightPolygon {
	
	//list of all shapes made by wall
	private ArrayList<Path2D> paths = new ArrayList<>();
	
	
	/**
	 * Adds all shapes created by a wall for the view cone
	 * @param wall wall to calculate shapes with
	 */
	public void addPath(Wall wall){
		paths.addAll(wall.getShadow());
	}
	
	/**
	 * Drawing all view cones
	 * @param g2d graphics unit
	 * @return update graphics unit
	 */
	public Graphics2D draw(Graphics2D g2d) {
		g2d.setColor(Color.black);
		for(Path2D p : paths) {
			
			g2d.fill(p);
			
		}
		return g2d;
	}

	/**
	 * remove all paths
	 */
	public void clear() {
		paths.clear();
	}
}
