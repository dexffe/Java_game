package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Triangle {
    public Body body;

    public Triangle(World world, float x, float y, float[] vertices){
        // Create our body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(x, y));
        body = world.createBody(bodyDef);

        // Создаем форму треугольника
        PolygonShape triangle = new PolygonShape();
        triangle.set(vertices);

        // Создаем фикстуру для тела
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = triangle;
        fixtureDef.density = 0f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);

// Освобождаем ресурсы формы
        triangle.dispose();
    }
}
