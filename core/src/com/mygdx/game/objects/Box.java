package com.mygdx.game.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box {

    public Body body;
    PolygonShape boxShape;

    public Box(World world, float x, float y, float w, float h, boolean dynamic){
        BodyDef bodyDef = new BodyDef();
        if (dynamic){
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        } else {bodyDef.type = BodyDef.BodyType.StaticBody;}
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        boxShape = new PolygonShape();
        boxShape.setAsBox(w, h);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = 1f; // Плотность коробки
        fixtureDef.friction = 0.3f; // Коэффициент трения коробки
        fixtureDef.restitution = 0.5f; // Коэффициент упругости коробки

        Fixture fixture = body.createFixture(fixtureDef);

        boxShape.dispose();
    }
}
