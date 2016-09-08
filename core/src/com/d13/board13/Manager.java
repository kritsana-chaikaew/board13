package com.d13.board13;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.d13.board13.Actor.OnActorClickListener;

public class Manager implements OnActorClickListener {

	private Environment environment;
	public Camera camera;
	private CameraInputController camController;

	private InputMultiplexer inputMultiplexer;

	public final AssetsManager assetsManager;

	private ModelBatch modelBatch;

	public ArrayList<Tile> tiles = new ArrayList<Tile>();
	public ArrayList<Character> characters = new ArrayList<Character>();
	
	private Stage stage;
	private Label label;
	private BitmapFont font;
	private StringBuilder stringBuilder;

	public Board board;
	
	public InputManager inputManager;

	public Manager () {
		camera = new Camera();
		camController = new CameraInputController(camera);
		assetsManager = new AssetsManager();
		board = new Board(this, assetsManager);
		modelBatch = new ModelBatch();
		inputManager = new InputManager(this);
		setupLabel();
		setupEnvironment();
		setInputMultiplexer();
	}
	
	public void onActorClicked () {
		
	}

	public void setInputMultiplexer () {
		inputMultiplexer = new InputMultiplexer(inputManager, camController);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	public void setupEnvironment () {
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

	}

	public void setupLabel () {
		stage = new Stage();
		font = new BitmapFont();
		label = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
		stage.addActor(label);
		stringBuilder = new StringBuilder();
	}

	public void render () {
		camController.update();

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(camera);
		for(int i=0; i<tiles.size(); i++){
			tiles.get(i).render(modelBatch, environment);
		}
		for(int i=0; i<characters.size(); i++){
			characters.get(i).render(modelBatch, environment);
		}
		modelBatch.end();

		stage.draw();

	}
}
