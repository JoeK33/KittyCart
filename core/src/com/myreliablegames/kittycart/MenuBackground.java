package com.myreliablegames.kittycart;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

import java.text.NumberFormat;

/**
 * Created by Joe on 6/5/2016.
 */
public class MenuBackground {
    private int tileSize;
    private int tilesWide;
    private int tilesHigh;
    private BitmapFont font;
    private float scrollingPoint;
    private final float SCROLL_SPEED = 40;
    private final String MUSIC_CREDIT = "Music by Nornec";

    public MenuBackground(BitmapFont font) {
        Texture tile = Assets.getInstance().backGroundAssets.menuCloudTile.getTexture();
        this.font = font;
        this.font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.font.getData().scale(1.05f);
        tileSize = tile.getWidth() / 2;
        scrollingPoint = 0;
        int bufferTiles = 3;
        tilesWide = ((int) Constants.WORLD_WIDTH / tileSize) * 2 + bufferTiles;
        tilesHigh = ((int) Constants.WORLD_HEIGHT / tileSize) * 2 + bufferTiles;
    }

    public void render(SpriteBatch batch) {

        for (int heightIndex = 0; heightIndex < tilesHigh; heightIndex++) {
            for (int widthIndex = 0; widthIndex < tilesWide; widthIndex++) {
                batch.draw(Assets.getInstance().backGroundAssets.menuCloudTile,
                        scrollingPoint + (widthIndex * tileSize) - tileSize * 2, -(heightIndex * tileSize) + tileSize * 2, tileSize, tileSize);
            }
        }


        font.draw(batch, MUSIC_CREDIT,
                Constants.HUD_MARGIN,
                Constants.WORLD_HEIGHT - Constants.HUD_MARGIN);
    }

    public void update(float delta) {
        scrollingPoint -= SCROLL_SPEED * delta;

        if (scrollingPoint < -tileSize * 2) {
            scrollingPoint = 0;
        }
    }
}
