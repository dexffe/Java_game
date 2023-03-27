package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class JavaGame extends ApplicationAdapter {

	SpriteBatch batch;
	OrthographicCamera camera;
	Texture img;
	World world;
	Box2DDebugRenderer debugRenderer;

	Wall floor;
	Ball ball;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 16, 9);
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();
		img = new Texture("badlogic.jpg");

		floor = new Wall(world, 8, 1, 16, 0.5f);
		floor = new Wall(world, 1, 4.5f, 0.5f, 9);
		floor = new Wall(world, 15, 4.5f, 0.5f, 9);

		ball = new Ball(world, 8, 8, 0.5f);
		ball = new Ball(world, 7, 8, 0.7f);
		ball = new Ball(world, 6, 8, 0.5f);
		ball = new Ball(world, 5, 8, 0.7f);
		ball = new Ball(world, 9, 8, 0.5f);
		ball = new Ball(world, 3, 8, 0.7f);
	}

	@Override
	public void render () {
		world.step(1/60f, 6, 2);
		ScreenUtils.clear(0, 0, 0, 1);
		debugRenderer.render(world, camera.combined);
		camera.update();

		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		//batch.draw(img, 0, 0, 3, 3);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}





