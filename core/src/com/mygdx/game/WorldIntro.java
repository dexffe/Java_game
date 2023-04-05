package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

import objects.Ball;
import objects.Gear;
import objects.StaticTriangle;
import objects.Swing;
import objects.Wall;

public class WorldIntro implements Screen {
    JavaGame JG;

    Texture imgBG;
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

    public WorldIntro(JavaGame context) {
        JG = context;
        //imgBG = new Texture("winter1.jpg"); // фон
        //btnPlay = new TextButton(JG.fontLarge, "ИГРАТЬ", 600, 600);
        //btnSettings = new TextButton(JG.fontLarge, "НАСТРОЙКИ", 600, 500);
        //btnExit = new TextButton(JG.fontLarge, "ВЫХОД", 600, 400);
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -10), false);

        floor = new Wall(world, 8, 1, 16, 0.5f);
        floor = new Wall(world, 1, 4.5f, 0.5f, 9);
        floor = new Wall(world, 15, 4.5f, 0.5f, 9);

        ball = new Ball(world, 12, 8, 0.5f);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        world.step(1/60f, 6, 2);
        JG.camera.update();
        JG.debugRenderer.render(world, JG.camera.combined);
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

    }
}
