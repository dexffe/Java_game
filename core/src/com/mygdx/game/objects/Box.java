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
    public float width, height;

    public Box(World world, float[] vertices, boolean dynamic, float x, float y){

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);

        bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
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

        height = vertices[1]-vertices[3];
        width = vertices[0]-vertices[6];
    }

    public float getX(){
        return body.getPosition().x;
    }

    public float getY() {
        return body.getPosition().y;
    }
}
