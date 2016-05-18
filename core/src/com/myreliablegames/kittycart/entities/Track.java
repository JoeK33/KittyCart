package com.myreliablegames.kittycart.entities;

import com.badlogic.gdx.Gdx;
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

        if (position.x < 0) {
            position.x = 700;
        }
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

    public TrackType getTrackType() {
        return trackType;
    }

    // Returns the point where the MineCart contacts the track. Returns null if no contact.
    public Vector2 contactsAtPosition(Vector2 position) {

        boolean withinXBounds = (position.x > this.position.x && position.x < this.position.x + Constants.TRACK_WIDTH);

        boolean withinYBounds = (position.y < this.position.y + Constants.TRACK_WIDTH &&
                                 position.y > this.position.y);

        Gdx.app.log(TAG, "in X bounds: " + withinXBounds + " in Y bounds: " + withinYBounds);


        if (withinXBounds && withinYBounds) {
            Gdx.app.log(TAG, "Contact");

            if (trackType == TrackType.UP) {
                Vector2 contactPoint = new Vector2(position.x, this.position.y + Constants.STRAIGHT_TRACK_THICKNESS + climbOffset(this.position, position));
                Gdx.app.log(TAG, "Contact up at " + Float.toString(contactPoint.x) + ", " + Float.toString(contactPoint.y));
                return contactPoint;
            } else if (trackType == TrackType.STRAIGHT) {
                Vector2 contactPoint=  new Vector2(position.x, this.position.y + Constants.STRAIGHT_TRACK_THICKNESS);
                Gdx.app.log(TAG, "Contact straight at " + Float.toString(contactPoint.x) + ", " + Float.toString(contactPoint.y));
                return contactPoint;
            } else if (trackType == TrackType.DOWN) {
                Vector2 contactPoint = new Vector2(position.x, this.position.y + Constants.STRAIGHT_TRACK_THICKNESS - climbOffset(this.position, position));
                Gdx.app.log(TAG, "Contact down at " + Float.toString(contactPoint.x) + ", " + Float.toString(contactPoint.y));
                return contactPoint;
            }
        }
        return null;
    }

    private float climbOffset(Vector2 trackPosition, Vector2 mineCartPosition) {

        float percentRemaining = (trackPosition.x + Constants.TRACK_WIDTH - mineCartPosition.x) / Constants.TRACK_WIDTH;

        float offset = (Constants.TRACK_WIDTH - Constants.STRAIGHT_TRACK_THICKNESS) * (1f - percentRemaining);
        Gdx.app.log(TAG, "Offset " + Float.toString(offset));
        return offset;

    }

    public enum TrackType {
        STRAIGHT, UP, DOWN
    }

}
