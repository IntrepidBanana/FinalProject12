import java.awt.Color;

public class Tile {
	int x;
	int y;
	Color color;
	static int size = 64;
	
	public Tile(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
}
