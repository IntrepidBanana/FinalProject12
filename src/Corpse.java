import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Corpse extends Entity {

	Corpse(float x, float y, Entity e) {
		super(x, y);
		for (CollisionBox c : e.getCollisionBoxes()) {
			CollisionBox box = new HitBox(this, c.x, c.y, c.len, c.wid, false);
			addCollisionBox(box);
		}
//		forces = e.forces;
		Force f = new Force(e.forces.getNetMagnitude(), e.forces.getNetTheta());
		f.setReduction(0.08f);
		forces.addForce(f);
		team = Team.ENEMY;
		health = 10;
		invincibility = 10;
	}

	@Override
	public void contactReply(CollisionBox box, CollisionBox myBox) {
		if (box.isSolid) {
			collide(box, myBox);
		}

	}

	@Override
	public void update() {
		invincibility = 10;
		tickUpdate();
		if(time > 120) {
			kill();
		}
	}
	
	@Override
	public Graphics2D draw(Graphics2D g2d) {
		g2d.setColor(Color.red);
		g2d.draw( new Rectangle2D.Float(PaintHelper.x(x-5), PaintHelper.y(y-5), 10, 10));
		g2d = PaintHelper.drawCollisionBox(g2d, getCollisionBoxes().get(0));
		return g2d;
	}

}
