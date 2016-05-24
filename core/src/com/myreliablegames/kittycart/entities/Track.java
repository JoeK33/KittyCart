package com.myreliablegames.kittycart.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/17/2016.
 */
public class Track {

    private static final String TAG = "Track";
    private TrackType trackType;
    private Vector2 position;
    private Vector2 velocity;


    public Track(TrackType trackType, Vector2 position, float speed) {
        this.trackType = trackType;
        this.position = position;
        velocity = new Vector2(speed, 0);
    }

    public void update(float delta) {
        position.mulAdd(velocity, delta);
    }

    public boolean movedOutOfBounds() {
        return (position.x < 0 - Constants.TRACK_WIDTH);
    }

    public void render(SpriteBatch batch) {

        if (trackType == TrackType.UP) {
            batch.draw(Assets.getInstance().trackAssets.upTrack, position.x, position.y);
        } else if (trackType == TrackType.STRAIGHT) {
            batch.draw(Assets.getInstance().trackAssets.straightTrack, position.x, position.y);
        } else if (trackType == TrackType.DOWN) {
            batch.draw(Assets.getInstance().trackAssets.downTrack, position.x, position.y);
        }
    }

    public Rectangle getBoundingRectangle() {

        if (trackType == TrackType.STRAIGHT) {
            return new Rectangle(position.x, position.y + Constants.TRACK_WIDTH - Constants.STRAIGHT_TRACK_THICKNESS, Constants.TRACK_WIDTH, Constants.TRACK_WIDTH);
        } else if (trackType == TrackType.DOWN){
            return new Rectangle(position.x, position.y + Constants.STRAIGHT_TRACK_THICKNESS, Constants.TRACK_WIDTH, Constants.TRACK_WIDTH - Constants.STRAIGHT_TRACK_THICKNESS);
        } else {
            return new Rectangle(position.x, position.y, Constants.TRACK_WIDTH, Constants.TRACK_WIDTH);
        }

    }

    public TrackType getTrackType() {
        return trackType;
    }

    // Returns the height where the MineCart contacts the track. Returns -1 if no contact.
    public float contactHeight(Vector2 position) {

        if (trackType == TrackType.UP) {
            float contactPoint = this.position.y + climbOffset(this.position, position) + Constants.STRAIGHT_TRACK_THICKNESS / 2;
            // Gdx.app.log(TAG, "Contact up at " + Float.toString(contactPoint));
            return contactPoint;
        } else if (trackType == TrackType.STRAIGHT) {
            float contactPoint = this.position.y + Constants.TRACK_WIDTH;
            // Gdx.app.log(TAG, "Contact straight at " + Float.toString(contactPoint));
            return contactPoint;
        } else if (trackType == TrackType.DOWN) {
            float contactPoint = this.position.y - climbOffset(this.position,
                            new Vector2(position.x - Constants.MINECART_WIDTH, position.y)) + Constants.STRAIGHT_TRACK_THICKNESS / 2;
           // float contactPoint = this.position.y - climbOffset(this.position, position);
            //  Gdx.app.log(TAG, "Contact down at " + Float.toString(contactPoint));
            return contactPoint;
        }

        return -1;
    }

    private float climbOffset(Vector2 trackPosition, Vector2 mineCartPosition) {

        float percentRemaining = (trackPosition.x + Constants.TRACK_WIDTH - mineCartPosition.x) / Constants.TRACK_WIDTH;

        float offset = (Constants.TRACK_WIDTH - Constants.STRAIGHT_TRACK_THICKNESS) * (1f - percentRemaining);
        //Gdx.app.log(TAG, "Offset " + Float.toString(offset));
        return offset;

    }

    public Vector2 getPosition() {
        return position;
    }

    public enum TrackType {
        STRAIGHT, UP, DOWN
    }

}
