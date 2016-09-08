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
	public static final String MONSTER_PATH = "objects/mon1/mon1.g3db";
	public static final String TILE_PATH = "objects/tile/tile.obj";
	
	private Environment environment;
	public PerspectiveCamera cam;
	private CameraInputController camController;
	
	private InputMultiplexer inputMultiplexer;
	
	public final AssetManager assetManager = new AssetManager();;

	private ModelBatch modelBatch;
	
	public Array<Tile> tiles = new Array<Tile>();
	public Array<Piece> pieces = new Array<Piece>();

	private Stage stage;
	private Label label;
	private BitmapFont font;
	private StringBuilder stringBuilder;

	public Board board;
	  
	public Manager () {
		setupCamera();
		camController = new CameraInputController(cam);
		setupAssetManager();
		board = new Board(this, assetManager);
		modelBatch = new ModelBatch();
		setupLabel();
		setupEnvironment();
		setInputMultiplexer();
	}
	
	public void setupAssetManager () {
	    assetManager.load(TILE_PATH, Model.class);
	    assetManager.load(MONSTER_PATH, Model.class);
	    assetManager.finishLoading();
	    Gdx.app.log("asd", assetManager.getQueuedAssets() + "");
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
	
	public void setupCamera () {
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 7f, 20f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();
	}
	
	public void render () {
		camController.update();

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
		modelBatch.render(tiles, environment);
		modelBatch.render(pieces, environment);
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
