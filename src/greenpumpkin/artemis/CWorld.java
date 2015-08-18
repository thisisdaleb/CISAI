package greenpumpkin.artemis;

import com.artemis.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import box2dLight.RayHandler;

public class CWorld extends World {
	private static com.badlogic.gdx.physics.box2d.World boxWorld;
	public static final int numRays = 16; //how many rays are emitted for shadow casting
	public static final float lightDistance = 64f; // distance light goes
	public static RayHandler rayHandler; //the main object of light2d, heavily important
	public static OrthographicCamera camera;
	public static SpriteBatch batch;
	public static char[][] area = new char[25][25];
	static Body body;
	static Body body2;
	
	public static void init() {
		initCamera();
		initBox();
		initWall();
		initRayHandler();
		initBatch();
		ASCII.initiateMaps();
		Player.loadSave();
	}

	private static void initCamera() {
		camera = new OrthographicCamera(950,720);
		camera.position.set(475, 360, 0);
		camera.update(true);
	}

	@SuppressWarnings("unused")
	private static void initBox() {
		boxWorld = new com.badlogic.gdx.physics.box2d.World(new Vector2(0, -98f), true);
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(300, 400);
		body = boxWorld.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(400, 310);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;
		Fixture fixture = body.createFixture(fixtureDef);
		shape.dispose();
	}
	
	@SuppressWarnings("unused")
	private static void initWall() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(695, 400);
		body = boxWorld.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(5, 310);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;
		Fixture fixture = body.createFixture(fixtureDef);
		shape.dispose();
	}
	
	private static void initRayHandler() {
		RayHandler.useDiffuseLight(true);
		rayHandler = new RayHandler(boxWorld);
		//rayHandler = new RayHandler(null);
		rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.setAmbientLight(0f, 0f, 0f, 1f);
		rayHandler.setCulling(true);
		rayHandler.setBlurNum(0);
		rayHandler.setShadows(true);
	}
	
	private static void initBatch() {
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
	}

	private static void saveGame() {
		// TODO Auto-generated method stub
		
	}
	
	public static void flushRayHandler() {
		rayHandler.removeAll();
	}

	public static void exit() {
		saveGame();
		System.exit(0);
	}
}
