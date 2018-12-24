import java.awt.Color;
import java.util.ArrayList;
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
	static ArrayList<Entity> entities = new ArrayList<>();
	static ArrayList<CollisionBox> collisionBoxes = new ArrayList<>();
	static ArrayList<Entity> toRemove = new ArrayList<>();
	static boolean sleep = false;
	static int sleepTime = 0;
	final static int sleepReset = 3;
	static long timeSinceLastCall = System.currentTimeMillis();
	static Player player;

	public WorldMap() {
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(i % 36 * Tile.size, Tile.size * (int) (i / 36),
					new Color(150 + 25 * (i % 5), 150 - 25 * (i % 5), 150));
		}

		System.out.println(entities.size());
	}

	public synchronized static void update() {
		long start = System.currentTimeMillis();
		if (sleep == true) {
			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime);
					sleepTime = 0;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (sleepTime > -sleepReset) {
				sleepTime -= FRAMERATE;
			} else {
				sleepTime = 0;
				sleep = false;
			}
		}
		globalTime++;

		if (globalTime % 60 == 0) {
//			System.out.println("@@@@@@@@@@@@@\nEntities on screen : " + entities.size());
//			System.out.println("Time since last in 60 ticks: "
//					+ (System.currentTimeMillis() - timeSinceLastCall) / 1000.0 + "s \n@@@@@@@@@@@@@");
			timeSinceLastCall = System.currentTimeMillis();
		}
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e != null) {
				e.update();

			}
		}
		try {
			ArrayList<CollisionBox> boxes = getCollisionBoxes();
			for (int i = 0; i < boxes.size(); i++) {
				for (int j = 0; j < boxes.size(); j++) {
					CollisionBox cb = boxes.get(i);
					CollisionBox hb = boxes.get(j);
					if (cb == hb)
						continue;
					CollisionHelper.sendReply(cb, hb);

				}
			}
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		// System.out.println("TIME FOR FRAME: " + (end - start));
	}

	public synchronized static void addEntity(Entity e) {

		entities.add(e);
		collisionBoxes.add(e.getCollisionBox());
		System.out.println("Added an Entity! Number Of Entities: " + entities.size());
	}

	public synchronized static void removeEntity(Entity e) {
		if (entities.contains(e)) {
			Iterator<CollisionBox> i = collisionBoxes.iterator();
			while (i.hasNext()) {
				CollisionBox cb = i.next();
				if (cb.getOwner() == e) {
					i.remove();
				}
			}
			// System.out.println("removing: " + e);
			entities.remove(e);
		}
	}

	public static Player getPlayer() {
		if (player != null) {
			return player;
		}
		for (Entity e : entities) {
			if (e instanceof Player) {
				player = (Player) e;
				return (Player) e;
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

		addEntity(new Enemy(300, 300, 100, 0, .2f));
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
		System.out.println("\nyour fisherman friend, Lauris petlah");

		// addEntity(new Enemy(400, 300, 100, 0, .2f));

		// addEntity(new Enemy(200, 300, 100, 0, .2f));

	}

	public static ArrayList<CollisionBox> getCollisionBoxes() {
		return (ArrayList<CollisionBox>) collisionBoxes.clone();
	}

	public static void sleep() {
		if (sleepTime > 0) {
			return;
			// sleepTime += 1/(2*sleepTime);
		} else {
			sleepTime = 10;
		}
		sleep = true;

	}
}
