package com.d13.board13;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import com.badlogic.gdx.Gdx;

public class Actor{
    protected ModelInstance modelInstance;
    Material modelMaterial;

    private float radius;
    private Vector3 center = new Vector3();
    private Vector3 dimension = new Vector3();

    public void render(ModelBatch modelBatch, Environment environment){
        modelBatch.render(modelInstance, environment);
    }

    public void toggleSelection(){
        Material material = modelInstance.materials.get(0);

        if(material.same(modelMaterial)){
            material.clear();
            material.set(ColorAttribute.createDiffuse(Color.ORANGE));
        } else{
            material.clear();
            material.set(modelMaterial);
        }
    }

    public Vector3 getPosition(){
        Vector3 position = new Vector3();
        modelInstance.transform.getTranslation(position);
        return position;
    }

    public void setPosition(Vector3 position) {
        modelInstance.transform.setToTranslation(position);
    }

    public Vector3 getCenter () {
      return center;
    }

    public float getRadius () {
      return radius;
    }

    public void prepareModel(){
        //calculate objects body
        BoundingBox boundingBox = new BoundingBox();
        modelInstance.calculateBoundingBox(boundingBox);
        boundingBox.getCenter(center);
        boundingBox.getDimensions(dimension);
        radius = dimension.len() / 2f;

        //setup material
        modelMaterial = new Material();
        modelMaterial.set(modelInstance.materials.get(0));
    }
}
