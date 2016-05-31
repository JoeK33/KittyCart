package com.myreliablegames.kittycart.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/19/2016.
 */
public class Coin implements Pool.Poolable {
    private Vector2 position;
    private Vector2 velocity;
    private float stateTime;

    public Coin() {
        position = new Vector2();
        velocity = new Vector2(Constants.TRACK_SPEED, 0);
        stateTime = 0;
    }

    public void init(Vector2 position) {
        this.position = position;
        stateTime = 0;
    }

    public void update(float delta) {
       position.mulAdd(velocity, delta);
        stateTime += delta;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void render(SpriteBatch batch) {
        batch.draw(Assets.getInstance().pickUpAssets.coinAnimation.getKeyFrame(stateTime), position.x, position.y);
    }

    public boolean isColliding(Rectangle rectangle) {
        return rectangle.overlaps(new Rectangle(position.x, position.y, Constants.COIN_SIZE, Constants.COIN_SIZE));
    }

    @Override
    public void reset() {
        position.set(0, 0);
    }
}
