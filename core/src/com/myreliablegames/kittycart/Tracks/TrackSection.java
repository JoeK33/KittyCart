package com.myreliablegames.kittycart.Tracks;

import com.badlogic.gdx.utils.Array;
import com.myreliablegames.kittycart.entities.Track;

/**
 * Created by Joe on 5/18/2016.
 */
public class TrackSection {

    private Array<Track> tracks;

    public TrackSection(Array<Track> tracks) {
        this.tracks = tracks;
    }

    public Array<Track> getTracks() {
        return tracks;
    }
}
