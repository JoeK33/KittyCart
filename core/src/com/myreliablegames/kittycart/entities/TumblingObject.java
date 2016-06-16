package com.myreliablegames.kittycart.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.myreliablegames.kittycart.Zone;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 6/13/2016.
 */
public class TumblingObject implements Pool.Poolable {
    private Vector2 position;
    private Vector2 velocity;
    private float rotationAngle = 0;
    private TumbleType type;
    private final float REMOVAL_BUFFER = Constants.BACKGROUND_TILE_SIZE * 2;
    private final float ROTATION_SPEED = 450;
    private boolean clockWise = false;

    public TumblingObject() {
        position = new Vector2();
        velocity = new Vector2();
    }

    public void init(TumbleType type) {
        float randomFactor = (float) (Math.random() * Constants.TRACK_SPEED / 2);
        this.type = type;
        this.position = new Vector2(Constants.WORLD_WIDTH + Constants.TUMBLE_OBJECT_SIZE, Constants.WORLD_HEIGHT);
        if (type == TumbleType.SWAY) {
            velocity = new Vector2((Constants.TRACK_SPEED / 2) + randomFactor, -Constants.GRAVITY * 2 - randomFactor);
        } else {
            velocity = new Vector2((Constants.TRACK_SPEED) + randomFactor, -Constants.GRAVITY * 2 - randomFactor);
        }
    }

    public void update(float delta) {

        if (type == TumbleType.SPIN) {
            velocity.y -= Constants.GRAVITY / 50;
            position.mulAdd(velocity, delta);
            rotationAngle += ROTATION_SPEED * delta;
        } else if (type == TumbleType.SWAY) {
            if (clockWise) {
                rotationAngle -= (ROTATION_SPEED / 4) * delta;
            } else {
                rotationAngle += (ROTATION_SPEED / 4) * delta;
            }
            rotationAngle = rotationAngle % 360;

            if (rotationAngle > 0 && !clockWise) {
                clockWise = true;
            } else if (rotationAngle < -90 && clockWise) {
                clockWise = false;
            }
            velocity.y -= Constants.GRAVITY / 50;
            position.mulAdd(velocity, delta);
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(Zone.getTumblingObject(),
                position.x,
                position.y,
                Constants.TUMBLE_OBJECT_SIZE / 2,
                Constants.TUMBLE_OBJECT_SIZE / 2,
                Constants.TUMBLE_OBJECT_SIZE,
                Constants.TUMBLE_OBJECT_SIZE,
                1f,
                1f,
                rotationAngle
        );
    }

    public boolean inBounds() {
        return !(position.y < 0 - REMOVAL_BUFFER || position.x < 0 - REMOVAL_BUFFER);

    }

    @Override
    public void reset() {
        this.position = new Vector2(Constants.WORLD_WIDTH + Constants.TUMBLE_OBJECT_SIZE, Constants.WORLD_HEIGHT);
        velocity = new Vector2(Constants.TRACK_SPEED, Constants.GRAVITY);
    }

    enum TumbleType {
        SPIN, SWAY
    }

}
