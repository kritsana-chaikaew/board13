package com.d13.board13;

import com.badlogic.gdx.Game;

public class Board13 extends Game {

	@Override
	public void create () {
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
	}
}
