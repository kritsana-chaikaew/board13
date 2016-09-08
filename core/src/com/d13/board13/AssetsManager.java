package com.d13.board13;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;

public class AssetsManager extends AssetManager{
	public static final String MONSTER_PATH = "objects/mon1/mon1.g3db";
	public static final String TILE_PATH = "objects/tile/tile.obj";
	public AssetsManager () {
		this.load(TILE_PATH, Model.class);
	    this.load(MONSTER_PATH, Model.class);
	    this.finishLoading();
	    Gdx.app.log("asd", this.getQueuedAssets() + "");
	}
}
