package com.d13.board13;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.assets.AssetManager;

import com.badlogic.gdx.Gdx;

public class Board extends InputAdapter {
	private static final String TILE_PATH = AssetsManager.TILE_PATH;
	private static final String MONSTER_PATH = AssetsManager.MONSTER_PATH;

import com.badlogic.gdx.Gdx;

public class Board extends InputAdapter {
	private static final String TILE_PATH = AssetsManager.TILE_PATH;
	private static final String MONSTER_PATH = AssetsManager.MONSTER_PATH;

	public Manager manager;
	public AssetsManager assetsManager;

	public int selected = -1, selecting = -1;
	public Material selectionMaterial;
	public Material originalMaterial;

	public Vector3 position = new Vector3();

	public Board (Manager manager,AssetsManager assetsManager) {
    	this.manager = manager;
    	this.assetsManager = assetsManager;
    	setup();

    	selectionMaterial = new Material();
		selectionMaterial.set(ColorAttribute.createDiffuse(Color.ORANGE));
		originalMaterial = new Material();
	}

	public void setup () {
		setupTile();
		setupPiece();
	}

	public void setupTile () {
		Model tileModel = assetsManager.get(TILE_PATH, Model.class);
		float tileRadius = 1f; //new Tile(tileModel).getRadius();
		float boardWidth = tileRadius * 10;
		for ( float x = -boardWidth; x < boardWidth; x +=  2 * tileRadius) {
			for ( float z = -boardWidth; z < boardWidth; z += 2 * tileRadius) {
				Tile tile = new Tile(tileModel);
				tile = new Tile(tileModel);
				tile.transform.setToTranslation(x, 0, z);
				manager.tiles.add(tile);
			}
	 	}

	 	for ( int i = 0; i < manager.tiles.size; i++) {
			manager.tiles.get(i).setIndex(i);
	  	}
	}

	public void setupPiece () {
		Model pieceModel = manager.assetsManager.get(MONSTER_PATH, Model.class);
	  	Character character = new Mon1(assetsManager);
	  	Vector3 tilePosition = new Vector3();
	  	manager.tiles.get(45).transform.getTranslation(tilePosition);
	  	tilePosition.y += 1.2f;
	  	character.setPosition(tilePosition);
		manager.characters.add(character);
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		selecting = getGameObject(screenX, screenY);
		return selecting >= 0;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		if (selecting >= 100) {
			if(selecting == getGameObject(screenX, screenY))
			   manager.characters.get(selecting-100).toggleSelection();
			selecting = -1;
			return true;
		}
		return false;
	}

	public int getGameObject (int screenX, int screenY) {
		Ray ray = manager.camera.getPickRay(screenX, screenY);
		int result = -1;
		float distance = -1;

		for (int i = 0; i < manager.tiles.size; i++) {

			final Tile tile = manager.tiles.get(i);
			tile.transform.getTranslation(position);
			position.add(tile.getCenter());

	      	float len = ray.direction.dot(
					position.x-ray.origin.x,
	        		position.y-ray.origin.y,
					position.z-ray.origin.z);

	      	if (len < 0f)
	        	continue;

	        float dist2 = position.dst2(
					ray.origin.x+ray.direction.x*len,
	        		ray.origin.y+ray.direction.y*len,
					ray.origin.z+ray.direction.z*len);

	        if (distance >= 0f && dist2 > distance)
	        	continue;

	        if (dist2 <= tile.getRadius() * tile.getRadius()) {
	        	result = i;
	        	distance = dist2;
			}
		}

    	// Loop for pieces
    	for (int i = 0; i < manager.characters.size; i++) {

			final Character character = manager.characters.get(i);
			position = character.getPosition();
			position.add(character.getCenter());

      		float len = ray.direction.dot(
					position.x-ray.origin.x,
        			position.y-ray.origin.y,
					position.z-ray.origin.z);

      		if (len < 0f)
        		continue;

        	float dist2 = position.dst2(
					ray.origin.x+ray.direction.x*len,
          			ray.origin.y+ray.direction.y*len,
					ray.origin.z+ray.direction.z*len);

        	if (distance >= 0f && dist2 > distance)
          		continue;

        	if (dist2 <= character.getRadius() * character.getRadius()) {
          		result = i + 100;
          		distance = dist2;
			}
		}

		return result;
	}
}
