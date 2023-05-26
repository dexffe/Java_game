package com.mygdx.game.Levels;

import static com.mygdx.game.JavaGame.height;
import static com.mygdx.game.JavaGame.width;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.JavaGame;
import com.mygdx.game.objects.Ball;
import com.mygdx.game.objects.Box;
import com.mygdx.game.objects.Ellipse;
import com.mygdx.game.objects.SensorBox;
import com.mygdx.game.objects.Triangle;
import com.mygdx.game.objects.Wall;

public class level1 implements Screen{
    JavaGame JG;
    public boolean pause, dead, win, spawn, destroyEllipseWin, win2;
    boolean isContact = true;
    boolean destroyEllipse, isLive = true;
    Texture texturePause, textureTriangle;
    Texture textureWatermelon, textureButtonRight, textureButtonLeft, textureButtonUp, textureBlock, textureBackground, texturePlatform;
    Texture texturePauseCause, texturePauseBackground, texturePauseBallPlay, texturePauseBallMenu;
    Box boxPauseCause, boxPauseBackground;
    Ball ballPauseMenu, ballPausePlay;

    World world;

    Triangle[] triangle = new Triangle[10];
    Wall floor;
    Ball buttonPause, ballLeft, ballRight, ballUp;
    Box boxStart, boxFinish, boxPlatform1, boxPlatform2, boxPlatform3;
    Ellipse ellipse;
    SensorBox sensorDead, sensorWin;
    Texture tCause, tPlay;
    public level1(JavaGame context) {
        world = new World(new Vector2(0, -10), false);
        JG = context;

        texturePause = new Texture(Gdx.files.internal("pause.png"));
        textureTriangle = new Texture(Gdx.files.internal("peak.png"));
        textureBlock = new Texture(Gdx.files.internal("block.png"));

        textureButtonRight = new Texture(Gdx.files.internal("arrowRight.png"));
        textureButtonLeft = new Texture(Gdx.files.internal("arrowLeft.png"));
        textureButtonUp = new Texture(Gdx.files.internal("arrowUp.png"));
        textureBackground = new Texture(Gdx.files.internal("bg1920x1080.png"));
        texturePlatform = new Texture(Gdx.files.internal("airbag.png"));

        floor = new Wall(world, width/2, height, 16, 0f);
        floor = new Wall(world, width/2, 1.5f, 16, 0f);
        floor = new Wall(world, 0, height/2, 0, 9);
        floor = new Wall(world, width, height/2, 0, 9);

        boxStart = new Box(world, new float[]{-2, -2, 1, -2, 2, 2, -2, 2}, false, 1.5f, 3.5f, 0);
        boxFinish = new Box(world, new float[]{-2, -2, 1, -2, 1, 1, -2, 2}, false, 15, 3.5f, 0);
        boxPlatform1 = new Box(world, new float[]{-1, -0.25f, 1, -0.25f, 1, 0.25f, -1, 0.25f}, false, 8, 3, 0);
        boxPlatform2 = new Box(world, new float[]{-1, -0.25f, 1, -0.25f, 1, 0.25f, -1, 0.25f}, false, 12, 3, 0);
        boxPlatform3 = new Box(world, new float[]{-1, -0.25f, 1, -0.25f, 1, 0.25f, -1, 0.25f}, false, 4, 3, 0);

        for (int i = 0; i < 10; i++) {
            triangle[i] = new Triangle(world, 3+i, 1.5f, new float[] {0, 0, 1, 0, 0.5f, 1});
        }


        ballLeft = new Ball(world, 1f, 0.75f, 0.5f, false);
        ballRight = new Ball(world, 2.5f, 0.75f, 0.5f, false);
        ballUp = new Ball(world, 14.5f, 0.75f, 0.5f, false);

        buttonPause = new Ball(world, 15.5f, 8.5f, 0.3f, false);

    }

    @Override
    public void show() {
        win2 = false;
        destroyEllipseWin = false;
        spawn = false;
        pause = false;
        dead = false;
        win = false;
        isLive = true;
        destroyEllipse = false;
        JG.camera.setToOrtho(false, width, height);
        ellipse = new Ellipse(world, 1.5f, 7, true);
        textureWatermelon = new Texture("watermelon.png");
        sensorDead = new SensorBox(world, 8, 2f, 5f, 0.5f, ellipse.ovalBody, "Dead");
        sensorWin = new SensorBox(world, 15, 6.1f, .5f, 1, ellipse.ovalBody, "Win");



    }

    @Override
    public void render(float delta) {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA(); // это область, в которую попадает шар
                Fixture fixtureB = contact.getFixtureB();
                Body bodyA = fixtureA.getBody();
                Body bodyB = fixtureB.getBody(); // это тело шара, который попадает в область
                if (fixtureB.isSensor()){
                    switch ((String) fixtureB.getUserData()) {
                        case "Dead":
                            destroyEllipse = true;
                            break;
                        case "Win":
                            win = true;
                            break;
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                isContact = false;
                //System.out.println("endContact");
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                isContact = true;
                //System.out.println("preSolve");
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        if (win) {
            win2 = true;
            destroyEllipse();
            win();
            pause();
            spawnMenu();
        }
        if (destroyEllipse) {
            destroyEllipse();
            dead = true;
            dead();
            pause();
            spawnMenu();
        }

        if (Gdx.input.justTouched()) {
            JG.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            JG.camera.unproject(JG.touch);
            if (buttonPause.hit(JG.touch.x, JG.touch.y)) {
                tCause = new Texture(Gdx.files.internal("block.png"));
                tPlay = new Texture(Gdx.files.internal("start.png"));
                pause();
                spawnMenu();
            }
            if (dead || pause || win2) {
                if (ballPausePlay.hit(JG.touch.x, JG.touch.y) && dead) {
                    pause = false;
                    destroyMenu();
                    JG.setScreen(JG.level1);
                }
                if (ballPausePlay.hit(JG.touch.x, JG.touch.y) && pause && !dead && !win2) {
                    pause = false;
                    destroyMenu();
                }
                if (ballPausePlay.hit(JG.touch.x, JG.touch.y) && win2) {
                    destroyMenu();
                    JG.setScreen(JG.level2);
                }
                if (ballPauseMenu.hit(JG.touch.x, JG.touch.y)) {
                    if (isLive) {
                        destroyEllipse();
                    }
                    destroyMenu();
                    JG.setScreen(JG.worldMenu);
                }
            }
        }
        if (Gdx.input.isTouched()) {
            JG.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            JG.camera.unproject(JG.touch);
            if (isContact){
                if (ballLeft.hit(JG.touch.x, JG.touch.y)) {
                    ellipse.ovalBody.setLinearVelocity(-2, 0);
                }
                if (ballRight.hit(JG.touch.x, JG.touch.y)) {
                    ellipse.ovalBody.setLinearVelocity(2, 0);
                }
                if (ballUp.hit(JG.touch.x, JG.touch.y)) {
                    ellipse.ovalBody.applyForceToCenter(0, 70, true);
                }
            }
        }
        ScreenUtils.clear(0,0,0,1);
        if (!pause)world.step(1/60f,6,2);
        //JG.camera.update();
        if (JG.renderOn) JG.debugRenderer.render(world,JG.camera.combined);
        // Отрисовываем спрайт
        JG.batch.setProjectionMatrix(JG.camera.combined);
        JG.batch.begin();
        JG.batch.draw(textureBackground,0, 0, width, height);
        JG.batch.draw(texturePlatform,
                boxPlatform1.getX()- 1, boxPlatform1.getY()-0.25f,
                0,0, 2, 0.8f, 1,1, 0,
                0,0, 400,100, true,false);
        JG.batch.draw(texturePlatform,
                boxPlatform2.getX()- 1, boxPlatform2.getY()-0.25f,
                0,0, 2, 0.8f, 1,1, 0,
                0,0, 400,100, true,false);
        JG.batch.draw(texturePlatform,
                boxPlatform3.getX()- 1, boxPlatform3.getY()-0.25f,
                0,0, 2, 0.8f, 1,1, 0,
                0,0, 400,100, true,false);
        JG.batch.draw(textureWatermelon,
                ellipse.ovalBody.getPosition().x- 0.4f,
                ellipse.ovalBody.getPosition().y- 0.3f,
                0.4f, 0.3f, 0.4f*2, 0.3f*2,
                1,1,ellipse.ovalBody.getAngle()* MathUtils.radiansToDegrees,0,0,150,120,false,false);
        JG.batch.draw(texturePause,
                buttonPause.body.getPosition().x- buttonPause.r,
                buttonPause.body.getPosition().y- buttonPause.r,
                0, buttonPause.r*2, buttonPause.r*2, buttonPause.r*2,
                1,1,0,0,0,100,100,false,false);

        JG.batch.draw(textureButtonLeft,
                ballLeft.body.getPosition().x- ballLeft.r,
                ballLeft.body.getPosition().y- ballLeft.r,
                0, ballLeft.r*2, ballLeft.r*2, ballLeft.r*2,
                1,1,0,0,0,100,100,false,false);
        JG.batch.draw(textureButtonRight,
                ballRight.body.getPosition().x- ballRight.r,
                ballRight.body.getPosition().y- ballRight.r,
                0, ballRight.r*2, ballRight.r*2, ballRight.r*2,
                1,1,0,0,0,100,100,false,false);
        JG.batch.draw(textureButtonUp,
                ballUp.body.getPosition().x- ballUp.r,
                ballUp.body.getPosition().y- ballUp.r,
                0, ballUp.r*2, ballUp.r*2, ballUp.r*2,
                1,1,0,0,0,100,100,false,false);
        // BlockStart
        JG.batch.draw(textureBlock,
                boxStart.getX()-1,
                boxStart.getY()+2,
                0,0, 2f, 3f, 1,1,
                -90,
                0,0, 700,700, false,false);
        JG.batch.draw(textureBlock,
                boxStart.getX()+0.1f,
                boxStart.getY()-1.67f,
                0,0, 1f, 3.9f, 1,1,
                -20,
                0,0, 700,700, false,false);
        JG.batch.draw(textureBlock,
                boxStart.getX()-1.5f,
                boxStart.getY()-2,
                0,0, 2.5f, 4, 1,1,
                0,
                0,0, 700,700, false,false);
        // BlockFinish
        JG.batch.draw(textureBlock,
                boxFinish.getX()+1.25f,
                boxFinish.getY()-1.2f,
                0,0, 2f, 4f, 1,1,
                71,
                0,0, 700,700, false,false);
        JG.batch.draw(textureBlock,
                boxFinish.getX()-2,
                boxFinish.getY()-2,
                0,0, 3, 3, 1,1,
                0,
                0,0, 700,700, false,false);

        for (int i = 0; i < triangle.length; i++) {
            JG.batch.draw(textureTriangle, triangle[i].body.getPosition().x, triangle[i].body.getPosition().y,
                    0,0, 1,1, 1f,1f, 0, 0,0, 300,300, false,false);
        }
        if (pause){
            JG.batch.draw(textureBackground,0, 0, width, height);
            JG.batch.draw(texturePauseCause,
                    boxPauseCause.getX()-4,
                    boxPauseCause.getY()-0.75f,
                    0,0, 8, 1.5f, 1,1,
                    0,
                    0,0, 800,150, false,false);
            JG.batch.draw(texturePauseBackground,
                    boxPauseBackground.getX()-4,
                    boxPauseBackground.getY()-2,
                    0,0, 8, 2.5f, 1,1,
                    0,
                    0,0, 800,150, false,false);
            JG.batch.draw(texturePauseBallMenu,
                    ballPauseMenu.body.getPosition().x- ballPauseMenu.r,
                    ballPauseMenu.body.getPosition().y- ballPauseMenu.r,
                    0, ballPauseMenu.r*2, ballPauseMenu.r*2, ballPauseMenu.r*2,
                    1,1,0,0,0,100,100,false,false);
            JG.batch.draw(texturePauseBallPlay,
                    ballPausePlay.body.getPosition().x- ballPausePlay.r,
                    ballPausePlay.body.getPosition().y- ballPausePlay.r,
                    0, ballPausePlay.r*2, ballPausePlay.r*2, ballPausePlay.r*2,
                    1,1,0,0,0,100,100,false,false);
        }
        JG.batch.end();
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
    }
    void destroyMenu() {
        world.destroyBody(boxPauseCause.body);
        world.destroyBody(boxPauseBackground.body);
        world.destroyBody(ballPausePlay.body);
        world.destroyBody(ballPauseMenu.body);
        boxPauseCause = new Box(world, new float[]{-4, -0.75f, 4, -0.75f, 4, 0.75f, -4, 0.75f}, false, 80, 6, 0);
        boxPauseBackground = new Box(world, new float[]{-4, -2, 4, -2, 4, 2, -4, 2}, false, 80, 4.75f, 0);
        ballPausePlay = new Ball(world, 60.5f, 4, 0.8f, false);
        ballPauseMenu = new Ball(world, 90.5f, 4, 0.8f, false);
        spawn = false;
    }
    void destroyEllipse() {
        world.destroyBody(ellipse.ovalBody);
        ellipse = new Ellipse(world, 100.5f, 7, true);
        isLive = false;
        destroyEllipse = false;
        win = false;
    }
    void dead() {
        tCause = new Texture(Gdx.files.internal("block.png"));
        tPlay = new Texture(Gdx.files.internal("return.png"));
    }
    void win() {
        tCause = new Texture(Gdx.files.internal("block.png"));
        tPlay = new Texture(Gdx.files.internal("start.png"));
    }
    void spawnMenu(){
        texturePauseCause = new Texture(Gdx.files.internal(String.valueOf(tCause)));
        texturePauseBackground = new Texture(Gdx.files.internal("block.png"));
        texturePauseBallPlay = new Texture(Gdx.files.internal(String.valueOf(tPlay)));
        texturePauseBallMenu = new Texture(Gdx.files.internal("mapMenung.png"));

        boxPauseCause = new Box(world, new float[]{-4, -0.75f, 4, -0.75f, 4, 0.75f, -4, 0.75f}, false, 8, 6, 0);
        boxPauseBackground = new Box(world, new float[]{-4, -2, 4, -2, 4, 2, -4, 2}, false, 8, 4.75f, 0);
        ballPausePlay = new Ball(world, 6.5f, 4, 0.8f, false);
        ballPauseMenu = new Ball(world, 9.5f, 4, 0.8f, false);
        spawn = true;
    }
}
