package com.myreliablegames.kittycart.Tracks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.myreliablegames.kittycart.entities.Track;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/18/2016.
 */
public class TrackLayer {

    private TrackSectionFactory factory;
    private DelayedRemovalArray<Track> tracksInPlay;
    private int tracksWide;
    private int tracksTraveled;

    public TrackLayer() {
        factory = new TrackSectionFactory();
        tracksInPlay = new DelayedRemovalArray<Track>();
        tracksInPlay.addAll(factory.makeStraightSection(new Vector2(0, Constants.WORLD_HEIGHT / 6)).getTracks());
        tracksWide = (int) (Constants.WORLD_WIDTH * 1.5f / Constants.TRACK_WIDTH);
        tracksTraveled = 0;

    }

    public void update(float delta){

        tracksInPlay.begin();


        while (tracksInPlay.size < tracksWide) {

            Track lastTrack = tracksInPlay.get(tracksInPlay.size - 1);
            Vector2 trackAddPosition = new Vector2(lastTrack.getPosition());
            trackAddPosition.x += Constants.TRACK_WIDTH;
            tracksInPlay.addAll(factory.makeRandomStepSection(trackAddPosition).getTracks());
        }


        for (Track track : tracksInPlay) {
            track.update(delta);

            // Remove tracks that have passed out of play.
            if (track.movedOutOfBounds()) {
                tracksInPlay.removeValue(track, true);
                tracksTraveled++;
            }
        }
        tracksInPlay.end();

      //  Gdx.app.log("TrackLayer", "Tracks in play: " + tracksInPlay.size);

    }

    public int getTracksTraveled() {
        return tracksTraveled;
    }

    public void render(SpriteBatch batch) {

        for (Track track : tracksInPlay) {
        track.render(batch);
        }

    }

    public void resetDistance() {
        tracksTraveled = 0;
    }

    public Array<Track> getTracksInPlay() {
        return tracksInPlay;
    }
}
