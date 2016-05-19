package com.myreliablegames.kittycart.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
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
    private boolean wantsToJump;
    private JumpState state;
    private long jumpStartTime;
    private Level level;
    private Timer jumpTimer;
    private boolean climbing;

    public MineCart(Level level) {
        this.position = new Vector2(Constants.WORLD_WIDTH / 4, Constants.WORLD_HEIGHT / 2);
        velocity = new Vector2(0, 0);
        state = JumpState.FALLING;
        prevPosition = new Vector2(position);
        this.level = level;
        climbing = false;
    }

    public void render(SpriteBatch batch) {
        batch.draw(Assets.getInstance().mineCartAssets.minecart, position.x - Constants.MINECART_WIDTH, position.y);
    }

    private float altitiudeBuffer = 5;

    public void update(float delta, Array<Track> trackList) {
        prevPosition.set(position);

        // Gravity
        velocity.y -= Constants.GRAVITY;

            continueJump();

            for (Track track : trackList) {
                if (track.getBoundingRectangle().overlaps(getBoundingRectangle()) && !climbing) {
                    float contactHeight;
                    if (track.getTrackType() == Track.TrackType.DOWN) {
                        contactHeight = track.contactHeight(new Vector2(position.x - Constants.MINECART_WIDTH, position.y));
                    } else {
                        contactHeight = track.contactHeight(position);
                    }
                    if (contactHeight > 0) {
                        if (position.y < contactHeight) {
                            position.y = contactHeight;
                            velocity.y = 0;
                            state = JumpState.GROUNDED;

                            if (wantsToJump) {
                                startJump();
                                wantsToJump = false;
                            }
                        }
                    }
          //      }
            }
        }

        position.mulAdd(velocity, delta);

        // Reset if falling off.
        if (position.y < 0) {
            position.y = Constants.WORLD_HEIGHT;
            level.resetScore();
        }

        if (prevPosition.y < position.y) {
            climbing = true;
        } else {
            climbing = false;
        }
    }

    private Rectangle getBoundingRectangle() {

        // Hit detection only works around the minecart wheels.
        return new Rectangle(position.x - Constants.MINECART_WIDTH, position.y, Constants.MINECART_WIDTH, Constants.MINECART_WHEEL_HEIGHT);
    }

    private float jumpTryTime = .05f;
    public void jump() {

        wantsToJump = true;

        jumpTimer = new Timer();

        // If player does not jump in try time, cancel the request to jump.
        jumpTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                wantsToJump = false;
            }
        }, jumpTryTime);

    }

    private void startJump() {
        state = JumpState.JUMPING;
        jumpStartTime = TimeUtils.nanoTime();
        continueJump();
    }

    private void continueJump() {
        if (state == JumpState.JUMPING) {
            if (TimeUtils.timeSinceNanos(jumpStartTime) * MathUtils.nanoToSec < Constants.JUMP_DURATION) {
                velocity.y = Constants.JUMP_SPEED;
                Gdx.app.log(TAG, "Jumping!");
            } else {
                endJump();
            }
        }
    }

    private void endJump() {
        if (state == JumpState.JUMPING) {
            state = JumpState.FALLING;
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    enum JumpState {
        JUMPING, FALLING, GROUNDED
    }

}
