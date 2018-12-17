import java.util.ArrayList;
import java.util.Iterator;

public class ForceSet {
	private ArrayList<Force> forces = new ArrayList<>();

	public ArrayList<Force> getForces() {
		return forces;
	}

	public void update() {
		Iterator<Force> i = forces.iterator();

		while (i.hasNext()) {
			Force next = i.next();
			if (next.update()) {
				i.remove();
			}
		}
	}

	public float getX() {
		float x = 0;
		for (Force f : forces) {
			x += f.getDx();
		}
		return x;
	}

	public float getY() {
		float y = 0;
		for (Force f : forces) {
			y += f.getDy();
		}
		return y;
	}
	
	public void setNetX(float magnitude, float x) {
		for (Force f : forces) {
			f.setDx(0);
		}
		forces.add(new Force(magnitude,x,0));
	}
	public void setNetY(float magnitude, float y) {
		for (Force f : forces) {
			f.setDy(0);
		}
		forces.add(new Force(magnitude,0,y));
	}
	
	
	public void addForce(float magnitude, float x, float y) {
		forces.add(new Force(magnitude, x, y));
	}
	public void addForce(float magnitude, float theta) {
		forces.add(new Force(magnitude,theta));
	}
	public void addForce(float magnitude, float x, float y, float reduction) {
		Force f = new Force(magnitude, x, y);
		f.setReduction(reduction);
		forces.add(f);
	}
	
	public void addForce(Force f) {
		forces.add(f);
	}
}
