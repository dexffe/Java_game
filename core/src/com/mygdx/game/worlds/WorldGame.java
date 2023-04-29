package com.mygdx.game.worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

import com.mygdx.game.objects.*;

public class WorldGame implements Screen {
    boolean pause;
    float w = 16;
    public static float h = 9;

    Texture texturePause;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera camera;
    Sprite sprite;
    SpriteBatch batch;
    Vector3 touch;
    TextButton btnPlay, btnSettings, btnExit;

    World world;

    Triangle triangle;
    Wall floor;
    Ball ball, buttonPause;
    Gear gear;
    Swing swing;

    public WorldGame() {
        world = new World(new Vector2(0, -10), false);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        batch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void show() {


        texturePause = new Texture(Gdx.files.internal("pause.png"));
        touch = new Vector3();

        floor = new Wall(world, 8, 1, 16, 0.5f);
        floor = new Wall(world, 1, 4.5f, 0.5f, 9);
        floor = new Wall(world, 15, 4.5f, 0.5f, 9);

        ball = new Ball(world, 12, 8, 0.5f, true);
        ball = new Ball(world, 11, 8, 0.5f, true);
        ball = new Ball(world, 10, 8, 0.5f, true);
        ball = new Ball(world, 9, 8, 0.5f, true);
        ball = new Ball(world, 8, 8, 0.5f, true);
        ball = new Ball(world, 7, 8, 0.5f, true);
        ball = new Ball(world, 6, 8, 0.5f, true);
        ball = new Ball(world, 5, 8, 0.5f, true);
        ball = new Ball(world, 4, 8, 0.5f, true);
        ball = new Ball(world, 3, 8, 0.5f, true);

        triangle = new Triangle(world, 7, 1.5f, new float[] {1f, 2, 2, 0, 0, 0});
        triangle = new Triangle(world, 1.5f, 4.5f, new float[] {0f, 1, 1, 0, 0, 0});
        triangle = new Triangle(world, 13f, 4.5f, new float[] {1, 1, 1, 0, 0, 0});

        gear = new Gear(world, 0f, 3, 3, true, 0.7f, 13, 35, 50);
        gear = new Gear(world, 0f, 13, 3, true, 0.7f, -13, 35 , 50);

        buttonPause = new Ball(world, 15.5f, 8.5f, 0.3f, false);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if (buttonPause.hit(touch.x, touch.y)) {
                pause();
            }
        }
        ScreenUtils.clear(0,0,0,1);
        if (!pause)world.step(1/60f,6,2);
        camera.update();
        debugRenderer.render(world,camera.combined);


        // Отрисовываем спрайт
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(texturePause,
                buttonPause.body.getPosition().x- buttonPause.r,
                buttonPause.body.getPosition().y- buttonPause.r,
                0, buttonPause.r*2, buttonPause.r*2, buttonPause.r*2,
                1,1,0,0,0,100,100,false,false);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        pause = !pause;
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        batch.dispose();
    }
}
