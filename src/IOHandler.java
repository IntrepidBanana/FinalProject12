import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Map;

public class IOHandler implements KeyListener, MouseMotionListener,MouseListener{
	Keys keys = new Keys();
	Camera camera;
	boolean isPressed = false;
	Mouse mouse = new Mouse(0, 0, false, camera);

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public Map<Integer, Boolean> getKeys() {
		return keys.keySet;
	}

	public void keyPressed(KeyEvent e) {
		keys.keySet.put(e.getKeyCode(), true);
		if(keys.keySet.containsKey(e.getKeyCode())) {
		}
		else {
		}
	}

	public void keyReleased(KeyEvent e) {
		if(keys.keySet.containsKey(e.getKeyCode())) {
			keys.keySet.put(e.getKeyCode(), false);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	private void setMouse(MouseEvent m){
		Point point = m.getPoint();
		mouse = new Mouse(point.getX(), point.getY(), isPressed, camera);
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		setMouse(arg0);
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		setMouse(arg0);
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		mouse.setPressed(true);
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mouse.setPressed(false);
	}

	
	

	

}
