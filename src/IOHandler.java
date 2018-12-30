import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Map;

public class IOHandler implements KeyListener, MouseMotionListener, MouseListener {
	static Keys keys = new Keys();
	static Camera camera;
	boolean isPressed = false;
	static Mouse mouse = new Mouse(0, 0);

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public static Map<Integer, Boolean> getKeys() {
		return keys.keySet;
	}

	public void keyPressed(KeyEvent e) {
		keys.keySet.put(e.getKeyCode(), true);
		if (keys.keySet.containsKey(e.getKeyCode())) {
		} else {
		}
	}

	public void keyReleased(KeyEvent e) {
		if (keys.keySet.containsKey(e.getKeyCode())) {
			keys.keySet.put(e.getKeyCode(), false);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private void setMouse(double x, double y) {
		mouse.setMouseX(x);
		mouse.setMouseY(y);
		// mouse = new Mouse(point.getX(), point.getY(), isPressed, camera);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		Point point = arg0.getPoint();

		setMouse(point.getX(), point.getY());

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		Point point = arg0.getPoint();

		setMouse(point.getX(), point.getY());

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
		if (arg0.getModifiers() == InputEvent.BUTTON3_MASK) {
			mouse.setRight(true);
		}
		if(arg0.getModifiers() == InputEvent.BUTTON1_MASK) {
			mouse.setLeft(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (arg0.getModifiers() == InputEvent.BUTTON3_MASK) {
			mouse.setRight(false);
		}
		if(arg0.getModifiers() == InputEvent.BUTTON1_MASK) {
			mouse.setLeft(false);
		}
	}

	public Mouse getMouse() {
		return mouse;
	}

	public static float distToMouse(float x, float y) {
		return (float) Math.hypot(x - IOHandler.mouse.realX(), y - IOHandler.mouse.realY());
	}

	public static boolean isKeyPressed(int i) {
		if(keys.keySet.containsKey(i)) {
			if(keys.keySet.get(i)) {
				return true;
			}
		}
		return false;
	}
	
}
