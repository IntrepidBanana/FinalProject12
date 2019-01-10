package com.aidenlauris.game.util;

import java.util.ArrayList;

import com.aidenlauris.gameobjects.Wall;
import com.aidenlauris.gameobjects.util.GameObject;

public class MapGeneration {

	

	public static ArrayList<GameObject> genMap(){
		
		Wall[][] walls = new Wall[100][100];
		
		for (int i = 0; i < walls.length; i++) {
			for (int j = 0; j < walls.length; j++) {
				walls[i][j] = new Wall(i*100, j*100, 100, 100);
			}
		}
		
		
		 
		
		
		
		
		
		
		
		
		return null;
		
	}

}
