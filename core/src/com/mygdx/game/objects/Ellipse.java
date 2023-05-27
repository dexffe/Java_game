package com.mygdx.game.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.JavaGame;

public class Ellipse {
    public Body ovalBody;
   public float radiusX,radiusY;
    public Ellipse(World world, float x, float y, boolean dynamic){
        BodyDef ovalBodyDef = new BodyDef();
        ovalBodyDef.type = BodyDef.BodyType.DynamicBody;
        ovalBodyDef.position.set(x, y);
        ovalBodyDef.angle = 0;
        ovalBody = world.createBody(ovalBodyDef);

        int numSectors = 30;
        float sectorAngle = 2 * MathUtils.PI / numSectors;

        radiusX = 0.4f;
        radiusY = 0.3f;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;
        for (int i = 0; i < numSectors; i++) {
            PolygonShape sector = new PolygonShape();
            float angle = i * sectorAngle;
            Vector2[] vertices = {
                    new Vector2(radiusX * MathUtils.cos(angle), radiusY * MathUtils.sin(angle)),
                    new Vector2(radiusX * MathUtils.cos(angle + sectorAngle), radiusY * MathUtils.sin(angle + sectorAngle)),
                    new Vector2(0, 0)
            };
            sector.set(vertices);
            fixtureDef.shape = sector;
            ovalBody.createFixture(fixtureDef);
            ovalBody.setUserData("Ellipse");
        }
    }
}
