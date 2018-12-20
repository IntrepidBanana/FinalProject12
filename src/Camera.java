
public class Camera extends Entity {

	// x and y are the centers
	static Entity player;
	IOHandler io;
	float mouseOffsetX = 0;
	float mouseOffsetY = 0;

	private static int size = 720;

	public Camera(WorldMap wm, int size, Entity player, IOHandler io) {
		super(wm, 0, 0, 1f, 1);
		this.io = io;
		this.size = size;
		this.player = player;
		ForceAnchor f = new ForceAnchor(0.9f, this, player, -1f);
		f.setInversion(true);
		forces.addForce(f);
	}

	public int getSize() {
		return size;
	}

	public int getRadius() {
		return size / 2;
	}

	@Override
	public void update() {
		mouseOffsetX = io.mouse.planeX();
		mouseOffsetY = io.mouse.planeY();
		forceUpdate();
	}

	public float camX() {
		return x - getRadius() + (mouseOffsetX / 20);
	}

	public float camY() {
		return y - getRadius() + (mouseOffsetY / 20);
	}

	public float relX(float x) {
		return x - camX();
	}

	public float relY(float y) {
		return y - camY();
	}

	private void cameraSmooth(float dx, float dy) {
		float deltaX = dx - x;
		float deltaY = dy - y;

		float moveX = (float) (deltaX / 100);
		float moveY = (float) (deltaY / 100);

		float threshold = 0.001f;

		if (Math.abs(moveX) < threshold) {
			x = dx;
		} else {
			x += moveX;

		}
		if (Math.abs(moveY) < threshold) {
			y = dy;
		} else {
			y += moveY;

		}

	}

	public void cameraShake(float theta, float angle, float power) {

		angle = (float) Math.toRadians(Math.random() * angle * 2 - angle);
		Force f = new Force(power, (float) (theta + angle));
		f.setReduction(0.1f);
		forces.addForce(f);

	}

	public void cameraShake(float power, int spawns) {
		ForceAnchor f = new ForceAnchor(5f, this, player, -1f);
		f.setId("cameraAnchor");
		forces.addForce(f);
		for (int i = 0; i < spawns; i++) {
			Force a, b;
			a = new Force(power, (float) (Math.toRadians(Math.random() * 360)));
			b = new Force(power, (float) (Math.toRadians(Math.random() * 360)));

			a.setLifeSpan(10);
			b.setLifeSpan(10);

			forces.addForce(a, 10 * i);
			forces.addForce(b, 10 * i);
		}

		forces.removeForce("cameraAnchor");

	}

	@Override
	public void contactReply(CollisionBox box, CollisionBox myBox) {
		// TODO Auto-generated method stub

	}

}
