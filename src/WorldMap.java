import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;

public class WorldMap {
	static final int FRAMERATE = 60;
	static long globalTime = 0;
	static int camx = 1280;
	static int camy = 920;
	static Tile[] tiles = new Tile[36 * 36];
	static private Camera camera;
	static ArrayList<GameObject> gameObjects = new ArrayList<>();
	static ArrayList<CollisionBox> collisionBoxes = new ArrayList<>();
	static ArrayList<Entity> toRemove = new ArrayList<>();
	static boolean sleep = false;
	static int sleepTime = 0;
	final static int sleepReset = 1;
	static long timeSinceLastCall = System.currentTimeMillis();
	static Player player;

	public WorldMap() {
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(i % 36 * Tile.size, Tile.size * (int) (i / 36),
					new Color(150 + 25 * (i % 5), 150 - 25 * (i % 5), 150));
		}

		System.out.println(gameObjects.size());
	}

	
	
	public synchronized static void update() {
		long start = System.currentTimeMillis();
		Cursor c = getCursor();
		if (c != null) {
			c.update();
		}
		if (sleep == true) {
			if(sleepTime<= 0) {
				sleep = false;
			}
			sleepTime--;
			return;
		} else {
			if (sleepTime > -sleepReset) {
				sleepTime--;
			}

		}
		

		camera.update();
		player.parseInput();
		timeSinceLastCall = System.currentTimeMillis();
		globalTime++;

		int numOfForces = 0;
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject e = gameObjects.get(i);
			if (e != null) {
				e.update();
				numOfForces += e.forces.getForces().size();

			}
		}
		// System.out.println("Number of Forces at Gametick: " + getGlobalTime() + ": "
		// + numOfForces);
		ArrayList<CollisionBox> boxes = getCollisionBoxes();
		ArrayList<CollisionBox[]> checkedBoxes = new ArrayList<>();
		for (int i = 0; i < boxes.size(); i++) {
			CollisionBox cb = boxes.get(i);

			for (int j = 0; j < boxes.size(); j++) {
				CollisionBox hb = boxes.get(j);
				boolean hasBeenChecked = false;
				for (CollisionBox[] a : checkedBoxes) {
					if (Arrays.equals(a, new CollisionBox[] { cb, hb })) {
						hasBeenChecked = true;
					}
				}
				if (hasBeenChecked) {
					continue;
				}
				if (cb == hb)
					continue;
				if (CollisionHelper.sendReply(cb, hb)) {
					checkedBoxes.add(new CollisionBox[] { hb, cb });
					checkedBoxes.add(new CollisionBox[] { cb, hb });
				}

			}
		}

		long end = System.currentTimeMillis();
		// System.out.println("TIME FOR FRAME: " + (end - start));
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

	static Camera getCamera() {
		return camera;
	}

	public static void init() {
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(i % 36 * Tile.size, Tile.size * (int) (i / 36),
					new Color(150 + 25 * (i % 5), 150 - 25 * (i % 5), 150));
		}
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

		addGameObject(new Cursor());
		addGameObject(new InteractableBox(700, 700));
		// addEntity(new Enemy(400, 300, 100, 0, .2f));

		// addEntity(new Enemy(200, 300, 100, 0, .2f));

	}

	public static ArrayList<CollisionBox> getCollisionBoxes() {
		return (ArrayList<CollisionBox>) collisionBoxes.clone();
	}

	public static void sleep() {
		if (sleep || sleepTime > -sleepReset) {
			return;
		} else {
			sleepTime = 0;
		}
//		sleep = true;

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
}
