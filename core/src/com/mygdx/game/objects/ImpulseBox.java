package com.mygdx.game.objects;

import static com.mygdx.game.objects.ImpulseBox.b;
import static com.mygdx.game.objects.ImpulseBox.c;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;



public class ImpulseBox{

    PolygonShape sensorShape;
    FixtureDef sensorFixtureDef;
    Body sensorBody;

    public static String c;
    public static Body b;

    public ImpulseBox(World world, float x, float y, float h, float w, Body body, String course){
        c = course;
        b = body;

        sensorShape = new PolygonShape();
        sensorShape.setAsBox(h, w, new Vector2(x, y), 0); // установка размеров квадрата

        sensorFixtureDef = new FixtureDef();
        sensorFixtureDef.shape = sensorShape;
        sensorFixtureDef.isSensor = true; // Установка флага isSensor в true для создания датчика столкновений
        sensorBody = world.createBody(new BodyDef());
        sensorBody.createFixture(sensorFixtureDef);
        System.out.println(body.getPosition());
        world.setContactListener(new MyContactListener());
        sensorShape.dispose();
    }
}

class MyContactListener implements ContactListener{

    @Override
    public void beginContact(Contact contact) {
        System.out.println(1);
        Fixture fixtureA = contact.getFixtureA();
        //Fixture fixtureB = contact.getFixtureB();
        Body bodyA = fixtureA.getBody();
        //Body bodyB = fixtureB.getBody();

        if (bodyA != b && fixtureA.isSensor()) {
            switch (c) {
                case "Left":
                    b.applyLinearImpulse(-0.3f, 0, b.getPosition().x, b.getPosition().y, true);
                    break;
                case "Right":
                    b.applyLinearImpulse(0.3f, 0, b.getPosition().x, b.getPosition().y, true);
                    break;
                case "Up":
                    b.applyLinearImpulse(0, 1f, b.getPosition().x, b.getPosition().y, true);
                    break;
                case "Down":
                    b.applyLinearImpulse(0, -1f, b.getPosition().x, b.getPosition().y, true);
                    break;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
