import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Ray extends Entity {

	private boolean collided = false;

	public Ray(float x, float y, float theta) {
		super(x, y);
		Force f = new Force(75, theta);
		f.setReduction(0);
		f.setLifeSpan(5);
		forces.addForce(f);
		addCollisionBox(new HitBox(this, -1, -1, 2, 2, false));
		health = 10;
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		if (collided) {
			g2d.draw(new Rectangle2D.Float(PaintHelper.x(x), PaintHelper.y(y), 2, 2));
		}
		g2d = PaintHelper.drawCollisionBox(g2d, getCollisionBoxes().get(0));
		return g2d;
	}

	@Override
	public void contactReply(CollisionBox box, CollisionBox myBox) {
		if (box.isSolid) {
			collide(box, myBox);
			collided = true;
		}

	}

	@Override
	public void update() {
		tickUpdate();
		health--;

	}

}
