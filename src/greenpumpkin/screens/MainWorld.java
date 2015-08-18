package greenpumpkin.screens;

import greenpumpkin.artemis.CWorld;
import greenpumpkin.artemis.systems.*;
import greenpumpkin.game.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

////////////////////////////////////////////////////////////
//This is the Artemis test screen. It is useful for/////////
//learning how Artemis works.///////////////////////////////
////////////////////////////////////////////////////////////

public class MainWorld implements Screen {
	private Stage stage = new Stage();
	private CWorld world;
	public static float time;

	@Override
	public void show() {
		world = new CWorld();

		CWorld.init();

		world.setSystem(new ControllerInputS());
		world.setSystem(new BatchRendererS());
		world.setSystem(new ASCIILightS());
		world.setSystem(new LightS());
		world.initialize();
	}

	@Override
	public void render(float delta) {
		if (delta < 0.2f) {
			time=delta;
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			CWorld.camera.update();
			world.setDelta(delta);
			world.process();
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().setCamera(
				new VirtualResolution(CISAI.WIDTH, CISAI.HEIGHT));
		CWorld.batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
