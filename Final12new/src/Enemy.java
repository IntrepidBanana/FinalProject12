
public class Enemy extends Entity {

	public Enemy(int x, int y) {
		super(x, y, 1, 1);
		health = 10;
		resistance = 0.9f;
		setCollisionBox(new HitBox(this, -75, -75, 150, 150, true));
	}

	@Override
	public void contactReply(CollisionBox box) {
		if(box instanceof HurtBox) {
			damage((HurtBox)box);
		}
		

	}

	public void update() {
		forceUpdate();
	}

}
