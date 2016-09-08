package com.d13.board13;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public final class Piece extends ModelInstance {
  private int onTile;

  private final float radius;
  private final Vector3 center = new Vector3();
  private final Vector3 dimensions = new Vector3();
  private final static BoundingBox bounds = new BoundingBox();

  public Piece (Model model) {
    super(model);
    calculateBoundingBox(bounds);
    bounds.getCenter(center);
    bounds.getDimensions(dimensions);
    radius = dimensions.len() / 2f;
  }

  public int getOnTile () {
    return onTile;
  }

  public void setOnTile (Tile tile) {
    Vector3 tilePosition = new Vector3();
    this.onTile = tile.getIndex();
    tile.transform.getTranslation(tilePosition);
    tilePosition.y += 1.2f;
    this.transform.setToTranslation(tilePosition);
  }

  public Vector3 getCenter () {
    return center;
  }

  public float getRadius () {
    return radius;
  }
}
