
public class Mouse {
	private float mouseX;
	private float mouseY;
	private boolean isPressed;
	private Camera camera;
	
	Mouse(double x, double y, boolean pressed, Camera c){
		mouseX = (float) x;
		mouseY = (float) y;
		isPressed = pressed;
		setCamera(c);
	}
	
	public float getX() {
		return mouseX;
	}
	public float getY() {
		return mouseY;
	}
	public boolean isPressed() {
		return isPressed;
	}
	public void setPressed(boolean isPressed) {
		this.isPressed = isPressed;
	}
	
	public float realX() {
		return mouseX + getCamera().camX();
	}
	public float realY() {
		return mouseY + getCamera().camX();
	}
	
	public float planeX() {
		return mouseX -getCamera().getRadius();
	}
	public float planeY() {
		return mouseY -getCamera().getRadius();
	}
	
	public float dist() {
		return (float) Math.hypot(planeX(), planeY());
	}
	
	public float theta() {
		return (float) Math.atan2(planeY(),planeX());
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	
}
