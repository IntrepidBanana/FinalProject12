package com.aidenlauris.game;

import java.awt.Color;
import java.awt.SecondaryLoop;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;

import com.Tile;
import com.aidenlauris.game.util.MapGen;
import com.aidenlauris.gameobjects.Camera;
import com.aidenlauris.gameobjects.Cursor;
import com.aidenlauris.gameobjects.Enemy;
import com.aidenlauris.gameobjects.Explosion;
import com.aidenlauris.gameobjects.HealthDropEntity;
import com.aidenlauris.gameobjects.Particle;
import com.aidenlauris.gameobjects.GunDrop;
import com.aidenlauris.gameobjects.AmmoDropEntity;
import com.aidenlauris.gameobjects.Player;
import com.aidenlauris.gameobjects.Portal;
import com.aidenlauris.gameobjects.Wall;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.CollisionHelper;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.GameObject;
import com.aidenlauris.render.menu.MenuLayer;
import com.aidenlauris.render.menu.MenuObject;
import com.aidenlauris.render.util.SightPolygon;
import com.aidenlauris.items.HealthPickup;
import com.aidenlauris.items.LaserGun;
import com.aidenlauris.items.Shotgun;

public class WorldMap {
	public static final int FRAMERATE = 60;
	public static long globalTime = 0;
	public static int camx = 1260;
	public static int camy = 960;
	public static Camera camera;

	public static int globalDifficulty = 0;
	static ArrayList<GameObject> gameObjects = new ArrayList<>();
	static ArrayList<GameObject> nonStaticObjects = new ArrayList<>();
	public static ArrayList<GameObject> objectsToDraw = new ArrayList<>();
	static ArrayList<CollisionBox> collisionBoxes = new ArrayList<>();
	static ArrayList<Entity> toRemove = new ArrayList<>();
	static boolean sleep = false;
	static int sleepTime = 0;
	final static int sleepReset = 1;
	static long timeSinceLastCall = System.currentTimeMillis();
	public static Player player;
	private static GameMap map = new GameMap(125);
	static int collisionsChecked = 0;
	public static MenuLayer menuLayer = new MenuLayer();
	public static SightPolygon sightPolygon = new SightPolygon();
	public static ArrayList<Enemy> enemies = new ArrayList<>();
	private static Enemy lastEnemy;
	private static boolean endOfLevel = false;
	private static ArrayList<Particle> particles = new ArrayList<>();
//	public static float globalRotation = 0;

	public synchronized static void update() {

		Time.nextTick();

		Cursor c = getCursor();
		if (c != null) {
			c.update();
		}

		int chunkCount = 0;

		for (GameObject obj : gameObjects) {
			getMap().add(obj);
		}

		camera.update();
		globalTime++;

		int numOfForces = 0;
		for (int i = 0; i < gameObjects.size(); i++) {
			if (i >= gameObjects.size()) {
				break;
			}
			GameObject e = gameObjects.get(i);
			if (e != null) {
				e.update();
				numOfForces += e.getForceSet().getForces().size();

			}
		}

		Iterator<ArrayList<GameObject>> iter = getMap().getAllChunks().iterator();

		while (iter.hasNext()) {
			ArrayList<GameObject> chunk = iter.next();
			if (chunk.isEmpty()) {
				iter.remove();
				continue;
			}
			checkChunkCollisions(chunk);
			chunkCount++;
			
		}

//		for (ArrayList<GameObject> chunk : getMap().getAllChunks()) {
//			checkChunkCollisions(chunk);
//			chunkCount++;
//		}

		if (enemies.size() == 1) {
			lastEnemy = enemies.get(0);
		}

		if (enemies.size() == 0 && !endOfLevel) {
			System.out.println(lastEnemy);
			addGameObject(new Portal(lastEnemy));
			endOfLevel = true;
		}

		objectsToDraw = (ArrayList<GameObject>) getMap().getUniqueObjects().clone();
		getMap().clearUnique();
		getMap().clear();

		if ((Time.global() % (1 * FRAMERATE)) == -1) {
			System.out.println("# of game Objects       : " + gameObjects.size());
			System.out.println("# of box cooliders      : " + collisionBoxes.size());
			System.out.println("# of forces             : " + numOfForces);
			System.out.println("# of Collisions checked : " + collisionsChecked);
			System.out.println("# of chunks             : " + chunkCount);
			System.out.println();
			collisionsChecked = 0;
		}

	}

	public synchronized static void addGameObject(GameObject e) {
		if (!(e instanceof Wall)) {
			nonStaticObjects.add(e);
		}
		if (e instanceof Particle) {
			particles.add((Particle) e);
			if ((gameObjects.size() - nonStaticObjects.size()) > 700) {
				removeGameObject(particles.get(0));
			}
		}

		gameObjects.add(e);
		collisionBoxes.addAll(e.getCollisionBoxes());
	}

	public synchronized static void removeGameObject(GameObject gameObject) {
		if (gameObjects.contains(gameObject)) {
			Iterator<CollisionBox> i = collisionBoxes.iterator();
			while (i.hasNext()) {
				CollisionBox cb = i.next();
				if (cb.getOwner() == gameObject) {
					i.remove();
				}
			}
			// System.out.println("removing: " + e);
			gameObjects.remove(gameObject);
			nonStaticObjects.remove(gameObject);
			enemies.remove(gameObject);
			particles.remove(gameObject);
		}
	}

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
		return null;
	}

	public static Cursor getCursor() {

		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject e = gameObjects.get(i);
			if (e instanceof Cursor) {
				Cursor c = (Cursor) e;
				return c;
			}
		}
		return null;
	}

	public static void setCamera(Camera cam) {
		camera = cam;

	}

	public static Camera getCamera() {
		return camera;
	}

	public static void init() {
		globalDifficulty++;
		lastEnemy = null;
		endOfLevel = false;
		gameObjects.clear();
		nonStaticObjects.clear();
		objectsToDraw.clear();
		gameObjects = MapGen.genMap(Player.getPlayer());
		for (GameObject e : gameObjects) {
			if (e instanceof Enemy) {
				enemies.add((Enemy) e);
			}
		}
		addGameObject(new Cursor());
		Explosion explosion = new Explosion(Player.getPlayer().x, Player.getPlayer().y, 300, 50f, 1000);
		addGameObject(explosion);
	}

	public static void sleep() {
		if (sleep || sleepTime > -sleepReset) {
			return;
		} else {
			sleepTime = 0;
		}
		// sleep = true;

	}

	public static String getGlobalTime() {
		long time = globalTime;
		String s = (time / FRAMERATE) + " " + (time % FRAMERATE);
		return s;
	}

	public static ArrayList<Wall> getAllWalls() {
		ArrayList<Wall> walls = new ArrayList<>();
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject e = ((ArrayList<GameObject>) gameObjects.clone()).get(i);
			if (e instanceof Wall) {
				walls.add((Wall) e);
			}
		}
		return walls;
	}

	public static void checkChunkCollisions(ArrayList<GameObject> chunk) {
		for (int i = 0; i < chunk.size() - 1; i++) {
			GameObject obj1 = chunk.get(i);
			for (int j = i + 1; j < chunk.size(); j++) {
				GameObject obj2 = chunk.get(j);
				CollisionHelper.sendReply(obj1, obj2);
				collisionsChecked++;
			}
		}
	}

	public static GameMap getMap() {
		return map;
	}

	public static void setMap(GameMap map) {
		WorldMap.map = map;
	}

	public static void addMenu(MenuObject m) {
		menuLayer.add(m);
	}
}
