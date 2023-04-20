package com.mygdx.game.worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

import com.mygdx.game.JavaGame;
import com.mygdx.game.objects.Arc;
import com.mygdx.game.objects.Ball;
import com.mygdx.game.objects.Gear;
import com.mygdx.game.objects.Triangle;
import com.mygdx.game.objects.Swing;
import com.mygdx.game.objects.Wall;

public class WorldSettings implements Screen {
    JavaGame JG;

    Texture texture;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera camera;
    Sprite sprite;
    SpriteBatch batch;
    Vector3 touch;
    TextButton btnPlay, btnSettings, btnExit;

    World world;

    Triangle triangle;
    Wall floor;
    Ball ballSettings, ball;
    Gear gear;
    Swing swing;
    Arc arc;

    PolygonShape sensorShape;
    FixtureDef sensorFixtureDef;
    Body sensorBody;


    public WorldSettings(JavaGame context) {
        JG = context;
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -10), false);

        texture = new Texture(Gdx.files.internal("return.png"));
        touch = new Vector3();

        floor = new Wall(world, 8, 0, 16, 0f);
        floor = new Wall(world, 8, 9, 16, 0f);
        floor = new Wall(world, 10, 8, 8, 0f);

        floor = new Wall(world, 0, 4.5f, 0f, 9);
        floor = new Wall(world, 1, 4f, 0f, 3);



        ballSettings = new Ball(world, 10, 3, 0.5f, true);
        ball = new Ball(world, 6f, 8, 0.2f, true);


        arc = new Arc(world, 1.7f, 7.3f, 30, 1.7f, 3.2f, 0.7f);
        arc = new Arc(world, 1f, 8f, 30, 1.7f, 3.2f, 1f);


        sensorShape = new PolygonShape();
        sensorShape.setAsBox(2.5f, 0.5f, new Vector2(5, 0.5f), 0); // установка размеров квадрата

// Создание фикстуры для датчика
        sensorFixtureDef = new FixtureDef();
        sensorFixtureDef.shape = sensorShape;
        sensorFixtureDef.isSensor = true; // Установка флага isSensor в true для создания датчика столкновений
        sensorBody = world.createBody(new BodyDef());
        sensorBody.createFixture(sensorFixtureDef);
        System.out.println(ball.body.getPosition());


        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                // Получение объекта, который столкнулся с датчиком
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Body bodyA = fixtureA.getBody();
                Body bodyB = fixtureB.getBody();
                if (bodyA == ball.body && fixtureA.isSensor()) {
                    // Добавление импульса к телу объекта
                    ball.body.applyLinearImpulse(new Vector2(10f, 0), ball.body.getWorldCenter(), true);
                } else if (bodyB == ball.body && fixtureB.isSensor()) {
                    // Добавление импульса к телу объекта
                    ball.body.applyLinearImpulse(new Vector2(10f, 0), ball.body.getWorldCenter(), true);
                }
            }

            @Override
            public void endContact(Contact contact) {
                System.out.println("YES");
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                //System.out.println("YES");
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                //System.out.println("YES");
            }
        });

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            JG.camera.unproject(touch);
            if (ballSettings.hit(touch.x, touch.y)) {
                JG.setScreen(JG.worldIntro);
            }
        }
        ScreenUtils.clear(0,0,0,1);
        world.step(1/60f,6,2);
        //JG.camera.update();
        JG.debugRenderer.render(world,JG.camera.combined);


        // Отрисовываем спрайт
        JG.batch.setProjectionMatrix(JG.camera.combined);
        JG.batch.begin();
        JG.batch.draw(texture,
                ballSettings.body.getPosition().x- ballSettings.r,
                ballSettings.body.getPosition().y- ballSettings.r,
                0, ballSettings.r*2, ballSettings.r*2, ballSettings.r*2,
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
        sensorShape.dispose();
    }
}
