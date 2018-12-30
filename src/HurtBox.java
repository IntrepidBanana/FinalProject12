
public class HurtBox extends CollisionBox {

	float damage;

	public HurtBox(GameObject owner, float x, float y, float len, float wid, float damage) {
		super(owner, x, y, len, wid, false);
		this.damage = damage;
		// TODO Auto-generated constructor stub
	}

	public HurtBox(GameObject owner, float len, float wid, float damage) {
		super(owner, len, wid, false);
		this.damage = damage;
		// TODO Auto-generated constructor stub
	}

}
