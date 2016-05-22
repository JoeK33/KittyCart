package com.myreliablegames.kittycart.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Joe on 5/17/2016.
 */
public class Assets implements Disposable, AssetErrorListener {

    public final static String TAG = "Asset Manager";

    private static Assets instance;
    private AssetManager assetManager;
    public MineCartAssets mineCartAssets;
    public TrackAssets trackAssets;
    public PickUpAssets pickUpAssets;
    public BackGroundAssets backGroundAssets;

    private Assets() {
    }

    public static Assets getInstance() {
        if (instance == null) {
            instance = new Assets();
        }
        return instance;
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        // Load Assets
        assetManager.load("minecart.png", Texture.class);
        assetManager.load("minecartfront.png", Texture.class);
        assetManager.load("minecartback.png", Texture.class);
        assetManager.load("spark.png", Texture.class);
        assetManager.load("catincart.png", Texture.class);
        assetManager.load("catframes.png", Texture.class);

        assetManager.load("downtracks.png", Texture.class);
        assetManager.load("straighttracks.png", Texture.class);
        assetManager.load("uptracks.png", Texture.class);
        assetManager.load("supports.png", Texture.class);

        assetManager.load("coinframes.png", Texture.class);

        assetManager.load("bottomtile.png", Texture.class);
        assetManager.load("middletile.png", Texture.class);

        assetManager.finishLoading();

        mineCartAssets = new MineCartAssets();
        trackAssets = new TrackAssets();
        pickUpAssets = new PickUpAssets();
        backGroundAssets = new BackGroundAssets();
    }

    public class MineCartAssets {
        public final Texture minecart;
        public final Texture minecartFront;
        public final Texture minecartBack;
        public final Texture spark;
        public final Texture cat;
        public final Animation animatedCat;

        public MineCartAssets() {
            minecart = assetManager.get("minecart.png");
            minecartFront = assetManager.get("minecartfront.png");
            minecartBack = assetManager.get("minecartback.png");
            spark = assetManager.get("spark.png");
            cat = assetManager.get("catincart.png");

            int FRAME_COLS = 5;
            int FRAME_ROWS = 1;


            Texture catSheet = assetManager.get("catframes.png");
            TextureRegion[][] tmp = TextureRegion.split(catSheet, catSheet.getWidth()/FRAME_COLS, catSheet.getHeight()/FRAME_ROWS);
            TextureRegion[] catFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
            int index = 0;
            for (int i = 0; i < FRAME_ROWS; i++) {
                for (int j = 0; j < FRAME_COLS; j++) {
                    catFrames[index++] = tmp[i][j];
                }
            }

            animatedCat = new Animation(.33f, catFrames);
            animatedCat.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        }
    }

    public class TrackAssets {
        public final Texture upTrack;
        public final Texture downTrack;
        public final Texture straightTrack;
        public final Texture supports;

        public TrackAssets() {
            upTrack = assetManager.get("uptracks.png");
            downTrack = assetManager.get("downtracks.png");
            straightTrack = assetManager.get("straighttracks.png");
            supports = assetManager.get("supports.png");
        }
    }

    public class PickUpAssets {

        public final Animation coinAnimation;

        public PickUpAssets() {

            int FRAME_COLS = 4;
            int FRAME_ROWS = 1;


            Texture coinSheet = assetManager.get("coinframes.png");
            TextureRegion[][] tmp = TextureRegion.split(coinSheet, coinSheet.getWidth()/FRAME_COLS,coinSheet.getHeight()/FRAME_ROWS);
            TextureRegion[] coinFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
            int index = 0;
            for (int i = 0; i < FRAME_ROWS; i++) {
                for (int j = 0; j < FRAME_COLS; j++) {
                    coinFrames[index++] = tmp[i][j];
                }
            }

            coinAnimation = new Animation(.20f, coinFrames);
            coinAnimation.setPlayMode(Animation.PlayMode.LOOP);
        }
    }

    public class BackGroundAssets {
        public final Texture bottomBackGroundTile;
        public final Texture middleBackGroundTile;

        public BackGroundAssets() {
            bottomBackGroundTile = assetManager.get("bottomtile.png");
            middleBackGroundTile = assetManager.get("middletile.png");
        }
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}

