package com.aidenlauris.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.gameobjects.util.HitBox;
import com.aidenlauris.gameobjects.util.HurtBox;

/**
 * @author Aiden & Lauris
 * Jan 22, 2019
 * 
 * Assortment of helper functions to assist in quickly drawing and calculating things
 */
public class PaintHelper {
	
	//quick access to font used
	public static Font font;

	
	
	/**
	 * calculates the position of an x coordinate in relation to the drawing area
	 * @param x x coordinate
	 * @return transformed coordinate
	 */
	public static float x(double x) {
		return GameLogic.getCamera().relX(x);
	}

	/**
	 * calculates the position of an y coordinate in relation to the drawing area
	 * @param y y coordinate
	 * @return transformed coordinate
	 */
	public static float y(double d) {
		return GameLogic.getCamera().relY(d);
	}
	
	/**
	 * Initialize the font used in this static class
	 */
	public static void initFont() {
		
		//find the file in the file system
		try {
			
			File[] f = new File("./Press_Start_2P").listFiles();
			
			//set the font to whats in the file
			font = Font.createFont(Font.TRUETYPE_FONT, new File(".\\Press_Start_2P\\VT323-Regular.ttf"))
					.deriveFont(24f);
			
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

			//register font
			ge.registerFont(font);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
	}

}
