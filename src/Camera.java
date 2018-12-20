
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
		mouseOffsetX = io.mouse.planeX();
		mouseOffsetY = io.mouse.planeY();
		forceUpdate();
	}

	public float camX() {
		return x - getRadius()+ (mouseOffsetX/8);
	}

	public float camY() {
		return y - getRadius() + (mouseOffsetY/8);
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
