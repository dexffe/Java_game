package com.mygdx.game.worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.JavaGame;

public class WorldAbout implements Screen {
    JavaGame JG;
    float w = 16, h = 9;
    Texture texture;
    World world;
    OrthographicCamera camera;
    SpriteBatch batch;

    public WorldAbout(JavaGame context) {
        world = new World(new Vector2(0, -10), false);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        camera.position.set(w, h/2, 0);
        texture = new Texture(Gdx.files.internal("pause.png"));
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0,0,0,1);
        camera.update();
        // Отрисовываем спрайт
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //batch.draw(texture,0, 0, w, h);
        batch.end();
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
        batch.dispose();
    }
}
