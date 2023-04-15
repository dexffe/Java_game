package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

import objects.*;

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

    StaticTriangle triangle;
    Wall floor;
    Ball ball;
    Gear gear;
    Swing swing;

    float radius = 2.0f;
    int segments = 10;
    ChainShape chain;
    Vector2[] vertices;

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

        floor = new Wall(world, 16, 4.5f, 0f, 9);


        ball = new Ball(world, 10, 8, 0.5f, true);


        vertices = new Vector2[segments + 2];
        vertices[0] = new Vector2(0, 0);
        vertices[segments + 1] = new Vector2(0, 0);

        for (int i = 0; i < segments; i++) {
            float angle = i * 2 * MathUtils.PI / segments;
            float x = radius * MathUtils.cos(angle);
            float y = radius * MathUtils.sin(angle);
            vertices[i + 1] = new Vector2(x, y);
        }
        chain = new ChainShape();
        chain.createChain(vertices);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.x = 5;
        bodyDef.position.y = 4;
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = chain;
        body.createFixture(fixtureDef);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            JG.camera.unproject(touch);
            if (ball.hit(touch.x, touch.y)) {
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
                ball.body.getPosition().x-ball.r,
                ball.body.getPosition().y-ball.r,
                0,ball.r*2,ball.r*2,ball.r*2,
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
        chain.dispose();
    }
}
