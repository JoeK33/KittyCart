package com.myreliablegames.kittycart;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.myreliablegames.kittycart.util.Constants;

import java.text.NumberFormat;

/**
 * Created by Joe on 5/18/2016.
 */
public class HUD {

    private final Camera camera;
    private BitmapFont font;
    private final String SCORE;
    private final String DISTANCE;
    private final String COINS;
    private final String GAME_PAUSED;
    private final String INSTRUCTIONS;
    private boolean paused;
    private long startTime;
    private long instructionShowTime;

    public HUD(Camera camera) {
        startTime = TimeUtils.nanoTime();
        // Four Seconds
        instructionShowTime = TimeUtils.millisToNanos(4000);
        this.camera = camera;
        paused = false;
        font = new BitmapFont();

        SCORE = "Score: ";
        DISTANCE = "Distance: ";
        COINS = "Coins: ";
        GAME_PAUSED = " Game Paused" + "\n" + "Tap to Unpause";
        INSTRUCTIONS = "                 Tap to Jump" + "\n" + "Hold and Release to Long Jump";
        font.getData().scale(1);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public void render(SpriteBatch batch, int score, int tracksTraveled, int coins) {

        int pauseOffset = 90;
        int instructionOffset = 200;
        font.draw(batch, SCORE + NumberFormat.getIntegerInstance().format(score), Constants.HUD_MARGIN, camera.position.y - Constants.HUD_MARGIN + (Constants.WORLD_HEIGHT / 2));
        font.draw(batch, DISTANCE + NumberFormat.getIntegerInstance().format(tracksTraveled) + " m", Constants.WORLD_WIDTH / 2, camera.position.y - Constants.HUD_MARGIN + (Constants.WORLD_HEIGHT / 2));
        font.draw(batch, COINS + NumberFormat.getIntegerInstance().format(coins), (Constants.WORLD_WIDTH / 4) * 3, camera.position.y - Constants.HUD_MARGIN + (Constants.WORLD_HEIGHT / 2));
        if (paused) {
            font.draw(batch, GAME_PAUSED, camera.position.x - pauseOffset, camera.position.y + camera.viewportHeight / 6);
        }

        if (TimeUtils.nanoTime() < startTime + instructionShowTime && !paused) {
            font.draw(batch, INSTRUCTIONS, camera.position.x - instructionOffset, camera.position.y + camera.viewportHeight / 6);

        }
    }

    public void pause() {
        paused = true;
    }

    public void unPause() {
        paused = false;
    }

    public void dispose() {
        font.dispose();
    }
}
