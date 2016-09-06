package com.d13.board13;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.*;

public class BoardManager extends InputAdapter{

  public GameScreen gameScreen;

  public AssetManager assets;
  public boolean loadingAsset;

  public Tile tile;
  public Array<Tile> tiles = new Array<Tile>();

  public Piece piece;
  public Array<Piece> pieces = new Array<Piece>();

  public int selected = -1, selecting = -1;
  public Material selectionMaterial;
  public Material originalMaterial;

  public Vector3 position = new Vector3();

  public String MONSTER_PATH = "objects/mon1/mon1.g3db";

  public BoardManager (GameScreen gameScreen) {
    this.gameScreen = gameScreen;
    assets = new AssetManager();
    assets.load("objects/tile/tile.obj", Model.class);
    assets.load(MONSTER_PATH, Model.class);
    Gdx.app.log("asd", assets.getQueuedAssets() + "");
	loadingAsset = true;

    selectionMaterial = new Material();
	selectionMaterial.set(ColorAttribute.createDiffuse(Color.ORANGE));
	originalMaterial = new Material();
  }

  public void doneLoading () {
    Model tileModel = assets.get("objects/tile/tile.obj", Model.class);
    if(assets.get(MONSTER_PATH, Model.class) != null);
    Model pieceModel = assets.get(MONSTER_PATH, Model.class);

    for ( float x = -10f; x < 10f; x += 2f) {
      for ( float z = -10f; z < 10f; z += 2f) {
        Tile tile = new Tile(tileModel);
        tile.transform.setToTranslation(x, 0, z);
        tiles.add(tile);
      }
    }
    for ( int i = 0; i < tiles.size; i++) {
      tiles.get(i).setIndex(i);
    }

    piece = new Piece(pieceModel);
    piece.setOnTile(tiles.get(45));
    pieces.add(piece);

    piece = new Piece(pieceModel);
    piece.setOnTile(tiles.get(89));
    pieces.add(piece);

    loadingAsset = false;
  }

  @Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		selecting = getGameObject(screenX, screenY);
		return selecting >= 0;
	}

	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		if (selecting >= 0) {
			if(selecting == getGameObject(screenX, screenY))
			   setSelected(selecting);
			selecting = -1;
			return true;
		}
		return false;
	}

	public void setSelected (int value) {
		if (selected == value) return;

		if (selected >= 0 && selected < 100) {
			Material mat = tiles.get(selected).materials.get(0);
			mat.clear();
			mat.set(originalMaterial);
		}

    if (selected >= 100) {
			Material mat = pieces.get(selected - 100).materials.get(0);
			mat.clear();
			mat.set(originalMaterial);
		}

		selected = value;

		if (selected >= 0 && selected < 100) {
			Material mat = tiles.get(selected).materials.get(0);
			originalMaterial.clear();
			originalMaterial.set(mat);
			mat.clear();
			mat.set(selectionMaterial);
		}

    if (selected >= 100) {
			Material mat = pieces.get(selected - 100).materials.get(0);
			originalMaterial.clear();
			originalMaterial.set(mat);
			mat.clear();
			mat.set(selectionMaterial);
		}
	}

	public int getGameObject (int screenX, int screenY) {
		Ray ray = gameScreen.cam.getPickRay(screenX, screenY);
		int result = -1;
		float distance = -1;

		for (int i = 0; i < tiles.size; i++) {

			final Tile tile = tiles.get(i);
			tile.transform.getTranslation(position);
			position.add(tile.getCenter());


      float len = ray.direction.dot(position.x-ray.origin.x,
        position.y-ray.origin.y, position.z-ray.origin.z);

      if (len < 0f)
        continue;

        float dist2 = position.dst2(ray.origin.x+ray.direction.x*len,
          ray.origin.y+ray.direction.y*len, ray.origin.z+ray.direction.z*len);

        if (distance >= 0f && dist2 > distance)
          continue;

        if (dist2 <= tile.getRadius() * tile.getRadius()) {
          result = i;
          distance = dist2;
			  }
		}

    // Loop for pieces
    for (int i = 0; i < pieces.size; i++) {

			final Piece piece = pieces.get(i);
			piece.transform.getTranslation(position);
			position.add(piece.getCenter());

      float len = ray.direction.dot(position.x-ray.origin.x,
        position.y-ray.origin.y, position.z-ray.origin.z);

      if (len < 0f)
        continue;

        float dist2 = position.dst2(ray.origin.x+ray.direction.x*len,
          ray.origin.y+ray.direction.y*len, ray.origin.z+ray.direction.z*len);

        if (distance >= 0f && dist2 > distance)
          continue;

        if (dist2 <= piece.getRadius() * piece.getRadius()) {
          result = i + 100;
          distance = dist2;
			  }
		}

		return result;
	}

  public void dispose () {
    tiles.clear();
    pieces.clear();
		assets.dispose();
  }
}
