package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;


public class Gear {
    public Body basis;

    // 4 зубца
    public Body box;
    Body topTooth;
    Body rightTooth;
    Body botTooth;
    Body leftTooth;
    int PPM;
    public float res;

    public Gear(World world, float rotation, float x, float y, boolean n, float resize, int speed, int hf, int _PPM){
        res = resize;
        PPM = _PPM;
        x = x / 2f;
        y = y / 2f;
        basis = createBasis(world, x, y, resize);
        box = createBox(world, x, y, resize);

        box.setTransform(box.getPosition().x, box.getPosition().y, rotation);

        topTooth = createTopTooth(world, x, y, x, y, resize);
        topTooth.setTransform(x, y, rotation);

        rightTooth = createRightTooth(world, x, y, x, y, resize);
        rightTooth.setTransform(x, y, rotation);

        botTooth = createBotTooth(world, x, y, x, y, resize);
        botTooth.setTransform(x, y, rotation);

        leftTooth = createLeftTooth(world, x, y, x, y, resize);
        leftTooth.setTransform(x, y, rotation);

        weldMotJoint(world, box, topTooth);
        weldMotJoint(world, box, rightTooth);
        weldMotJoint(world, box, botTooth);
        weldMotJoint(world, box, leftTooth);

        revJoint(world, basis, box, n, speed, hf);
    }
    // описание связки для обеспечения вращения
    public void revJoint(World world, Body body1, Body body2, boolean n, int speed, int hf) {
        RevoluteJointDef rjd = new RevoluteJointDef();
        if (n == true) {
            rjd.enableMotor = true;
            rjd.motorSpeed = speed;
            rjd.maxMotorTorque = hf;
        }
        rjd.collideConnected = false;
        rjd.bodyA = body1;
        rjd.bodyB = body2;
        rjd.localAnchorA.set(body1.getPosition().x, body1.getPosition().y);
        rjd.localAnchorB.set(body2.getPosition().x, body2.getPosition().y);
        world.createJoint(rjd);
    }

    // неподвижная связка
    public void weldMotJoint(World world, Body body1, Body body2) {
        WeldJointDef rjd = new WeldJointDef();
        rjd.collideConnected = false;
        rjd.bodyA = body1;
        rjd.bodyB = body2;
        rjd.localAnchorA.set(body1.getPosition().x, body1.getPosition().y);
        rjd.localAnchorB.set(body2.getPosition().x, body2.getPosition().y);
        world.createJoint(rjd);
    }

    // создание центра вращения для шестерни с 4 зубьями
    public Body createBox(World world, float dx, float dy, float resize) {
        Vector2[] verticles = new Vector2[4];
        verticles[0] = new Vector2(2f * 10 / PPM * resize + dx,
                2f * 10 / PPM * resize + dy);
        verticles[1] = new Vector2(2f * 10 / PPM * resize + dx,
                -2f * 10 / PPM * resize + dy);
        verticles[2] = new Vector2(-2f * 10 / PPM * resize + dx,
                2f * 10 / PPM * resize + dy);
        verticles[3] = new Vector2(-2f * 10 / PPM * resize + dx,
                -2f * 10 / PPM * resize + dy);
        PolygonShape shape = new PolygonShape();
        Body fBody;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(dx, dy);
        def.fixedRotation = false;
        fBody = world.createBody(def);
        shape.set(verticles);
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.friction = 0.5f;
        fd.density = 3f;
        fd.restitution = 0.5f;
        fBody.createFixture(fd);

        fBody.createFixture(fd);
        shape.dispose();
        return fBody;
    }

    // каждый зубец создается, как полигон с четырьмя вершинами
    public Body createTopTooth(World world, float xo, float yo, float dx, float dy, float resize) {
        PolygonShape shape = new PolygonShape();

        Vector2[] verticles = new Vector2[4];
        verticles[0] = new Vector2(-2f * 10 / PPM * resize + dx,
                2f * 10 / PPM * resize + dy);
        verticles[1] = new Vector2(-0.5f * 10 / PPM * resize + dx,
                8f * 10 / PPM * resize + dy);
        verticles[2] = new Vector2(0.5f * 10 / PPM * resize + dx,
                8f * 10 / PPM * resize + dy);
        verticles[3] = new Vector2(2f * 10 / PPM * resize + dx,
                2f * 10 / PPM * resize + dy);
        Body fBody;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(xo * 10 / PPM + dx, yo * 10 / PPM + dy);
        def.fixedRotation = false;
        shape.set(verticles);
        fBody = world.createBody(def);
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.friction = 0.7f;
        fd.density = 3f;
        fd.restitution = 0.5f;
        fBody.createFixture(fd);

        shape.dispose();
        return fBody;
    }

    public Body createRightTooth(World world, float xo, float yo, float dx, float dy, float resize) {
        PolygonShape shape = new PolygonShape();

        Vector2[] verticles = new Vector2[4];
        verticles[0] = new Vector2(2f * 10 / PPM * resize + dx,
                -2f * 10 / PPM * resize + dy);
        verticles[1] = new Vector2(8f * 10 / PPM * resize + dx,
                0.5f * 10 / PPM * resize + dy);
        verticles[2] = new Vector2(8f * 10 / PPM * resize + dx,
                -0.5f * 10 / PPM * resize + dy);
        verticles[3] = new Vector2(2f * 10 / PPM * resize + dx,
                2f * 10 / PPM * resize + dy);
        Body fBody;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(xo * 10 / PPM + dx, yo * 10 / PPM + dy);
        def.fixedRotation = false;
        shape.set(verticles);
        fBody = world.createBody(def);
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.friction = 0.7f;
        fd.density = 3f;
        fd.restitution = 0.5f;
        fBody.createFixture(fd);
        shape.dispose();
        return fBody;
    }

    public Body createBotTooth(World world, float xo, float yo, float dx, float dy, float resize) {
        PolygonShape shape = new PolygonShape();

        Vector2[] verticles = new Vector2[4];
        verticles[0] = new Vector2(2f * 10 / PPM * resize + dx,
                -2f * 10 / PPM * resize + dy);
        verticles[1] = new Vector2(-0.5f * 10 / PPM * resize + dx,
                -8f * 10 / PPM * resize + dy);
        verticles[2] = new Vector2(0.5f * 10 / PPM * resize + dx,
                -8f * 10 / PPM * resize + dy);
        verticles[3] = new Vector2(-2f * 10 / PPM * resize + dx,
                -2f * 10 / PPM * resize + dy);

        Body fBody;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(xo * 10 / PPM + dx, yo * 10 / PPM + dy);
        def.fixedRotation = false;
        shape.set(verticles);
        fBody = world.createBody(def);
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.friction = 0.7f;
        fd.density = 3f;
        fd.restitution = 0.5f;
        fBody.createFixture(fd);

        shape.dispose();
        return fBody;
    }

    public Body createLeftTooth(World world, float xo, float yo, float dx, float dy, float resize) {
        PolygonShape shape = new PolygonShape();

        Vector2[] verticles = new Vector2[4];
        verticles[0] = new Vector2(-2f * 10 / PPM * resize + dx,
                -2f * 10 / PPM * resize + dy);
        verticles[1] = new Vector2(-8f * 10 / PPM * resize + dx,
                0.5f * 10 / PPM * resize + dy);
        verticles[2] = new Vector2(-8f * 10 / PPM * resize + dx,
                -0.5f * 10 / PPM * resize + dy);
        verticles[3] = new Vector2(-2f * 10 / PPM * resize + dx,
                2f * 10 / PPM * resize + dy);

        Body fBody;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(xo * 10 / PPM + dx, yo * 10 / PPM + dy);
        def.fixedRotation = false;
        shape.set(verticles);
        fBody = world.createBody(def);
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.friction = 0.7f;
        fd.density = 3f;
        fd.restitution = 0.5f;
        fBody.createFixture(fd);

        shape.dispose();
        return fBody;
    }

    public Body createBasis(World world, float dx, float dy, float resize) {
        Vector2[] verticles = new Vector2[4];
        verticles[0] = new Vector2(2f * 10 / PPM / 2 * resize + dx,
                2f * 10 / PPM / 2 * resize + dy);
        verticles[1] = new Vector2(2f * 10 / PPM / 2 * resize + dx,
                -2f * 10 / PPM / 2 * resize + dy);
        verticles[2] = new Vector2(-2f * 10 / PPM / 2 * resize + dx,
                2f * 10 / PPM / 2 * resize + dy);
        verticles[3] = new Vector2(-2f * 10 / PPM / 2 * resize + dx,
                -2f * 10 / PPM / 2 * resize + dy);

        PolygonShape shape = new PolygonShape();
        Body fBody;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(dx, dy);
        def.fixedRotation = false;
        fBody = world.createBody(def);
        shape.set(verticles);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.friction = 0.5f;
        fd.density = 3f;
        fd.restitution = 0.5f;
        fBody.createFixture(fd);

        fBody.createFixture(fd);
        shape.dispose();
        return fBody;
    }
}
