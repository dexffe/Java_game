package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box {

    public Body body;
    PolygonShape boxShape;

    public Box(World world, float[] vertices, boolean dynamic){

        ChainShape chainShape = new ChainShape();
        chainShape.createChain(vertices);

        BodyDef bodyDef = new BodyDef();

        if (dynamic){
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        } else {bodyDef.type = BodyDef.BodyType.StaticBody;}

        Body body = world.createBody(bodyDef);
        body.createFixture(chainShape, 1);
        chainShape.dispose();

        // Рисуем цепную форму
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < vertices.length / 2 - 1; i++) {
            shapeRenderer.line(vertices[i * 2], vertices[i * 2 + 1], vertices[(i + 1) * 2], vertices[(i + 1) * 2 + 1]);
        }
        shapeRenderer.end();
    }
}
