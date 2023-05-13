package com.mygdx.game.Levels;

import static com.mygdx.game.JavaGame.height;
import static com.mygdx.game.JavaGame.width;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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

    Texture texturePause;
    Texture tWatermelon;
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
    SensorBox sensorDead;
    Ellipse ellipse;

    public level3(JavaGame context) {
        world = new World(new Vector2(0, -10), false);
        JG = context;
        texturePause = new Texture(Gdx.files.internal("pause.png"));
        tWatermelon = new Texture("watermelon.png");

        floor = new Wall(world, width/2, height, 16, 0f);
        floor = new Wall(world, width/2, 0, 16, 0f);
        floor = new Wall(world, 0, height/2, 0, 9);
        floor = new Wall(world, width, height/2, 0, 9);

        floor = new Wall(world, width/2, 1.5f, 16, 0);

        box = new Box(world, new float[]{0, 1.5f, 2f, 1.5f, 2.5f, 4, 0, 4.5f}, false);
        ellipse = new Ellipse(world, 1f, 5, true);
        sensorDead = new SensorBox(world, 8, 8.5f, 8f, 0.5f, ellipse.ovalBody, "Dead");

        gear = new Gear(world, 0, 4.5f, 3, true, 0.6f, -10, 50 , 50);
        gear = new Gear(world, 0, 9f, 3.5f, true, 1f, 10, 50 , 50);



        for (int i = 0; i < 15; i++) {
            if (i % 2 == 0) triangle = new Triangle(world, i, 8f, new float[] {0, 1, 1.5f, 1, 0.75f, 0});
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


    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isTouched()) {
            JG.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            JG.camera.unproject(JG.touch);
            if (ballLeft.hit(JG.touch.x, JG.touch.y)) {
                ellipse.ovalBody.setLinearVelocity(-5, 0);
            }
            if (ballRight.hit(JG.touch.x, JG.touch.y)) {
                ellipse.ovalBody.setLinearVelocity(5, 0);
            }
            if (ballUp.hit(JG.touch.x, JG.touch.y)) {
                ellipse.ovalBody.applyForceToCenter(0, 10, true);
            }
        }
        if (Gdx.input.justTouched()) {
            JG.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            JG.camera.unproject(JG.touch);
            if (buttonPause.hit(JG.touch.x, JG.touch.y)) {
                //pause();
                JG.setScreen(JG.worldsMenu);
            }
        }
        ScreenUtils.clear(0,0,0,1);
        if (!pause)world.step(1/60f,6,2);
        //JG.camera.update();
        JG.debugRenderer.render(world,JG.camera.combined);


        // Отрисовываем спрайт
        JG.batch.setProjectionMatrix(JG.camera.combined);
        JG.batch.begin();
        JG.batch.draw(tWatermelon,
                buttonPause.body.getPosition().x- buttonPause.r,
                buttonPause.body.getPosition().y- buttonPause.r,
                0, buttonPause.r*2, buttonPause.r*2, buttonPause.r*2,
                1,1,0,0,0,100,100,false,false);
        JG.batch.draw(texturePause,
              ellipse.ovalBody.getPosition().x- 0.4f,
             ellipse.ovalBody.getPosition().y- 0.3f,
           0.4f, 0.3f, 0.4f*2, 0.3f*2,
           1,1,ellipse.ovalBody.getAngle()* MathUtils.radiansToDegrees,0,0,150,120,false,false);
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
