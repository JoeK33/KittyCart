package com.myreliablegames.kittycart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/26/2016.
 */
public class OptionsScreen extends ScreenAdapter {

    private KittyCartGame kittyCartGame;
    private Viewport viewport;
    private Camera camera;
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    private Table table;
    private BitmapFont font;
    private OptionsBackground background;
    private OptionsButtons customizeButtons;
    private OptionsScreenHUD customizeScreenHUD;
    private ActionResolver resolver;

    public OptionsScreen(KittyCartGame game, ActionResolver resolver) {
        background = new OptionsBackground();
        viewport = new ExtendViewport((Constants.WORLD_WIDTH / 4) * 3, (Constants.WORLD_HEIGHT / 4) * 3);
        camera = new OrthographicCamera(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2);
        camera.position.set(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2, 0);
        viewport.setCamera(camera);
        batch = new SpriteBatch();
        skin = new Skin();
        stage = new Stage(viewport);
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Gdx.input.setInputProcessor(stage);
        table = new Table(skin);
        table.defaults().width(Constants.TABLE_CELL_WIDTH).height(Constants.TABLE_CELL_HEIGHT).pad(10).expandX();
        this.kittyCartGame = game;
        this.resolver = resolver;

        customizeButtons = new OptionsButtons(table, skin, font, resolver);

        stage.addActor(table);
        customizeScreenHUD = new OptionsScreenHUD(camera, font);

    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();

    }

    @Override
    public void render(float delta) {
        camera.update();
        customizeScreenHUD.update(delta);
        batch.setProjectionMatrix(camera.combined);

        // Light blue
        Gdx.gl.glClearColor(.56f, .76f, .83f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.render(batch);
        batch.end();

        stage.act(delta);
        stage.draw();
        background.update(delta);

        batch.begin();
        customizeButtons.render(batch);
        customizeButtons.update(delta);
        customizeScreenHUD.render(batch);
        batch.end();

        // Exit on back press
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            kittyCartGame.setScreen(new MenuScreen(kittyCartGame, resolver));
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

}
