package com.mygdx.game.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box {

    public Body body;
    BodyDef bodyDef;
    FixtureDef fixtureDef;

    public Box(World world, float[] vertices, boolean dynamic){

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);

        bodyDef = new BodyDef();
        if (dynamic){
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        } else {bodyDef.type = BodyDef.BodyType.StaticBody;}

        body = world.createBody(bodyDef);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;


        body.createFixture(fixtureDef);
        shape.dispose();
    }
}
