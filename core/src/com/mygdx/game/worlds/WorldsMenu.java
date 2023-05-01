package com.mygdx.game.worlds;

import static com.mygdx.game.JavaGame.h;
import static com.mygdx.game.JavaGame.w;

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
import com.mygdx.game.objects.Box;
import com.mygdx.game.objects.Gear;
import com.mygdx.game.objects.ImpulseBox;
import com.mygdx.game.objects.Swing;
import com.mygdx.game.objects.Triangle;
import com.mygdx.game.objects.Wall;

import java.util.HashMap;
import java.util.Map;

public class WorldsMenu  implements Screen {
    JavaGame JG;

    Texture textureGoIntroInSettings, tBg;
    Texture textureLevelInIntro, textureSettingsInIntro, textureAboutInIntro;
    Texture textureIntroInLevel, textureGameInLevel;

    Box2DDebugRenderer debugRenderer;
    OrthographicCamera camera;
    Sprite sprite;
    SpriteBatch batch;
    Vector3 touch;
    World world;
    TextButton btnPlay, btnSettings, btnExit;
    public Map<Texture, Ball> ListTextureBall = new HashMap<Texture, Ball>();


    Triangle triangle;
    Wall floor;
    public Ball ballGoIntroInSettings, ball, ball1;
    public Ball buttonSettingsInIntro, buttonLevelInIntro, buttonAboutInIntro;
    public Ball buttonIntroInLevel, buttonGameInLevel;
    Gear gear;
    Swing swing;
    Arc arc;
    Box box;
    ImpulseBox impulseBox;
    float x;



    public WorldsMenu(JavaGame context) {
        JG = context;
        world = new World(new Vector2(0, -10), false);
        touch = new Vector3();
    }

    @Override
    public void show() {

        // Settings objects
        textureGoIntroInSettings = new Texture(Gdx.files.internal("return.png"));
        floor = new Wall(world, 8, 0, 16, 0f);
        floor = new Wall(world, 8, 9, 16, 0f);
        floor = new Wall(world, 9.6f, 8, 8, 0f);
        floor = new Wall(world, 0, 4.5f, 0f, 9);
        floor = new Wall(world, 1, 4.2f, 0f, 3.2f);
        ballGoIntroInSettings = new Ball(world, 3, 7, 0.4f, false);
        ball = new Ball(world, 5f, 1.3f, 0.2f, true);
        ball = new Ball(world, 5f, 1.7f, 0.2f, true);
        ball = new Ball(world, 5f, 2.1f, 0.2f, true);
        arc = new Arc(world, 1.7f, 7.3f, 10, 1.7f, 3f, 0.7f);
        arc = new Arc(world, 1f, 8f, 10, 1.7f, 3f, 1f);
        arc = new Arc(world, 1f, 1f, 10, -1.7f, -3f, 1f);
        impulseBox = new ImpulseBox(world, 5, 0.5f, 2.5f, 0.5f, ball.body, "Left");
        impulseBox = new ImpulseBox(world, 0f, 0.5f, 0.3f, 2.5f, ball.body, "Up");
        ListTextureBall.put(textureGoIntroInSettings, ballGoIntroInSettings);

        //Intro objects
        x = 16;
        tBg = new Texture("background.png");
        textureSettingsInIntro = new Texture(Gdx.files.internal("settings.png"));
        textureLevelInIntro = new Texture(Gdx.files.internal("start.png"));
        textureAboutInIntro = new Texture(Gdx.files.internal("skelet.png"));
        floor = new Wall(world, 8+x, 0, 16, 0f);
        floor = new Wall(world, 8+x, 9, 16, 0f);
        floor = new Wall(world, 2+x, 8, 4, 0f);
        floor = new Wall(world, 7.5f+x, 9, 0, 1f);
        buttonLevelInIntro = new Ball(world, 8+x, 3.5f, 1f, false);
        buttonSettingsInIntro = new Ball(world, 6+x, 3.5f, 0.4f, false);
        buttonAboutInIntro = new Ball(world, 10+x, 3.5f, 0.4f, false);
        box = new Box(world, new float[]{1f+x-0.5f, 7.5f, 1f+x-0.5f, 7f, 4f+x-0.5f, 7f, 4f+x-0.5f, 7.5f}, true);
        for (int i = 1; i < 16; i++) {
            gear = new Gear(world, 0, i+x, 6, true, 0.3f, -3, 35 , 50);
        }
        ListTextureBall.put(textureLevelInIntro, buttonLevelInIntro);
        ListTextureBall.put(textureSettingsInIntro, buttonSettingsInIntro);
        ListTextureBall.put(textureAboutInIntro, buttonAboutInIntro);

        // Level objects
        x = 16*2;
        textureIntroInLevel = new Texture(Gdx.files.internal("return.png"));
        textureGameInLevel = new Texture(Gdx.files.internal("start.png"));
        floor = new Wall(world, 8+x, 0, 16, 0f);
        floor = new Wall(world, 8+x, 9, 16, 0f);
        floor = new Wall(world, 16+x, 4.5f, 0f, 9);
        buttonIntroInLevel = new Ball(world, 15+x, 1, 0.5f, false);
        buttonGameInLevel = new Ball(world, 2+x, 3, 0.5f, false);float h = 6f;
        float count = 0;
        for (int i = 0; i < 12; i++) {
            gear = new Gear(world, 0, i+x, h, true, 0.3f, -3, 35 , 50);
            if (h != 7f && count >= 4 && count <= 8) h += 0.2f;
            count += 1;
        }
        arc = new Arc(world, 11f+x, 5f, 30, 0f, -1.5f, 5f);
        ListTextureBall.put(textureIntroInLevel, buttonIntroInLevel);
        ListTextureBall.put(textureGameInLevel, buttonGameInLevel);
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            JG.camera.unproject(touch);
            if (buttonSettingsInIntro.hit(touch.x, touch.y)) {
                JG.camera.position.set(w/6, h/2, 0);
            }
            if (buttonAboutInIntro.hit(touch.x, touch.y)) {
                //camera.position.set(w/6, h/2, 0);
            }
            if (buttonLevelInIntro.hit(touch.x, touch.y)) {
                JG.camera.position.set(w/1.2f, h/2, 0);
            }

            if (ballGoIntroInSettings.hit(touch.x, touch.y)) {
                JG.camera.position.set(w/2, h/2, 0);
            }

            if (buttonGameInLevel.hit(touch.x, touch.y)) {
                JG.setScreen(JG.worldGame);
            }
            if (buttonIntroInLevel.hit(touch.x, touch.y)) {
                JG.camera.position.set(w/2, h/2, 0);
            }


        }
        ScreenUtils.clear(0,0,0,1);
        world.step(1/60f,6,2);
        JG.camera.update();
        JG.debugRenderer.render(world,JG.camera.combined);


        // Отрисовываем спрайт
        JG.batch.setProjectionMatrix(JG.camera.combined);
        JG.batch.begin();

        JG.batch.draw(tBg,0,0, w/3, h);
        JG.batch.draw(tBg,w/3, 0, w/3, h);
        JG.batch.draw(tBg,w/3+ w/3, 0, w/3, h);


        for (Texture i : ListTextureBall.keySet()) {
            JG.batch.draw(i,
                    ListTextureBall.get(i).body.getPosition().x- ListTextureBall.get(i).r,
                    ListTextureBall.get(i).body.getPosition().y- ListTextureBall.get(i).r,
                    0, ListTextureBall.get(i).r*2, ListTextureBall.get(i).r*2, ListTextureBall.get(i).r*2,
                    1,1,0,0,0,100,100,false,false);
        }
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
    }
}
