
public class Wall extends Entity{

	Wall(WorldMap wm, float x, float y, float moveSpeed, int health) {
		super(wm, x, y, moveSpeed, health);
//		 TODO Auto-generated constructor stub
	
		setCollisionBox(new HitBox(this, 0, 0, 40, 1000, true));
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
