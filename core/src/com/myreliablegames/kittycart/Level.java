package com.myreliablegames.kittycart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myreliablegames.kittycart.entities.MineCart;
import com.myreliablegames.kittycart.entities.Track;
import com.myreliablegames.kittycart.util.Constants;
import com.myreliablegames.kittycart.util.FollowCamera;

/**
 * Created by Joe on 5/17/2016.
 */
public class Level {

    public static final String TAG = "Level";

    private Viewport viewport;
    private FollowCamera camera;
    private MineCart mineCart;
    private com.myreliablegames.kittycart.Tracks.TrackLayer trackLayer;
    private HUD hud;
    private int score;

    public Level() {
        viewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        mineCart = new MineCart(this);


        camera = new FollowCamera(new OrthographicCamera(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2));
        hud = new HUD(camera.getCamera());
        viewport.setCamera(camera.getCamera());
        camera.update(mineCart);

        Controller controller = new Controller(mineCart);
        Gdx.input.setInputProcessor(controller);

       trackLayer = new com.myreliablegames.kittycart.Tracks.TrackLayer();

    }

    public void dispose() {
        hud.dispose();
    }

    public void update(float delta) {
        score++;

        trackLayer.update(delta);
        mineCart.update(delta, trackLayer.getTracksInPlay());
        camera.update(mineCart);
    }

    public void render(SpriteBatch batch) {

        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        trackLayer.render(batch);
        mineCart.render(batch);
        hud.render(batch, score);

        batch.end();

    }

    public void resetScore() {
        score = 0;
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
