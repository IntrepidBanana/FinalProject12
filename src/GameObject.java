import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public abstract class GameObject{
	float x;
	float y;
	int z = 0;
	ForceSet forces = new ForceSet();
	boolean forceAccurate = false;

	ArrayList<CollisionBox> collisionBoxes = new ArrayList<>();

	public void addCollisionBox(CollisionBox box) {
		collisionBoxes.add(box);
	}

	public ArrayList<CollisionBox> getCollisionBoxes() {
		return collisionBoxes;
	}

	abstract void update();

	public void contactReply(CollisionBox box, CollisionBox collisionBox) {
		// TODO Auto-generated method stub

	}

	public Graphics2D draw(Graphics2D g2d) {
		for (CollisionBox cb : collisionBoxes) {
			g2d = PaintHelper.drawCollisionBox(g2d, cb);
		}

		return g2d;
	}

	public float distToPlayer() {
		return (float) Math.hypot(x - WorldMap.player.x, y - WorldMap.player.y);
	}

	public void forceUpdate() {

		if (forces.getNetMagnitude() > 50 && forceAccurate) {
			float divisions = forces.getNetMagnitude() / 15;
			float subMagnitude = forces.getNetMagnitude() / divisions;
			float dx = forces.getX() / divisions;
			float dy = forces.getY() / divisions;
			for (int i = 0; i < divisions; i++) {
				if (CollisionHelper.collideSets(collisionBoxes, WorldMap.collisionBoxes)) {

				}
				x += dx;
				y += dy;
			}

		} else {
			x += forces.getX();
			y += forces.getY();
		}

		forces.update();
		// x += forces.getX();
		// y += forces.getY();
	}

	public void kill() {
		removeSelf();
	}

	public void removeSelf() {
		WorldMap.removeGameObject(this);
	}

}
