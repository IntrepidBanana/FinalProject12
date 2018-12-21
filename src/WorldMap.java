import java.awt.Color;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;

public class WorldMap {
	static Tile[] tiles = new Tile[36 * 36];
	static private Camera camera;
	static ArrayList<Entity> entities = new ArrayList<>();
	static ArrayList<CollisionBox> collisionBoxes = new ArrayList<>();
	static long globalTime = 0;
	static ArrayList<Entity> toRemove = new ArrayList<>();
	static boolean sleep = false;
	static int sleepTime = 0;
	final static int sleepReset = 50;

	public WorldMap() {
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(i % 36 * Tile.size, Tile.size * (int) (i / 36),
					new Color(150 + 25 * (i % 5), 150 - 25 * (i % 5), 150));
		}

		System.out.println(entities.size());
	}

	public synchronized static void update() {
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
				sleepTime--;
			} else {
				sleepTime = 0;
				sleep = false;
			}
		}
		globalTime++;
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
	}

	public synchronized static void addEntity(Entity e) {

		entities.add(e);

		collisionBoxes.add(e.getCollisionBox());
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
			System.out.println("removing: " + e);
			entities.remove(e);
		}
	}

	public static Player getPlayer() {

		for (Entity e : entities) {
			if (e instanceof Player) {
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

		addEntity(new Enemy(400, 300, 100, 0, .2f));

		addEntity(new Enemy(200, 300, 100, 0, .2f));

	}

	public static ArrayList<CollisionBox> getCollisionBoxes() {
		return (ArrayList<CollisionBox>) collisionBoxes.clone();
	}

	public static void sleep() {
		if (sleepTime > 0) {
			return;
			// sleepTime += 1/(2*sleepTime);
		} else {
			sleepTime = 30;
		}
		sleep = true;

	}
}
