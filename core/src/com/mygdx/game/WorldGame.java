package com.mygdx.game;

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

import objects.Ball;
import objects.Gear;
import objects.StaticTriangle;
import objects.Swing;
import objects.Wall;

public class WorldGame implements Screen {
    JavaGame JG;

    Texture texture;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera camera;
    Sprite sprite;
    SpriteBatch batch;
    Vector3 touch;
    TextButton btnPlay, btnSettings, btnExit;

    World world;
    //SpriteBatch batch;
    //Sprite sprite;
    //OrthographicCamera camera;
    //Box2DDebugRenderer debugRenderer;

    StaticTriangle triangle;
    Wall floor;
    Ball ball;
    Gear gear;
    Swing swing;

    public WorldGame(JavaGame context) {
        JG = context;
        //imgBG = new Texture("winter1.jpg"); // фон
        //btnPlay = new TextButton(JG.fontLarge, "ИГРАТЬ", 600, 600);
        //btnSettings = new TextButton(JG.fontLarge, "НАСТРОЙКИ", 600, 500);
        //btnExit = new TextButton(JG.fontLarge, "ВЫХОД", 600, 400);
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -10), false);

        texture = new Texture(Gdx.files.internal("setings.png"));
        touch = new Vector3();

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

        triangle = new StaticTriangle(world, 7, 1.5f, new float[] {1f, 2, 2, 0, 0, 0});
        triangle = new StaticTriangle(world, 1.5f, 4.5f, new float[] {0f, 1, 1, 0, 0, 0});
        triangle = new StaticTriangle(world, 13f, 4.5f, new float[] {1, 1, 1, 0, 0, 0});

        gear = new Gear(world, 0f, 3, 3, true, 0.7f, 13, 35, 50);
        gear = new Gear(world, 0f, 13, 3, true, 0.7f, -13, 35 , 50);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            JG.camera.unproject(touch);
            if (ball.hit(touch.x, touch.y)) {
                JG.setScreen(JG.worldSettings);
            }
        }
        ScreenUtils.clear(0,0,0,1);
        world.step(1/60f,6,2);
        //JG.camera.update();
        JG.debugRenderer.render(world,JG.camera.combined);


        // Отрисовываем спрайт
        JG.batch.setProjectionMatrix(JG.camera.combined);
        JG.batch.begin();
        JG.batch.draw(texture,
                ball.body.getPosition().x-ball.r,
                ball.body.getPosition().y-ball.r,
                0,ball.r*2,ball.r*2,ball.r*2,
                1,1,0,0,0,100,100,false,false);
        JG.batch.end();
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
