package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class JavaGame extends Game {

	public static float w = 16;
	public static float h = 9;

	SpriteBatch batch;
	OrthographicCamera camera;
	World world;
	Box2DDebugRenderer debugRenderer;

	WorldIntro worldIntro;
	WorldGame worldGame;
	WorldLevel worldLevel;
	WorldSettings worldSettings;



	@Override
	public void create() {

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		debugRenderer = new Box2DDebugRenderer();

		worldIntro = new WorldIntro(this);
		worldLevel = new WorldLevel(this);
		worldGame = new WorldGame(this);
		worldSettings = new WorldSettings(this);

		setScreen(worldIntro);
	}



	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
		batch.dispose();
	}
}





