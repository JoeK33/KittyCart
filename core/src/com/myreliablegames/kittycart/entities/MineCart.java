package com.myreliablegames.kittycart.entities;

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

/**
 * Created by Joe on 5/17/2016.
 */
public class MineCart {

    private static final String TAG = "MineCart";
    // Position of minecart is the front corner of texture.
    private Vector2 position;
    private Vector2 prevPosition;
    private Vector2 velocity;
    private final double BOB_SPEED = 15;
    private final double ROTATION_SPEED = 300;
    private boolean wantsToJump;
    private JumpState jumpState;
    private MoveState moveState;
    private long jumpStartTime;
    private Level level;
    private boolean wantsToLongJump;
    private double wiggleDegrees;
    private float bob_offset;
    private float rotationAngle;
    private final float MAX_ROTATION = 30;

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
        wiggleDegrees = 0;
        bob_offset = 0;
        rotationAngle = 0;
    }

    public void render(SpriteBatch batch) {
        bob_offset = 0;

        if (jumpState == JumpState.GROUNDED) {
            bob_offset = (float) (Math.cos(wiggleDegrees) * 2) - (Constants.STRAIGHT_TRACK_THICKNESS / 2);
        }

        batch.draw(
                Assets.getInstance().mineCartAssets.minecart,
                position.x - Constants.MINECART_WIDTH,
                position.y + bob_offset,
                Constants.MINECART_WIDTH / 2,
                Constants.MINECART_WIDTH / 2,
                Constants.MINECART_WIDTH,
                Constants.MINECART_WIDTH,
                1f,
                1f,
                rotationAngle,
                1,
                1,
                64,
                64,
                false,
                false

                );

        sparkEmitter.render(batch);
    }


    public void update(float delta, Array<Track> trackList, CoinHandler coinHandler) {
        wiggleDegrees += (BOB_SPEED * delta);
        updateRotationAngle(delta);

        if (velocity.y < -Constants.SPEED_LIMIT) {
            velocity.y = -Constants.SPEED_LIMIT;
        }

        if (wiggleDegrees > 360) {
            wiggleDegrees = 0;
        }

        prevPosition.set(position);
        position.mulAdd(velocity, delta);
        sparkEmitter.update(delta);

        // Gravity
        velocity.y -= Constants.GRAVITY;

        float buffer = 1f;
        if (prevPosition.y + buffer < position.y) {
            moveState = MoveState.CLIMBING;
        } else if (prevPosition.y > position.y + buffer) {
            moveState = MoveState.DESCENDING;
            if (jumpState == JumpState.GROUNDED) {
                jumpState = JumpState.FALLING;
            }
        } else {
            moveState = MoveState.LEVEL;
        }

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
                        if (track.getTrackType() == Track.TrackType.DOWN) {
                            velocity.y = Constants.TRACK_SPEED;

                        } else if (track.getTrackType() == Track.TrackType.UP) {
                            velocity.y = -(Constants.TRACK_SPEED - (Constants.GRAVITY));
                            moveState = MoveState.CLIMBING;

                        }

                        if (moveState == MoveState.DESCENDING) {
                            sparkEmitter.spark();
                        }

                        jumpState = JumpState.GROUNDED;
                    }
                }
            }
        }

        if (jumpState == JumpState.GROUNDED) {
            if (wantsToJump) {
                startJump(wantsToLongJump);
                wantsToJump = false;
                wantsToLongJump = false;
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
    }

    private void updateRotationAngle(float delta) {
        if(moveState == MoveState.DESCENDING && rotationAngle > - MAX_ROTATION) {
            rotationAngle -= ROTATION_SPEED * delta;
        } else if(moveState == MoveState.CLIMBING && rotationAngle <  MAX_ROTATION) {
            rotationAngle += ROTATION_SPEED * delta;
        } else if (moveState == MoveState.LEVEL && jumpState == JumpState.GROUNDED){
            rotationAngle = 0;
        }
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
        float jumpTryTime = .10f;
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
        if (jumpState == JumpState.GROUNDED)
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

        if (jumpState == JumpState.GROUNDED) {
            return new Vector2(position.x - Constants.MINECART_WIDTH + Constants.MINECART_EDGE_TO_WHEEL_SIZE - Constants.SPARK_WIDTH, position.y + bob_offset);
        } else {

         return new Vector2(position.x - Constants.MINECART_WIDTH + Constants.MINECART_EDGE_TO_WHEEL_SIZE - Constants.SPARK_WIDTH, position.y);
        }
    }

    enum JumpState {
        JUMPING, FALLING, GROUNDED, LONG_JUMPING
    }

    enum MoveState {
        CLIMBING, DESCENDING, LEVEL
    }

}
