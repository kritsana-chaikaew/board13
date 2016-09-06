package com.d13.board13;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public final class Tile extends ModelInstance {
  private int index;
  private int type;
  private boolean isEmpty = true;

  private final float radius;
  private final Vector3 center = new Vector3();
  private final Vector3 dimensions = new Vector3();
  private final static BoundingBox bounds = new BoundingBox();

  public Tile (Model model) {
    super(model);
    calculateBoundingBox(bounds);
    bounds.getCenter(center);
    bounds.getDimensions(dimensions);
    radius = dimensions.len() / 2f;
  }


  public int getIndex () {
    return index;
  }

  public void setIndex (int value) {
    index = value;
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

  public Vector3 getCenter () {
    return center;
  }

  public float getRadius () {
    return radius;
  }
}
