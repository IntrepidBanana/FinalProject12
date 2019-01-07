package com.aidenlauris.game;
import java.awt.Color;
import java.awt.SecondaryLoop;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;

import com.Tile;
import com.aidenlauris.gameobjects.Camera;
import com.aidenlauris.gameobjects.Cursor;
import com.aidenlauris.gameobjects.InteractableBox;
import com.aidenlauris.gameobjects.Player;
import com.aidenlauris.gameobjects.Wall;
import com.aidenlauris.gameobjects.util.CollisionBox;
import com.aidenlauris.gameobjects.util.CollisionHelper;
import com.aidenlauris.gameobjects.util.Entity;
import com.aidenlauris.gameobjects.util.GameObject;

public class WorldMap {
	public static final int FRAMERATE = 60;
	public static long globalTime = 0;
	public static int camx = 1280;
	public static int camy = 920;
	public static Camera camera;

	static ArrayList<GameObject> gameObjects = new ArrayList<>();
	public static ArrayList<GameObject> objectsToDraw = new ArrayList<>();
	static ArrayList<CollisionBox> collisionBoxes = new ArrayList<>();
	static ArrayList<Entity> toRemove = new ArrayList<>();
	static boolean sleep = false;
	static int sleepTime = 0;
	final static int sleepReset = 1;
	static long timeSinceLastCall = System.currentTimeMillis();
	public static Player player;
	private static GameMap map = new GameMap(500);
	static int collisionsChecked = 0;


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
		for (ArrayList<GameObject> chunk : getMap().getAllChunks()) {
			checkChunkCollisions(chunk);
			chunkCount++;
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
		System.out.println("Dear Aiden,");
		System.out.println("i added guns, which can be switched using the spacebar");
		System.out.println("WorldMap can now be accessed in any class with out iniitalizing it");
		System.out.println("ex\nWorldMap.addEntity(...)");
		System.out.println("Each entity has a kill() to remove it");
		System.out.println("kill is customizable, however call removeSelf() inside kill()");
		System.out.println(
				"You may notice some lag on hit. this is on purpose. its me trying out different configurations to make it feel heavier");
		System.out.println("to change it, goto WorldMap.sleep() and change the sleepTime value to 0");
		System.out.println(
				"Yo also each time an enemy dies, it spawns 2  enemies. to change this goto enemy.kill and remove one line");
		System.out.println("(this message is inside WorldMap.Init()");

		System.out.println("also 6478688591 is my number this is the only way i can contact you");
		System.out.println("\nyour fisherman friend, Lauris petlah\n");
		addGameObject(new Player(500, 500, 2f));
		addGameObject(new Cursor());
		addGameObject(new InteractableBox(700, 700));
		// addEntity(new Enemy(400, 300, 100, 0, .2f));

		// addEntity(new Enemy(200, 300, 100, 0, .2f));

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
}
