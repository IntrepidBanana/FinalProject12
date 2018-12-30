import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Particle extends Entity {

	private int size = 6;
	private Color color = Color.DARK_GRAY;
	private int lifeSpan = 20;
	private int rotation = 0;

	public static void create(float x, float y, float magnitude, float theta, float spread, int count, boolean isSolid,
			Color color, int lifeSpan, int size) {
		for (int i = 0; i < count; i++) {
			Particle p = new Particle(x, y, magnitude, spread, theta);

			if (isSolid) {
				p.addCollider();
			}
			p.size = size;
			p.color = color;
			p.lifeSpan = lifeSpan;
			p.rotation = (int) (Math.random()*360);
			WorldMap.addGameObject(p);
		}
	}
	public static void create(float x, float y, float magnitude, float theta, float spread, int count, boolean isSolid,
			Color color, int lifeSpan) {
		create(x, y, magnitude, theta, spread, count, isSolid, color, lifeSpan, 6);
	}
	public static void create(float x, float y, float magnitude, float theta, float spread, int count, Color color) {
		create(x, y, magnitude, theta, spread, count, true, color, 20);
	}

	public static void create(float x, float y, float magnitude, float theta, float spread, int count) {
		create(x, y, magnitude, theta, spread, count, Color.DARK_GRAY);
	}

	public static void create(float x, float y, float magnitude, float theta) {
		create(x, y, magnitude, theta, 0, 1, Color.DARK_GRAY);
	}

	public static void create(float x, float y) {
		create(x, y, 0, 0);
	}

	Particle(float x, float y, float magnitude, float spread, float theta) {
		super(x, y);
		float a = (float) Math.toRadians(Math.random() * spread * 2 - spread);
		Force f = new Force(magnitude, (float) (theta + Math.PI + a));
		f.setReduction(0.3f);
		forces.addForce(f);
		team = Team.PLAYER;

	}

	public void addCollider() {
		addCollisionBox(new HitBox(this, 4, 4, true));
	}

	public void removeCollider() {
		collisionBoxes.clear();
	}

	@Override
	public void contactReply(CollisionBox box, CollisionBox myBox) {
		if (box.isSolid) {
			collide(box, myBox);
		}

	}

	@Override
	public void update() {
		tickUpdate();
		if (time > lifeSpan) {
			kill();
		}
		else {
			System.out.println(255*(1-(time/(double)lifeSpan)));
			color = new Color(color.getRed(), color.getGreen(), color.getBlue(),  Math.max((int) (255*(1-(time/(double)lifeSpan))),0));
			
		}
	}

	@Override
	public Graphics2D draw(Graphics2D g2d) {
		float drawX = PaintHelper.x(x);
		float drawY = PaintHelper.y(y);
		g2d.setColor(color);
		
		AffineTransform transform = new AffineTransform();
		AffineTransform old = g2d.getTransform();
		transform.rotate(Math.toRadians(rotation), drawX+size/2, drawY+size/2);
		g2d.transform(transform);
		
		g2d.fill(new Rectangle2D.Float(drawX - size / 2, drawY - size / 2, size, size));

		g2d.setTransform(old);
		return g2d;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
