package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.Levels.*;
import com.mygdx.game.worlds.WorldAbout;
import com.mygdx.game.worlds.WorldGame;
import com.mygdx.game.worlds.WorldsMenu;

public class JavaGame extends Game {

	public static float width = 16;
	public static float height = 9;

	public SpriteBatch batch;
	public OrthographicCamera camera;
	public Box2DDebugRenderer debugRenderer, debugRenderer2;
	public Vector3 touch;

	public WorldAbout worldAbout;
	public WorldGame worldGame;
	public WorldsMenu worldsMenu;



	public level1 level1;
	public level2 level2;
	public level3 level3;



	@Override
	public void create() {

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		touch = new Vector3();

		//camera.setToOrtho(false, width, height);
		debugRenderer2 = new Box2DDebugRenderer();
		debugRenderer = new Box2DDebugRenderer();

		worldsMenu = new WorldsMenu(this);
		//worldAbout = new WorldAbout(this);
		worldGame = new WorldGame();

		level1 = new level1(this);
		level2 = new level2(this);
		level3 = new level3(this);

		setScreen(worldsMenu);
	}



	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		debugRenderer2.dispose();
		debugRenderer.dispose();
		batch.dispose();
	}
}





