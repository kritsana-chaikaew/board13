package com.d13.board13;

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

public class Manager {

	private Environment environment;
	public Camera camera;
	private CameraInputController camController;

	private InputMultiplexer inputMultiplexer;

	public final AssetsManager assetsManager;

	private ModelBatch modelBatch;

	public Array<Tile> tiles = new Array<Tile>();
	public Array<Character> characters = new Array<Character>();

	private Stage stage;
	private Label label;
	private BitmapFont font;
	private StringBuilder stringBuilder;

	public Board board;

	public Manager () {
		camera = new Camera();
		camController = new CameraInputController(camera);
		assetsManager = new AssetsManager();
		board = new Board(this, assetsManager);
		modelBatch = new ModelBatch();
		setupLabel();
		setupEnvironment();
		setInputMultiplexer();
	}

	public void setInputMultiplexer () {
		inputMultiplexer = new InputMultiplexer(board, camController);
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
		modelBatch.render(tiles, environment);
		for(int i=0; i<characters.size; i++){
			characters.get(i).render(modelBatch, environment);
		}
		modelBatch.end();

		stringBuilder.setLength(0);
		stringBuilder.append(" Selected: ").append(board.selected);
		if (board.selected >= 0 && board.selected < 100) {
			stringBuilder.append(" isEmpty: ").append(tiles.get(board.selected).isEmpty());
			stringBuilder.append(" Type: ").append(tiles.get(board.selected).getType());
		}
		label.setText(stringBuilder);
		stage.draw();

	}
}
