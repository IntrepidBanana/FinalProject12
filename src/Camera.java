
public class Camera extends Entity {

	// x and y are the centers
	static Entity player;
	IOHandler io;
	float mouseOffsetX = 0;
	float mouseOffsetY = 0;
	float destX = 0;
	float destY = 0;
	int sizeX = 720;
	int sizeY = 720;

	public Camera(int size, Entity player, IOHandler io) {
		super(0, 0, 1f, 1);
		this.io = io;
		this.sizeX = WorldMap.camx;
		this.sizeY = WorldMap.camy;
		this.player = player;
		ForceAnchor f = new ForceAnchor(0.9f, this, player, -1f);
		f.setInversion(true);
		// forces.addForce(f);
		System.out.println(this.sizeX);
	}

	public int getSize() {
		return sizeX;
	}

	public int getRadiusX() {
		return sizeX / 2;
	}

	public int getRadiusY() {
		return sizeY / 2;
	}

	@Override
	public void update() {
		mouseOffsetX = io.mouse.planeX();
		mouseOffsetY = io.mouse.planeY();
		destX = (io.mouse.realX() + player.x) / 2;
		destY = (io.mouse.realY() + player.y) / 2;
		moveTo(player.x + mouseOffsetX/5, player.y + mouseOffsetY/5);
		 forceUpdate();
	}

	public float camX() {
		return x - getRadiusX();
		// return player.x-getRadiusX() + mouseOffsetX/5;
		// return x - getRadius() + (mouseOffsetX / 20);
	}

	public float camY() {
		return y - getRadiusY();
		// return player.y-getRadiusY() + mouseOffsetY/5;
		// return y - getRadius() + (mouseOffsetY / 20);
	}

	public float relX(float x) {
		return x - camX();
	}

	public float relY(float y) {
		return y - camY();
	}

	private void moveTo(float dx, float dy) {
		float distToX = dx - this.x;
		float distToY = dy - this.y;
		this.x += distToX*0.2 ;
		this.y += distToY*0.2;

	}

	public void cameraShake(double theta, float angle, float power) {

		angle = (float) Math.toRadians(Math.random() * angle * 2 - angle);
		Force f = new Force(power*1f, (float) (theta + Math.PI + angle));
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

			a.setLifeSpan(30);
			b.setLifeSpan(30);

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
