package com.aidenlauris.game.util;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.aidenlauris.gameobjects.Camera;

public class IOHandler implements KeyListener, MouseMotionListener, MouseListener {
	static Camera camera;
	boolean isPressed = false;

	public void setCamera(Camera camera) {
		IOHandler.camera = camera;
	}


	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (Keys.keySet.containsKey(key)) {
			
			if(Keys.keySet.get(key) == KeyType.NOT_USED) {
				return;
			}
			
			
			if (Keys.keySet.get(key) == KeyType.PRESSED || Keys.keySet.get(key) == KeyType.HELD) {
				Keys.keySet.put(key, KeyType.HELD);
			} else {

				Keys.keySet.put(key, KeyType.PRESSED);
			}
		}

	}

	public void keyReleased(KeyEvent e) {

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

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		if (arg0.getModifiers() == InputEvent.BUTTON1_MASK) {
			Mouse.setLeft(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (arg0.getModifiers() == InputEvent.BUTTON1_MASK) {
			Mouse.setLeft(false);
		}
	}


	public static float distToMouse(float x, float y) {
		return (float) Math.hypot(x - Mouse.realX(), y - Mouse.realY());
	}


}
