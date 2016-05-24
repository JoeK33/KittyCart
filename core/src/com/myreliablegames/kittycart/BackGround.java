package com.myreliablegames.kittycart;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/19/2016.
 * The scrolling background.  Scrolls various tiles across the back of the game world.
 */
public class BackGround {

    private int numTilesWide;
    private float tileSize;
    private float scrollingPoint;
    private final float SCROLL_SPEED = 30;
    private final int NUMBER_OF_BOTTOM_TILE_ROWS = 3;
    private final int NUMBER_OF_MIDDLE_TILE_ROWS = 1;
    private final int NUMBER_OF_TOP_TILE_ROWS = 3;


    public BackGround() {
        tileSize = Assets.getInstance().backGroundAssets.bottomDesertTile.getWidth();
        scrollingPoint = 0;
        int extraScrollTiles = 2;
        numTilesWide = (int) (Constants.WORLD_WIDTH / tileSize) + extraScrollTiles;
    }

    public void render(SpriteBatch batch, Camera camera) {
        for (int i = 0; i < numTilesWide; i++) {

            for (int j = 0; j < NUMBER_OF_BOTTOM_TILE_ROWS; j++) {
                if (scrollingPoint + (i * tileSize) < Constants.WORLD_WIDTH && -tileSize * j < camera.position.y) {
                    batch.draw(Zone.getBottomTile(Zone.getZone()), scrollingPoint + (i * tileSize), -tileSize * j);
                }
            }

            for (int j = 0; j < NUMBER_OF_MIDDLE_TILE_ROWS; j++) {
                if (scrollingPoint + (i * tileSize) < Constants.WORLD_WIDTH) {
                    batch.draw(Zone.getMiddleTile(Zone.getZone()), scrollingPoint + (i * tileSize), (tileSize * j) + tileSize);
                }
            }

            // Middle tile rows multiplied by 2 here because they are twice as tall as the other tiles.
            for (int j = 0; j < NUMBER_OF_TOP_TILE_ROWS; j++) {

                if (scrollingPoint + (i * tileSize) < Constants.WORLD_WIDTH) {
                    batch.draw(Zone.getTopTile(Zone.getZone()), scrollingPoint + (i * tileSize), tileSize * (j + NUMBER_OF_MIDDLE_TILE_ROWS * 2) + tileSize);
                }
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
