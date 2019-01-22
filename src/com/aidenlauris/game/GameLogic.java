package com.aidenlauris.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.aidenlauris.game.util.MapGen;
import com.aidenlauris.game.util.Time;
import com.aidenlauris.gameobjects.Camera;
import com.aidenlauris.gameobjects.Cursor;
import com.aidenlauris.gameobjects.Enemy;
import com.aidenlauris.gameobjects.Explosion;
import com.aidenlauris.gameobjects.Particle;
import com.aidenlauris.gameobjects.Player;
import com.aidenlauris.gameobjects.Portal;
import com.aidenlauris.gameobjects.Wall;
import com.aidenlauris.gameobjects.util.CollisionHelper;
import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.render.menu.MenuLayer;
import com.aidenlauris.render.menu.MenuObject;
import com.aidenlauris.render.util.SightPolygon;

/**
 * @author Lauris & Aiden 
 * Jan 21, 2019
 * 
 *         all logic, settings and global variables here handles game updates,
 *         adding, removing objects, etc.
 * 
 */
public class GameLogic {

	public static final int FRAMERATE = 60;

	// size of camera
	public static int camx = 1260;
	public static int camy = 960;
	public static Camera camera;

	// easy access to player
	public static Player player;

	// difficulty level of game (0 is tutorial room)
	public static int globalDifficulty = 0;

	// list of all game objects and their sub classifications
	public static ArrayList<GameObject> gameObjects = new ArrayList<>();
	public static ArrayList<GameObject> nonStaticObjects = new ArrayList<>();
	public static ArrayList<GameObject> objectsToDraw = new ArrayList<>();
	private static ArrayList<Particle> particles = new ArrayList<>();
	public static ArrayList<Enemy> enemies = new ArrayList<>();

	// spatial tree map
	private static SpatialTreeMap map = new SpatialTreeMap(125);

	// top menulayer and view cone classes
	public static MenuLayer menuLayer = new MenuLayer();
	public static SightPolygon sightPolygon = new SightPolygon();

	// End of level variables
	private static Enemy lastEnemy;
	private static boolean endOfLevel = false;

	// death logic variables
	private static boolean restart;
	private static long restartTimer;

	/**
	 * Handles each game tick, includes collision detection, game object updates,
	 * etc.
	 */
	public synchronized static void update() {

		// progresses time
		Time.nextTick();

		// DEATH LOGIC
		// restarts after a certain time
		if (restart == true) {
			if (Time.alertPassed(restartTimer)) {
				restart = false;
				globalDifficulty = 0;
				init();
			}
		}
		// for each game object, add it to the spatial map
		for (GameObject obj : gameObjects) {
			getMap().add(obj);
		}

		// updates camera activity
		camera.update();

		// for each game object, update the logic for it
		for (int i = 0; i < gameObjects.size(); i++) {
			if (i >= gameObjects.size()) {
				break;
			}
			GameObject e = gameObjects.get(i);
			if (e != null) {
				e.update();
			}
		}
		
		// check chunk collisions and remove any unused chunks
		Iterator<ArrayList<GameObject>> iter = getMap().getAllChunks().iterator();
		while (iter.hasNext()) {
			ArrayList<GameObject> chunk = iter.next();
			if (chunk.isEmpty()) {
				iter.remove();
				continue;
			}
			checkChunkCollisions(chunk);
			
		}

		// DEBUG POINT
		// Uncomment if on a slow machine
		// if (particles.size() > 800) {
		// for (int i = 0; i < particles.size() - 800; i++) {
		// removeGameObject(particles.get(0));
		// }
		// }

		// for (ArrayList<GameObject> chunk : getMap().getAllChunks()) {
		// checkChunkCollisions(chunk);
		// chunkCount++;
		// }

		
		
		//gets the last enemy and spawns a portal when its killed.
		//goto next level like this
		if (enemies.size() == 1) {
			lastEnemy = enemies.get(0);
		}
		if (enemies.size() == 0 && !endOfLevel) {
			addGameObject(new Portal(lastEnemy));
			endOfLevel = true;
		}

		//clone all objects to make sure no errors occur when drawing
		objectsToDraw = (ArrayList<GameObject>) getMap().getUniqueObjects().clone();
		getMap().clearUnique();
		getMap().clear();

	}

	/**
	 * adds the game object to the game, classifies it too
	 * @param e game object
	 */
	public synchronized static void addGameObject(GameObject e) {

		//classification
		if (!(e instanceof Wall)) {
			nonStaticObjects.add(e);
		}
		if (e instanceof Particle) {
			particles.add((Particle) e);

		}
		if (e instanceof Enemy) {
			enemies.add((Enemy) e);
		}
		if (e instanceof Player) {
			player = (Player) e;
		}

		gameObjects.add(e);
	}

	/**
	 * removes the object and all classifications
	 * @param gameObject game object to remove
	 */
	public synchronized static void removeGameObject(GameObject gameObject) {
		if (gameObjects.contains(gameObject)) {
			gameObjects.remove(gameObject);
			nonStaticObjects.remove(gameObject);
			enemies.remove(gameObject);
			particles.remove(gameObject);
		}
	}

	/**
	 * gets the player object
	 * @return the player object
	 */
	public static Player getPlayer() {
		if (player != null) {
			return player;
		}
		for (GameObject e : gameObjects) {
			if (e instanceof Player) {
				player = (Player) e;
				return (Player) e;
			}
		}
		return Player.getPlayer();
	}

	/**
	 * returns the cursor
	 * @return cursor
	 */
	public static Cursor getCursor() {

		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject e = gameObjects.get(i);
			if (e instanceof Cursor) {
				Cursor c = (Cursor) e;
				return c;
			}
		}
		return new Cursor();
	}

	/**
	 * sets the camera 
	 * @param cam camera
	 */
	public static void setCamera(Camera cam) {
		camera = cam;

	}

	/**
	 * gets the camera
	 * @return camera
	 */
	public static Camera getCamera() {
		return camera;
	}

	/**
	 * initializes the current level
	 */
	public static void init() {
		//resets all values then generates map
		lastEnemy = null;
		endOfLevel = false;
		gameObjects.clear();
		nonStaticObjects.clear();
		objectsToDraw.clear();
		enemies.clear();
		particles.clear();
		
		//generate map and add all objects
		ArrayList<GameObject> generatedMap = MapGen.genMap(player);
		for (GameObject e : generatedMap) {
			addGameObject(e);
		}
		addGameObject(new Cursor());
		Explosion explosion = new Explosion(Player.getPlayer().x, Player.getPlayer().y, 300, 50f, 1000);
		addGameObject(explosion);
		
		
		globalDifficulty++;
	}


	
	/**
	 * checks all collisions within a list of objects
	 * @param chunk list of all objects
	 */
	public static void checkChunkCollisions(ArrayList<GameObject> chunk) {
		
		//do a brute force collision check. 
		//this is okay because the lists given are usually tiny due to the spatial tree
		for (int i = 0; i < chunk.size() - 1; i++) {
			GameObject obj1 = chunk.get(i);
			for (int j = i + 1; j < chunk.size(); j++) {
				GameObject obj2 = chunk.get(j);
				CollisionHelper.sendReply(obj1, obj2);
			}
		}
	}

	/**
	 * gets the spatial tree responsible for objects
	 * @return spatial tree
	 */
	/**
	 * @return
	 */
	public static SpatialTreeMap getMap() {
		return map;
	}

	/**
	 * sets the spatial tree map
	 * @param map spatial tree
	 */
	public static void setMap(SpatialTreeMap map) {
		GameLogic.map = map;
	}

	/**
	 * adds a menu to the menu layer
	 * @param m menuobject to add
	 */
	public static void addMenu(MenuObject m) {
		menuLayer.add(m);
	}

	/**
	 * start the restart game timer and difficulty
	 * used on player death
	 */
	public static void restart() {
		restart = true;
		restartTimer = Time.alert(120);
	}

}
