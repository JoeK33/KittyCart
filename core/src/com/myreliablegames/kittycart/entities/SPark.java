package com.myreliablegames.kittycart.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Joe on 5/19/2016.
 */
public class Spark {

    private Vector2 position;
    private Vector2 velocity;
    private final float REMOVAL_BUFFER = 10;

    public Spark(Vector2 position) {
        this.position = position;
        velocity = new Vector2(- getWiggleRoom(), getWiggleRoom());
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

}
