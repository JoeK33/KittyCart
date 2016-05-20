package com.myreliablegames.kittycart.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.myreliablegames.kittycart.Level;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

import java.util.ArrayList;

/**
 * Created by Joe on 5/17/2016.
 */
public class MineCart {

    private static final String TAG = "MineCart";
    // Position of minecart is the front corner of texture.
    private Sprite sprite;
    private Vector2 position;
    private Vector2 prevPosition;
    private Vector2 velocity;
    private boolean wantsToJump;
    private JumpState jumpState;
    private MoveState moveState;
    private long jumpStartTime;
    private Level level;
    private boolean wantsToLongJump;

    private SparkEmitter sparkEmitter;

    public MineCart(Level level) {
        this.position = new Vector2(Constants.WORLD_WIDTH / 5, Constants.WORLD_HEIGHT / 2);
        velocity = new Vector2(0, 0);
        jumpState = JumpState.FALLING;
        prevPosition = new Vector2(position);
        this.level = level;
        moveState = MoveState.DESCENDING;
        wantsToLongJump = false;
        sparkEmitter = new SparkEmitter(this);
        sprite = new Sprite(Assets.getInstance().mineCartAssets.minecart);
    }

    public void render(SpriteBatch batch) {
        // batch.draw(Assets.getInstance().mineCartAssets.minecart, position.x - Constants.MINECART_WIDTH, position.y);

/*
        if (moveState == MoveState.CLIMBING) {
            sprite.setRotation(45);
        } else if (moveState == MoveState.DESCENDING) {
            sprite.setRotation(-45);
        } else {
            sprite.setRotation(0);
        }
        */
        sprite.draw(batch);
        sparkEmitter.render(batch);
    }


    public void update(float delta, Array<Track> trackList, CoinHandler coinHandler) {

        setPreviousPosition(position);

        position.mulAdd(velocity, delta);
        sprite.setPosition(position.x - Constants.MINECART_WIDTH, position.y);
        sparkEmitter.update(delta);

        // Gravity
        velocity.y -= Constants.GRAVITY;

        continueJump();

        for (Track track : trackList) {
            if (landedOnTrack(track)) {
                float contactHeight;
                if (track.getTrackType() == Track.TrackType.DOWN) {
                    contactHeight = track.contactHeight(new Vector2(position.x - Constants.MINECART_WIDTH, position.y));
                } else {
                    contactHeight = track.contactHeight(position);
                }
                if (contactHeight > 0) {
                    if (position.y < contactHeight) {
                        velocity.y = 0;
                        position.y = contactHeight;
                        if (moveState == MoveState.DESCENDING) {
                            sparkEmitter.spark();
                            //Gdx.app.log(TAG, "Sparking!");
                            velocity.y += Constants.TRACK_SPEED;
                        } else if (track.getTrackType() == Track.TrackType.UP) {
                            velocity.mulAdd(new Vector2(0, Constants.GRAVITY), 1);
                        }

                        jumpState = JumpState.GROUNDED;

                        if (wantsToJump) {
                            startJump(wantsToLongJump);
                            wantsToJump = false;
                            wantsToLongJump = false;
                        }


                    }
                }
            }
        }

        DelayedRemovalArray<Coin> coins = coinHandler.getCoins();
        for (Coin coin : coins) {
            if (coin.isColliding(getMineCartBoundingRectangle())) {
                coinHandler.remove(coin);
                level.addScore(Constants.COIN_SCORE_VALUE);
                level.addCoin();
            }
        }

        // Reset if falling off.
        if (position.y < 0) {
            position.y = Constants.WORLD_HEIGHT;
            level.reset();
            velocity.y = 0;
        }

        // Determine how the cart is moving.
        float positionBuffer = 2;

        if (prevPosition.y + positionBuffer < position.y) {
            moveState = MoveState.CLIMBING;
        } else if (prevPosition.y > position.y + positionBuffer) {
            moveState = MoveState.DESCENDING;
        } else {
            moveState = MoveState.LEVEL;
        }
    }

    // The previous position is the position 10 frames ago.
    private ArrayList<Vector2> previousPositions = new ArrayList<Vector2>();

    private void setPreviousPosition(Vector2 position) {

        if (previousPositions.size() < 10) {
            previousPositions.add(position);
        } else {
            previousPositions.remove(0);
            previousPositions.add(position);
        }

        prevPosition.set(previousPositions.get(0));
    }

    private Rectangle getWheelBoundingRectangle() {

        // Hit detection only works around the minecart wheels.
        return new Rectangle(position.x - Constants.MINECART_WIDTH, position.y, Constants.MINECART_WIDTH, Constants.MINECART_WHEEL_HEIGHT);
    }

    private Rectangle getMineCartBoundingRectangle() {

        // Whole cart detection for coins or whatever.
        return new Rectangle(position.x - Constants.MINECART_WIDTH, position.y, Constants.MINECART_WIDTH, Constants.MINECART_WIDTH);
    }

    public void jump() {

        wantsToJump = true;

        Timer jumpTimer = new Timer();

        // If player does not jump in try time, cancel the request to jump.
        float jumpTryTime = .25f;
        jumpTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                wantsToJump = false;
                wantsToLongJump = false;
            }
        }, jumpTryTime);

    }

    private boolean landedOnTrack(Track track) {

        if (track.getTrackType() == Track.TrackType.DOWN) {
            if (track.getBoundingRectangle().overlaps(getWheelBoundingRectangle()) && moveState == MoveState.DESCENDING) {
                return true;
            }
        } else {
            return track.getBoundingRectangle().overlaps(getWheelBoundingRectangle()) && moveState != MoveState.CLIMBING;
        }

        return false;
    }

    public void longLump() {
        wantsToLongJump = true;
        jump();
    }

    private void startJump(boolean longJump) {
        if (longJump) {
            jumpState = JumpState.LONG_JUMPING;
        } else {
            jumpState = JumpState.JUMPING;
        }
        jumpStartTime = TimeUtils.nanoTime();
        continueJump();
    }

    private void continueJump() {
        if (jumpState == JumpState.JUMPING) {
            if (TimeUtils.timeSinceNanos(jumpStartTime) * MathUtils.nanoToSec < Constants.JUMP_DURATION) {
                velocity.y = Constants.JUMP_SPEED;
                //    Gdx.app.log(TAG, "Jumping!");
            } else {
                endJump();
            }
        } else if (jumpState == JumpState.LONG_JUMPING) {
            if (TimeUtils.timeSinceNanos(jumpStartTime) * MathUtils.nanoToSec < Constants.JUMP_DURATION) {
                velocity.y = Constants.JUMP_SPEED * 1.5f;
                //    Gdx.app.log(TAG, "Long Jumping!");
            } else {
                endJump();
            }
        }
    }

    private void endJump() {
        if (jumpState == JumpState.JUMPING || jumpState == JumpState.LONG_JUMPING) {
            jumpState = JumpState.FALLING;
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getSparkPosition() {
        return new Vector2(position.x - Constants.MINECART_WIDTH, position.y);
    }

    enum JumpState {
        JUMPING, FALLING, GROUNDED, LONG_JUMPING
    }

    enum MoveState {
        CLIMBING, DESCENDING, LEVEL
    }

}
