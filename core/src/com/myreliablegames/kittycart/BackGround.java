package com.myreliablegames.kittycart;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/19/2016.
 */
public class BackGround {

    private int numTilesWide;
    private float tileSize;
    private float scrollingPoint;
    private final float SCROLL_SPEED = 10;
    private final int NUMBER_OF_BOTTOM_TILE_ROWS = 3;
    private final int NUMBER_OF_MIDDLE_TILE_ROWS = 2;
    private final int NUMBER_OF_TOP_TILE_ROWS = 3;

    public BackGround() {
        tileSize = Assets.getInstance().backGroundAssets.bottomBackGroundTile.getWidth();
        scrollingPoint = 0;

        int extraScrollTiles = 3;
        numTilesWide = (int) (Constants.WORLD_WIDTH / tileSize) + extraScrollTiles;

    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < numTilesWide; i++) {

            for (int j = 0; j < NUMBER_OF_BOTTOM_TILE_ROWS; j++) {
                batch.draw(Assets.getInstance().backGroundAssets.bottomBackGroundTile, scrollingPoint + (i * tileSize), -tileSize * j);
            }

            for (int j = 0; j < NUMBER_OF_MIDDLE_TILE_ROWS; j++) {
                batch.draw(Assets.getInstance().backGroundAssets.middleBackGroundTile, scrollingPoint + (i * tileSize), (tileSize * j) + tileSize);
            }

            for (int j = 0; j < NUMBER_OF_TOP_TILE_ROWS; j++) {
                batch.draw(Assets.getInstance().backGroundAssets.bottomBackGroundTile, scrollingPoint + (i * tileSize), tileSize * (j + NUMBER_OF_MIDDLE_TILE_ROWS) + tileSize);
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
