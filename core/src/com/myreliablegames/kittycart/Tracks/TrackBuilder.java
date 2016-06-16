package com.myreliablegames.kittycart.Tracks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.myreliablegames.kittycart.entities.EntityPools;
import com.myreliablegames.kittycart.entities.Track;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/18/2016.
 * <p/>
 * Builds sections of track.
 */
public class TrackBuilder {

    private Array<Track> railSection = new DelayedRemovalArray<Track>();
    private float speed;
    private Vector2 nextPosition;

    private final float TRACK_MAX_HEIGHT = Constants.WORLD_HEIGHT - (2 * Constants.TRACK_WIDTH);
    private final float TRACK_MIN_HEIGHT = Constants.TRACK_WIDTH;

    public TrackBuilder(Vector2 startPosition, float speed) {
        nextPosition = startPosition;
        this.speed = speed;
    }

    public TrackBuilder addStraight(int howMany) {
        checkForNegative(howMany);
        for (int i = 0; i < howMany; i++) {
            Track track = EntityPools.getInstance().trackPool.obtain();
            track.init(Track.TrackType.STRAIGHT, new Vector2(nextPosition), this.speed);
            railSection.add(track);
            nextPosition.set(nextPosition.x + Constants.TRACK_WIDTH, nextPosition.y);
        }
        return this;
    }

    public TrackBuilder addTransition() {
        Track track = EntityPools.getInstance().trackPool.obtain();
        track.init(Track.TrackType.TRANSITION, new Vector2(nextPosition), this.speed);
        railSection.add(track);
        nextPosition.set(nextPosition.x + Constants.TRACK_WIDTH, nextPosition.y);

        return this;
    }

    public TrackBuilder addUp(int howMany) {
        checkForNegative(howMany);
        for (int i = 0; i < howMany; i++) {
            Track track = EntityPools.getInstance().trackPool.obtain();
            track.init(Track.TrackType.UP, new Vector2(nextPosition.x, nextPosition.y + Constants.TRACK_WIDTH - Constants.STRAIGHT_TRACK_THICKNESS), this.speed);
            if (track.getPosition().y <= TRACK_MAX_HEIGHT) {
                railSection.add(track);
                nextPosition.set(nextPosition.x + Constants.TRACK_WIDTH, nextPosition.y + Constants.TRACK_WIDTH - Constants.STRAIGHT_TRACK_THICKNESS);
            } else {
                EntityPools.getInstance().trackPool.free(track);
            }
        }
        return this;
    }

    public TrackBuilder addDown(int howMany) {
        checkForNegative(howMany);
        for (int i = 0; i < howMany; i++) {
            Track track = EntityPools.getInstance().trackPool.obtain();
            track.init(Track.TrackType.DOWN, new Vector2(nextPosition.x, nextPosition.y), this.speed);
            if (track.getPosition().y >= TRACK_MIN_HEIGHT) {
                railSection.add(track);
                nextPosition.set(nextPosition.x + Constants.TRACK_WIDTH, nextPosition.y - Constants.TRACK_WIDTH + Constants.STRAIGHT_TRACK_THICKNESS);
            } else {
                EntityPools.getInstance().trackPool.free(track);
            }
        }
        return this;
    }

    public TrackBuilder shiftUp(int howManyLevels) {
        checkForNegative(howManyLevels);
        for (int i = 0; i < howManyLevels; i++) {
            if ((nextPosition.y + Constants.TRACK_WIDTH) < TRACK_MAX_HEIGHT) {
                nextPosition.y = nextPosition.y + Constants.TRACK_WIDTH;
            }
        }
        return this;
    }

    public TrackBuilder shiftDown(int howManyLevels) {
        checkForNegative(howManyLevels);
        for (int i = 0; i < howManyLevels; i++) {
            if ((nextPosition.y - Constants.TRACK_WIDTH) > TRACK_MIN_HEIGHT) {
                nextPosition.y = nextPosition.y - Constants.TRACK_WIDTH;
            }
        }
        return this;
    }

    public TrackBuilder addGap(int gapSpan) {
        checkForNegative(gapSpan);
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

    private void checkForNegative(int howMany) {
        if (howMany < 1) {
            throw new RuntimeException("Can't add negative number of tracks, gaps, or shifts.");
        }
    }
}
