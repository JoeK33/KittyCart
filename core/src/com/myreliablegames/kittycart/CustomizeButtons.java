package com.myreliablegames.kittycart;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;


/**
 * Created by Joe on 5/30/2016.
 */
public class CustomizeButtons {
    private TextButton cat1SelectButton;
    private TextButton cat2SelectButton;
    private TextButton cat3SelectButton;
    private TextButton cat4SelectButton;

    private TextButton minecart1SelectButton;
    private TextButton minecart2SelectButton;
    private TextButton minecart3SelectButton;
    private TextButton minecart4SelectButton;

    private TextButton rollerCoasterModeButton;
    private float stateTime = 0;
    private float[] wiggleDegrees = {0, 45, 90, 180};
    private final int BOB_SPEED = 5;


    public CustomizeButtons(Table table, Skin skin, BitmapFont font) {

        skin.add("default", font);
        Pixmap pixmap = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        pixmap.setColor(64, 64, 64, .7f);
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

        cat1SelectButton = new TextButton("", skin);

        cat1SelectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayerPreferences.setCat(PlayerPreferences.Cat.Cat1);
            }
        });

        cat2SelectButton = new TextButton("", skin);

        cat2SelectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayerPreferences.setCat(PlayerPreferences.Cat.Cat2);
            }
        });

        cat3SelectButton = new TextButton("", skin);

        cat3SelectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayerPreferences.setCat(PlayerPreferences.Cat.Cat3);
            }
        });

        cat4SelectButton = new TextButton("", skin);

        cat4SelectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayerPreferences.setCat(PlayerPreferences.Cat.Cat4);
            }
        });

        minecart1SelectButton = new TextButton("", skin);

        minecart1SelectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayerPreferences.setCart(PlayerPreferences.Cart.MINECART);
            }
        });

        minecart2SelectButton = new TextButton("", skin);

        minecart2SelectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayerPreferences.setCart(PlayerPreferences.Cart.LITTERBOX);
            }
        });

        minecart3SelectButton = new TextButton("", skin);

        minecart3SelectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayerPreferences.setCart(PlayerPreferences.Cart.COASTER);
            }
        });

        minecart4SelectButton = new TextButton("", skin);

        minecart4SelectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayerPreferences.setCart(PlayerPreferences.Cart.SKATEBOARD);
            }
        });

        rollerCoasterModeButton = new TextButton("", skin);

        rollerCoasterModeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayerPreferences.toggleCoasterMode();
            }
        });

        Label.LabelStyle titleStyle = new Label.LabelStyle(font, Color.WHITE);
        skin.add("default", titleStyle);
        Label label = new Label("Customize", skin);
        label.setWrap(false);
        label.setFontScale(2f);
        label.setWidth(200);

        Label coasterLabel = new Label("Coaster Mode", skin);
        coasterLabel.setWrap(false);
        coasterLabel.setFontScale(2f);
        coasterLabel.setWidth(200);

        table.setDebug(false);
        table.center();
        table.add(label).colspan(4).center();
        table.row();
        table.add(cat1SelectButton);
        table.add(cat2SelectButton);
        table.add(minecart1SelectButton);
        table.add(minecart2SelectButton);
        table.row();
        table.add(cat3SelectButton);
        table.add(cat4SelectButton);
        table.add(minecart3SelectButton);
        table.add(minecart4SelectButton);
        table.row();
        table.add(coasterLabel).colspan(4).center();
        table.row();
        table.add(rollerCoasterModeButton).colspan(4).center();
        table.setFillParent(true);

    }

    public void render(SpriteBatch batch) {
        drawCatOnButton(batch, cat1SelectButton, PlayerPreferences.Cat.Cat1);
        drawCatOnButton(batch, cat2SelectButton, PlayerPreferences.Cat.Cat2);
        drawCatOnButton(batch, cat3SelectButton, PlayerPreferences.Cat.Cat3);
        drawCatOnButton(batch, cat4SelectButton, PlayerPreferences.Cat.Cat4);

        drawMineCartOnButton(batch, minecart1SelectButton, PlayerPreferences.Cart.MINECART, 0);
        drawMineCartOnButton(batch, minecart2SelectButton, PlayerPreferences.Cart.LITTERBOX, 1);
        drawMineCartOnButton(batch, minecart3SelectButton, PlayerPreferences.Cart.COASTER, 2);
        drawMineCartOnButton(batch, minecart4SelectButton, PlayerPreferences.Cart.SKATEBOARD, 3);

        drawCoasterButton(batch, rollerCoasterModeButton);

        drawSelectedItems(batch);
    }

    public Vector2 getLocation(Actor actor) {
        return actor.localToStageCoordinates(new Vector2(0, 0));
    }


    private void drawCatOnButton(SpriteBatch batch, TextButton button, PlayerPreferences.Cat cat) {

        Vector2 location = getLocation(button);
        batch.draw(PlayerPreferences.getCatAnimation(cat).getKeyFrame(stateTime), location.x + Constants.CAT_SIZE / 2, location.y);

    }

    private void drawCoasterButton(SpriteBatch batch, TextButton button) {

        Vector2 location = getLocation(button);
        batch.draw(Assets.getInstance().trackAssets.upTrackCoaster, location.x + (Constants.TRACK_WIDTH / 2), location.y);

    }

    private void drawMineCartOnButton(SpriteBatch batch, TextButton button, PlayerPreferences.Cart cart, int wiggleDegreesIndex) {

        Vector2 location = getLocation(button);
        float offset = getBobOffset(wiggleDegreesIndex);
        batch.draw(PlayerPreferences.getCartBack(cart), location.x + Constants.MINECART_WIDTH / 2, location.y + offset);
        batch.draw(PlayerPreferences.getCartFront(cart), location.x + Constants.MINECART_WIDTH / 2, location.y + offset);

    }

    private void drawSelectedItems(SpriteBatch batch) {
        // Draw the selection box for the cat, cart, and if coaster mode is enabled.
        PlayerPreferences.Cat cat = PlayerPreferences.getCat();
        PlayerPreferences.Cart cart = PlayerPreferences.getCart();
        boolean coasterEnabled = PlayerPreferences.coasterModeOn();

        switch (PlayerPreferences.Cat.getValue(cat)) {
            case 1:
                select(batch, cat1SelectButton);
                break;
            case 2:
                select(batch, cat2SelectButton);
                break;
            case 3:
                select(batch, cat3SelectButton);
                break;
            case 4:
                select(batch, cat4SelectButton);
                break;
            default:
                select(batch, cat1SelectButton);
        }

        switch (PlayerPreferences.Cart.getValue(cart)) {
            case 1:
                select(batch, minecart1SelectButton);
                break;
            case 2:
                select(batch, minecart2SelectButton);
                break;
            case 3:
                select(batch, minecart3SelectButton);
                break;
            case 4:
                select(batch, minecart4SelectButton);
                break;
            default:
                select(batch, minecart1SelectButton);
        }

        if (coasterEnabled) {
            select(batch, rollerCoasterModeButton);
        }

    }

    private void select(SpriteBatch batch, TextButton button) {

        Vector2 location = getLocation(button);
        batch.draw(Assets.getInstance().menuAssets.selectionRectangle, location.x - (Constants.SELECTION_FRAME_SIZE / 2) + (Constants.TABLE_CELL_WIDTH / 2), location.y);


    }



    public float getBobOffset(int index) {
        // Count -1 to match wiggleDegrees index.
        float bobOffset = (float) (Math.cos(wiggleDegrees[index]) * 2) + (Constants.STRAIGHT_TRACK_THICKNESS / 2);

        return bobOffset;
    }

    public void update(float delta) {
        stateTime += delta;

        for (int i = 0; i < wiggleDegrees.length; i++) {
            wiggleDegrees[i] += (BOB_SPEED * delta);
            wiggleDegrees[i] = wiggleDegrees[i] % 360;
        }

    }
}

