package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Wall {
    Body body;
    Wall(World world, float x, float y, float width, float height){
        // Create our body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(x, y));
        body = world.createBody(bodyDef);
        PolygonShape Box = new PolygonShape();
        Box.setAsBox(width, height); // первый аргумент длинна, второй высота
        body.createFixture(Box, 0.0f);
        Box.dispose();
    }
}
