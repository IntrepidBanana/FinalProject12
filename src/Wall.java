
public class Wall extends Entity {

	Wall(float x, float y, int len, int wid) {
		super(x, y, 0, 1);
		// TODO Auto-generated constructor stub

		setCollisionBox(new HitBox(this, 0, 0, len, wid, true));
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void contactReply(CollisionBox box, CollisionBox myBox) {
		// TODO Auto-generated method stub

	}

}
