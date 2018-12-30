import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class ShotgunShell extends Projectile {

	public ShotgunShell(float x, float y, float theta, float damage, float offset, float reduction) {
		super(x + (float) Math.random() * 20 - 10, y + (float) Math.random() * 20 - 10, 12f, damage, theta, offset,
				reduction);
		setDamage(damage);
		addCollisionBox(new HurtBox(this, -7.5f, -7.5f, 15, 15, damage));
	}

	public ShotgunShell(float damage) {
		addCollisionBox(new HurtBox(this, -7.5f, -7.5f, 15, 15, damage));
		setKnockback(3f);
		health = 3;
	}

	@Override
	public void kill() {
		if (forces.getNetMagnitude() > 10f) {
			Particle.create(x, y, forces.getNetMagnitude(), getTheta(), 90, 1);
		}
		super.kill();
	}

	
	@Override
	public Graphics2D draw(Graphics2D g2d) {
		
		
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		float theta = (float) (forces.getNetTheta() + Math.PI);
		int trail = 16;

		Shape s = new Rectangle2D.Float(drawX, drawY - 2f, trail, 4f);

		
		AffineTransform transform = new AffineTransform();
		AffineTransform old = g2d.getTransform();
		transform.rotate(theta, drawX, drawY);
		g2d.transform(transform);
		g2d.setColor(Color.red);
		g2d.fill(s);
		g2d.setTransform(old);
		return g2d;
	}
}
