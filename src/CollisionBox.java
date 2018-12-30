
public abstract class CollisionBox {

	private GameObject owner;
	// box coords relative to owner
	float x;
	float y;
	float len;
	float wid;
	boolean isSolid;

	public CollisionBox(GameObject owner, float x, float y, float len, float wid, boolean isSolid) {
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.len = len;
		this.wid = wid;
		this.isSolid = isSolid;
	}

	public CollisionBox(GameObject owner, float len, float wid, boolean isSolid) {
		this(owner,-wid/2,-len/2,len,wid,isSolid);
	}
	
	public void setOwner(GameObject owner) {
		this.owner = owner;
	}

	public GameObject getOwner() {
		return owner;
	}

	public float getLeft() {
		return owner.x + x;
	}

	public float getRight() {
		return owner.x + x + wid;
	}

	public float getTop() {
		return owner.y + y;
	}

	public float getBottom() {
		return owner.y + y + len;
	}

	public float getX() {
		return getLeft() + len / 2;
	}

	public float getY() {
		return getTop() + len / 2;
	}

	public void notifyOwner(CollisionBox box) {
		owner.contactReply(box, this);
	}

}
