package com.d13.board13;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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

	private Environment environment;
	private ModelBatch modelBatch;

	private Board board;
	private Camera camera;

	private AssetsManager assetsManager;
	private ArrayList<Actor> actors;

	public Manager () {
		setupInput();
		setupEnvironment();

		modelBatch = new ModelBatch();
		assetsManager = new AssetsManager();

		actors = new ArrayList<Actor>();
		board = new Board(this);
		board.setup(assetsManager);
	}

	public Camera getCamera(){
		return camera;
	}

	public ArrayList<Actor> getActors(){
		return actors;
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
		for(int i=0; i<actors.size(); i++){
			actors.get(i).render(modelBatch, environment);
		}
		modelBatch.end();
	}
}
