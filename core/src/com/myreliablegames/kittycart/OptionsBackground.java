package com.myreliablegames.kittycart;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/26/2016.
 */
public class OptionsBackground {
    private int tileSize;
    private int tilesWide;
    private int tilesHigh;
    private float scrollingPoint;
    private final float SCROLL_SPEED = 10;

    public OptionsBackground() {
        Texture tile = Assets.getInstance().backGroundAssets.fishTile.getTexture();
        tileSize = tile.getWidth() / 2;
        scrollingPoint = 0;
        int bufferTiles = 3;
        tilesWide = ((int) Constants.WORLD_WIDTH / tileSize) * 2 + bufferTiles;
        tilesHigh = ((int) Constants.WORLD_HEIGHT / tileSize) * 2 + bufferTiles;
    }

    public void render(SpriteBatch batch) {

        for (int heightIndex = 0; heightIndex < tilesHigh; heightIndex++) {
            for (int widthIndex = 0; widthIndex < tilesWide; widthIndex++) {
                batch.draw(Assets.getInstance().backGroundAssets.fishTile,
                        scrollingPoint + (widthIndex * tileSize) - tileSize * 2, -scrollingPoint + -(heightIndex * tileSize) + tileSize * 2, tileSize, tileSize);
            }
        }
    }

    public void update(float delta) {
        scrollingPoint -= SCROLL_SPEED * delta;

        if (scrollingPoint < -tileSize * 2) {
            scrollingPoint = 0;
        }
    }
}
