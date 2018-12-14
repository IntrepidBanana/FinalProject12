import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class IOHandler implements KeyListener {
 Camera camera;
 boolean pressed = false;
 ArrayList<Integer> keys = new ArrayList<Integer>();

 public IOHandler(Camera camera) {
  this.camera = camera;

 }

 public ArrayList<Integer> getUpdate() {
  return keys;
 }

 public void keyPressed(KeyEvent e) {
  keys.add(e.getKeyCode());
 }

 public void keyReleased(KeyEvent e) {
  if (keys.contains(e.getKeyCode())) {
   keys.remove(e.getKeyCode());
  }
 }

 @Override
 public void keyTyped(KeyEvent e) {
  // TODO Auto-generated method stub

 }
}
