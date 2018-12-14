import java.awt.Color;

public class WorldMap {
	Tile[] tiles = new Tile[36 * 36];
	Entity[] entities = new Entity[5];

	public WorldMap() {
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(i % 36 * Tile.size, Tile.size * (int) (i / 36),
					new Color(150 + 25 * (i % 5), 150 - 25 * (i % 5), 150));
		}
	}

}
