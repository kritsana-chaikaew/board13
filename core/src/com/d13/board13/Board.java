package com.d13.board13;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector3;

public class Board {
	private Manager manager;
	private ArrayList<Tile> tiles;
	private ArrayList<Character> characters;

	public Board (Manager manager) {
    	this.manager = manager;
		tiles = new ArrayList<Tile>();
		characters = new ArrayList<Character>();
	}

	public void setup (AssetsManager assetsManager) {
		setupBoard(assetsManager);
		setupCharactor(assetsManager);
	}

	private void setupBoard(AssetsManager assetsManager){
		float tileRadius = 1f;
		float boardWidth = tileRadius * 10;
		for ( float x = -boardWidth; x < boardWidth; x +=  2 * tileRadius) {
			for ( float z = -boardWidth; z < boardWidth; z += 2 * tileRadius) {
				Tile tile = new Tile(assetsManager.getModel(Tile.class));
				tile.setPosition(new Vector3(x, 0, z));
				tiles.add(tile);
				manager.getActors().add(tile);
			}
	 	}
	}

	private void setupCharactor(AssetsManager assetsManager){
		Vector3 tilePosition = tilePosition = tiles.get(45).getPosition();
	  	tilePosition.y += 1.2f;

		Character character = new Mon1(assetsManager.getModel(Mon1.class));
	  	character.setPosition(tilePosition);
		characters.add(character);
		manager.getActors().add(character);
	}
}
