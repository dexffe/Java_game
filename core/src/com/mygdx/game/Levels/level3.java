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

public class level3 implements Screen{
    JavaGame JG;
    boolean pause;
    boolean isContact = true;
    boolean destroyEllipse;

    Texture texturePause, tGearsBody, textureTriangle;
    Texture textureWatermelon, textureButtonRight, textureButtonLeft, textureButtonUp;
    Texture tBg;
    World world;

    Triangle[] triangle = new Triangle[14];
    Wall floor;
    Ball buttonPause, ballLeft, ballRight, ballUp;
    Gear gear, gear1;
    Box box;
    SensorBox sensorDead;
    Ellipse ellipse;

    public level3(JavaGame context) {
        destroyEllipse = false;
        world = new World(new Vector2(0, -10), false);
        JG = context;
        texturePause = new Texture(Gdx.files.internal("pause.png"));
        textureTriangle = new Texture(Gdx.files.internal("peak.png"));

        textureButtonRight = new Texture(Gdx.files.internal("arrowRight.png"));
        textureButtonLeft = new Texture(Gdx.files.internal("arrowLeft.png"));
        textureButtonUp = new Texture(Gdx.files.internal("arrowUp.png"));

        tBg = new Texture("bg.png");
        floor = new Wall(world, width/2, height, 16, 0f);
        floor = new Wall(world, width/2, 0, 16, 0f);
        floor = new Wall(world, 0, height/2, 0, 9);
        floor = new Wall(world, width, height/2, 0, 9);

        floor = new Wall(world, width/2, 1.5f, 16, 0);

        box = new Box(world, new float[]{0, 1.5f, 2f, 1.5f, 2.5f, 4, 0, 4.5f}, false, 0, 0, 0);


        gear = new Gear(world, 0, 4.5f, 3, true, 0.6f, -10, 50 , 50);
        gear1 = new Gear(world, 0, 9f, 3.5f, true, 1f, 10, 50 , 50);



        for (int i = 0; i < 14; i++) {
            triangle[i] = new Triangle(world, i+1, 8f, new float[] {0, 1, 1.5f, 1, 0.75f, 0});
        }


        ballLeft = new Ball(world, 1f, 0.75f, 0.5f, false);
        ballRight = new Ball(world, 2.5f, 0.75f, 0.5f, false);
        ballUp = new Ball(world, 14.5f, 0.75f, 0.5f, false);

        buttonPause = new Ball(world, 15.5f, 8.5f, 0.3f, false);
        tGearsBody = new Texture("gear.png");
        ellipse = new Ellipse(world, 1f, 5, true);


        ballLeft = new Ball(world, 1f, 0.75f, 0.4f, false);
        ballRight = new Ball(world, 2.5f, 0.75f, 0.4f, false);
        ballUp = new Ball(world, 14.5f, 0.75f, 0.4f, false);

        buttonPause = new Ball(world, 15.5f, 8.5f, 0.3f, false);
        tGearsBody = new Texture("gear.png");
    }

    @Override
    public void show() {
        JG.camera.setToOrtho(false, width, height);
        textureWatermelon = new Texture("watermelon.png");
        sensorDead = new SensorBox(world, 8, 8.5f, 8f, 0.5f, ellipse.ovalBody, "Dead");


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
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                isContact = true;
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
                    ellipse.ovalBody.applyForceToCenter(0, 150, true);
                }
            }
        }
        if (Gdx.input.justTouched()) {
            JG.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            JG.camera.unproject(JG.touch);
            if (buttonPause.hit(JG.touch.x, JG.touch.y)) {
                JG.setScreen(JG.worldMenu);
            }
        }
        ScreenUtils.clear(0,0,0,1);
        if (!pause)world.step(1/60f,6,2);
        JG.debugRenderer.render(world,JG.camera.combined);


        // Отрисовываем спрайт
        JG.batch.setProjectionMatrix(JG.camera.combined);
        JG.batch.begin();
        JG.batch.draw(tBg, 0,0);
        JG.batch.draw(tGearsBody, gear.basis.getPosition().x*2 - gear.res/2, gear.basis.getPosition().y*2 - gear.res/2,
                gear.res/2,gear.res/2, gear.res,gear.res, 3f,3f, gear.box.getAngle()* MathUtils.radiansToDegrees, 0,0, 500,500, false,false);
        JG.batch.draw(tGearsBody, gear1.basis.getPosition().x*2 - gear1.res/2, gear1.basis.getPosition().y*2 - gear1.res/2,
                gear1.res/2,gear1.res/2, gear1.res,gear1.res, 3f,3f, gear1.box.getAngle()* MathUtils.radiansToDegrees, 0,0, 500,500, false,false);
        JG.batch.draw(texturePause,
                buttonPause.body.getPosition().x- buttonPause.r,
                buttonPause.body.getPosition().y- buttonPause.r,
                0, buttonPause.r*2, buttonPause.r*2, buttonPause.r*2,
                1,1,0,0,0,100,100,false,false);
        JG.batch.draw(textureWatermelon,
              ellipse.ovalBody.getPosition().x- 0.4f,
             ellipse.ovalBody.getPosition().y- 0.3f,
           0.4f, 0.3f, 0.4f*2, 0.3f*2,
           1,1,ellipse.ovalBody.getAngle()* MathUtils.radiansToDegrees,0,0,150,120,false,false);

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
        for (int i = 0; i < triangle.length; i++) {
            JG.batch.draw(textureTriangle, triangle[i].body.getPosition().x+0.5f, triangle[i].body.getPosition().y+1,
                    0,0, 1,1, 1.5f,1f, 180, 0,0, 300,300, false,false);
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
}
