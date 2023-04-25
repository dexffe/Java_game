package com.mygdx.game.objects;

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

    public ImpulseBox(World world, float x, float y, float h, float w, Body body, String course){

        sensorShape = new PolygonShape();
        sensorShape.setAsBox(h, w, new Vector2(x, y), 0); // установка размеров квадрата

        sensorFixtureDef = new FixtureDef();
        sensorFixtureDef.shape = sensorShape;
        sensorFixtureDef.isSensor = true; // Установка флага isSensor в true для создания датчика столкновений

        sensorBody = world.createBody(new BodyDef());
        Fixture fixture = sensorBody.createFixture(sensorFixtureDef);
        fixture.setUserData(course);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA(); // это область, в которую попадает шар
                Fixture fixtureB = contact.getFixtureB();
                //Body bodyA = fixtureA.getBody();
                Body bodyB = fixtureB.getBody(); // это тело шара, который попадает в область

                if (fixtureA.isSensor()) {
                    switch ((String) fixtureA.getUserData()) {
                        case "Left":
                            bodyB.applyLinearImpulse(-0.3f, 0, bodyB.getPosition().x, bodyB.getPosition().y, true);
                            break;
                        case "Right":
                            bodyB.applyLinearImpulse(0.3f, 0, bodyB.getPosition().x, bodyB.getPosition().y, true);
                            break;
                        case "Up":
                            bodyB.applyLinearImpulse(0, 1f, bodyB.getPosition().x, bodyB.getPosition().y, true);
                            break;
                        case "Down":
                            bodyB.applyLinearImpulse(0, -1f, bodyB.getPosition().x, bodyB.getPosition().y, true);
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
        });
        sensorShape.dispose();
    }
}
