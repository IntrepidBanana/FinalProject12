import java.util.Comparator;

public class DrawCompare implements Comparator<GameObject> {

	@Override
	public int compare(GameObject a, GameObject b) {
		if(a.z == b.z) {
			if( a.y > b.y) {
				return 1;
			}
			else {
				return -1;
			}
		}
		else {
			return a.z - b.z;
		}
	}

}
