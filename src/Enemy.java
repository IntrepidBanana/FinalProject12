import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Enemy extends Entity {

	private boolean isHovering;

	public Enemy(int x, int y, int health, int strength, float speed) {
		super(x, y, 1, 1);

		addCollisionBox(new HitBox(this, -12.5f, -12.5f, 25, 25, true));

		this.health = health;
		this.maxHealth = health;
		setMoveSpeed(speed);
	}

	@Override
	public void contactReply(CollisionBox box, CollisionBox myBox) {
		if (box instanceof HurtBox && box.getOwner() instanceof Entity) {
			Entity owner = (Entity) box.getOwner();
			// knockBack(owner.forces.getNetMagnitude() * owner.weight / 3,
			// owner.forces.getX(), owner.forces.getY());
			// damage((HurtBox) box);
			if (owner instanceof Projectile) {
				WorldMap.sleep();
			}
		}
		if (box.isSolid) {
			collide(box, myBox);
		}

	}

	public void update() {
		tickUpdate();
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		time++;
		if (isStunned()) {
			return;
		}
		move();

		if (dist < 80) {
			attack();
		}

	}

	public void move() {
		float dist = (float) Math
				.sqrt(Math.pow(WorldMap.getPlayer().x - x, 2) + Math.pow(WorldMap.getPlayer().y - y, 2));
		if (dist < 300) {
			ForceAnchor f = new ForceAnchor(3f, this, WorldMap.getPlayer(), -1);

			f.setId("PlayerFollow");
			f.hasVariableSpeed(false);
			if (forces.getForce("PlayerFollow") == null) {
				forces.removeForce("Random");
				forces.addForce(f);
				// System.out.println("Running Attack");
			}
		} else {
			Force f = new Force(getMoveSpeed(), (float) Math.toRadians(Math.random() * 360));
			f.setId("Random");
			f.setLifeSpan(60);
			f.setReduction(0f);
			if (forces.getForce("Random") == null) {
				forces.removeForce("PlayerFollow");
				forces.addForce(f);
			}
		}
	}

	public void attack() {

	}

	@Override
	public void kill() {

		ItemDropEntity.drop(x, y, new BulletAmmo(1), 0.2, 4, 10);
		ItemDropEntity.drop(x, y, new ShotgunAmmo(1), 0.05, 2, 3);
		ItemDropEntity.drop(x, y, new ExplosiveAmmo(1), 0.05, 1, 1);

		WorldMap.addGameObject(new Corpse(x, y, this));

		removeSelf();
	}




	@Override
	public Graphics2D draw(Graphics2D g2d) {
		float drawX, drawY;
		drawX = PaintHelper.x(x);
		drawY = PaintHelper.y(y);
		if (isHovering) {
			g2d.setColor(Color.red);
			g2d.fill(new Rectangle2D.Float(drawX - 24, drawY + 18, 48, 2));

			int healthLength = (int) (48 * (health / (float) maxHealth));
			g2d.setColor(Color.GREEN);
			g2d.fill(new Rectangle2D.Float(drawX - 24, drawY + 18, healthLength, 2));

		}

		g2d = super.draw(g2d);

		return super.draw(g2d);
	}
}
