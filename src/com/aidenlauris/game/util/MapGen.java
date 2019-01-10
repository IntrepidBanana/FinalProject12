package com.aidenlauris.game.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import com.aidenlauris.gameobjects.Player;
import com.aidenlauris.gameobjects.Wall;
import com.aidenlauris.gameobjects.util.GameObject;

public class MapGen {

	final static int wallSize = 125;

	static void printMap(int[][] m) {

		for (int i = m.length; i >= 0; i--) {
			if (i == m.length || i == 0) {
				for (int j = 0; j < m[0].length; j++) {
					System.out.print("W ");
				}
				System.out.println();
				continue;
			}
			for (int j = -1; j <= m[0].length; j++) {
				if (j == m[0].length || j == -1) {
					System.out.print("E ");
				} else if (m[i][j] != 0) {
					System.out.print(m[i][j] + " ");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}

	}

	public static ArrayList<GameObject> genMap() {
		int[][] geo = new int[30][30];

		// a single wall is going to be something like 200 blocks

		long seed = 1;

		// Random ran = new Random(seed);
		Random ran = new Random();
		// plotting the rooms
		int rooms = 10;
		int additionalRooms = 0;
		int roomSize = 3;
		int roomVariance = 3;
		ArrayList<XY> roomLocations = new ArrayList<>();

		for (int i = 0; i < rooms + additionalRooms; i++) {

			int r = (int) (ran.nextGaussian() * geo.length / 7 + geo.length / 2);
			int c = (int) (ran.nextGaussian() * geo[0].length / 7 + geo[0].length / 2);

			int wid = roomSize + ran.nextInt(roomVariance);
			int len = roomSize + ran.nextInt(roomVariance);

			geo = carveRoom(geo, r, c, wid, len);
			XY rc = new XY(Math.min(r + Math.floor(len / 2.0), geo.length - 1),
					Math.min(c + Math.floor(wid / 2.0), geo[1].length - 1));
			geo[rc.x][rc.y] = 4;

			roomLocations.add(rc);

		}
		printMap(geo);

		Iterator<XY> iter = roomLocations.iterator();

		XY start = iter.next();
		XY playerSpawn = start;
		iter.remove();
		while (iter.hasNext()) {
			XY end = iter.next();
			geo = pathToRoom(geo, start, end);
			start = end;
			iter.remove();

		}

		printMap(geo);
		geo = pruneDeadArea(geo);
		printMap(geo);
		ArrayList<GameObject> objects = genObjects(geo, playerSpawn);
		return objects;

	}

	public static int[][] carveRoom(int[][] geo, int row, int col, int wid, int len) {
		// System.out.println(row + " " + col);
		row = Math.max(0, row);
		col = Math.max(0, col);
		for (int r = row; r < geo.length && r < row + wid; r++) {
			for (int c = col; c < geo[0].length && c < col + len; c++) {
				geo[r][c] = 1;
			}
		}
		return geo;

	}

	public static int[][] pathToRoom(int[][] geo, XY start, XY end) {

		int height = end.x - start.x;
		int wid = end.y - start.y;

		int r = start.x;
		int c = start.y;

		for (int i = 0; (Math.abs(height - i)) >= 0; i += Integer.signum(height)) {
			int index = r + i;
			geo[index][c] = 1;
			if (Math.abs(height - i) == 0)
				break;
		}
		for (int i = 0; (Math.abs(wid - i)) >= 0; i += Integer.signum(wid)) {
			int index = c + i;
			geo[r + height][index] = 1;
			if (Math.abs(wid - i) == 0)
				break;
		}

		return geo;

	}

	public static int[][] pruneDeadArea(int[][] geo) {

		for (int r = 0; r < geo.length; r++) {

			for (int c = 0; c < geo[0].length; c++) {

				if (geo[r][c] == 1) {
					geo = addWall(geo, r, c);
				}
			}

		}

		return geo;

	}

	private static int[][] addWall(int[][] geo, int r, int c) {

		int rLow = Math.max(0, r - 1);
		int rHigh = Math.min(geo.length - 1, r + 1);

		int cLow = Math.max(0, c - 1);
		int cHigh = Math.min(geo[0].length - 1, c + 1);

		for (int i = rLow; i <= rHigh; i++) {
			for (int j = cLow; j <= cHigh; j++) {
				if (geo[i][j] == 0) {
					geo[i][j] = 5;
				}
			}
		}

		return geo;
	}

	private static ArrayList<GameObject> genObjects(int[][] geo, XY player) {

		ArrayList<GameObject> objects = new ArrayList<>();

		for (int r = 0; r < geo.length; r++) {
			for (int c = 0; c < geo[0].length; c++) {
				if (geo[r][c] == 5) {
					Wall w = new Wall(r * wallSize, c * wallSize, wallSize, wallSize);
					objects.add(w);
				}
			}
		}
		for (int r = -1; r <= geo.length; r++) {
			Wall w1 = new Wall(r * wallSize, -1 * wallSize, wallSize, wallSize);
			Wall w2 = new Wall(r * wallSize, geo.length * wallSize, wallSize, wallSize);
			objects.add(w1);
			objects.add(w2);
		}for (int c = -1; c <= geo[0].length; c++) {
			Wall w1 = new Wall(-1 * wallSize, c * wallSize, wallSize, wallSize);
			Wall w2 = new Wall(geo.length * wallSize, c * wallSize, wallSize, wallSize);
			objects.add(w1);
			objects.add(w2);
		}

		Player p = new Player(player.x * wallSize, player.y * wallSize, 1);
		objects.add(p);

		return objects;

	}

}