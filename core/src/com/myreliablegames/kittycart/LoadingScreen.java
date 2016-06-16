package com.myreliablegames.kittycart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/30/2016.
 */
public class LoadingScreen extends ScreenAdapter {
    private KittyCartGame kittyCartGame;
    private SpriteBatch batch;
    private Viewport viewport;
    private Camera camera;
    private Texture splashLogo;
    private BitmapFont font;
    private long startTime;

    public LoadingScreen(KittyCartGame kittyCartGame) {
        splashLogo = new Texture("myreliablegames.png");
        this.kittyCartGame = kittyCartGame;
        viewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        camera = new OrthographicCamera(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        batch = new SpriteBatch();
        font = new BitmapFont();
        startTime = TimeUtils.nanoTime();
        Assets.getInstance().loadAssets(new AssetManager());
    }

    @Override
    public void show() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        splashLogo.dispose();
    }

    @Override
    public void render(float delta) {

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // .update() is true when loading is complete.
        if (Assets.getInstance().update() && TimeUtils.nanoTime() > startTime + Constants.THREE_SECONDS_NANO) {
            Assets.getInstance().assign();
            kittyCartGame.openMainMenu();
        } else {
            Gdx.gl.glClearColor(0, 0, 0, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.draw(splashLogo, camera.position.x - splashLogo.getWidth() / 2, camera.position.y - splashLogo.getHeight() / 2);
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

}
