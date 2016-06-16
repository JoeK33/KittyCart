package com.myreliablegames.kittycart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

import java.text.NumberFormat;


/**
 * Created by Joe on 5/30/2016.
 */
public class OptionsScreenHUD {
    Camera camera;
    private String totalCoins = "Total Coins: ";
    private String totalDistance = "Total Distance: ";
    private String highScore = "High Score: ";
    private BitmapFont font;
    private Preferences prefs = Gdx.app.getPreferences("My Preferences");
    private int numberOfHUDBackgroundTiles;

    public OptionsScreenHUD(Camera camera, BitmapFont font) {
        this.camera = camera;
        this.font = font;
        this.font.getData().setScale(1.3f);
        numberOfHUDBackgroundTiles = (int) (Constants.WORLD_WIDTH / Constants.HUD_BACKGROUND_WIDTH) + 1;
    }

    public void render(SpriteBatch batch) {
        int numCoins = prefs.getInteger("coins", 0);
        int numDistance = prefs.getInteger("distance", 0);
        int score = prefs.getInteger("highScore", 0);

        for (int i = 0; i < numberOfHUDBackgroundTiles; i++) {

            batch.draw(Assets.getInstance().backGroundAssets.hudTextBackground, (i * Constants.HUD_BACKGROUND_WIDTH),
                    camera.position.y - Constants.HUD_MARGIN + (Constants.WORLD_HEIGHT / 3) - Constants.HUD_BACKGROUND_HEIGHT / 2);
        }

        font.draw(batch, totalCoins + NumberFormat.getIntegerInstance().format(numCoins),
                Constants.HUD_MARGIN,
                camera.position.y - Constants.HUD_MARGIN + (Constants.WORLD_HEIGHT / 3));
        font.draw(batch, totalDistance + NumberFormat.getIntegerInstance().format(numDistance) + " m",
                (Constants.WORLD_WIDTH / 7) * 2,
                camera.position.y - Constants.HUD_MARGIN + (Constants.WORLD_HEIGHT / 3));
        font.draw(batch, highScore + NumberFormat.getIntegerInstance().format(score),
                (Constants.HUD_MARGIN + (Constants.WORLD_WIDTH / 2)),
                camera.position.y - Constants.HUD_MARGIN + (Constants.WORLD_HEIGHT / 3));
    }

    public void update(float delta) {

    }
}
