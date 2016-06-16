package com.myreliablegames.kittycart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myreliablegames.kittycart.util.Assets;

/**
 * Created by Joe on 5/17/2016.
 */
public class GameScreen extends ScreenAdapter {

    private SpriteBatch batch;
    private Level level;
    private KittyCartGame game;
    private ActionResolver resolver;

    public GameScreen(KittyCartGame game, ActionResolver resolver) {
        this.game = game;
        this.resolver = resolver;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        level = new Level(this, resolver);
    }

    @Override
    public void pause() {
       if (!level.isPaused()) {
           level.pauseToggle();
       }
    }

    @Override
    public void dispose() {
        Assets.getInstance().dispose();
        level.dispose();
        batch.dispose();
    }

    public void goToMenuScreen() {
        game.openMainMenu();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 02f, 02f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        level.update(delta);
        level.render(batch);

    }

    @Override
    public void resize(int width, int height) {
        level.resize(width, height);
    }
}
