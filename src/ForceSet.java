import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ForceSet {
	
	private ArrayList<Force> forces = new ArrayList<>();
	private Map<Force, Integer> forceQueue = new HashMap<>();
	
	
	
	
	
	public ArrayList<Force> getForces() {
		return forces;
	}

	public void update() {
		for(Force f : forceQueue.keySet()) {
			int timeLeft = forceQueue.get(f);
			timeLeft--;
			
			if(timeLeft <= 0) {
				forces.add(f);
			}
			forceQueue.put(f, timeLeft);
			
		}
		Iterator<Force> i = forces.iterator();

		while (i.hasNext()) {
			Force next = i.next();
			if (next.isTerminated()) {
				i.remove();
				removeForce(next.getId());
				continue;
			}
			next.update();
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
	
	public float getNetMagnitude() {
		return (float) Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(),2));
	}
	
	public void setNetX(float magnitude, float x) {
		for (Force f : forces) {
			f.setDx(0);
		}
		Force f = new Force(magnitude,x,0);
		f.setLifeSpan(1);
		f.setReduction(0.05f);
		forces.add(f);
	}
	public void setNetY(float magnitude, float y) {
		for (Force f : forces) {
			f.setDy(0);
		}
		Force f = new Force(magnitude,0,y);
		f.setLifeSpan(1);
		f.setReduction(0.05f);
		forces.add(f);
	}
	
	public void addForce(Force f, int milliseconds) {
		forceQueue.put(f, milliseconds);
	}
	
	public Force getForce(String id) {
		for(Force f : forces) {
			
			if(f.getId().equals(id)) {
				
				return f;
			}
		}
		return null;
	}
	
	public boolean removeForce(String id) {
		Iterator<Force> i = forces.iterator();
		while(i.hasNext()) {
			Force f = i.next();
			
			if(f.getId().equals(id)) {
				i.remove();
				return true;
			}
		}
		return false;
	}
	
	public void addForce(Force f) {
		forces.add(f);
	}
}
