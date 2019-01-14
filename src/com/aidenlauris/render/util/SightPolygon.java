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

import com.aidenlauris.game.WorldMap;
import com.aidenlauris.gameobjects.Player;
import com.aidenlauris.gameobjects.Ray;
import com.aidenlauris.gameobjects.Wall;
import com.aidenlauris.render.PaintHelper;

public class SightPolygon {
	ArrayList<Path2D> paths = new ArrayList<>();
	
	public void findAllPaths(){
		ArrayList<Wall> walls = WorldMap.getAllWalls();

		paths.clear();
		for(Wall w : walls) {
			
			paths.addAll(w.getShadow());		
		}
		
	}
	
	public void addPath(Wall wall){
		paths.addAll(wall.getShadow());
	}
	
	public Graphics2D draw(Graphics2D g2d) {
		g2d.setColor(Color.black);
		for(Path2D p : paths) {
			
			g2d.fill(p);
			
		}
		return g2d;
	}

	public void clear() {
		paths.clear();
	}
}
