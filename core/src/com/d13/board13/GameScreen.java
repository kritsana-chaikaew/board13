package com.d13.board13;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;

public class GameScreen implements Screen {
	private Manager manager;

	public GameScreen (final Game game) {
		manager = new Manager();
	}

	public void render (float delta) {
		manager.render();
	}

	public void dispose () {
	}

	public void resize (int width, int height) {
	}

	public void hide () {
	}

	public void resume () {
	}

	public void pause () {
	}

	public void show () {
	}
}
