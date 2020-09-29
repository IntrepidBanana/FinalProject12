
public class ForceAnchor extends Force {

	Entity owner;
	Entity anchor;
	float magnitude;
	float pull;
	float offset = 0;

	public ForceAnchor(float magnitude, Entity owner, Entity anchor) {
		super(magnitude, (float) Math.atan2(owner.y - anchor.y, owner.x - anchor.y));
		setReduction(0);
		this.magnitude = -magnitude;
		this.pull = pull;
		this.owner = owner;
		this.anchor = anchor;
	}

	public ForceAnchor(float magnitude, Entity owner, Entity anchor, float offset) {
		this(magnitude, owner, anchor);
		this.offset = offset;

	}

	@Override
	public boolean update() {
		System.out.println(owner.x + " " + anchor.y);
		float dist = (float) Math.sqrt(Math.pow(owner.x - anchor.x, 2) + Math.pow(owner.y - anchor.y, 2));
		if (dist <= 1f) {
			dx = 0;
			dy = 0;
			return true;
		}

		// magnitude = 1/dist;
		theta = (float) ((float) Math.atan2(owner.y - anchor.y, owner.x - anchor.x) + Math.toRadians(offset));

		dx = (float) (magnitude * (Math.min(100 / dist, 1)) * Math.cos(theta));
		dy = (float) (magnitude * (Math.min(100 / dist, 1)) * Math.sin(theta));
		System.out.println(magnitude + "\n");

		return false;

	}

}
