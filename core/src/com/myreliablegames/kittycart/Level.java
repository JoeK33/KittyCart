package com.myreliablegames.kittycart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myreliablegames.kittycart.Tracks.TrackLayer;
import com.myreliablegames.kittycart.entities.CoinHandler;
import com.myreliablegames.kittycart.entities.MineCart;
import com.myreliablegames.kittycart.entities.TumblingObjectManager;
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
    private TumblingObjectManager tumblingObjectManager;
    private int coinsCollected = 0;
    private BackGround backGround;
    private long lastTransition;
    private boolean transitioning;
    private boolean tryToTransition;
    private GameScreen gameScreen;
    private Preferences prefs = Gdx.app.getPreferences("My Preferences");
    private ActionResolver resolver;

    public Level(GameScreen gameScreen, ActionResolver resolver) {
        this.resolver = resolver;
        viewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        mineCart = new MineCart(this);
        this.gameScreen = gameScreen;
        Zone.changeZone();
        camera = new FollowCamera(new OrthographicCamera(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2));
        hud = new HUD(camera.getCamera());
        viewport.setCamera(camera.getCamera());
        camera.update(mineCart);

        Controller controller = new Controller(mineCart, this);
        Gdx.input.setInputProcessor(controller);

        trackLayer = new TrackLayer(false);
        coinHandler = new CoinHandler(trackLayer);
        tumblingObjectManager = new TumblingObjectManager();
        backGround = new BackGround();
        paused = false;
        tryToTransition = false;
        lastTransition = TimeUtils.nanoTime();
        Assets.getInstance().soundAssets.getCorrespondingMusic(Zone.getZone()).play();

    }

    public void goToMenu() {
        Assets.getInstance().soundAssets.getCorrespondingMusic(Zone.getZone()).stop();
        gameScreen.goToMenuScreen();
    }

    public void startTransition() {
        tryToTransition = true;
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

            tumblingObjectManager.update(delta);

            if (tryToTransition) {
                transition();
                tryToTransition = false;
            }
        }
        camera.update(mineCart);
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void reset() {

        // Reset the level, save important values.
        int oldCoins = prefs.getInteger("coins", 0);
        int newCoinTotal = oldCoins + coinsCollected;
        prefs.putInteger("coins", newCoinTotal);

        int highScore = prefs.getInteger("highScore", 0);

        if (score > highScore) {
            prefs.putInteger("highScore", score);
        }

        int oldDistance = prefs.getInteger("distance", 0);
        int newDistanceTotal = oldDistance + trackLayer.getTracksTraveled();
        prefs.putInteger("distance", newDistanceTotal);

        prefs.flush();

        score = 0;
        coinsCollected = 0;
        resolver.submitScore(trackLayer.getTracksTraveled());
        trackLayer.resetDistance();
    }

    public void render(SpriteBatch batch) {

        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        // If transitioning between zones, render only white.
        if (transitioning) {
            Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        } else {
            backGround.render(batch, camera.getCamera());
            trackLayer.render(batch);
            coinHandler.render(batch);
            mineCart.render(batch);
            tumblingObjectManager.render(batch);
            hud.render(batch, score, trackLayer.getTracksTraveled(), coinsCollected);
        }

        batch.end();
    }

    public boolean isPaused() {
        return paused;
    }

    public void pauseToggle() {
        if (paused) {
            paused = false;
            Assets.getInstance().soundAssets.getCorrespondingMusic(Zone.getZone()).play();
            mineCart.unPause();
            hud.unPause();
        } else {
            paused = true;
            Assets.getInstance().soundAssets.getCorrespondingMusic(Zone.getZone()).pause();
            hud.pause();
            mineCart.pause();
        }
    }

    private int transitionBufferSeconds = 5;

    private void transition() {
        if (TimeUtils.timeSinceNanos(lastTransition) * MathUtils.nanoToSec > transitionBufferSeconds) {
            lastTransition = TimeUtils.nanoTime();
            transitioning = true;
            Assets.getInstance().soundAssets.getCorrespondingMusic(Zone.getZone()).stop();
            Assets.getInstance().soundAssets.transitionSound.play();
            trackLayer.transition();
            coinHandler.removeOffScreenCoins();
            Timer timer = new Timer();
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    Zone.changeZone();
                    tumblingObjectManager.reset();
                    transitioning = false;
                    Assets.getInstance().soundAssets.getCorrespondingMusic(Zone.getZone()).play();

                }
            }, .5f);

        }
    }

    public void addCoin() {
        coinsCollected++;
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
