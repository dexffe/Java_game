package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.worlds.WorldGame;
import com.mygdx.game.worlds.WorldIntro;
import com.mygdx.game.worlds.WorldLevel;
import com.mygdx.game.worlds.WorldSettings;

public class JavaGame extends Game {

	public static float w = 16;
	public static float h = 9;

	public SpriteBatch batch;
	public OrthographicCamera camera;
	public Box2DDebugRenderer debugRenderer;

	public WorldIntro worldIntro;
	public WorldGame worldGame;
	public WorldLevel worldLevel;
	public WorldSettings worldSettings;



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

		setScreen(worldLevel);
	}



	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		debugRenderer.dispose();
		batch.dispose();
	}
}





