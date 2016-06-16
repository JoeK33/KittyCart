package com.myreliablegames.kittycart.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Joe on 5/19/2016.
 * Sparks that fly off the back wheel of the cart.
 */
public class Spark implements Pool.Poolable {

    private Vector2 position;
    private Vector2 velocity;
    private final float REMOVAL_BUFFER = Constants.BACKGROUND_TILE_SIZE;

    public Spark() {
        position = new Vector2();
        velocity = new Vector2();
    }

    public void init(Vector2 position) {
        this.position.set(position);
        velocity.set(- getWiggleRoom(), getWiggleRoom());
    }

    public void update(float delta) {
        velocity.y -= Constants.GRAVITY;
        position.mulAdd(velocity, delta);
    }

    public void render(SpriteBatch batch) {
            batch.draw(Assets.getInstance().mineCartAssets.spark, position.x, position.y);
    }

    public boolean inBounds() {
        return !(position.y < 0 - REMOVAL_BUFFER || position.x < 0 - REMOVAL_BUFFER);

    }

    private float getWiggleRoom() {

        return (float) (Math.random() * 400);
    }

    @Override
    public void reset() {
        position.set(0, 0);
    }
}
