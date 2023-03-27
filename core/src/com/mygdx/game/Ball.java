package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

<<<<<<< HEAD

public class Ball {
    Body body;
    Ball(World world, float x, float y, float radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

=======
public class Ball {
    Body body;
    Ball(World world, float x, float y, float radius){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
>>>>>>> origin/master
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
<<<<<<< HEAD
        fixtureDef.restitution = 0.8f; // Make it bounce a little bit
=======
        fixtureDef.restitution = 0.6f;
>>>>>>> origin/master

        Fixture fixture = body.createFixture(fixtureDef);

        circle.dispose();
<<<<<<< HEAD
    }
}
=======

    }
}
>>>>>>> origin/master
