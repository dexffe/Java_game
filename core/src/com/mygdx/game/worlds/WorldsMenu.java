package com.mygdx.game.worlds;

import static com.mygdx.game.JavaGame.*;

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
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.JavaGame;
import com.mygdx.game.objects.Arc;
import com.mygdx.game.objects.Ball;
import com.mygdx.game.objects.Box;
import com.mygdx.game.objects.Gear;
import com.mygdx.game.objects.SensorBox;
import com.mygdx.game.objects.Swing;
import com.mygdx.game.objects.Triangle;
import com.mygdx.game.objects.Wall;

import java.util.HashMap;
import java.util.Map;

public class WorldsMenu  implements Screen {
    JavaGame JG;

    Texture textureGoIntroInSettings, tBg;
    Texture textureLevelInIntro, textureSettingsInIntro, textureAboutInIntro;
    Texture textureIntroInLevel, textureLevel1InLevel, textureLevel2InLevel, textureLevel3InLevel;

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
    public Ball buttonIntroInLevel, buttonLevel1InLevel, buttonLevel2InLevel, buttonLevel3InLevel;
    Gear gear;
    Swing swing;
    Arc arc;
    Box box;
    Box box1, box2, box3;
    SensorBox impulseBox, destroyBox;
    float x;
    float speed;
    boolean goScreen;
    String fromScreen, toScreen;
    float w, h;



    public WorldsMenu(JavaGame context) {
        JG = context;
        JG.camera.setToOrtho(false, width, height);
        world = new World(new Vector2(0, -10), false);
        touch = new Vector3();
        w = width*3;
        h = height;
        System.out.println("show");

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
        impulseBox = new SensorBox(world, 5, 0.5f, 2.5f, 0.5f, ball.body, "Left");
        impulseBox = new SensorBox(world, 5, 8.5f, 2.5f, 0.5f, ball.body, "Right");
        impulseBox = new SensorBox(world, 0f, 0.5f, 0.3f, 2.5f, ball.body, "Up");
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

        box1 = new Box(world, new float[]{0.8f+x-0.5f, 7.5f, 0.8f+x-0.5f, 7f, 3.8f+x-0.5f, 7f, 3.8f+x-0.5f, 7.5f}, true);
        box2 = new Box(world, new float[]{0.8f+x-0.5f, 8f, 0.8f+x-0.5f, 7f, 0.8f+x, 7f, 0.8f+x, 8f}, true);
        box3 = new Box(world, new float[]{3.3f+x-0.5f, 8f, 3.3f+x-0.5f, 7f, 3.3f+x, 7f, 3.3f+x, 8f}, true);
        //world.destroyBody(box1.body);

        WeldJointDef rjd = new WeldJointDef();
        rjd.collideConnected = false;
        rjd.bodyA = box1.body;
        rjd.bodyB = box2.body;
        rjd.localAnchorA.set(box1.body.getPosition().x, box1.body.getPosition().y);
        rjd.localAnchorB.set(box2.body.getPosition().x, box2.body.getPosition().y);
        world.createJoint(rjd);

        WeldJointDef rjd1 = new WeldJointDef();
        rjd1.collideConnected = false;
        rjd1.bodyA = box1.body;
        rjd1.bodyB = box3.body;
        rjd1.localAnchorA.set(box1.body.getPosition().x, box1.body.getPosition().y);
        rjd1.localAnchorB.set(box3.body.getPosition().x, box3.body.getPosition().y);
        world.createJoint(rjd1);

        for (int i = 1; i < 16; i++) {
            gear = new Gear(world, 0, i+x, 6, true, 0.3f, -3, 50 , 50);
        }
        ListTextureBall.put(textureLevelInIntro, buttonLevelInIntro);
        ListTextureBall.put(textureSettingsInIntro, buttonSettingsInIntro);
        ListTextureBall.put(textureAboutInIntro, buttonAboutInIntro);

        // Level objects
        x = 16*2;
        textureIntroInLevel = new Texture(Gdx.files.internal("return.png"));
        textureLevel1InLevel = new Texture(Gdx.files.internal("start.png"));
        textureLevel2InLevel = new Texture(Gdx.files.internal("start.png"));
        textureLevel3InLevel = new Texture(Gdx.files.internal("start.png"));
        floor = new Wall(world, 8+x, 0, 16, 0f);
        floor = new Wall(world, 8+x, 9, 16, 0f);
        floor = new Wall(world, 16+x, 4.5f, 0f, 9);
        impulseBox = new SensorBox(world, 5+x, 0.5f, 2.5f, 0.5f, ball.body, "Left");
        buttonIntroInLevel = new Ball(world, 15+x, 1, 0.5f, false);
        buttonLevel1InLevel = new Ball(world, 2+x, 3, 0.5f, false);
        buttonLevel2InLevel = new Ball(world, 4+x, 3, 0.5f, false);
        buttonLevel3InLevel = new Ball(world, 6+x, 3, 0.5f, false);
        float h = 6f;
        float count = 0;
        for (int i = 0; i < 12; i++) {
            gear = new Gear(world, 0, i+x, h, true, 0.3f, -3, 35 , 50);
            if (h != 7f && count >= 4 && count <= 8) h += 0.2f;
            count += 1;
        }
        arc = new Arc(world, 11f+x, 5f, 30, 0f, -1.5f, 5f);
        ListTextureBall.put(textureIntroInLevel, buttonIntroInLevel);
        ListTextureBall.put(textureLevel1InLevel, buttonLevel1InLevel);
        ListTextureBall.put(textureLevel2InLevel, buttonLevel2InLevel);
        ListTextureBall.put(textureLevel3InLevel, buttonLevel3InLevel);

    }

    @Override
    public void show() {
        JG.camera.position.set(w /2f, h/2, 0);
    }

    @Override
    public void render(float delta) {
        /*final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final Runnable runnable = new Runnable() {
            int countdownStarter = 5;  //27

            public void run() {
                //System.out.println(countdownStarter);
                countdownStarter--;


                if (countdownStarter < 0) {
                    System.out.println("over");
                    x = 16;
                    countdownStarter = 27;
                    world.destroyBody(box1.body);
                    world.destroyBody(box2.body);
                    world.destroyBody(box3.body);
                    box1 = new Box(world, new float[]{0.8f+x-0.5f, 7.5f, 0.8f+x-0.5f, 7f, 3.8f+x-0.5f, 7f, 3.8f+x-0.5f, 7.5f}, true);
                    box2 = new Box(world, new float[]{0.8f+x-0.5f, 8f, 0.8f+x-0.5f, 7f, 0.8f+x, 7f, 0.8f+x, 8f}, true);
                    box3 = new Box(world, new float[]{3.3f+x-0.5f, 8f, 3.3f+x-0.5f, 7f, 3.3f+x, 7f, 3.3f+x, 8f}, true);
                    //world.destroyBody(box1.body);

                    WeldJointDef rjd = new WeldJointDef();
                    rjd.collideConnected = false;
                    rjd.bodyA = box1.body;
                    rjd.bodyB = box2.body;
                    rjd.localAnchorA.set(box1.body.getPosition().x, box1.body.getPosition().y);
                    rjd.localAnchorB.set(box2.body.getPosition().x, box2.body.getPosition().y);
                    world.createJoint(rjd);

                    WeldJointDef rjd1 = new WeldJointDef();
                    rjd1.collideConnected = false;
                    rjd1.bodyA = box1.body;
                    rjd1.bodyB = box3.body;
                    rjd1.localAnchorA.set(box1.body.getPosition().x, box1.body.getPosition().y);
                    rjd1.localAnchorB.set(box3.body.getPosition().x, box3.body.getPosition().y);
                    world.createJoint(rjd1);
                    scheduler.shutdown();
                }
            }
        };
        scheduler.scheduleAtFixedRate(runnable, 0, 1, SECONDS);*/


        ScreenUtils.clear(0,0,0,1);
        world.step(1/60f,6,2);
        JG.camera.update();
        JG.debugRenderer.render(world,JG.camera.combined);

        if (goScreen){
            speed += 0.3;
            if (fromScreen == "Settings" && toScreen == "Intro"){    //  Settings -> Intro
                if (w/6+speed <= w/2) {
                    JG.camera.position.set(w/6+ speed, h/2, 0);
                }else {
                    goScreen = false;
                    speed = 0;
                }
            }
            if (fromScreen == "Intro" && toScreen == "Level"){
                if (w/2+speed <= w/1.2f) {             //  Intro -> Level
                    JG.camera.position.set(w/2+ speed, h/2, 0);
                }else {
                    goScreen = false;
                    speed = 0;
                }
            }
            if (fromScreen == "Level" && toScreen == "Intro"){
                if (w/1.2f-speed >= w/2) {             //  Level -> Intro
                    JG.camera.position.set(w/1.2f- speed, h/2, 0);
                }else {
                    goScreen = false;
                    speed = 0;
                }
            }
            if (fromScreen == "Intro" && toScreen == "Settings") {
                if (w / 2 - speed >= w / 6) {             //  Intro -> Settings
                    JG.camera.position.set(w / 2 - speed, h / 2, 0);
                } else {
                    goScreen = false;
                    speed = 0;
                }
            }
        }




        // Отрисовываем спрайт
        JG.batch.setProjectionMatrix(JG.camera.combined);
        JG.batch.begin();
        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            JG.camera.unproject(touch);
            if (buttonSettingsInIntro.hit(touch.x, touch.y)) {
                fromScreen = "Intro";
                toScreen = "Settings";
                goScreen = true;
                //JG.camera.position.set(w/6, h/2, 0);
            }
            if (buttonAboutInIntro.hit(touch.x, touch.y)) {
                fromScreen = "Intro";
                toScreen = "About";
                goScreen = true;
                //camera.position.set(w/6, h/2, 0);
            }
            if (buttonLevelInIntro.hit(touch.x, touch.y)) {
                fromScreen = "Intro";
                toScreen = "Level";
                goScreen = true;
                //JG.camera.position.set(w/1.2f, h/2, 0);
            }

            if (ballGoIntroInSettings.hit(touch.x, touch.y)) {
                fromScreen = "Settings";
                toScreen = "Intro";
                goScreen = true;
                //JG.camera.position.set(w/2, h/2, 0);
            }

            if (buttonLevel1InLevel.hit(touch.x, touch.y)) {
                JG.setScreen(JG.level1);
            }
            if (buttonLevel2InLevel.hit(touch.x, touch.y)) {
                JG.setScreen(JG.level2);
            }
            if (buttonLevel3InLevel.hit(touch.x, touch.y)) {
                JG.setScreen(JG.level3);
            }
            if (buttonIntroInLevel.hit(touch.x, touch.y)) {
                fromScreen = "Level";
                toScreen = "Intro";
                goScreen = true;
                //JG.camera.position.set(w/2, h/2, 0);
            }


        }
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