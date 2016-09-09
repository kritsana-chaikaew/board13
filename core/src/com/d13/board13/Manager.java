package com.d13.board13;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;

public class Manager{
	private CameraInputController camController;
	private InputManager inputManager;
	private InputMultiplexer inputMultiplexer;

 	private AssetsManager assetsManager;
	private Environment environment;
	private ModelBatch modelBatch;

	private Board board;

	public Camera camera;
	public ArrayList<Tile> tiles = new ArrayList<Tile>();
	public ArrayList<Character> characters = new ArrayList<Character>();

	public Manager () {
		setupInput();
		setupEnvironment();

		modelBatch = new ModelBatch();
		assetsManager = new AssetsManager();

		board = new Board(this);
		board.setup(assetsManager);
		setupCharactor();
	}

	public void setupCharactor () {
	  	Vector3 tilePosition = new Vector3();
	  	tilePosition = tiles.get(45).getPosition();
	  	tilePosition.y += 1.2f;

		Character character = new Mon1(assetsManager.getModel(Mon1.class));
	  	character.setPosition(tilePosition);
		characters.add(character);
	}

	private void setupInput () {
		camera = new Camera();
		camController = new CameraInputController(camera);

		inputManager = new InputManager(this);

		inputMultiplexer = new InputMultiplexer(inputManager, camController);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	private void setupEnvironment () {
		environment = new Environment();
		environment.set(new ColorAttribute(
				ColorAttribute.AmbientLight,
				0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().
				set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}

	public void render () {
		camController.update();

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		//real render
		modelBatch.begin(camera);
		for(int i=0; i<tiles.size(); i++){
			tiles.get(i).render(modelBatch, environment);
		}
		for(int i=0; i<characters.size(); i++){
			characters.get(i).render(modelBatch, environment);
		}
		modelBatch.end();
	}
}
