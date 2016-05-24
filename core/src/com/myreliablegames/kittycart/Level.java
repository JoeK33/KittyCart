package com.myreliablegames.kittycart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myreliablegames.kittycart.Tracks.TrackLayer;
import com.myreliablegames.kittycart.entities.CoinHandler;
import com.myreliablegames.kittycart.entities.MineCart;
import com.myreliablegames.kittycart.util.Assets;
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
    private TrackLayer trackLayer;
    private HUD hud;
    private int score;
    private boolean paused;
    private CoinHandler coinHandler;
    private int coinsCollected = 0;
    private BackGround backGround;

    public Level() {
        viewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        mineCart = new MineCart(this);

        camera = new FollowCamera(new OrthographicCamera(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2));
        hud = new HUD(camera.getCamera());
        viewport.setCamera(camera.getCamera());
        camera.update(mineCart);

        Controller controller = new Controller(mineCart, this);
        Gdx.input.setInputProcessor(controller);

        trackLayer = new TrackLayer();
        coinHandler = new CoinHandler(trackLayer);
        backGround = new BackGround();
        paused = false;
    //    Assets.getInstance().soundAssets.gameMusic.play();

    }

    public void dispose() {
        hud.dispose();
    }

    public void update(float delta) {
        if (!paused) {
            score++;
            coinHandler.update(delta);
            backGround.update(delta);
            trackLayer.update(delta);
            mineCart.update(delta, trackLayer.getTracksInPlay(), coinHandler);
            camera.update(mineCart);
        }
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void reset() {
        score = 0;
        coinsCollected = 0;
        trackLayer.resetDistance();
    }

    public void render(SpriteBatch batch) {

        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        backGround.render(batch);
        trackLayer.render(batch);
        coinHandler.render(batch);
        mineCart.render(batch);
        hud.render(batch, score, trackLayer.getTracksTraveled(), coinsCollected);

        batch.end();

    }

    public void pauseToggle() {
        if (paused) {
            paused = false;
        //    Assets.getInstance().soundAssets.gameMusic.play();
        } else {
            paused = true;
        //    Assets.getInstance().soundAssets.gameMusic.pause();
        }
    }

    public void addCoin() {
        coinsCollected++;
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
