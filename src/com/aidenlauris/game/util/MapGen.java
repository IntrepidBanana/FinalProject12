package com.aidenlauris.game.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.aidenlauris.game.GameLogic;
import com.aidenlauris.gameobjects.BeamShooter;
import com.aidenlauris.gameobjects.Chaser;
import com.aidenlauris.gameobjects.Explosion;
import com.aidenlauris.gameobjects.FourShooter;
import com.aidenlauris.gameobjects.Giant;
import com.aidenlauris.gameobjects.GunDrop;
import com.aidenlauris.gameobjects.Gunman;
import com.aidenlauris.gameobjects.MachineGunner;
import com.aidenlauris.gameobjects.Player;
import com.aidenlauris.gameobjects.PoisonWalker;
import com.aidenlauris.gameobjects.Shotgunner;
import com.aidenlauris.gameobjects.Slug;
import com.aidenlauris.gameobjects.Spinner;
import com.aidenlauris.gameobjects.SuperSlug;
import com.aidenlauris.gameobjects.TutorialText;
import com.aidenlauris.gameobjects.Wall;
import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.items.Pistol;

/**
 * @author Lauris & Aiden
 * 1/20/19
 * Handles room generation.
 */
public class MapGen {

	//wall size for the rooms
	private final static int wallSize = 100;

	/**
	 * Debugging:  prints the map of the a given map in question
	 * @param m integer array of map
	 */
	static void printMap(int[][] m) {

		
		for (int i = m.length; i >= 0; i--) {
			if (i == m.length || i == 0) {

				//adds a border around top and bottom section
				for (int j = 0; j <= m[0].length; j++) {
					System.out.print("W ");
				}
				System.out.println();
				continue;
			}
			
			//for each cell, print it out if its not a empty space: (0)
			for (int j = -1; j <= m[0].length; j++) {
				
				if (j == m[0].length || j == -1) {
					//adds a border around left and right sides
					System.out.print("W ");
				} else if (m[i][j] == 1) {
					System.out.print("  ");
				}else if (m[i][j] != 0) {
					System.out.print(m[i][j] + " ");
				} else {
					System.out.print("@ ");
				}
			}
			System.out.println();
		}

	}

	/**
	 * generates the given level based upon the worldmap global difficulty
	 * @param current player entity to allow the passing of the player between levels
	 * @return a list of gameobjects
	 */
	public static ArrayList<GameObject> genMap(Player p) {
		
		//generate the tutorial room if difficulty = 0;
		if (GameLogic.globalDifficulty == 0) {
			return genTutorialRoom();
		}

		
		//an integer representation of the map
		int[][] geo = new int[30][30];


		Random ran = new Random();
		//seed is used to recreate rooms
//		long seed = 1;

		// PLOTTING ROOMS
		// rooms are a calculation based upon the global difficulty  
		int rooms = 7 + (GameLogic.globalDifficulty / 3);
		int roomSize = 3 + (GameLogic.globalDifficulty / 3);
		int roomVariance = 3;
		
		//room locations in real x,y coordinates
		ArrayList<XY> roomLocations = new ArrayList<>();
		
		//creating the rooms
		for (int i = 0; i <= rooms; i++) {

			//rooms are place at a row and column in the room array.
			//all rooms are placed based on a standard deviation from the center of the board.
			int r = (int) (ran.nextGaussian() * geo.length / 8 + geo.length / 2);
			int c = (int) (ran.nextGaussian() * geo[0].length / 8 + geo[0].length / 2);

			
			//wid and length of the room size
			int wid = roomSize + ran.nextInt(roomVariance);
			int len = roomSize + ran.nextInt(roomVariance);

			
			//places the room into the map
			geo = carveRoom(geo, r, c, wid, len);
			
			
			// adds the location of the center of the room to the array and a seperate arraylist
			XY rc = new XY(Math.min(r + len / 2.0, geo.length - 1),
					Math.min(c + wid / 2.0, geo[1].length - 1));
			geo[rc.x][rc.y] = 4;

			roomLocations.add(rc);

		}
		
		//DEBUG POINT
		//prints the map at this point
		printMap(geo);

		//going through each room add a path to the room and either enemies or the player spawn
		Iterator<XY> iter = roomLocations.iterator();
		ArrayList<GameObject> objects = new ArrayList<>();
		XY start = iter.next();
		XY playerSpawn = start;
		iter.remove();
		while (iter.hasNext()) {
			XY end = iter.next();
			objects.addAll(genEnemy(geo, end, ran));
			geo = pathToRoom(geo, start, end);
			start = end;
			iter.remove();

		}

		//DEBUG POINT
		printMap(geo);
		
		//adds walls to the map based on the open spaces
		geo = genWalls(geo);
		
		//DEBUG POINT
		printMap(geo);
		
		//adds all remaining objects needed to be generated
		objects.addAll(genObjects(geo, playerSpawn, p));

		return objects;

	}

	/**
	 * places the room into the integer map
	 * @param geo the map to place room
	 * @param row the row position
	 * @param col the column position
	 * @param wid the width of the room
	 * @param len the length of the room
	 * @return the redone map, now added with the room
	 */
	private static int[][] carveRoom(int[][] geo, int row, int col, int wid, int len) {
		
		//edge case fix
		row = Math.max(0, row);
		col = Math.max(0, col);
		
		//adds a 1 to designate a walkable space
		for (int r = row; r < geo.length && r < row + wid; r++) {
			for (int c = col; c < geo[0].length && c < col + len; c++) {
				geo[r][c] = 1;
			}
		}
		return geo;

	}

	/**
	 * adds a path from 1 room to another
	 * @param geo integer map of level
	 * @param start first x,y coordinate
	 * @param end second x,y coordinate
	 * @return map with a path from start to end coordinates
	 */
	private static int[][] pathToRoom(int[][] geo, XY start, XY end) {

		//heihgt and width of path
		int height = end.x - start.x;
		int wid = end.y - start.y;

		//row and col position of the start position
		int r = start.x;
		int c = start.y;

		//Funky math to add the path for the height of the path
		//either goes north or south then stops when the centers are equal.
		for (int i = 0; (Math.abs(height - i)) >= 0; i += Integer.signum(height)) {
			int index = r + i;
			geo[index][c] = 1;
			if (Math.abs(height - i) == 0)
				break;
		}
		//same as above
		for (int i = 0; (Math.abs(wid - i)) >= 0; i += Integer.signum(wid)) {
			int index = c + i;
			geo[r + height][index] = 1;
			if (Math.abs(wid - i) == 0)
				break;
		}

		return geo;

	}

	/**
	 * generates the walls of the room surrounding the walkable space
	 * @param geo integer map of level
	 * @return new map
	 */
	private static int[][] genWalls(int[][] geo) {

		
		//for each cell, perform an algorithm if it an open space
		for (int r = 0; r < geo.length; r++) {

			for (int c = 0; c < geo[0].length; c++) {

				if (geo[r][c] == 1) {
					geo = addWall(geo, r, c);
				}
			}

		}

		return geo;

	}

	/**
	 * adds a set of walls based on an open(1) coordinate
	 * @param geo integer map of level
	 * @param r row of coord
	 * @param c col of coord
	 * @return new map with walls(5)
	 */
	private static int[][] addWall(int[][] geo, int r, int c) {

		
		
		//checks the spaces around the point to figure out where to place.
		//o = open space
		//w = places to check walls
		//WWW
		//WOW
		//WWW
		
		//setting bounds
		int rLow = Math.max(0, r - 1);
		int rHigh = Math.min(geo.length - 1, r + 1);

		int cLow = Math.max(0, c - 1);
		int cHigh = Math.min(geo[0].length - 1, c + 1);

		for (int i = rLow; i <= rHigh; i++) {
			for (int j = cLow; j <= cHigh; j++) {
				
				//if a place possible to place a wall, put a wall(5) there
				if (geo[i][j] == 0) {
					geo[i][j] = 5;
				}
			}
		}

		return geo;
	}

	/**
	 * generates all walls and player game objects to place
	 * @param geo integer map of level
	 * @param playerXY player coordinate
	 * @param p player class
	 * @return list of game objects
	 */
	private static ArrayList<GameObject> genObjects(int[][] geo, XY playerXY, Player p) {

		ArrayList<GameObject> objects = new ArrayList<>();

		
		//adds all walls based on walls in integer map
		for (int r = 0; r < geo.length; r++) {
			for (int c = 0; c < geo[0].length; c++) {
				if (geo[r][c] == 5) {
					Wall w = new Wall(r * wallSize, c * wallSize, wallSize, wallSize);
					objects.add(w);
				}
			}
		}
		
		//overall bounding rectangle of walls surrounding edge of map size
		for (int r = -1; r <= geo.length; r++) {
			Wall w1 = new Wall(r * wallSize, -1 * wallSize, wallSize, wallSize);
			Wall w2 = new Wall(r * wallSize, geo.length * wallSize, wallSize, wallSize);
			objects.add(w1);
			objects.add(w2);
		}
		for (int c = -1; c <= geo[0].length; c++) {
			Wall w1 = new Wall(-1 * wallSize, c * wallSize, wallSize, wallSize);
			Wall w2 = new Wall(geo.length * wallSize, c * wallSize, wallSize, wallSize);
			objects.add(w1);
			objects.add(w2);
		}
		
		
		//adds a player and explosion at the position of the player
		p.x = playerXY.x * wallSize;
		p.y = playerXY.y * wallSize;
		objects.add(p);

		Explosion explosion = new Explosion(p.x, p.y, 2 * wallSize, 50f, 1000);

		objects.add(explosion);

		return objects;

	}

	/**
	 * generates all enemies at the room centers
	 * @param geo integer map of level
	 * @param rc xy coordinate of room center
	 * @param ran random class (used for same seed debugging)
	 * @return list of enemies
	 */
	private static ArrayList<GameObject> genEnemy(int[][] geo, XY rc, Random ran) {
		
		
		ArrayList<GameObject> enemies = new ArrayList<>();
		
		//number of enemies is a calculation with a minimum of 1
		int numOfEnemies = ran.nextInt(2) + GameLogic.globalDifficulty - 2;
		numOfEnemies = Math.max(numOfEnemies, 1);
		
		//generates all enemies
		for (int i = 0; i < numOfEnemies; i++) {
			
			//choice of which enemy to spawn
			int choice = ran.nextInt(11);
			
			//DEBUG POINT
			// choose for a specific enemy
			// choice = 3;

			
			//all choices for enemies. also look how hard coded this is haha
			//adds to enemy list based on choice
			if (choice == 0) {
				Gunman g = new Gunman(rc.x * wallSize + ran.nextInt(2 * wallSize) - wallSize,
						rc.y * wallSize + ran.nextInt(2 * wallSize) - wallSize);
				enemies.add(g);
			} else if (choice == 1) {
				FourShooter f = new FourShooter(rc.x * wallSize + ran.nextInt(2 * wallSize) - wallSize,
						rc.y * wallSize + ran.nextInt(2 * wallSize) - wallSize);
				enemies.add(f);
			} else if (choice == 2) {
				Chaser c = new Chaser(rc.x * wallSize + ran.nextInt(2 * wallSize) - wallSize,
						rc.y * wallSize + ran.nextInt(2 * wallSize) - wallSize);
				enemies.add(c);
			} else if (choice == 3) {
				Giant g2 = new Giant(rc.x * wallSize + ran.nextInt(300) - wallSize,
						rc.y * wallSize + ran.nextInt(2 * wallSize) - wallSize);
				enemies.add(g2);
			} else if (choice == 4) {
				MachineGunner m = new MachineGunner(rc.x * wallSize + ran.nextInt(2 * wallSize) - wallSize,
						rc.y * wallSize + ran.nextInt(2 * wallSize) - wallSize);
				enemies.add(m);
			} else if (choice == 5) {
				Shotgunner s = new Shotgunner(rc.x * wallSize + ran.nextInt(2 * wallSize) - wallSize,
						rc.y * wallSize + ran.nextInt(2 * wallSize) - wallSize);
				enemies.add(s);
			} else if (choice == 6) {
				Spinner s2 = new Spinner(rc.x * wallSize + ran.nextInt(2 * wallSize) - wallSize,
						rc.y * wallSize + ran.nextInt(2 * wallSize) - wallSize);
				enemies.add(s2);
			} else if (choice == 7) {
				for (int j = 0; j < Math.random() * 3 + 3; j++) {
					Slug s3 = new Slug(rc.x * wallSize + ran.nextInt(2 * wallSize) - wallSize,
							rc.y * wallSize + ran.nextInt(2 * wallSize) - wallSize);
					enemies.add(s3);
				}
			} else if (choice == 8) {
				PoisonWalker p = new PoisonWalker(rc.x * wallSize + ran.nextInt(2 * wallSize) - wallSize,
						rc.y * wallSize + ran.nextInt(2 * wallSize) - wallSize);
				enemies.add(p);
			} else if (choice == 9) {
				BeamShooter b = new BeamShooter(rc.x * wallSize + ran.nextInt(2 * wallSize) - wallSize,
						rc.y * wallSize + ran.nextInt(2 * wallSize) - wallSize);
				enemies.add(b);
			} else if (choice == 10) {
				SuperSlug s4 = new SuperSlug(rc.x * wallSize + ran.nextInt(2 * wallSize) - wallSize,
						rc.y * wallSize + ran.nextInt(2 * wallSize) - wallSize);
				enemies.add(s4);
			}

		}
		return enemies;
	}

	/**
	 * generates the tutorial room
	 * @return list of game objects
	 */
	private static ArrayList<GameObject> genTutorialRoom() {
		
		
		//hardcoded walls, player, enemy, gun and text
		Wall up = new Wall(0, 0, 300, 800);
		Wall down = new Wall(0, 800, 300, 800);

		Wall left = new Wall(0, 0, 800, 300);
		Wall right = new Wall(800, 0, 1200, 300);

		Player player = new Player(385, 560, 0.2f);

		Spinner spinner = new Spinner(725, 550);

		GunDrop pistol = new GunDrop(550, 710);
		pistol.gun = new Pistol();
		
		TutorialText text1 = new TutorialText(450, 400, "Use W,A,S,D to Move");
		TutorialText text2 = new TutorialText(450, 420, "Use Space to switch weapons");
		TutorialText text3 = new TutorialText(450, 440, "Left Click to shoot!");
		TutorialText text4 = new TutorialText(450, 750, "E to pick up weapons");

		ArrayList<GameObject> objects = new ArrayList<>();
		objects.add(up);
		objects.add(left);
		objects.add(right);
		objects.add(down);
		objects.add(player);
		objects.add(spinner);
		objects.add(text1);
		objects.add(text2);
		objects.add(text3);
		objects.add(text4);
		objects.add(pistol);

		return objects;

	}
}
