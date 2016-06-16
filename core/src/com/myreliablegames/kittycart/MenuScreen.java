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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myreliablegames.kittycart.Tracks.TrackLayer;
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
    private Stage imageStage;
    private Table table;
    private Table imageTable;
    private BitmapFont font;
    private TrackLayer trackLayer;

    private TextButton startGameButton;
    private TextButton optionsButton;
    private TextButton leaderBoardButton;
    private TextButton achievementButton;
    private TextButton exitButton;
    private MenuBackground menuBackground;
    private float offset;
    private float wiggleDegrees;

    private final int BOB_SPEED = 2;

    public MenuScreen(KittyCartGame game, final ActionResolver resolver) {
        viewport = new ExtendViewport((Constants.WORLD_WIDTH / 4) * 3, (Constants.WORLD_HEIGHT / 4) * 3);
        camera = new OrthographicCamera((Constants.WORLD_WIDTH), (Constants.WORLD_HEIGHT));
        camera.position.set(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2, 0);
        batch = new SpriteBatch();
        skin = new Skin();
        font = new BitmapFont();
        stage = new Stage(viewport);
        imageStage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        table = new Table(skin);
        table.defaults().width(Constants.TABLE_CELL_WIDTH).height(Constants.TABLE_CELL_HEIGHT).padBottom(10);
        imageTable = new Table(skin);
        imageTable.defaults().width(Constants.TABLE_CELL_WIDTH).height(Constants.TABLE_CELL_HEIGHT).padBottom(10);
        menuBackground = new MenuBackground(font);
        wiggleDegrees = 0;

        this.kittyCartGame = game;
        trackLayer = new TrackLayer(true);

        skin.add("default", font);
        Pixmap mPixmap = getPixmapRoundedRectangle(128, 64, 15);
        skin.add("white", new Texture(mPixmap));

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.GREEN);
        textButtonStyle.checked = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.GREEN);
        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        startGameButton = new TextButton("", skin);
        optionsButton = new TextButton("", skin);
        leaderBoardButton = new TextButton("", skin);
        achievementButton = new TextButton("", skin);
        exitButton = new TextButton("", skin);

        table.right().padRight(Constants.WORLD_WIDTH / 6);
        table.add(startGameButton);
        table.row();
        table.add(optionsButton);
        table.row();
        table.add(leaderBoardButton);
        table.row();
        table.add(achievementButton);
        table.row();
        table.add(exitButton);
        table.setFillParent(true);
        table.setDebug(false);

        imageTable.right().padRight(Constants.WORLD_WIDTH / 6);
        imageTable.add(new Image(Assets.getInstance().menuAssets.startButton));
        imageTable.row();
        imageTable.add(new Image(Assets.getInstance().menuAssets.optionsButton));
        imageTable.row();
        imageTable.add(new Image(Assets.getInstance().menuAssets.leaderboardButton));
        imageTable.row();
        imageTable.add(new Image(Assets.getInstance().menuAssets.achievementButton));
        imageTable.row();
        imageTable.add(new Image(Assets.getInstance().menuAssets.exitButton));
        imageTable.setFillParent(true);
        imageTable.setDebug(false);

        startGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Assets.getInstance().soundAssets.meow3.play(.75f);
                Timer timer = new Timer();
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        kittyCartGame.startGame();
                    }
                }, .5f);
            }
        });

        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Assets.getInstance().soundAssets.meow2.play(.75f);
                Timer timer = new Timer();
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        kittyCartGame.openCustomizeMenu();
                    }
                }, .5f);
            }
        });

        leaderBoardButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resolver.showLeaderboard();
            }
        });

        achievementButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resolver.showAchievements();
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Assets.getInstance().soundAssets.meow1.play();
                Timer timer = new Timer();
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        Gdx.app.exit();
                    }
                }, .5f);
            }
        });

        stage.addActor(table);
        imageStage.addActor(imageTable);


    }

    @Override
    public void show() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        imageStage.dispose();
        batch.dispose();
    }

    @Override
    public void render(float delta) {

        offset = 8 * (float) (Math.cos(wiggleDegrees));
        wiggleDegrees += (BOB_SPEED * delta);
        trackLayer.update(delta);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        menuBackground.update(delta);
        //Gdx.gl.glClearColor(0f, .5f, 1f, 1f);
        Gdx.gl.glClearColor(.56f, .76f, .83f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        menuBackground.render(batch);
        trackLayer.render(batch);
        batch.draw(Assets.getInstance().menuAssets.title, Constants.WORLD_WIDTH / 7, Constants.WORLD_HEIGHT / 4 - Constants.MENU_BUTTON_HEIGHT / 2 + offset);
        batch.end();

        stage.act(delta);
        stage.draw();

        imageStage.act(delta);
        imageStage.draw();
    }



    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        imageStage.getViewport().update(width, height, true);
    }

    // From https://developerover30.wordpress.com/2014/11/11/libgdx-creating-a-rounded-rectangle-pixmap/
    private Pixmap getPixmapRoundedRectangle(int width, int height, int radius) {

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);

        // Pink rectangle
        pixmap.fillRectangle(0, radius, pixmap.getWidth(), pixmap.getHeight() - 2 * radius);

        // Green rectangle
        pixmap.fillRectangle(radius, 0, pixmap.getWidth() - 2 * radius, pixmap.getHeight());

        // Bottom-left circle
        pixmap.fillCircle(radius, radius, radius);

        // Top-left circle
        pixmap.fillCircle(radius, pixmap.getHeight() - radius, radius);

        // Bottom-right circle
        pixmap.fillCircle(pixmap.getWidth() - radius, radius, radius);

        // Top-right circle
        pixmap.fillCircle(pixmap.getWidth() - radius, pixmap.getHeight() - radius, radius);
        return pixmap;
    }
}
