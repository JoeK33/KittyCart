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
import com.myreliablegames.kittycart.PlayerPreferences;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/17/2016.
 * The minecart aka the player.
 */
public class MineCart {

    private static final String TAG = "MineCart";
    // Position of minecart is the bottom right corner of texture.
    private Vector2 position;
    private Vector2 prevPosition;
    private Vector2 velocity;
    private final double BOB_SPEED = 15;
    private final double ROTATION_SPEED = 300;
    private final float MAX_ROTATION = 24;
    private boolean wantsToJump;
    private JumpState jumpState;
    private MoveState moveState;
    private long jumpStartTime;
    private Level level;
    private boolean wantsToLongJump;
    private double wiggleDegrees;
    private float bobOffset;
    private float rotationAngle;
    private Cat cat;

    private SparkEmitter sparkEmitter;

    public MineCart(Level level) {
        this.position = new Vector2(Constants.WORLD_WIDTH / 5, (Constants.WORLD_HEIGHT / 4) * 3);
        velocity = new Vector2(0, 0);
        jumpState = JumpState.FALLING;
        prevPosition = new Vector2(position);
        this.level = level;
        moveState = MoveState.DESCENDING;
        wantsToLongJump = false;
        sparkEmitter = new SparkEmitter(this);
        wiggleDegrees = 0;
        bobOffset = 0;
        rotationAngle = 0;
        cat = new Cat();
    }

    public void render(SpriteBatch batch) {
        bobOffset = 0;
        bobOffset = (float) (Math.cos(wiggleDegrees) * 2) - (Constants.STRAIGHT_TRACK_THICKNESS / 2);
        if (jumpState == JumpState.GROUNDED && moveState == MoveState.LEVEL) {
            //Draw the back of the cart, then the cat, then the front.
            batch.draw(PlayerPreferences.getCartBack(), position.x - Constants.MINECART_WIDTH, position.y + bobOffset);
            cat.render(batch, position, bobOffset, 0, jumpState);
            batch.draw(PlayerPreferences.getCartFront(), position.x - Constants.MINECART_WIDTH, position.y + bobOffset);
        } else {

            // Back of cart draws first.
            batch.draw(PlayerPreferences.getCartBack(), position.x - Constants.MINECART_WIDTH,
                    position.y + bobOffset,
                    Constants.MINECART_WIDTH / 2,
                    Constants.MINECART_WIDTH / 2,
                    Constants.MINECART_WIDTH,
                    Constants.MINECART_WIDTH,
                    1f,
                    1f,
                    rotationAngle);

            // Render the cat passenger.
            cat.render(batch, position, bobOffset, rotationAngle, jumpState);

            // Finally the front draws
            batch.draw(PlayerPreferences.getCartFront(), position.x - Constants.MINECART_WIDTH,
                    position.y + bobOffset,
                    Constants.MINECART_WIDTH / 2,
                    Constants.MINECART_WIDTH / 2,
                    Constants.MINECART_WIDTH,
                    Constants.MINECART_WIDTH,
                    1f,
                    1f,
                    rotationAngle);
        }

        sparkEmitter.render(batch);
    }

    public void update(float delta, DelayedRemovalArray<Track> trackList, CoinHandler coinHandler) {
        wiggleDegrees += (BOB_SPEED * delta);
        updateRotationAngle(delta);

        if (wiggleDegrees > 360) {
            wiggleDegrees = 0;
        }

        // Gravity
        velocity.y -= Constants.GRAVITY;

        prevPosition.set(position);
        position.mulAdd(velocity, delta);
        sparkEmitter.update(delta);

        continueJump();

        checkPositionAndMoveState();

        // checkTracks must be called to handle track hit detection.
        boolean madeContact = checkTracks(trackList);

        if (moveState == MoveState.DESCENDING && !madeContact) {
            jumpState = JumpState.FALLING;
        }

        if (jumpState == JumpState.GROUNDED) {
            if (wantsToJump) {
                startJump(wantsToLongJump);
                wantsToJump = false;
                wantsToLongJump = false;
            }
        }

        checkCoins(coinHandler);
        handleMusic();
        cat.update(delta);
    }

    private void checkPositionAndMoveState() {

        float upBuffer = 1f;
        float downBuffer = 2f;
        if (prevPosition.y + upBuffer < position.y) {
            moveState = MoveState.CLIMBING;
        } else if (prevPosition.y - downBuffer > position.y) {
            moveState = MoveState.DESCENDING;
        } else {
            moveState = MoveState.LEVEL;
        }

        // Reset if falling off.
        if (position.y < 0) {
            Assets.getInstance().soundAssets.gameOverSound.play();
            position.y = Constants.WORLD_HEIGHT + (Constants.BACKGROUND_TILE_SIZE / 2);
            level.reset();
            velocity.y = 0;
        }

        if (velocity.y < -Constants.SPEED_LIMIT) {
            velocity.y = -Constants.SPEED_LIMIT;
        }
    }

    // Returns true if minecart touched a rail.  False if no contact.
    private boolean checkTracks(Array<Track> trackList) {
        boolean touchedATrack = false;

        for (Track track : trackList) {

            if (track.getTrackType() == Track.TrackType.TRANSITION && track.getBoundingRectangle().overlaps(getMineCartBoundingRectangle())) {
                level.startTransition();
            }

            if (landedOnTrack(track)) {
                float contactHeight;
                if (track.getTrackType() == Track.TrackType.DOWN) {
                    contactHeight = track.contactHeight(new Vector2(position.x - Constants.MINECART_WIDTH, position.y));
                } else {
                    contactHeight = track.contactHeight(position);
                }
                if (contactHeight > 0) {
                    if (position.y < contactHeight) {

                        if (velocity.y < 0) {
                            velocity.y = 0;
                        }
                        position.y = contactHeight;

                        if (track.getTrackType() == Track.TrackType.DOWN) {
                            velocity.y = Constants.TRACK_SPEED;
                        } else if (track.getTrackType() == Track.TrackType.UP || track.getTrackType() == Track.TrackType.TRANSITION) {
                            velocity.y = -(Constants.TRACK_SPEED);
                            moveState = MoveState.CLIMBING;
                        } else if (track.getTrackType() == Track.TrackType.STRAIGHT) {
                            velocity.y = Constants.GRAVITY;
                        }

                        if (jumpState != JumpState.GROUNDED) {
                            sparkEmitter.spark();
                            Assets.getInstance().soundAssets.railContactSound.play();
                        }

                        jumpState = JumpState.GROUNDED;
                        touchedATrack = true;
                    }
                }
            }
        }

        return touchedATrack;
    }

    private void checkCoins(CoinHandler coinHandler) {
        DelayedRemovalArray<Coin> coins = coinHandler.getCoins();
        for (Coin coin : coins) {
            if (coin.isColliding(getMineCartBoundingRectangle())) {
                coinHandler.remove(coin);
                level.addScore(Constants.COIN_SCORE_VALUE);
                level.addCoin();
                Assets.getInstance().soundAssets.coinPickupSound.play(.5f);
            }
        }

    }

    private void handleMusic() {
        if (jumpState == JumpState.GROUNDED && !level.isPaused()) {
            if (!Assets.getInstance().soundAssets.trainSound.isPlaying()) {
                Assets.getInstance().soundAssets.trainSound.play();
            }
        } else {
            if (Assets.getInstance().soundAssets.trainSound.isPlaying()) {
                Assets.getInstance().soundAssets.trainSound.pause();
            }
        }
    }

    public void pause() {
            Assets.getInstance().soundAssets.trainSound.pause();
    }

    public void unPause() {
            Assets.getInstance().soundAssets.trainSound.play();
    }

    private void updateRotationAngle(float delta) {

        if (moveState == MoveState.DESCENDING && rotationAngle > -MAX_ROTATION) {
            rotationAngle -= ROTATION_SPEED * delta;
        } else if (moveState == MoveState.CLIMBING && rotationAngle < MAX_ROTATION) {
            rotationAngle += ROTATION_SPEED * delta;
        } else if (moveState == MoveState.LEVEL && jumpState == JumpState.GROUNDED) {
            if (rotationAngle > 1) {
                rotationAngle -= ROTATION_SPEED * delta;
            } else if (rotationAngle < -1) {
                rotationAngle += ROTATION_SPEED * delta;
            }
        }

        if (rotationAngle > MAX_ROTATION) {
            rotationAngle = MAX_ROTATION;
        } else if (rotationAngle < -MAX_ROTATION) {
            rotationAngle = -MAX_ROTATION;
        }

    }

    private Rectangle getWheelBoundingRectangle() {

        // Hit detection only works around the minecart wheels.
        return new Rectangle(position.x - Constants.MINECART_WIDTH + Constants.MINECART_EDGE_TO_WHEEL_SIZE, position.y, Constants.MINECART_WIDTH - (2 * Constants.MINECART_EDGE_TO_WHEEL_SIZE), Constants.MINECART_WHEEL_HEIGHT);
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
        if (jumpState == JumpState.GROUNDED) {
            velocity.y = 0;
            if (longJump) {
                jumpState = JumpState.LONG_JUMPING;
                Assets.getInstance().soundAssets.longJumpSound.play();
                velocity.y = Constants.JUMP_SPEED * 1.45f;
            } else {
                jumpState = JumpState.JUMPING;
                Assets.getInstance().soundAssets.jumpSound.play();
                velocity.y = Constants.JUMP_SPEED;
            }
            jumpStartTime = TimeUtils.nanoTime();
            continueJump();
        }
    }

    private void continueJump() {
        if (jumpState == JumpState.JUMPING) {
            if (TimeUtils.timeSinceNanos(jumpStartTime) * MathUtils.nanoToSec < Constants.JUMP_DURATION) {
                //  velocity.y = Constants.JUMP_SPEED;
                //    Gdx.app.log(TAG, "Jumping!");
            } else {
                endJump();
            }
        } else if (jumpState == JumpState.LONG_JUMPING) {
            if (TimeUtils.timeSinceNanos(jumpStartTime) * MathUtils.nanoToSec < Constants.JUMP_DURATION) {
                //  velocity.y = Constants.JUMP_SPEED * 1.5f;
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
            return new Vector2(position.x - Constants.MINECART_WIDTH + Constants.MINECART_EDGE_TO_WHEEL_SIZE - Constants.SPARK_WIDTH / 2, position.y + bobOffset - Constants.SPARK_WIDTH / 2);
        } else {

            return new Vector2(position.x - Constants.MINECART_WIDTH + Constants.MINECART_EDGE_TO_WHEEL_SIZE - Constants.SPARK_WIDTH / 2, position.y - Constants.SPARK_WIDTH / 2);
        }
    }

    enum JumpState {
        JUMPING, FALLING, GROUNDED, LONG_JUMPING
    }

    enum MoveState {
        CLIMBING, DESCENDING, LEVEL
    }

}
