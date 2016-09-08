package com.d13.board13;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class Camera extends PerspectiveCamera{
	public Camera () {
		super(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		position.set(0f, 7f, 20f);
		this.lookAt(0, 0, 0);
		near = 1f;
		far = 300f;
		this.update();
	}

}
