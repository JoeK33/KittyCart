package com.myreliablegames.kittycart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/24/2016.
 */
public class MenuScreen extends ScreenAdapter {

    private KittyCartGame kittyCartGame;
    private Viewport viewport;
    private Camera camera;
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    private Table table;
    private BitmapFont font;

    public MenuScreen(KittyCartGame game) {
        // Load assets
        Assets.getInstance();
        viewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        camera = new OrthographicCamera(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2);
        batch = new SpriteBatch();
        skin = new Skin();
        font = new BitmapFont();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        table = new Table(skin);
        table.defaults().width(200).padBottom(10);

        this.kittyCartGame = game;

        skin.add("default", font);
        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.WHITE);
        textButtonStyle.checked = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        final TextButton startGameButton = new TextButton("Start game", skin);
        final TextButton customizeButton = new TextButton("Customize", skin);
        final TextButton leaderBoardButton = new TextButton("Leaderboard", skin);
        final TextButton achievementButton = new TextButton("Achievements", skin);
        final TextButton exitButton = new TextButton("Exit", skin);

        table.add(startGameButton);
        table.row();
        table.add(customizeButton);
        table.row();
        table.add(leaderBoardButton);
        table.row();
        table.add(achievementButton);
        table.row();
        table.add(exitButton);
        table.setFillParent(true);

        startGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                kittyCartGame.startGame();
            }
        });

        customizeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                kittyCartGame.openCustomizeMenu();
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        stage.addActor(table);
    }


    @Override
    public void show() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        Gdx.gl.glClearColor(.2f, .2f, .2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        table.draw(batch, 1);

        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

}
