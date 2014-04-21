package ru.footmade.zombieninja;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class MyGdxGame implements ApplicationListener {
	private float scrW, scrH;
	
	private static final float VIEWPORT_WIDTH = 5;
	private static final float BODY_RELATIVE_WIDTH = 0.5f;
	private static final float GRAVITY = 3f;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private TextureAtlas atlas;
	private Ragdoll zombie;
	
	private World world;
	
	@Override
	public void create() {
		scrW = VIEWPORT_WIDTH;
		scrH = VIEWPORT_WIDTH * Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
		
		camera = new OrthographicCamera(scrW, scrH);
		camera.translate(scrW / 2, scrH / 2);
		camera.update();
		
		batch = new SpriteBatch();
		
		atlas = new TextureAtlas(Gdx.files.internal("img/pack.atlas"));
		
		world = new World(new Vector2(), false);
		createGround();
		createBody();
	}
	
	private void createBody() {
		BodyEditorLoader bodyLoader = new BodyEditorLoader(Gdx.files.internal("phys/body.json"));
		RagdollLoader ragdollLoader = new RagdollLoader(Gdx.files.internal("phys/ragdoll.json"), bodyLoader);
		zombie = ragdollLoader.addToWorld(world, new Vector2(scrW / 2, scrH / 2), atlas, BODY_RELATIVE_WIDTH);
	}
	
	private void createGround() {
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		
		ChainShape shape = new ChainShape();
		Vector2 bottomLeft = new Vector2(0, 0);
		Vector2 bottomRight = new Vector2(scrW, 0);
		Vector2 topLeft = new Vector2(0, scrH);
		Vector2 topRight = new Vector2(scrW, scrH);
		shape.createChain(new Vector2[] { bottomLeft, bottomRight, topRight, topLeft, bottomLeft });

		FixtureDef fd = new FixtureDef();
		fd.friction = 0.5f;
		fd.restitution = 0.5f;
		fd.shape = shape;

		world.createBody(bd).createFixture(fd);

		shape.dispose();
	}

	@Override
	public void dispose() {
		batch.dispose();
		atlas.dispose();
		world.dispose();
	}

	@Override
	public void render() {
		processInput();
		world.step(1/60f, 10, 10);
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		zombie.draw(batch);
        
		batch.end();
	}
	
	private void processInput() {
		float accelX = Gdx.input.getAccelerometerX();
		float accelY = Gdx.input.getAccelerometerY();
		float magnitude = (float) Math.sqrt(accelX * accelX + accelY * accelY) * GRAVITY / 10;
		float angle = (float) Math.atan2(accelX, accelY);
		world.setGravity(new Vector2((float) Math.cos(angle) * magnitude, -(float) Math.sin(angle) * magnitude));
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
