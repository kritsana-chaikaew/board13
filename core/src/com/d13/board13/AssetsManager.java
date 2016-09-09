package com.d13.board13;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;

public class AssetsManager extends AssetManager{
	private static final String MONSTER_PATH = "objects/mon1/mon1.g3db";
	private static final String TILE_PATH = "objects/tile/tile.obj";

	public AssetsManager () {
		load(TILE_PATH, Model.class);
	    load(MONSTER_PATH, Model.class);
	    finishLoading();
	}

	public Model getModel(Class actor){
		if(actor == Mon1.class){
			return get(MONSTER_PATH, Model.class);
		}
		if(actor == Tile.class){
			return get(TILE_PATH, Model.class);
		}
		return null;
	}
}
