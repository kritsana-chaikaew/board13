package com.d13.board13;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class Tile extends Actor {
  private int type;
  private boolean isEmpty = true;

  public Tile (AssetManager assetManager) {
      modelInstance = new ModelInstance(assetManager.get(
              Path.getPathByName("tile"),
              Model.class));
      prepareModel();
  }

  public int getType () {
    return type;
  }

  public void setType (int value) {
    type = value;
  }

  public boolean isEmpty () {
    return isEmpty;
  }

  public void setIsEmpty (boolean value) {
    isEmpty = value;
  }

}
