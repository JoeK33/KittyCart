package com.myreliablegames.kittycart.Tracks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.myreliablegames.kittycart.PlayerPreferences;
import com.myreliablegames.kittycart.Zone;
import com.myreliablegames.kittycart.entities.EntityPools;
import com.myreliablegames.kittycart.entities.Track;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/18/2016.
 */
public class TrackLayer {

    private TrackSectionFactory factory;
    private DelayedRemovalArray<Track> tracksInPlay;
    private int tracksWide;
    private int tracksTraveled;
    private int tracksInZone;
    private int supportsHigh;
    private final int SUPPORT_HEIGHT;
    private boolean onMenu;

    public TrackLayer(boolean onMenu) {
        this.onMenu = onMenu;
        factory = new TrackSectionFactory();
        tracksInPlay = new DelayedRemovalArray<Track>();
        tracksInPlay.addAll(factory.makeStraightSection(new Vector2(0, Constants.WORLD_HEIGHT / 6)).getTracks());
        tracksWide = (int) (Constants.WORLD_WIDTH * 1.5f / Constants.TRACK_WIDTH);
        tracksTraveled = 0;
        tracksInZone = 0;
        SUPPORT_HEIGHT = Assets.getInstance().trackAssets.supports.getRegionHeight();
        supportsHigh = (int) (Constants.WORLD_HEIGHT / SUPPORT_HEIGHT) * 2;
    }

    public void update(float delta) {
        tracksInPlay.begin();

        if (tracksInPlay.size < 1) {
            tracksInPlay.addAll(factory.makeStraightSection(new Vector2(0, Constants.WORLD_HEIGHT / 6)).getTracks());
        }
        while (tracksInPlay.size < tracksWide) {
            Track lastTrack = tracksInPlay.peek();
            Vector2 trackAddPosition = new Vector2(lastTrack.getPosition());
            trackAddPosition.x += Constants.TRACK_WIDTH;

            if (onMenu) {
                tracksInPlay.addAll(factory.makeRandomConnectedSection(trackAddPosition).getTracks());
            } else {
                if (tracksInZone > Constants.TRACKS_PER_ZONE) {
                    tracksInPlay.addAll(factory.makeTransitionSection(trackAddPosition).getTracks());
                    tracksInZone = 0;
                } else {
                    tracksInPlay.addAll(factory.makeCorrespondingSection(Zone.getZone(), trackAddPosition).getTracks());
                }
            }
        }

        for (Track track : tracksInPlay) {
            track.update(delta);
            // Remove tracks that have passed out of play.
            if (track.movedOutOfBounds()) {
                tracksInPlay.removeValue(track, true);
                EntityPools.getInstance().free(track);
                tracksTraveled++;
                tracksInZone++;
            }
        }
        tracksInPlay.end();
    }

    public void transition() {

        // Remove tracks off screen that are a different style.
        tracksInPlay.begin();
        for (Track track: tracksInPlay) {
            if (track.getPosition().x > Constants.WORLD_WIDTH) {
                tracksInPlay.removeValue(track, true);
                EntityPools.getInstance().trackPool.free(track);
            }
        }
        tracksInPlay.end();
    }

    public int getTracksTraveled() {
        return tracksTraveled;
    }

    public void render(SpriteBatch batch) {

        for (Track track : tracksInPlay) {

            // Don't render what you can't see
            if (track.getPosition().x < Constants.WORLD_WIDTH) {
                track.render(batch);
                // Place track supports
                for (int i = 0; i < supportsHigh; i++) {
                    if (PlayerPreferences.coasterModeOn()) {
                        batch.draw(Assets.getInstance().trackAssets.supportsCoaster,
                                track.getPosition().x,
                                track.getPosition().y - SUPPORT_HEIGHT - (i * SUPPORT_HEIGHT));
                    } else {
                        batch.draw(Assets.getInstance().trackAssets.supports,
                                track.getPosition().x,
                                track.getPosition().y - SUPPORT_HEIGHT - (i * SUPPORT_HEIGHT));
                    }
                }
            }
        }
    }

    public void resetDistance() {
        tracksTraveled = 0;
        tracksInZone = 0;
    }

    // Don't send out tracks that are out of the bounds of the game world.
    public DelayedRemovalArray<Track> getTracksInPlay() {
        if (tracksInPlay.size > tracksWide) {
            DelayedRemovalArray<Track> tempTracks = new DelayedRemovalArray<Track>(tracksInPlay);
            tempTracks.removeRange(tracksWide, tempTracks.size - 1);
            return tempTracks;
        } else {
            return tracksInPlay;
        }

    }
}
