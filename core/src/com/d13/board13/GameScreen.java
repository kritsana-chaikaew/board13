package com.d13.board13;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.assets.AssetManager;

public class GameScreen extends InputAdapter implements Screen {

  private Environment environment;
  public PerspectiveCamera cam;
  private CameraInputController camController;

	private ModelBatch modelBatch;

  private Stage stage;
	private Label label;
	private BitmapFont font;
	private StringBuilder stringBuilder;

  private BoardManager board;


  public GameScreen (final Game game) {
    stage = new Stage();
		font = new BitmapFont();
		label = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
		stage.addActor(label);
		stringBuilder = new StringBuilder();

    modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

    cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 7f, 20f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

    board = new BoardManager(this);

    camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(new InputMultiplexer(board, camController));
  }

  public void render (float delta) {
    if (board.loadingAsset && board.assets.update())
      board.doneLoading();
    camController.update();

    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
    modelBatch.render(board.tiles, environment);
    modelBatch.render(board.pieces, environment);
    modelBatch.end();

    stringBuilder.setLength(0);
	stringBuilder.append(" Selected: ").append(board.selected);
    if (board.selected >= 0 && board.selected < 100) {
      stringBuilder.append(" isEmpty: ").append(board.tiles.get(board.selected).isEmpty());
      stringBuilder.append(" Type: ").append(board.tiles.get(board.selected).getType());
    }
		label.setText(stringBuilder);
		stage.draw();
  }

  public void dispose () {
    modelBatch.dispose();
    board.dispose();
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
