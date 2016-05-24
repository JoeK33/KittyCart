package com.myreliablegames.kittycart;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

import java.util.Random;

/**
 * Created by Joe on 5/19/2016.
 * The scrolling background.  Scrolls various tiles across the back of the game world.
 */
public class BackGround {

    private int numTilesWide;
    private float tileSize;
    private float scrollingPoint;
    private final float SCROLL_SPEED = 20;
    private final int NUMBER_OF_BOTTOM_TILE_ROWS = 3;
    private final int NUMBER_OF_MIDDLE_TILE_ROWS = 1;
    private final int NUMBER_OF_TOP_TILE_ROWS = 3;
    private Zone zone;

    public BackGround() {
        tileSize = Assets.getInstance().backGroundAssets.bottomDesertTile.getWidth();
        scrollingPoint = 0;
        zone = Zone.getZone();

        int extraScrollTiles = 2;
        numTilesWide = (int) (Constants.WORLD_WIDTH / tileSize) + extraScrollTiles;

    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < numTilesWide; i++) {

            for (int j = 0; j < NUMBER_OF_BOTTOM_TILE_ROWS; j++) {
                batch.draw(Zone.getBottomTile(zone), scrollingPoint + (i * tileSize), -tileSize * j);
            }

            for (int j = 0; j < NUMBER_OF_MIDDLE_TILE_ROWS; j++) {
                batch.draw(Zone.getMiddleTile(zone), scrollingPoint + (i * tileSize), (tileSize * j) + tileSize);
            }

            // Middle tile rows multiplied by 2 here because they are twice as tall as the other tiles.
            for (int j = 0; j < NUMBER_OF_TOP_TILE_ROWS; j++) {
                batch.draw(Zone.getTopTile(zone), scrollingPoint + (i * tileSize), tileSize * (j + NUMBER_OF_MIDDLE_TILE_ROWS * 2) + tileSize);
            }
        }

    }

    public void update(float delta) {
        scrollingPoint -= SCROLL_SPEED * delta;

        if (scrollingPoint < -tileSize) {
            scrollingPoint = 0;
        }
    }


}
