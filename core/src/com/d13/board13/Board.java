package com.d13.board13;

import com.badlogic.gdx.math.Vector3;

public class Board {
	private Manager manager;

	public Board (Manager manager) {
    	this.manager = manager;
	}

	public void setup (AssetsManager assetsManager) {
		float tileRadius = 1f;
		float boardWidth = tileRadius * 10;
		for ( float x = -boardWidth; x < boardWidth; x +=  2 * tileRadius) {
			for ( float z = -boardWidth; z < boardWidth; z += 2 * tileRadius) {
				Tile tile = new Tile(assetsManager.getModel(Tile.class));
				tile.setPosition(new Vector3(x, 0, z));
				manager.tiles.add(tile);
			}
	 	}
	}
}
