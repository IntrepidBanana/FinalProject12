package com.aidenlauris.game;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import com.aidenlauris.game.util.KeyType;
import com.aidenlauris.game.util.Keys;
import com.aidenlauris.game.util.Mouse;
import com.aidenlauris.gameobjects.Camera;

public class IOHandler implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener {
	static Keys keys = new Keys();
	static Camera camera;
	boolean isPressed = false;

	public void setCamera(Camera camera) {
		this.camera = camera;
	}


	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (Keys.keySet.containsKey(key)) {
			
			if(keys.keySet.get(key) == KeyType.NOT_USED) {
				return;
			}
			
			
			if (Keys.keySet.get(key) == KeyType.PRESSED || keys.keySet.get(key) == KeyType.HELD) {
				Keys.keySet.put(key, KeyType.HELD);
			} else {

				Keys.keySet.put(key, KeyType.PRESSED);
			}
		}

	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		Keys.keySet.put(e.getKeyCode(), KeyType.RELEASED);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private void setMouse(double x, double y) {
		Mouse.setMouseX(x);
		Mouse.setMouseY(y);
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
			Mouse.setRight(true);
		}
		if (arg0.getModifiers() == InputEvent.BUTTON1_MASK) {
			Mouse.setLeft(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (arg0.getModifiers() == InputEvent.BUTTON3_MASK) {
			Mouse.setRight(false);
		}
		if (arg0.getModifiers() == InputEvent.BUTTON1_MASK) {
			Mouse.setLeft(false);
		}
	}


	public static float distToMouse(float x, float y) {
		return (float) Math.hypot(x - Mouse.realX(), y - Mouse.realY());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		Mouse.setRotation(e.getWheelRotation());
	}

}
