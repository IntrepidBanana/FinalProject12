package com.aidenlauris.render.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * @author Aiden & Lauris
 * Jan 22, 2019
 * 
 * Manages starting and getting sprites.
 */
public class SpriteManager {

	
	
	/**
	 * @author Aiden & Lauris
	 * Jan 22, 2019
	 * 
	 * the state an sprite can be
	 */
	public enum State{
		Idle,
		Move1,
		Move2;
	}

	
	//total sprite sheet for player 
	private static BufferedImage playersheet;
	
	//map with sprites ordered by state
	public static Map<State, BufferedImage> playerSprites = new HashMap<>();
	
	
	/**
	 * Initialize sprite sheets by reading from file
	 */
	public static void initSpriteSheets() {
		try {
			playersheet = ImageIO.read(new File(".\\Assets\\Sprites\\player.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		parseSprites();
	}
	
	
	/**
	 * Interpreting sprite values and placing them into the appropriate sprite field
	 */
	private static void parseSprites() {
		int iter = 0;
		for(State s : State.values()) {
			playerSprites.put(s, playersheet.getSubimage(iter*24, 0, 24, 24));
			iter++;
		}
		
	
		
	}
	
}
