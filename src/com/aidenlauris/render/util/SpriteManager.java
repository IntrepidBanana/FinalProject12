package com.aidenlauris.render.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class SpriteManager {

	public enum State{
		Idle,
		Move1,
		Move2;
	}

	private static BufferedImage playersheet;
	private static BufferedImage slugsheet;
	private static BufferedImage tilesheet;
	public static Map<State, BufferedImage> playerSprites = new HashMap<>();
	public static Map<State, BufferedImage> slugSprites = new HashMap<>();
	
	
	public static ArrayList<BufferedImage> tileSprites = new ArrayList<>()	;
	public static void initSpriteSheets() {
		try {
			playersheet = ImageIO.read(new File(".\\Assets\\Sprites\\player.png"));
			slugsheet = ImageIO.read(new File(".\\Assets\\Sprites\\slug.png"));
			tilesheet = ImageIO.read(new File(".\\Assets\\Sprites\\tiles.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		parseSprites();
	}
	
	
	public static void parseSprites() {
		int iter = 0;
		for(State s : State.values()) {
			playerSprites.put(s, playersheet.getSubimage(iter*24, 0, 24, 24));
			slugSprites.put(s, slugsheet.getSubimage(iter*24, 0, 24, 24));
			iter++;
		}
		
		for(int i = 0; i < 8; i++) {
			tileSprites.add(tilesheet.getSubimage(i*32, 0, 32, 32));
		}
	
		
	}
	
	public static BufferedImage getTile() {
		int index = (int) (Math.random()*8);
		return tileSprites.get(index);
	}
}
