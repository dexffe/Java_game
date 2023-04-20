package objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Arc {

    public Body body;
    BodyDef groundBodyDef;
    public float r;

    ChainShape chainShape;
    Vector2[] vertices;
    public Arc(World world, float x, float y, int segments, float startAngle, float endAngle, float radius){

        groundBodyDef = new BodyDef();
        groundBodyDef.position.set(x, y);
        body = world.createBody(groundBodyDef);

        chainShape = new ChainShape();
        Vector2[] vertices = new Vector2[segments];
        for (int i = 0; i < segments; i++) {
            float angle = startAngle + (endAngle - startAngle) * i / (segments - 1);
            vertices[i] = new Vector2(radius * MathUtils.cos(angle), radius * MathUtils.sin(angle));
        }
        chainShape.createChain(vertices);

        // Создание фикстуры для тела
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = chainShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.3f;
        body.createFixture(fixtureDef);

        chainShape.dispose();
    }
}
