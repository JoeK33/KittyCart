package com.myreliablegames.kittycart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.myreliablegames.kittycart.util.Assets;

/**
 * Created by Joe on 5/17/2016.
 */
public class GameScreen extends ScreenAdapter {

    private SpriteBatch batch;
    private Level level;
    private FPSLogger logger;
    private KittyCartGame game;
    private long pauseBuffer;
    private long lastPause;

    public GameScreen(KittyCartGame game) {
        this.game = game;
        pauseBuffer = TimeUtils.millisToNanos(300);
        lastPause = TimeUtils.nanoTime();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        level = new Level(this);
        logger = new FPSLogger();
    }

    @Override
    public void dispose() {
        Assets.getInstance().dispose();
        level.dispose();
        batch.dispose();
    }

    public void goToMenuScreen() {
        game.setScreen(new MenuScreen(game));
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 02f, 02f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        level.update(delta);
        level.render(batch);
        logger.log();

    }

    @Override
    public void resize(int width, int height) {
        level.resize(width, height);
    }
}
