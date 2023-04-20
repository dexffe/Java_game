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
import com.mygdx.game.JavaGame;
import com.mygdx.game.objects.Arc;
import com.mygdx.game.objects.Ball;
import com.mygdx.game.objects.Gear;
import com.mygdx.game.objects.Swing;
import com.mygdx.game.objects.Triangle;
import com.mygdx.game.objects.Wall;


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

    Triangle triangle;
    Wall floor;
    Ball buttonIntro, buttonGame;
    Gear gear;
    Swing swing;
    Arc arc;

    public WorldLevel(JavaGame context) {
        JG = context;
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -10), false);

        textureIntro = new Texture(Gdx.files.internal("return.png"));
        textureGame = new Texture(Gdx.files.internal("start.png"));
        touch = new Vector3();

        floor = new Wall(world, 8, 0, 16, 0f);
        floor = new Wall(world, 8, 9, 16, 0f);
        floor = new Wall(world, 16, 4.5f, 0f, 9);

        buttonIntro = new Ball(world, 15, 1, 0.5f, false);
        buttonGame = new Ball(world, 2, 3, 0.5f, false);

        float h = 6f;
        float count = 0;
        for (int i = 0; i < 12; i++) {
            gear = new Gear(world, 0, i, h, true, 0.3f, -3, 35 , 50);
            if (h != 7f && count >= 4 && count <= 8) h += 0.2f;
            count += 1;
        }

        arc = new Arc(world, 11f, 5f, 30, 0f, -1.5f, 5f);

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            JG.camera.unproject(touch);
            if (buttonIntro.hit(touch.x, touch.y)) {
                JG.setScreen(JG.worldIntro);
            }
            if (buttonGame.hit(touch.x, touch.y)) {
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
            buttonIntro.body.getPosition().x- buttonIntro.r,
            buttonIntro.body.getPosition().y- buttonIntro.r,
            0, buttonIntro.r*2, buttonIntro.r*2, buttonIntro.r*2,
            1,1,0,0,0,100,100,false,false);
        JG.batch.draw(textureGame,
                buttonGame.body.getPosition().x- buttonGame.r,
                buttonGame.body.getPosition().y- buttonGame.r,
                0, buttonGame.r*2, buttonGame.r*2, buttonGame.r*2,
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