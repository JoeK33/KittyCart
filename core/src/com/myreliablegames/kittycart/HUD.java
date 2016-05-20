package com.myreliablegames.kittycart;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/18/2016.
 */
public class HUD {

    private final Camera camera;
    private BitmapFont font;
    private final String SCORE;
    private final String DISTANCE;
    private final String COINS;

    public HUD(Camera camera) {
        this.camera = camera;

        font = new BitmapFont();

        SCORE = "Score: ";
        DISTANCE = "Distance: ";
        COINS ="Coins: ";
        font.getData().scale(1);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public void render(SpriteBatch batch, int score, int tracksTraveled, int coins) {
        font.draw(batch, SCORE + score, Constants.SCORE_MARGIN, camera.position.y - Constants.SCORE_MARGIN + (Constants.WORLD_HEIGHT / 2));
        font.draw(batch, DISTANCE + tracksTraveled, Constants.WORLD_WIDTH / 2, camera.position.y - Constants.SCORE_MARGIN + (Constants.WORLD_HEIGHT / 2));
        font.draw(batch, COINS + coins, (Constants.WORLD_WIDTH / 4) * 3, camera.position.y - Constants.SCORE_MARGIN + (Constants.WORLD_HEIGHT / 2));
    }

    public void dispose() {
        font.dispose();
    }
}
