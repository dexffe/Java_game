package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class JavaGame extends ApplicationAdapter {

	SpriteBatch batch;
	OrthographicCamera camera;
	World world;
	Box2DDebugRenderer debugRenderer;

	Static_triangle triangle;
	Wall floor;
	Ball ball;
	Gear gear;



	@Override
	public void create() {
		float w = 16;
		float h = 9;
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		world = new World(new Vector2(0, -10), false);
		debugRenderer = new Box2DDebugRenderer();

		floor = new Wall(world, 8, 1, 16, 0.5f);
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



		triangle = new Static_triangle(world, 7, 1.5f, new float[] {1f, 2, 2, 0, 0, 0});
		triangle = new Static_triangle(world, 1.5f, 4.5f, new float[] {0f, 1, 1, 0, 0, 0});
		triangle = new Static_triangle(world, 13f, 4.5f, new float[] {1, 1, 1, 0, 0, 0});

		gear = new Gear(world, 0f, 3, 3, true, 0.7f, 13, 35, 50);
		gear = new Gear(world, 0f, 13, 3, true, 0.7f, -13, 35 , 50);

	}



	@Override
	public void render() {
		ScreenUtils.clear(1, 0, 0, 1);
		world.step(1/60f, 6, 2);
		ScreenUtils.clear(0, 0, 0, 1);
		//gear.cameraUpdate(camera, Gdx.graphics.getDeltaTime());
		camera.update();
		//Gdx.gl.glClearColor(0, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		debugRenderer.render(world, camera.combined);
		//b2dr.render(world, camera.combined.scl(20));

	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
	}
}





