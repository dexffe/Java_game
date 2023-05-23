package com.mygdx.game.worlds;

import static com.mygdx.game.JavaGame.height;
import static com.mygdx.game.JavaGame.width;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
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
    public Music BackgroundMusic, PushButton, ScreenMove;
    Texture textureGoIntroInSettings;
    Texture tBg, tGearsPeace, tGearsBody, tBall, t1Box200x300,t1Box300x200;

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
    public Ball ballGoIntroInSettings, ball1, ball2, ball3;
    public Ball buttonSettingsInIntro, buttonLevelInIntro, buttonAboutInIntro;
    public Ball buttonIntroInLevel, buttonLevel1InLevel, buttonLevel2InLevel, buttonLevel3InLevel;
    Gear[] gear = new Gear[15];
    Gear[] gear1 = new Gear[12];
    Swing swing;
    Arc arc;
    public Box boxDown, boxRight, boxLeft;
    SensorBox impulseBox, destroyBox;
    float x;
    float speed;
    boolean goScreen, isObjDeleted;
    String fromScreen, toScreen;
    float w, h;
    long timeLastCreateBox, timeCreateBoxInterval = 25000; //27500





    public WorldsMenu(JavaGame context) {
        // System parameters
        JG = context;
        JG.camera.setToOrtho(false, width, height);
        world = new World(new Vector2(0, -10), false);
        touch = new Vector3();
        w = width*3;
        h = height;

        // Music
        ScreenMove = Gdx.audio.newMusic(Gdx.files.internal("ScreenMove.mp3"));
        ScreenMove.setVolume(1);

        PushButton = Gdx.audio.newMusic(Gdx.files.internal("ButtonPush.mp3"));
        PushButton.setVolume(1);

        BackgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("MusicGame.mp3"));
        BackgroundMusic.play();
        BackgroundMusic.setLooping(true);
        BackgroundMusic.setVolume(0.1f);

        // Settings objects
        textureGoIntroInSettings = new Texture(Gdx.files.internal("return.png"));
        floor = new Wall(world, 8, 0, 16, 0f);
        floor = new Wall(world, 8, 9, 16, 0f);
        floor = new Wall(world, 9.6f, 8, 8, 0f);
        floor = new Wall(world, 0, 4.5f, 0f, 9);
        floor = new Wall(world, 1, 4.2f, 0f, 3.2f);
        ballGoIntroInSettings = new Ball(world, 3, 7, 0.4f, false);

        ball1 = new Ball(world, 7.2f, 0.5f, 0.2f, true);
        ball1.body.setLinearVelocity(-4, 0);
        ball2 = new Ball(world, 7.6f, 0.5f, 0.2f, true);
        ball2.body.setLinearVelocity(-4, 0);
        ball3 = new Ball(world, 8f, 0.5f, 0.2f, true);
        ball3.body.setLinearVelocity(-4, 0);

        arc = new Arc(world, 1.7f, 7.3f, 10, 1.7f, 3f, 0.7f);
        arc = new Arc(world, 1f, 8f, 10, 1.7f, 3f, 1f);
        arc = new Arc(world, 1f, 1f, 10, -1.7f, -3f, 1f);
        impulseBox = new SensorBox(world, 5, 0.5f, 2.5f, 0.5f, ball1.body, "Left");
        impulseBox = new SensorBox(world, 5, 8.5f, 2.5f, 0.5f, ball1.body, "Right");
        impulseBox = new SensorBox(world, 0f, 0.5f, 0.3f, 2.5f, ball1.body, "Up");
        ListTextureBall.put(textureGoIntroInSettings, ballGoIntroInSettings);

        //Intro objects
        x = 16;
        tBg = new Texture("bg.png");
        textureSettingsInIntro = new Texture(Gdx.files.internal("settings.png"));
        textureLevelInIntro = new Texture(Gdx.files.internal("start.png"));
        textureAboutInIntro = new Texture(Gdx.files.internal("about.png"));
        tGearsBody = new Texture("gear.png");
        tGearsPeace = new Texture("gearsPeace.png");
        tBall = new Texture("Ball.png");
        t1Box200x300 = new Texture("1box200x300.png");
        t1Box300x200 = new Texture("1box300x200.png");
        floor = new Wall(world, 8+x, 0, 16, 0f);
        floor = new Wall(world, 8+x, 9, 16, 0f);
        floor = new Wall(world, 2+x, 8, 4, 0f);
        floor = new Wall(world, 7.5f+x, 9, 0, 1f);
        buttonLevelInIntro = new Ball(world, 8+x, 3.5f, 1f, false);
        buttonSettingsInIntro = new Ball(world, 6+x, 3.5f, 0.4f, false);
        buttonAboutInIntro = new Ball(world, 10+x, 3.5f, 0.4f, false);

        boxDown = new Box(world, new float[]{-1.5f, 0.25f, -1.5f, -0.25f, 1.5f, -0.25f, 1.5f, 0.25f}, true, 18.25f, 6.75f);
        boxRight = new Box(world, new float[]{-0.25f, 1f, -0.25f, 0, 0.25f, 0, 0.25f, 1}, true, 17f, 6.5f);
        boxLeft = new Box(world, new float[]{-0.25f, 1f, -0.25f, 0, 0.25f, 0, 0.25f, 1}, true, 19.5f, 6.5f);

        WeldJointDef rjd = new WeldJointDef();
        rjd.collideConnected = false;
        rjd.bodyA = boxDown.body;
        rjd.bodyB = boxRight.body;
        rjd.localAnchorA.set(1.25f, 0);
        rjd.localAnchorB.set(0, 0.25f);
        world.createJoint(rjd);

        WeldJointDef rjd1 = new WeldJointDef();
        rjd1.collideConnected = false;
        rjd1.bodyA = boxDown.body;
        rjd1.bodyB = boxLeft.body;
        rjd1.localAnchorA.set(-1.25f, 0);
        rjd1.localAnchorB.set(0, 0.25f);
        world.createJoint(rjd1);

        float r = 0;
        for (int i = 1; i < 16; i++) {
            //r += 0.25f;
            gear[i-1] = new Gear(world, 0, i+x+r, 6, true, 0.3f, -3, 50 , 50); //speed -3
        }
        ListTextureBall.put(textureLevelInIntro, buttonLevelInIntro);
        ListTextureBall.put(textureSettingsInIntro, buttonSettingsInIntro);
        ListTextureBall.put(textureAboutInIntro, buttonAboutInIntro);

        // Level objects
        x = 16*2;
        textureIntroInLevel = new Texture(Gdx.files.internal("return.png"));
        textureLevel1InLevel = new Texture(Gdx.files.internal("level1.png"));
        textureLevel2InLevel = new Texture(Gdx.files.internal("level2.png"));
        textureLevel3InLevel = new Texture(Gdx.files.internal("level3.png"));
        floor = new Wall(world, 8+x, 0, 16, 0f);
        floor = new Wall(world, 8+x, 9, 16, 0f);
        floor = new Wall(world, 16+x, 4.5f, 0f, 9);
        impulseBox = new SensorBox(world, 12+x, 5.5f, 2.5f, 0.5f, ball1.body, "Right");
        buttonIntroInLevel = new Ball(world, 15+x, 1, 0.5f, false);
        buttonLevel1InLevel = new Ball(world, 2+x, 3, 0.5f, false);
        buttonLevel2InLevel = new Ball(world, 4+x, 3, 0.5f, false);
        buttonLevel3InLevel = new Ball(world, 6+x, 3, 0.5f, false);
        float h = 6f, r1 = 0;
        float count = 0;
        for (int i = 0; i < 12; i++) {
            //r1 += 0.25f;
            gear1[i] = new Gear(world, 0, i+x+r1, h, true, 0.3f, -3, 35 , 50);
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
        JG.camera.position.set(w /2, h/2, 0);
        timeLastCreateBox = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {
        if (timeLastCreateBox + timeCreateBoxInterval < TimeUtils.millis() && !isObjDeleted) {
            world.destroyBody(boxDown.body);
            world.destroyBody(boxRight.body);
            world.destroyBody(boxLeft.body);
            isObjDeleted = true;
        }

        if (timeLastCreateBox + timeCreateBoxInterval + 6600  < TimeUtils.millis()) {
            isObjDeleted = false;
            timeLastCreateBox = TimeUtils.millis();
            x = 16;
            //box1 = new Box(world, new float[]{0.8f+x-0.5f, 7.5f, 0.8f+x-0.5f, 7f, 3.8f+x-0.5f, 7f, 3.8f+x-0.5f, 7.5f}, true);
            boxDown = new Box(world, new float[]{-1.5f, 0.25f, -1.5f, -0.25f, 1.5f, -0.25f, 1.5f, 0.25f}, true, 18.25f, 6.75f);
            //box2 = new Box(world, new float[]{0.8f+x-0.5f, 8f, 0.8f+x-0.5f, 7f, 0.8f+x, 7f, 0.8f+x, 8f}, true);
            boxRight = new Box(world, new float[]{-0.25f, 1f, -0.25f, 0, 0.25f, 0, 0.25f, 1}, true, 17f, 6.5f);
            //box3 = new Box(world, new float[]{3.3f+x-0.5f, 8f, 3.3f+x-0.5f, 7f, 3.3f+x, 7f, 3.3f+x, 8f}, true);
            boxLeft = new Box(world, new float[]{-0.25f, 1f, -0.25f, 0, 0.25f, 0, 0.25f, 1}, true, 19.5f, 6.5f);

            WeldJointDef rjd = new WeldJointDef();
            rjd.collideConnected = false;
            rjd.bodyA = boxDown.body;
            rjd.bodyB = boxRight.body;
            rjd.localAnchorA.set(1.25f, 0);
            rjd.localAnchorB.set(0, 0.25f);
            world.createJoint(rjd);

            WeldJointDef rjd1 = new WeldJointDef();
            rjd1.collideConnected = false;
            rjd1.bodyA = boxDown.body;
            rjd1.bodyB = boxLeft.body;
            rjd1.localAnchorA.set(-1.25f, 0);
            rjd1.localAnchorB.set(0, 0.25f);
            world.createJoint(rjd1);
        }



        ScreenUtils.clear(0,0,0,1);
        world.step(1/60f,6,2);
        JG.camera.update();
        //JG.debugRenderer2.render(world,JG.camera.combined);
        //JG.debugRenderer.render(world,JG.camera.combined);

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
        JG.batch.draw(tBg,0, 0, w, h);

        JG.batch.draw(t1Box200x300, boxRight.getX()- boxRight.width/2, boxRight.getY(),
                boxRight.width/2,0, boxRight.width, boxRight.height, 1,1,
                boxRight.body.getAngle()* MathUtils.radiansToDegrees,
                0,0, 200,300, false,false);


        JG.batch.draw(t1Box200x300, boxLeft.getX()- boxLeft.width/2, boxLeft.getY(),
                boxLeft.width/2,0, boxLeft.width, boxLeft.height, 1,1,
                boxLeft.body.getAngle()* MathUtils.radiansToDegrees,
                0,0, 200,300, false,false);

        JG.batch.draw(t1Box300x200, boxDown.getX()- boxDown.width/2, boxDown.getY()- boxDown.height/2,
                boxDown.width/2, boxDown.height/2, boxDown.width, boxDown.height, 1,1,
                boxDown.body.getAngle()* MathUtils.radiansToDegrees,
                0,0, 300,200, false,false);

        JG.batch.draw(tBall,
                ball1.body.getPosition().x- ball1.r,
                ball1.body.getPosition().y- ball1.r,
                0, ball1.r*2, ball1.r*2, ball1.r*2,
                1,1,0,0,0,200,200,false,false);
        JG.batch.draw(tBall,
                ball2.body.getPosition().x- ball2.r,
                ball2.body.getPosition().y- ball2.r,
                0, ball2.r*2, ball2.r*2, ball2.r*2,
                1,1,0,0,0,200,200,false,false);
        JG.batch.draw(tBall,
                ball3.body.getPosition().x- ball3.r,
                ball3.body.getPosition().y- ball3.r,
                0, ball3.r*2, ball3.r*2, ball3.r*2,
                1,1,0,0,0,200,200,false,false);


        for (int i = 0; i < gear.length; i++) {
            JG.batch.draw(tGearsBody, gear[i].basis.getPosition().x*2 - gear[i].res/2, gear[i].basis.getPosition().y*2 - gear[i].res/2,
                    gear[i].res/2,gear[i].res/2, gear[i].res,gear[i].res, 3f,3f, gear[i].box.getAngle()* MathUtils.radiansToDegrees, 0,0, 500,500, false,false);
        }
        for (int i = 0; i < gear1.length; i++) {
            JG.batch.draw(tGearsBody, gear1[i].basis.getPosition().x*2 - gear1[i].res/2, gear1[i].basis.getPosition().y*2 - gear1[i].res/2,
                    gear1[i].res/2,gear1[i].res/2, gear1[i].res,gear1[i].res, 3f,3f, gear1[i].box.getAngle()* MathUtils.radiansToDegrees, 0,0, 500,500, false,false);
        }

        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            JG.camera.unproject(touch);
            if (buttonSettingsInIntro.hit(touch.x, touch.y)) {
                PushButton.play();
                ScreenMove.play();
                fromScreen = "Intro";
                toScreen = "Settings";
                goScreen = true;
                //JG.camera.position.set(w/6, h/2, 0);
            }
            if (buttonAboutInIntro.hit(touch.x, touch.y)) {
                PushButton.play();
                ScreenMove.play();
                fromScreen = "Intro";
                toScreen = "About";
                goScreen = true;
                //camera.position.set(w/6, h/2, 0);
            }
            if (buttonLevelInIntro.hit(touch.x, touch.y)) {
                PushButton.play();
                ScreenMove.play();
                fromScreen = "Intro";
                toScreen = "Level";
                goScreen = true;
                //JG.camera.position.set(w/1.2f, h/2, 0);
            }

            if (ballGoIntroInSettings.hit(touch.x, touch.y)) {
                PushButton.play();
                ScreenMove.play();
                fromScreen = "Settings";
                toScreen = "Intro";
                goScreen = true;
                //JG.camera.position.set(w/2, h/2, 0);
            }

            if (buttonLevel1InLevel.hit(touch.x, touch.y)) {
                PushButton.play();
                ScreenMove.play();
                JG.setScreen(JG.level1);
            }
            if (buttonLevel2InLevel.hit(touch.x, touch.y)) {
                PushButton.play();
                ScreenMove.play();
                JG.setScreen(JG.level2);
            }
            if (buttonLevel3InLevel.hit(touch.x, touch.y)) {
                PushButton.play();
                ScreenMove.play();
                JG.setScreen(JG.level3);
            }
            if (buttonIntroInLevel.hit(touch.x, touch.y)) {
                PushButton.play();
                ScreenMove.play();
                fromScreen = "Level";
                toScreen = "Intro";
                goScreen = true;
                //JG.camera.position.set(w/2, h/2, 0);
            }


        }



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
