import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class Keys {
	Map<Integer, Boolean> keySet = new HashMap<>();
	Keys(){
		keySet.put(KeyEvent.VK_W, false);
		keySet.put(KeyEvent.VK_A, false);
		keySet.put(KeyEvent.VK_S, false);
		keySet.put(KeyEvent.VK_D, false);
	}
}
