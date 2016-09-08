package com.d13.board13;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.math.Vector3;

public class Actor{
    protected ModelInstance modelInstance;

    public void render(ModelBatch modelBatch, Environment environment){
        modelBatch.render(modelInstance, environment);
    }

    public void setPosition(Vector3 position) {
      modelInstance.transform.setToTranslation(position);
    }
}
