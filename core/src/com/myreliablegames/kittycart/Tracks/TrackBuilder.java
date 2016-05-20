package com.myreliablegames.kittycart.Tracks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.myreliablegames.kittycart.entities.Track;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/18/2016.
 * <p/>
 * Builds sections of track.
 */
public class TrackBuilder {

    private Array<Track> railSection = new Array();
    private float speed;
    private Vector2 nextPosition;

    private final float TRACK_MAX_HEIGHT = Constants.WORLD_HEIGHT - (2 * Constants.TRACK_WIDTH);
    private final float TRACK_MIN_HEIGHT = Constants.TRACK_WIDTH;


    public TrackBuilder(Vector2 startPosition, float speed) {
        nextPosition = startPosition;
        this.speed = speed;
    }

    public TrackBuilder addStraight(int howMany) {
        verifyHowMany(howMany);
        for (int i = 0; i < howMany; i++) {
            Track track = new Track(Track.TrackType.STRAIGHT, new Vector2(nextPosition), this.speed);
            railSection.add(track);
            nextPosition = nextPosition.set(nextPosition.x + Constants.TRACK_WIDTH, nextPosition.y);
        }
        return this;
    }

    public TrackBuilder addUp(int howMany) {
        verifyHowMany(howMany);
        for (int i = 0; i < howMany; i++) {
            Track track = new Track(Track.TrackType.UP, new Vector2(nextPosition.x, nextPosition.y + Constants.TRACK_WIDTH - Constants.STRAIGHT_TRACK_THICKNESS), this.speed);
            if (track.getPosition().y <= TRACK_MAX_HEIGHT) {
                railSection.add(track);
                nextPosition = nextPosition.set(nextPosition.x + Constants.TRACK_WIDTH, nextPosition.y + Constants.TRACK_WIDTH - Constants.STRAIGHT_TRACK_THICKNESS);
            }
        }
        return this;
    }

    public TrackBuilder addDown(int howMany) {
        verifyHowMany(howMany);
        for (int i = 0; i < howMany; i++) {
            Track track = new Track(Track.TrackType.DOWN, new Vector2(nextPosition.x, nextPosition.y), this.speed);
            if (track.getPosition().y >= TRACK_MIN_HEIGHT) {
                railSection.add(track);
                nextPosition = nextPosition.set(nextPosition.x + Constants.TRACK_WIDTH, nextPosition.y - Constants.TRACK_WIDTH + Constants.STRAIGHT_TRACK_THICKNESS);
            }
        }
        return this;
    }

    public TrackBuilder shiftUp(int howManyLevels) {
        verifyHowMany(howManyLevels);
        for (int i = 0; i < howManyLevels; i++) {
            if ((nextPosition.y + Constants.TRACK_WIDTH) < TRACK_MAX_HEIGHT) {
                nextPosition.y = nextPosition.y + Constants.TRACK_WIDTH;
            }
        }
        return this;
    }

    public TrackBuilder shiftDown(int howManyLevels) {
        verifyHowMany(howManyLevels);
        for (int i = 0; i < howManyLevels; i++) {
            if ((nextPosition.y - Constants.TRACK_WIDTH) > TRACK_MIN_HEIGHT) {
                nextPosition.y = nextPosition.y - Constants.TRACK_WIDTH;
            }
        }
        return this;
    }

    public TrackBuilder addGap(int gapSpan) {
        verifyHowMany(gapSpan);
        for (int i = 0; i < gapSpan; i++) {
            nextPosition.x = nextPosition.x + Constants.TRACK_WIDTH;
        }
        return this;
    }

    public TrackSection build() {
        return new TrackSection(railSection);
    }

    public void clear() {
        railSection.clear();
    }

    private void verifyHowMany(int howMany) {
        if (howMany < 1) {
            throw new RuntimeException("Can't add negative number of tracks, gaps, or shifts.");
        }
    }
}
