package com.myreliablegames.kittycart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myreliablegames.kittycart.entities.MineCart;
import com.myreliablegames.kittycart.entities.Track;
import com.myreliablegames.kittycart.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 5/17/2016.
 */
public class Level {

    public static final String TAG = "Level";

    private Viewport viewport;
    private Camera camera;

    private MineCart mineCart;
    private List<Track> tracks;

    public Level() {
        viewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        mineCart = new MineCart();
        camera = new OrthographicCamera();
        camera.position.set(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2, 0);
        viewport.setCamera(camera);
        camera.update();

        tracks = new ArrayList<Track>();

        for (int i = 0; i < 6; i++) {

            if ( i < 5) {
                tracks.add(new Track(Track.TrackType.STRAIGHT, new Vector2(50 + (i * 120), 200), -80));
            } else {
                tracks.add(new Track(Track.TrackType.UP, new Vector2(50 + (i * 120), 200), -80));
            }
        }
    }

    public void update(float delta) {

        for (Track track : tracks) {
            track.update(delta);
        }

        mineCart.update(delta, tracks);
        camera.update();
    }

    public void render(SpriteBatch batch) {

        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        Gdx.app.log(TAG, "Batch Begin");

        for (Track track : tracks) {
            track.render(batch);
        }

        mineCart.render(batch);

        batch.end();

    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
