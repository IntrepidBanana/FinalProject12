
public class Camera extends Entity {

	// x and y are the centers
	static Entity player;

	private static int size = 720;

	public Camera(int size, Entity player) {
		super(0, 0, 1f, 1);
		this.size = size;
		this.player = player;
		ForceAnchor f = new ForceAnchor(1f, this, player, -1f);
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
	public void contactReply(CollisionBox box) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		forceUpdate();
//		if (forces.getX() != 0 && forces.getY() != 0) {
//		} else {
//			cameraSmooth(player.x, player.y);
//		}
	}

	public float camX() {
		return x - getRadius();
	}

	public float camY() {
		return y - getRadius();
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

}
