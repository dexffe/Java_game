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

import objects.*;

public class WorldIntro implements Screen {
    JavaGame JG;

    Texture textureLevel, textureSettings;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera camera;
    Sprite sprite;
    SpriteBatch batch;
    Vector3 touch;
    TextButton btnPlay, btnSettings, btnExit;

    World world;

    StaticTriangle triangle;
    Wall floor;
    Ball buttonSettings, buttonLevel;
    Gear gear;
    Swing swing;

    public WorldIntro(JavaGame context) {
        JG = context;
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -10), false);

        textureSettings = new Texture(Gdx.files.internal("setings.png"));
        textureLevel = new Texture(Gdx.files.internal("mapMenung.png"));
        touch = new Vector3();

        floor = new Wall(world, 8, 1, 16, 0.5f);
        floor = new Wall(world, 1, 4.5f, 0.5f, 9);
        floor = new Wall(world, 15, 4.5f, 0.5f, 9);

        buttonSettings = new Ball(world, 10, 8, 0.5f, true);
        buttonLevel = new Ball(world, 3, 8, 0.5f, false);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            JG.camera.unproject(touch);
            if (buttonSettings.hit(touch.x, touch.y)) {
                JG.setScreen(JG.worldSettings);
            }
            if (buttonLevel.hit(touch.x, touch.y)) {
                JG.setScreen(JG.worldLevel);
            }
        }
        ScreenUtils.clear(0,0,0,1);
        world.step(1/60f,6,2);
        //JG.camera.update();
        JG.debugRenderer.render(world,JG.camera.combined);


    // Отрисовываем спрайт
        JG.batch.setProjectionMatrix(JG.camera.combined);
        JG.batch.begin();
        JG.batch.draw(textureSettings,
            buttonSettings.body.getPosition().x- buttonSettings.r,
            buttonSettings.body.getPosition().y- buttonSettings.r,
            0, buttonSettings.r*2, buttonSettings.r*2, buttonSettings.r*2,
            1,1,0,0,0,100,100,false,false);
        JG.batch.draw(textureLevel,
                buttonLevel.body.getPosition().x- buttonLevel.r,
                buttonLevel.body.getPosition().y- buttonLevel.r,
                0, buttonLevel.r*2, buttonLevel.r*2, buttonLevel.r*2,
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
