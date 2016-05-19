package com.myreliablegames.kittycart.Tracks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.myreliablegames.kittycart.entities.Track;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/18/2016.
 */
public class TrackBuilder {

    private Array<Track> railSection = new Array();
    private float speed;
    private Vector2 nextPosition;

    public TrackBuilder(Vector2 startPosition, float speed) {
        nextPosition = startPosition;
        this.speed = speed;
    }

    public TrackBuilder addStraight(int howMany) {
        for (int i = 0; i < howMany; i++) {
            Track track = new Track(Track.TrackType.STRAIGHT, new Vector2(nextPosition), this.speed);
            railSection.add(track);
            nextPosition = nextPosition.set(nextPosition.x + Constants.TRACK_WIDTH, nextPosition.y);
        }
        return this;
    }

    public TrackBuilder addUp(int howMany) {
        for (int i = 0; i < howMany; i++) {
            Track track = new Track(Track.TrackType.UP, new Vector2(nextPosition.x, nextPosition.y + Constants.TRACK_WIDTH - Constants.STRAIGHT_TRACK_THICKNESS), this.speed);
            railSection.add(track);
            nextPosition = nextPosition.set(nextPosition.x + Constants.TRACK_WIDTH, nextPosition.y + Constants.TRACK_WIDTH - Constants.STRAIGHT_TRACK_THICKNESS);
        }
        return this;
    }

    public TrackBuilder addDown(int howMany) {
        for (int i = 0; i < howMany; i++) {
            Track track = new Track(Track.TrackType.DOWN, new Vector2(nextPosition.x, nextPosition.y), this.speed);
            railSection.add(track);
            nextPosition = nextPosition.set(nextPosition.x + Constants.TRACK_WIDTH, nextPosition.y - Constants.TRACK_WIDTH + Constants.STRAIGHT_TRACK_THICKNESS);
        }
        return this;
    }

    public TrackBuilder shiftUp(int howManyLevels) {
        for (int i = 0; i < howManyLevels; i++) {
            if ((nextPosition.y + Constants.TRACK_WIDTH) < Constants.WORLD_HEIGHT) {
                nextPosition.y = nextPosition.y + Constants.TRACK_WIDTH;
            }
        }
        return this;
    }

    public TrackBuilder shiftDown(int howManyLevels) {
        for (int i = 0; i < howManyLevels; i++) {
            if ((nextPosition.y - Constants.TRACK_WIDTH) > 0) {
                nextPosition.y = nextPosition.y - Constants.TRACK_WIDTH;
            }
        }
        return this;
    }

    public TrackBuilder addGap(int gapSpan) {
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
}
