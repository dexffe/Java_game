package com.mygdx.game.Levels;

import static com.mygdx.game.JavaGame.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.JavaGame;
import com.mygdx.game.objects.Ball;
import com.mygdx.game.objects.Box;
import com.mygdx.game.objects.Ellipse;
import com.mygdx.game.objects.Gear;
import com.mygdx.game.objects.SensorBox;
import com.mygdx.game.objects.Swing;
import com.mygdx.game.objects.Triangle;
import com.mygdx.game.objects.Wall;

public class level1 implements Screen{
    JavaGame JG;
    boolean pause;
    boolean isContact = true;
    boolean destroyEllipse;
    Texture texturePause;
    Texture textureWatermelon;

    Sprite sprite;
    SpriteBatch batch;
    TextButton btnPlay, btnSettings, btnExit;

    World world;

    Triangle triangle;
    Wall floor;
    Ball ball, buttonPause, ballLeft, ballRight, ballUp;
    Gear gear;
    Swing swing;
    Box box;
    Ellipse ellipse;
    SensorBox sensorDead, sensorContact;
    public level1(JavaGame context) {
        destroyEllipse = false;
        world = new World(new Vector2(0, -10), false);
        JG = context;

        texturePause = new Texture(Gdx.files.internal("pause.png"));


        floor = new Wall(world, width/2, height, 16, 0f);
        floor = new Wall(world, width/2, 0, 16, 0f);
        floor = new Wall(world, 0, height/2, 0, 9);
        floor = new Wall(world, width, height/2, 0, 9);

        box = new Box(world, new float[]{0, 1.5f, 3, 1.5f, 3, 6.5f, 0, 6.5f}, false);
        box = new Box(world, new float[]{13, 1.5f, 16, 1.5f, 13, 5.5f, 16, 4.5f}, false);

        //ball = new Ball(world, 1.5f, 7, 0.5f, true);


        for (int i = 1; i < 11; i++) {
            triangle = new Triangle(world, 2+i, 1.5f, new float[] {0, 0, 1, 0, 0.5f, 1});
        }


        ballLeft = new Ball(world, 1f, 0.75f, 0.4f, false);
        ballRight = new Ball(world, 2.5f, 0.75f, 0.4f, false);
        ballUp = new Ball(world, 14.5f, 0.75f, 0.4f, false);

        buttonPause = new Ball(world, 15.5f, 8.5f, 0.3f, false);

    }

    @Override
    public void show() {
        JG.camera.setToOrtho(false, width, height);
        //JG.camera.position.set(width, height/2, 0);
        ellipse = new Ellipse(world, 1.5f, 7, true);
        textureWatermelon = new Texture("watermelon.png");
        sensorDead = new SensorBox(world, 8, 2f, 5f, 0.5f, ellipse.ovalBody, "Dead");

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
                            System.out.println("Dead");
                            break;
                        case "Win":
                            System.out.println("Win");
                            break;
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                isContact = false;
                System.out.println("endContact");
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                isContact = true;
                System.out.println("preSolve");
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        if (destroyEllipse) {
            world.destroyBody(ellipse.ovalBody);
            textureWatermelon.dispose();
            destroyEllipse = false;
        }
        if (Gdx.input.justTouched()) {
            JG.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            JG.camera.unproject(JG.touch);
            if (buttonPause.hit(JG.touch.x, JG.touch.y)) {
                //pause();
                JG.setScreen(JG.worldsMenu);
            }
        }
        if (Gdx.input.isTouched()) {
            JG.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            JG.camera.unproject(JG.touch);
            if (isContact){
                if (ballLeft.hit(JG.touch.x, JG.touch.y)) {
                    ellipse.ovalBody.setLinearVelocity(-5, 0);
                }
                if (ballRight.hit(JG.touch.x, JG.touch.y)) {
                    ellipse.ovalBody.setLinearVelocity(5, 0);
                }
                if (ballUp.hit(JG.touch.x, JG.touch.y)) {
                    ellipse.ovalBody.applyForceToCenter(0, 50, true);
                }
            }
        }
        ScreenUtils.clear(0,0,0,1);
        if (!pause)world.step(1/60f,6,2);
        //JG.camera.update();
        JG.debugRenderer.render(world,JG.camera.combined);
        // Отрисовываем спрайт
        JG.batch.setProjectionMatrix(JG.camera.combined);
        JG.batch.begin();
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
}
