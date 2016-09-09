package com.d13.board13;

import com.badlogic.gdx.graphics.g3d.Model;

public class Tile extends Actor {
    private int type;
    private boolean isEmpty = true;

    public Tile (Model model) {
        super(model);
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
