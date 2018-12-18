
public abstract class CollisionBox {

	private Entity owner;
	// box coords relative to owner
	float x;
	float y;
	float len;
	float wid;
	boolean isSolid;

	public CollisionBox(Entity owner, float x, float y, float len, float wid, boolean isSolid) {
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.len = len;
		this.wid = wid;
		this.isSolid = isSolid;
	}

	public void setOwner(Entity owner) {
		this.owner = owner;
	}

	public Entity getOwner() {
		return owner;
	}

	public float getLeft() {
		return owner.x + x;
	}

	public float getRight() {
		return owner.x + x + wid ;
	}

	public float getTop() {
		return owner.y + y;
	}

	public float getBottom() {
		return owner.y + y + len;
	}
	
	public float getX() {
		return owner.x;
	}
	public float getY() {
		return owner.y;
	}

	public void notifyOwner(CollisionBox box) {
		owner.contactReply(box);
	}

}
