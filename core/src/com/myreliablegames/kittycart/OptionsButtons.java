package com.myreliablegames.kittycart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
public class OptionsButtons {
    private TextButton cat1SelectButton;
    private UnlockableTextButton cat2SelectButton;
    private UnlockableTextButton cat3SelectButton;
    private UnlockableTextButton cat4SelectButton;

    private TextButton minecart1SelectButton;
    private UnlockableTextButton minecart2SelectButton;
    private UnlockableTextButton minecart3SelectButton;
    private UnlockableTextButton minecart4SelectButton;

    private ActionResolver resolver;
    private Preferences prefs;

    private UnlockableTextButton rollerCoasterModeButton;
    private float stateTime = 0;
    private float[] wiggleDegrees = {0, 45, 90, 180};
    private final int BOB_SPEED = 5;

    public OptionsButtons(Table table, Skin skin, BitmapFont font, final ActionResolver resolver) {
        this.resolver = resolver;
        prefs = Gdx.app.getPreferences("My Preferences");
        skin.add("default", font);
        Pixmap pixmap = getPixmapRoundedRectangle(64, 64, 30);
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
                Assets.getInstance().soundAssets.selectSound.play();
            }
        });

        cat2SelectButton = new UnlockableTextButton("", skin, prefs.getBoolean("cat2locked", true));
        cat2SelectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (cat2SelectButton.isLocked()) {
                    if (resolver.advertismentIsLoaded()) {
                        resolver.showAdvertisment();
                        cat2SelectButton.setLock(false);
                        prefs.putBoolean("cat2locked", false);
                        prefs.flush();
                    } else {
                        resolver.showFailedAdToast();
                    }
                } else {
                    PlayerPreferences.setCat(PlayerPreferences.Cat.Cat2);
                    Assets.getInstance().soundAssets.selectSound.play();
                }
            }
        });

        cat3SelectButton = new UnlockableTextButton("", skin, prefs.getBoolean("cat3locked", true));
        cat3SelectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (cat3SelectButton.isLocked()) {
                    if (resolver.advertismentIsLoaded()) {
                        resolver.showAdvertisment();
                        cat3SelectButton.setLock(false);
                        prefs.putBoolean("cat3locked", false);
                        prefs.flush();
                    } else {
                        resolver.showFailedAdToast();
                    }
                } else {
                    PlayerPreferences.setCat(PlayerPreferences.Cat.Cat3);
                    Assets.getInstance().soundAssets.selectSound.play();
                }
            }
        });

        cat4SelectButton = new UnlockableTextButton("", skin, prefs.getBoolean("cat4locked", true));
        cat4SelectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (cat4SelectButton.isLocked()) {
                    if (resolver.advertismentIsLoaded()) {
                        resolver.showAdvertisment();
                        cat4SelectButton.setLock(false);
                        prefs.putBoolean("cat4locked", false);
                        prefs.flush();
                    } else {
                        resolver.showFailedAdToast();
                    }
                } else {
                    PlayerPreferences.setCat(PlayerPreferences.Cat.Cat4);
                    Assets.getInstance().soundAssets.selectSound.play();
                }
            }
        });

        minecart1SelectButton = new TextButton("", skin);
        minecart1SelectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayerPreferences.setCart(PlayerPreferences.Cart.MINECART);
                Assets.getInstance().soundAssets.selectSound.play();
            }
        });

        minecart2SelectButton = new UnlockableTextButton("", skin, prefs.getBoolean("cart2locked", true));
        minecart2SelectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (minecart2SelectButton.isLocked()) {
                    if (resolver.advertismentIsLoaded()) {
                        resolver.showAdvertisment();
                        minecart2SelectButton.setLock(false);
                        prefs.putBoolean("cart2locked", false);
                        prefs.flush();
                    } else {
                        resolver.showFailedAdToast();
                    }
                } else {
                    PlayerPreferences.setCart(PlayerPreferences.Cart.LITTERBOX);
                    Assets.getInstance().soundAssets.selectSound.play();
                }

            }
        });

        minecart3SelectButton = new UnlockableTextButton("", skin, prefs.getBoolean("cart3locked", true));

        minecart3SelectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (minecart3SelectButton.isLocked()) {
                    if (resolver.advertismentIsLoaded()) {
                        resolver.showAdvertisment();
                        minecart3SelectButton.setLock(false);
                        prefs.putBoolean("cart3locked", false);
                        prefs.flush();
                    } else {
                        resolver.showFailedAdToast();
                    }
                } else {
                    PlayerPreferences.setCart(PlayerPreferences.Cart.COASTER);
                    Assets.getInstance().soundAssets.selectSound.play();
                }
            }
        });

        minecart4SelectButton = new UnlockableTextButton("", skin, prefs.getBoolean("cart4locked", true));

        minecart4SelectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (minecart4SelectButton.isLocked()) {
                    if (resolver.advertismentIsLoaded()) {
                        resolver.showAdvertisment();
                        minecart4SelectButton.setLock(false);
                        prefs.putBoolean("cart4locked", false);
                        prefs.flush();
                    } else {
                        resolver.showFailedAdToast();
                    }
                } else {
                    PlayerPreferences.setCart(PlayerPreferences.Cart.SKATEBOARD);
                    Assets.getInstance().soundAssets.selectSound.play();
                }
            }
        });


        int numCoins = prefs.getInteger("coins", 0);

        if (numCoins > Constants.COASTER_UNLOCK_COINS_REQUIRED) {
            prefs.putBoolean("coastermodelocked", false);
            prefs.flush();
        } else {
            prefs.putBoolean("coastermodelocked", true);
            prefs.flush();
        }

        rollerCoasterModeButton = new UnlockableTextButton("", skin, prefs.getBoolean("coastermodelocked", true));

        rollerCoasterModeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (rollerCoasterModeButton.isLocked()) {

                } else {
                    PlayerPreferences.toggleCoasterMode();
                    Assets.getInstance().soundAssets.selectSound.play();
                }
            }
        });

        table.setDebug(false);
        table.center();
        table.padTop(120);
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

        if (button instanceof UnlockableTextButton) {

            // Draw locking box if button is locked.
            if (((UnlockableTextButton) button).isLocked()) {
                batch.draw(Assets.getInstance().menuAssets.adLockRectangle,
                        location.x - (Constants.SELECTION_FRAME_SIZE / 2) + (Constants.TABLE_CELL_WIDTH / 2),
                        location.y - Constants.SELECTION_FRAME_EDGE_WIDTH);
            }
        }
    }

    private void drawCoasterButton(SpriteBatch batch, TextButton button) {

        Vector2 location = getLocation(button);
        batch.draw(Assets.getInstance().trackAssets.upTrackCoaster, location.x + (Constants.TRACK_WIDTH / 2), location.y);

        if (button instanceof UnlockableTextButton) {
            // Draw locking box if button is locked.
            if (((UnlockableTextButton) button).isLocked()) {
                batch.draw(Assets.getInstance().menuAssets.coinLockRectangle,
                        location.x - (Constants.SELECTION_FRAME_SIZE / 2) + (Constants.TABLE_CELL_WIDTH / 2),
                        location.y - Constants.SELECTION_FRAME_EDGE_WIDTH);
            }
        }


    }

    private void drawMineCartOnButton(SpriteBatch batch, TextButton button, PlayerPreferences.Cart cart, int wiggleDegreesIndex) {

        Vector2 location = getLocation(button);
        float offset = getBobOffset(wiggleDegreesIndex);
        batch.draw(PlayerPreferences.getCartBack(cart), location.x + Constants.MINECART_WIDTH / 2, location.y + offset);
        batch.draw(PlayerPreferences.getCartFront(cart), location.x + Constants.MINECART_WIDTH / 2, location.y + offset);

        if (button instanceof UnlockableTextButton) {
            // Draw locking box if button is locked.
            if (((UnlockableTextButton) button).isLocked()) {
                batch.draw(Assets.getInstance().menuAssets.adLockRectangle,
                        location.x - (Constants.SELECTION_FRAME_SIZE / 2) + (Constants.TABLE_CELL_WIDTH / 2),
                        location.y - Constants.SELECTION_FRAME_EDGE_WIDTH);
            }
        }
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
        batch.draw(Assets.getInstance().menuAssets.selectionRectangle,
                location.x - (Constants.SELECTION_FRAME_SIZE / 2) + (Constants.TABLE_CELL_WIDTH / 2),
                location.y - Constants.SELECTION_FRAME_EDGE_WIDTH);

    }


    public float getBobOffset(int index) {
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

