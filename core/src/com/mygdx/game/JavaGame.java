package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class JavaGame extends Game {
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
		camera.setToOrtho(false,16,9);
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();
		img = new Texture("badlogic.jpg");
		floor= new Wall(world,camera, 11 ,5, 4 , 2);
		ball = new Ball(world, 7, 11, 1f);



	}

	@Override
	public void render () {
		world.step(1/60f, 6,2 );
		ScreenUtils.clear(1, 0, 0, 1);
		debugRenderer.render(world,camera.combined);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// batch.draw(img, 0, 0, 3, 3);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}



