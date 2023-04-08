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

public class WorldLevel implements Screen {
    JavaGame JG;

    Texture textureIntro, textureGame;
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
    Ball ballIntro, ballGame;
    Gear gear;
    Swing swing;

    public WorldLevel(JavaGame context) {
        JG = context;
        //imgBG = new Texture("winter1.jpg"); // фон
        //btnPlay = new TextButton(JG.fontLarge, "ИГРАТЬ", 600, 600);
        //btnSettings = new TextButton(JG.fontLarge, "НАСТРОЙКИ", 600, 500);
        //btnExit = new TextButton(JG.fontLarge, "ВЫХОД", 600, 400);
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -10), false);

        textureIntro = new Texture(Gdx.files.internal("return.png"));
        textureGame = new Texture(Gdx.files.internal("start.png"));
        touch = new Vector3();

        floor = new Wall(world, 8, 1, 16, 0.5f);
        floor = new Wall(world, 1, 4.5f, 0.5f, 9);
        floor = new Wall(world, 15, 4.5f, 0.5f, 9);

        ballIntro = new Ball(world, 10, 8, 0.5f);
        ballGame  = new Ball(world, 2, 8, 0.5f);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            JG.camera.unproject(touch);
            if (ballIntro.hit(touch.x, touch.y)) {
                JG.setScreen(JG.worldIntro);
            }
            if (ballGame.hit(touch.x, touch.y)) {
                JG.setScreen(JG.worldGame);
            }
        }
        ScreenUtils.clear(0,0,0,1);
        world.step(1/60f,6,2);
        //JG.camera.update();
        JG.debugRenderer.render(world,JG.camera.combined);


    // Отрисовываем спрайт
        JG.batch.setProjectionMatrix(JG.camera.combined);
        JG.batch.begin();
        JG.batch.draw(textureIntro,
            ballIntro.body.getPosition().x- ballIntro.r,
            ballIntro.body.getPosition().y- ballIntro.r,
            0, ballIntro.r*2, ballIntro.r*2, ballIntro.r*2,
            1,1,0,0,0,100,100,false,false);
        JG.batch.draw(textureGame,
                ballGame.body.getPosition().x- ballGame.r,
                ballGame.body.getPosition().y- ballGame.r,
                0, ballGame.r*2, ballGame.r*2, ballGame.r*2,
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
