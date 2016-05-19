package com.myreliablegames.kittycart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myreliablegames.kittycart.util.Assets;

/**
 * Created by Joe on 5/17/2016.
 */
public class GameScreen extends ScreenAdapter {
    SpriteBatch batch;
    Level level;
    FPSLogger logger;

    @Override
    public void show() {
        AssetManager assetManager = new AssetManager();
        Assets.getInstance().init(assetManager);
        batch = new SpriteBatch();
        level = new Level();
        logger = new FPSLogger();
    }

    @Override
    public void dispose() {
        Assets.getInstance().dispose();
        level.dispose();
        batch.dispose();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 1, 0, 1);
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
