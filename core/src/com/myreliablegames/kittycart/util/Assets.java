package com.myreliablegames.kittycart.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Joe on 5/17/2016.
 * Controls assets for the game.  Assets are loaded into asset manager before being made accessable via inner
 * objects.
 */
public class Assets implements Disposable, AssetErrorListener {

    private final static String TAG = "Asset Manager";

    private static Assets instance;
    private AssetManager assetManager;
    public MineCartAssets mineCartAssets;
    public TrackAssets trackAssets;
    public PickUpAssets pickUpAssets;
    public BackGroundAssets backGroundAssets;
    public SoundAssets soundAssets;

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

        assetManager.load("background/desertbottomtile.png", Texture.class);
        assetManager.load("background/desertmiddletile.png", Texture.class);
        assetManager.load("background/deserttoptile.png", Texture.class);

        assetManager.load("background/oceanbottomtile.png", Texture.class);
        assetManager.load("background/oceanmiddletile.png", Texture.class);
        assetManager.load("background/oceantoptile.png", Texture.class);

        assetManager.load("background/mountainbottomtile.png", Texture.class);
        assetManager.load("background/mountainmiddletile.png", Texture.class);
        assetManager.load("background/mountaintoptile.png", Texture.class);

        assetManager.load("background/forestbottomtile.png", Texture.class);
        assetManager.load("background/forestmiddletile.png", Texture.class);
        assetManager.load("background/foresttoptile.png", Texture.class);

        assetManager.load("background/plainsbottomtile.png", Texture.class);
        assetManager.load("background/plainsmiddletile.png", Texture.class);
        assetManager.load("background/plainstoptile.png", Texture.class);

        assetManager.load("sounds/coinpickupsound.wav", Sound.class);
        assetManager.load("sounds/gameoversound.wav", Sound.class);
        assetManager.load("sounds/jumpsound.wav", Sound.class);
        assetManager.load("sounds/longjumpsound.wav", Sound.class);
        assetManager.load("sounds/railhitsound.wav", Sound.class);
        assetManager.load("sounds/trainsound.wav", Music.class);
        assetManager.load("sounds/gamemusic.mp3", Music.class);

        assetManager.finishLoading();

        mineCartAssets = new MineCartAssets();
        trackAssets = new TrackAssets();
        pickUpAssets = new PickUpAssets();
        backGroundAssets = new BackGroundAssets();
        soundAssets = new SoundAssets();
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
        public final Texture bottomDesertTile;
        public final Texture middleDesertTile;
        public final Texture topDesertTile;

        public final Texture bottomOceanTile;
        public final Texture middleOceanTile;
        public final Texture topOceanTile;

        public final Texture bottomMountainTile;
        public final Texture middleMountainTile;
        public final Texture topMountainTile;

        public final Texture bottomForestTile;
        public final Texture middleForestTile;
        public final Texture topForestTile;

        public final Texture bottomPlainsTile;
        public final Texture middlePlainsTile;
        public final Texture topPlainsTile;


        public BackGroundAssets() {
            bottomDesertTile = assetManager.get("background/desertbottomtile.png");
            middleDesertTile = assetManager.get("background/desertmiddletile.png");
            topDesertTile = assetManager.get("background/deserttoptile.png");

            bottomOceanTile = assetManager.get("background/oceanbottomtile.png");
            middleOceanTile = assetManager.get("background/oceanmiddletile.png");
            topOceanTile = assetManager.get("background/oceantoptile.png");

            bottomMountainTile = assetManager.get("background/mountainbottomtile.png");
            middleMountainTile = assetManager.get("background/mountainmiddletile.png");
            topMountainTile = assetManager.get("background/mountaintoptile.png");

            bottomForestTile = assetManager.get("background/forestbottomtile.png");
            middleForestTile = assetManager.get("background/forestmiddletile.png");
            topForestTile = assetManager.get("background/foresttoptile.png");

            bottomPlainsTile = assetManager.get("background/plainsbottomtile.png");
            middlePlainsTile = assetManager.get("background/plainsmiddletile.png");
            topPlainsTile = assetManager.get("background/plainstoptile.png");
        }
    }

    public class SoundAssets {

        public final Sound coinPickupSound;
        public final Sound gameOverSound;
        public final Sound jumpSound;
        public final Sound longJumpSound;
        public final Sound railContactSound;
        public final Music trainSound;
        public final Music gameMusic;

        public SoundAssets() {
            coinPickupSound = assetManager.get("sounds/coinpickupsound.wav");
            gameOverSound = assetManager.get("sounds/gameoversound.wav");
            jumpSound = assetManager.get("sounds/jumpsound.wav");
            longJumpSound = assetManager.get("sounds/longjumpsound.wav");
            railContactSound = assetManager.get("sounds/railhitsound.wav");

            trainSound = assetManager.get("sounds/trainsound.wav");
            trainSound.setLooping(true);
            trainSound.setVolume(.30f);

            gameMusic = assetManager.get("sounds/gamemusic.mp3");
            gameMusic.setLooping(true);
            gameMusic.setVolume(.5f);

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

