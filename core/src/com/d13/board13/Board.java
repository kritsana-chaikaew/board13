package com.d13.board13;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;

public class Board {
	private static final String TILE_PATH = AssetsManager.TILE_PATH;
	public Manager manager;
	public AssetsManager assetsManager;

	public Vector3 position = new Vector3();

	public Board (Manager manager,AssetsManager assetsManager) {
    	this.manager = manager;
    	this.assetsManager = assetsManager;
    	setup();
	}

	public void setup () {
		setupTile();
		setupCharactor();
	}

	public void setupTile () {
		Model tileModel = assetsManager.get(TILE_PATH, Model.class);
		float tileRadius = 1f; //new Tile(tileModel).getRadius();
		float boardWidth = tileRadius * 10;
		for ( float x = -boardWidth; x < boardWidth; x +=  2 * tileRadius) {
			for ( float z = -boardWidth; z < boardWidth; z += 2 * tileRadius) {
				Tile tile = new Tile(assetsManager);
				Gdx.app.log("x", Float.toString(x));
				Gdx.app.log("z", Float.toString(z));
				tile.setPosition(new Vector3(x, 0, z));
				manager.tiles.add(tile);
			}
	 	}
	}

	public void setupCharactor () {
	  	Character character = new Mon1(assetsManager);
	  	Vector3 tilePosition = new Vector3();
	  	tilePosition = manager.tiles.get(45).getPosition();
	  	tilePosition.y += 1.2f;
	  	character.setPosition(tilePosition);
		manager.characters.add(character);
	}
}
