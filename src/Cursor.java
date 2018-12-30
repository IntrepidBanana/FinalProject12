
public class Cursor extends GameObject {

	public Cursor() {
		x = 0;
		y = 0;
//		addCollisionBox(new HitBox(this, 0f, 0f, 32, 32, false));
		addCollisionBox(new HitBox(this, -1f, -1f, 2, 2, false));
		}	


	@Override
	public void update() {
		float rX = IOHandler.mouse.realX();
		float rY = IOHandler.mouse.realY();
		this.x = rX; //- rX % 32;
		this.y = rY; // - rY % 32;
	}
}
