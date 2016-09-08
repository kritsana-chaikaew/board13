package com.d13.board13;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class Mon1 extends Character{
    public Mon1(AssetManager assetManager){
        name = "mon1";
        modelInstance = new ModelInstance(assetManager.get(
                Path.getPathByName(name),
                Model.class));
        prepareModel();
    }
}
