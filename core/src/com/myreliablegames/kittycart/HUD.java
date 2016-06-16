package com.myreliablegames.kittycart;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

import java.text.NumberFormat;

/**
 * Created by Joe on 5/18/2016.
 */
public class HUD {

    private final Camera camera;
    private BitmapFont font;
    private final String DISTANCE;
    private final String GAME_PAUSED;
    private final String INSTRUCTIONS;
    private boolean paused;
    private long startTime;
    private long instructionShowTime;
  //  private int numberOfHUDBackgroundTiles;
    private final int HUD_TEXT_BG_MARGIN = 5;

    public HUD(Camera camera) {
        startTime = TimeUtils.nanoTime();
        // Four Seconds
        instructionShowTime = TimeUtils.millisToNanos(4000);
        this.camera = camera;
        paused = false;
        font = new BitmapFont();

        DISTANCE = "Distance: ";
        GAME_PAUSED = " Game Paused" + "\n" + "Tap to Unpause";
        INSTRUCTIONS = "                 Tap to Jump" + "\n" + "Hold and Release to Long Jump";
        font.getData().scale(1);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
   //     numberOfHUDBackgroundTiles = (int) (Constants.WORLD_WIDTH / Constants.HUD_BACKGROUND_WIDTH) + 1;
    }

    public void render(SpriteBatch batch, int score, int tracksTraveled, int coins) {

        int pauseOffset = 90;
        int instructionOffset = 200;


            batch.draw(Assets.getInstance().backGroundAssets.hudTextBackground, - Constants.HUD_BACKGROUND_WIDTH / 3,
                    (camera.position.y - Constants.HUD_MARGIN + (Constants.WORLD_HEIGHT / 2) - Constants.HUD_BACKGROUND_HEIGHT / 2) - HUD_TEXT_BG_MARGIN);

        font.draw(batch, NumberFormat.getIntegerInstance().format(score),
                Constants.HUD_MARGIN,
                camera.position.y - Constants.HUD_MARGIN + (Constants.WORLD_HEIGHT / 2));

        batch.draw(Assets.getInstance().backGroundAssets.hudTextBackground, Constants.WORLD_WIDTH / 4 - Constants.HUD_MARGIN + Constants.HUD_BACKGROUND_WIDTH / 2,
                (camera.position.y - Constants.HUD_MARGIN + (Constants.WORLD_HEIGHT / 2) - Constants.HUD_BACKGROUND_HEIGHT / 2) - HUD_TEXT_BG_MARGIN);

        font.draw(batch, DISTANCE + NumberFormat.getIntegerInstance().format(tracksTraveled) + " m",
                Constants.WORLD_WIDTH / 4 + Constants.HUD_BACKGROUND_WIDTH / 2,
                camera.position.y - Constants.HUD_MARGIN + (Constants.WORLD_HEIGHT / 2));

        batch.draw(Assets.getInstance().backGroundAssets.hudTextBackground, (Constants.WORLD_WIDTH / 4) * 3 - Constants.HUD_MARGIN + Constants.HUD_BACKGROUND_WIDTH / 2,
                (camera.position.y - Constants.HUD_MARGIN + (Constants.WORLD_HEIGHT / 2) - Constants.HUD_BACKGROUND_HEIGHT / 2) - HUD_TEXT_BG_MARGIN);

        batch.draw(Assets.getInstance().pickUpAssets.coinAnimation.getKeyFrame(0),
                (Constants.WORLD_WIDTH / 4) * 3 + Constants.HUD_BACKGROUND_WIDTH / 2,
                camera.position.y - Constants.HUD_MARGIN + (Constants.WORLD_HEIGHT / 2) - Constants.COIN_SIZE + (font.getLineHeight() - Constants.COIN_SIZE));

        font.draw(batch, " X " + NumberFormat.getIntegerInstance().format(coins),
                ((Constants.WORLD_WIDTH / 4) * 3) + Constants.COIN_SIZE + Constants.HUD_BACKGROUND_WIDTH / 2,
                camera.position.y - Constants.HUD_MARGIN + (Constants.WORLD_HEIGHT / 2));

        if (paused) {
            font.draw(batch, GAME_PAUSED,
                    camera.position.x - pauseOffset,
                    camera.position.y + camera.viewportHeight / 6);
        }

        if (TimeUtils.nanoTime() < startTime + instructionShowTime && !paused) {
            font.draw(batch, INSTRUCTIONS,
                    camera.position.x - instructionOffset,
                    camera.position.y + camera.viewportHeight / 6);
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
