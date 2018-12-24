import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.sql.Time;
import java.util.Map;

public class Player extends Entity {

	Gun gun = new Shotgun();

	Player(float x, float y, float moveSpeed) {
		super(x, y, moveSpeed, 10);
		setCollisionBox(new HitBox(this, -7.5f, -7.5f, 15, 15, false));

	}

	public void parseInput(Map<Integer, Boolean> keys, Mouse mouse) {
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
		}if (keys.containsKey(KeyEvent.VK_Z) && keys.get(KeyEvent.VK_Z)) {
			gun.setDamage(gun.getDamage()+10);
			keys.put(KeyEvent.VK_Z, false);
		}
		if (keys.containsKey(KeyEvent.VK_SPACE) && keys.get(KeyEvent.VK_SPACE)) {

			if (gun instanceof Shotgun) {
				gun = new MachineGun();

			} else if( gun instanceof MachineGun)
				gun = new Cannon();
			else
				gun = new Shotgun();
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

		if (mouse.isPressed()) {
			float theta = (float) Math.atan2(mouse.realY() - y, mouse.realX() - x);
			gun.fire(theta);
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
	public void contactReply(CollisionBox box, CollisionBox myBox) {
		if (box.isSolid) {
			collide(box, myBox);
		} // TODO Auto-generated method stub
			// System.out.println("yo");
	}

	
}
