package objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Swing extends Actor {
    Body constructionSwing;
    public static Body bodySwing;

    public Swing(World world, float x, float y){
        constructionSwing = construction_swing(world);
        bodySwing = body_swing(world);

        revolute_joint_def(world, constructionSwing, bodySwing);
    }
    public void revolute_joint_def(World world, Body body1, Body body2) {
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.initialize(body1, body2, new Vector2(10, 15));
        world.createJoint(jointDef);
    }

    public Body construction_swing(World world) {
        BodyDef standBodyDef = new BodyDef();
        standBodyDef.type = BodyDef.BodyType.StaticBody;
        standBodyDef.position.set(10, 10);
        Body standBody = world.createBody(standBodyDef);

        PolygonShape standShape = new PolygonShape();
        standShape.setAsBox(0.5f, 5f);
        FixtureDef standFixtureDef = new FixtureDef();
        standFixtureDef.shape = standShape;
        standFixtureDef.density = 1f;
        standFixtureDef.friction = 0.5f;
        standFixtureDef.restitution = 0.3f;
        standBody.createFixture(standFixtureDef);
        standShape.dispose();
        return standBody;
    }

    public Body body_swing(World world) {
        BodyDef swingBodyDef = new BodyDef();
        swingBodyDef.type = BodyDef.BodyType.DynamicBody;
        swingBodyDef.position.set(10, 15);
        Body swingBody = world.createBody(swingBodyDef);

        PolygonShape swingShape = new PolygonShape();
        swingShape.setAsBox(1f, 0.1f);
        FixtureDef swingFixtureDef = new FixtureDef();
        swingFixtureDef.shape = swingShape;
        swingFixtureDef.density = 1f;
        swingFixtureDef.friction = 0.5f;
        swingFixtureDef.restitution = 0.3f;
        swingBody.createFixture(swingFixtureDef);
        swingShape.dispose();
        return swingBody;
    }
}
