
public class Mouse {
	private float mouseX;
	private float mouseY;
	private boolean isPressed;
	private Camera camera;

	Mouse(double x, double y, boolean pressed, Camera c) {
		setMouseX((float) x);
		setMouseY((float) y);
		isPressed = pressed;
		setCamera(c);
	}

	public float getX() {
		return getMouseX();
	}

	public float getY() {
		return getMouseY();
	}

	public boolean isPressed() {
		return isPressed;
	}

	public void setPressed(boolean isPressed) {
		this.isPressed = isPressed;
	}

	public float realX() {
		if (getCamera() != null) {
			return getMouseX() + getCamera().camX();
		}
		return 0;
	}

	public float realY() {
		if (getCamera() != null) {
			return getMouseY() + getCamera().camY();
		}
		return 0;
	}

	public float planeX() {
		if (getCamera() != null) {
			return getMouseX() - getCamera().getRadius();
		}
		return 0;
	}

	public float planeY() {
		if (getCamera() != null) {
			return getMouseY() - getCamera().getRadius();
		}
		return 0;
	}

	public float dist() {
		return (float) Math.hypot(planeX(), planeY());
	}

	public float theta() {
		return (float) Math.atan2(planeY(), planeX());
	}

	public Camera getCamera() {
		return WorldMap.getCamera();
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public float getMouseX() {
		return mouseX;
	}

	public void setMouseX(double d) {
		this.mouseX = (float) d;
	}

	public float getMouseY() {
		return mouseY;
	}

	public void setMouseY(double d) {
		this.mouseY = (float) d;
	}

}
