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
import com.mygdx.game.objects.Ball;
import com.mygdx.game.objects.Box;
import com.mygdx.game.objects.Gear;
import com.mygdx.game.objects.Swing;
import com.mygdx.game.objects.Triangle;
import com.mygdx.game.objects.Wall;

import java.util.HashMap;
import java.util.Map;

public class WorldIntro implements Screen {
    JavaGame JG;

    Texture textureLevel, textureSettings, textureAbout;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera camera;
    Sprite sprite;
    SpriteBatch batch;
    Vector3 touch;
    TextButton btnPlay, btnSettings, btnExit;

    Map<Texture, Ball> ListTextureBall = new HashMap<Texture, Ball>();
    World world;

    Triangle triangle;
    Wall floor;
    Ball buttonSettings, buttonLevel, buttonAbout;
    Gear gear;
    Swing swing;
    Box box;

    public WorldIntro(JavaGame context) {
        JG = context;
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -10), false);

        textureSettings = new Texture(Gdx.files.internal("settings.png"));
        textureLevel = new Texture(Gdx.files.internal("start.png"));
        textureAbout= new Texture(Gdx.files.internal("skelet.png"));
        touch = new Vector3();

        floor = new Wall(world, 8, 0, 16, 0f);
        floor = new Wall(world, 8, 9, 16, 0f);
        floor = new Wall(world, 2, 8, 4, 0f);
        floor = new Wall(world, 7.5f, 9, 0, 1f);

        buttonLevel = new Ball(world, 8, 3.5f, 1f, false);
        buttonSettings = new Ball(world, 6, 3.5f, 0.4f, true);
        buttonAbout = new Ball(world, 10, 3.5f, 0.4f, false);

        box = new Box(world, 2, 7, 2, 0.5f, true);

        for (int i = 1; i < 20; i++) {
            gear = new Gear(world, 0, i, 6, true, 0.3f, -3, 35 , 50);
        }


        ListTextureBall.put(textureLevel, buttonLevel);
        ListTextureBall.put(textureSettings, buttonSettings);
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
        JG.debugRenderer.render(world,JG.camera.combined);


    // Отрисовываем спрайт
        JG.batch.setProjectionMatrix(JG.camera.combined);
        JG.batch.begin();
        for (Texture i : ListTextureBall.keySet()) {
            JG.batch.draw(i,
                    ListTextureBall.get(i).body.getPosition().x- ListTextureBall.get(i).r,
                    ListTextureBall.get(i).body.getPosition().y- ListTextureBall.get(i).r,
                    0, ListTextureBall.get(i).r*2, ListTextureBall.get(i).r*2, ListTextureBall.get(i).r*2,
                    1,1,0,0,0,100,100,false,false);
        }
        /*JG.batch.draw(textureSettings,
                buttonSettings.body.getPosition().x- buttonSettings.r,
                buttonSettings.body.getPosition().y- buttonSettings.r,
                0, buttonSettings.r*2, buttonSettings.r*2, buttonSettings.r*2,
                1,1,0,0,0,100,100,false,false);
        JG.batch.draw(textureLevel,
                buttonLevel.body.getPosition().x- buttonLevel.r,
                buttonLevel.body.getPosition().y- buttonLevel.r,
                0, buttonLevel.r*2, buttonLevel.r*2, buttonLevel.r*2,
                1,1,0,0,0,100,100,false,false);
        JG.batch.draw(textureAbout,
                buttonAbout.body.getPosition().x- buttonAbout.r,
                buttonAbout.body.getPosition().y- buttonAbout.r,
                0, buttonAbout.r*2, buttonAbout.r*2, buttonAbout.r*2,
                1,1,0,0,0,100,100,false,false);*/
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
