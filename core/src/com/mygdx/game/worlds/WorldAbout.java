package com.mygdx.game.worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.JavaGame;
import com.mygdx.game.objects.Ball;

public class WorldAbout implements Screen {
    JavaGame JG;
    float w = 16, h = 9;
    Texture texture, textureBall, textureRender;
    World world;
    OrthographicCamera camera;
    SpriteBatch batch;
    Ball ball, ballRender;
    Vector3 touch;

    public WorldAbout(JavaGame context) {
        JG = context;
        world = new World(new Vector2(0, -10), false);

    }

    @Override
    public void show() {
        JG.camera.setToOrtho(false, w, h);
        texture = new Texture(Gdx.files.internal("aboutText.png"));
        textureBall = new Texture(Gdx.files.internal("return.png"));
        textureRender = new Texture(Gdx.files.internal("about.png"));
        ball = new Ball(world, 1, 8, 0.7f, false);
        ballRender = new Ball(world, 15.5f, 8.5f, 0.3f, false);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            JG.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            JG.camera.unproject(JG.touch);
            if (ball.hit(JG.touch.x, JG.touch.y)) {
                JG.setScreen(JG.worldMenu);
            }
            if (ballRender.hit(JG.touch.x, JG.touch.y)) {
                JG.renderOn = !JG.renderOn;
            }
        }
        ScreenUtils.clear(0,0,0,1);
        JG.camera.update();
        // Отрисовываем спрайт
        JG.batch.setProjectionMatrix(JG.camera.combined);
        JG.batch.begin();
        JG.batch.draw(texture,0, 0, w, h);
        JG.batch.draw(textureBall,
                ball.body.getPosition().x- ball.r,
                ball.body.getPosition().y- ball.r,
                0, ball.r*2, ball.r*2, ball.r*2,
                1,1,0,0,0,100,100,false,false);
        JG.batch.draw(textureRender,
                ballRender.body.getPosition().x- ballRender.r,
                ballRender.body.getPosition().y- ballRender.r,
                0, ballRender.r*2, ballRender.r*2, ballRender.r*2,
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
    }
}
