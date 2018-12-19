import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.sql.Time;
import java.util.Map;

public class Player extends Entity {
	WorldMap wm;
	boolean test = false;
	Player(float x, float y, float moveSpeed, WorldMap wm) {
		super(x, y, moveSpeed, 10);
		this.wm = wm;
		setCollisionBox(new HitBox(this, -7.5f, -7.5f, 15, 15, false));
	}

	public void giveInput(Map<Integer, Boolean> keys, Mouse mouse) {
		boolean idle = true;
		float dx = 0;
		float dy = 0;

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
		if (keys.containsKey(KeyEvent.VK_SPACE) && keys.get(KeyEvent.VK_SPACE)) {

			forces.addForce(3f, (float) Math.toRadians(Math.random() * 360));
			forces.addForce(3f, (float) Math.toRadians(Math.random() * 360));
			forces.addForce(3f, (float) Math.toRadians(Math.random() * 360));
			forces.addForce(3f, (float) Math.toRadians(Math.random() * 360));
			// forces.addForce(0, (float)Math.random()*10-5, 0.01f);
			keys.put(KeyEvent.VK_SPACE, false);
		}

		if (dx != 0 || dy != 0) {
			if (dx != 0 && dy != 0) {

				forces.addForce(moveSpeed * 1.3f, dx, dy);
			}
			forces.addForce(moveSpeed, dx, dy);

			// forces.addForce(moveSpeed, 0, dy);
		}

		if (mouse.isPressed()) {
			// screenShake(mouse.getCamera(), 500);
			wm.addEntity(new Bullet(x, y, 3f, mouse.theta()));
			mouse.setPressed(false);
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
		forceUpdate();

	}

	public static void useEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void contactReply(CollisionBox box) {
		if (box.isSolid) {
			collide(box.getX(), box.getY(), 1f);
		} // TODO Auto-generated method stub
			// System.out.println("yo");
	}
}
