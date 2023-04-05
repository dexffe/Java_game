package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import objects.Ball;
import objects.Gear;
import objects.StaticTriangle;
import objects.Swing;
import objects.Wall;

public class JavaGame extends Game {

	public static float w = 16;
	public static float h = 9;

	SpriteBatch batch;
	Sprite sprite;
	OrthographicCamera camera;
	World world;
	Box2DDebugRenderer debugRenderer;

	StaticTriangle triangle;
	Wall floor;
	Ball ball;
	Gear gear;
	Swing swing;

	WorldIntro worldIntro;
	WorldGame worldGame;
	WorldSettings worldSettings;



	@Override
	public void create() {

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		world = new World(new Vector2(0, -10), false);
		debugRenderer = new Box2DDebugRenderer();

		worldIntro = new WorldIntro(this);
		//screenGame = new WorldGame(this);
		//screenSettings = new WorldSettings(this);
		setScreen(worldIntro);

		//swing = new Swing(world, 5, 5);
		/*floor = new Wall(world, 8, 1, 16, 0.5f);
		floor = new Wall(world, 1, 4.5f, 0.5f, 9);
		floor = new Wall(world, 15, 4.5f, 0.5f, 9);

		ball = new Ball(world, 12, 8, 0.5f);
		ball = new Ball(world, 11, 8, 0.5f);
		ball = new Ball(world, 10, 8, 0.5f);
		ball = new Ball(world, 9, 8, 0.5f);
		ball = new Ball(world, 8, 8, 0.5f);
		ball = new Ball(world, 7, 8, 0.5f);
		ball = new Ball(world, 6, 8, 0.5f);
		ball = new Ball(world, 5, 8, 0.5f);
		ball = new Ball(world, 4, 8, 0.5f);
		ball = new Ball(world, 3, 8, 0.5f);

		triangle = new StaticTriangle(world, 7, 1.5f, new float[] {1f, 2, 2, 0, 0, 0});
		triangle = new StaticTriangle(world, 1.5f, 4.5f, new float[] {0f, 1, 1, 0, 0, 0});
		triangle = new StaticTriangle(world, 13f, 4.5f, new float[] {1, 1, 1, 0, 0, 0});

		gear = new Gear(world, 0f, 3, 3, true, 0.7f, 13, 35, 50);
		gear = new Gear(world, 0f, 13, 3, true, 0.7f, -13, 35 , 50);*/
	}



	@Override
	public void render() {
		super.render();
		/*
		ScreenUtils.clear(0, 0, 0, 1);
		world.step(1/60f, 6, 2);
		camera.update();
		debugRenderer.render(world, camera.combined);
		*/

	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
		batch.dispose();
	}
}





