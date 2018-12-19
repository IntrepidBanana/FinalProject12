
public class ForceAnchor extends Force {

	Entity owner;
	Entity anchor;
	float magnitude;
	float pull;
	float offset = 0;
	float threshold = 1f;
	boolean inversion = false;
	
	
	
	public ForceAnchor(float magnitude, Entity owner, Entity anchor, float threshold) {
		super(magnitude, (float) Math.atan2(owner.y - anchor.y, owner.x - anchor.y));
		setReduction(0);
		this.magnitude = -magnitude;
		this.pull = pull;
		this.owner = owner;
		this.anchor = anchor;
		this.threshold = threshold;
	}
	
	public ForceAnchor(float magnitude, Entity owner, Entity anchor){
		this(magnitude, owner, anchor, 1f);
	}
	

	public ForceAnchor(float magnitude, Entity owner, Entity anchor, float threshold, float offset) {
		this(magnitude, owner, anchor, threshold);
		this.offset = offset;

	}

	public void setInversion(boolean isInverted) {
		inversion = isInverted;
	}
	
	@Override
	public boolean update() {
		float dist = (float) Math.sqrt(Math.pow(owner.x - anchor.x, 2) + Math.pow(owner.y - anchor.y, 2));
		if (dist <= threshold) {
			dx = 0;
			dy = 0;
			return true;
		}

		// magnitude = 1/dist;
		theta = (float) ((float) Math.atan2(owner.y - anchor.y, owner.x - anchor.x) + Math.toRadians(offset));
		
		
		float multiplier = (Math.min(100 / dist, 1));
		if(inversion){
			multiplier = (dist/100);
		}
		
		dx = (float) (magnitude * multiplier * Math.cos(theta));
		dy = (float) (magnitude * multiplier * Math.sin(theta));

		return false;

	}

}
