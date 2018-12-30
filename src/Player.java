import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Map;

public class Player extends Entity implements LightSource, ItemContainer {

	int score = 0;
	boolean idle = true;
	Inventory inventory = new Inventory();
	Menu menu = new Menu();
	MenuItemLabel[] itemLabels = new MenuItemLabel[20];
	int selectedItem = 0;
	ArrayList<GameObject> interactables = new ArrayList<>();

	Player(float x, float y, float moveSpeed) {
		super(x, y, moveSpeed, 10);
		z = 1;
		team = Team.PLAYER;
		addCollisionBox(new HitBox(this, -7.5f, -7.5f, 15, 15, false));
		inventory.addItem(new Sword());
		inventory.addItem(new MachineGun());
		inventory.addItem(new Cannon());
		inventory.addItem(new Shotgun());
		inventory.addItem(new Pistol());
		inventory.addItem(new Knife());

	}

	public void parseInput() {
		Map<Integer, Boolean> keys = IOHandler.getKeys();
		Mouse mouse = IOHandler.mouse;
		idle = true;
		float dx = 0;
		float dy = 0;
		if (isStunned()) {
			return;
		}
		if (keys.get(KeyEvent.VK_W)) {
			dy += -1;
			idle = false;
		}
		if (keys.get(KeyEvent.VK_S)) {
			dy += 1;
			idle = false;
		}
		if (keys.get(KeyEvent.VK_A)) {
			dx += -1;
			idle = false;
		}
		if (keys.get(KeyEvent.VK_D)) {
			dx += 1;
			idle = false;
		}
		if (keys.containsKey(KeyEvent.VK_Z) && keys.get(KeyEvent.VK_Z)) {
			WorldMap.addGameObject(new Enemy(500, 500, 250, 0, 0.2f));
			// keys.put(KeyEvent.VK_Z, false);
		}
		if (keys.containsKey(KeyEvent.VK_SPACE) && keys.get(KeyEvent.VK_SPACE)) {

			inventory.nextItem();
			keys.put(KeyEvent.VK_SPACE, false);
		}

		if (dx != 0 || dy != 0) {
			setMoveSpeed(3);
			Force f = new Force(getMoveSpeed(), dx, dy);
			if (dx != 0 && dy != 0) {
				f.setMagnitude(getMoveSpeed() * 1.4f);
			}
			f.setReduction(0f);
			f.setLifeSpan(1);
			forces.addForce(f);
		}

		if (Mouse.isLeftPressed()) {
			inventory.getItem().useItem();
		}

		if (idle) {

		}

	}

	public void screenShake(Camera c, int time) {
		if (time <= 0)
			return;
		if (time % 50 == 0) {
			Force f = new Force(10f, (float) Math.toRadians(Math.random() * 360));
			f.setReduction(0.8f);
			c.forces.addForce(f);
		}
		System.out.println(time);
		screenShake(c, time - 1);
	}

	public void update() {
		tickUpdate();
		menu = inventory.getMenu();
		menu.x = 64;

	}

	public static void useEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void contactReply(CollisionBox box, CollisionBox myBox) {
		if (box.isSolid) {
			collide(box, myBox);
		} // TODO Auto-generated method stub
			// System.out.println("yo");
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		g2d = super.draw(g2d);

		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		float gunLength = 0;
		if (inventory.getItem() instanceof Gun) {
			gunLength = ((Gun) inventory.getItem()).getLength();
		}

		float theta = IOHandler.mouse.theta(x, y);
		g2d.draw(new Rectangle2D.Float(drawX - 1f, drawY - 1f, 2, 2));

		Shape s = new Rectangle2D.Float(drawX, drawY - 5, gunLength, 10);

		AffineTransform transform = new AffineTransform();
		AffineTransform old = g2d.getTransform();
		transform.rotate(theta, drawX, drawY);
		g2d.transform(transform);
		g2d.setColor(Color.DARK_GRAY);
		g2d.fill(s);
		g2d.setTransform(old);
		g2d = menu.draw(g2d);
		return g2d;
	}

	@Override
	public Graphics2D renderLight(Graphics2D gl) {
		SightPolygon sight = new SightPolygon();
		gl.setColor(new Color(0, 0, 0, 255));
		return gl;
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	public static Player getPlayer() {
		return WorldMap.getPlayer();
	}

}
