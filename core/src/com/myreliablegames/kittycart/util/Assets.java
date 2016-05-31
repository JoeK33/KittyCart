package com.myreliablegames.kittycart.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.myreliablegames.kittycart.Zone;

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
    public MenuAssets menuAssets;

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
        assetManager.load("packed/kittycart.pack", TextureAtlas.class);

        // Load Assets
        assetManager.load("sounds/coinpickupsound.wav", Sound.class);
        assetManager.load("sounds/gameoversound.wav", Sound.class);
        assetManager.load("sounds/jumpsound.wav", Sound.class);
        assetManager.load("sounds/longjumpsound.wav", Sound.class);
        assetManager.load("sounds/railhitsound.wav", Sound.class);
        assetManager.load("sounds/transitionsound.wav", Sound.class);
        assetManager.load("sounds/trainsound.wav", Music.class);
        assetManager.load("sounds/oceanmusic.mp3", Music.class);
        assetManager.load("sounds/forestmusic.mp3", Music.class);
        assetManager.load("sounds/desertmusic.mp3", Music.class);
        assetManager.load("sounds/plainsmusic.mp3", Music.class);
        assetManager.load("sounds/mountainmusic.mp3", Music.class);

        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get("packed/kittycart.pack");

        mineCartAssets = new MineCartAssets(atlas);
        trackAssets = new TrackAssets(atlas);
        pickUpAssets = new PickUpAssets(atlas);
        backGroundAssets = new BackGroundAssets(atlas);
        soundAssets = new SoundAssets();
        menuAssets = new MenuAssets(atlas);

    }

    public class MineCartAssets {
        public final TextureRegion minecartFront;
        public final TextureRegion minecartBack;
        public final TextureRegion skateboardFront;
        public final TextureRegion skateboardBack;
        public final TextureRegion litterboxFront;
        public final TextureRegion litterboxBack;
        public final TextureRegion coasterCarFront;
        public final TextureRegion coasterCarBack;

        public final TextureRegion spark;

        public final Animation animatedCat1;
        public final Animation animatedCat2;
        public final Animation animatedCat3;
        public final Animation animatedCat4;

        public MineCartAssets(TextureAtlas atlas) {

            minecartFront = atlas.findRegion("minecarts/minecartfront");
            minecartBack = atlas.findRegion("minecarts/minecartback");

            skateboardFront = atlas.findRegion("minecarts/skateboardfront");
            skateboardBack = atlas.findRegion("minecarts/skateboardback");

            litterboxFront = atlas.findRegion("minecarts/litterboxfront");
            litterboxBack = atlas.findRegion("minecarts/litterboxback");

            coasterCarFront = atlas.findRegion("minecarts/coastercarfront");
            coasterCarBack = atlas.findRegion("minecarts/coastercarback");

            spark = atlas.findRegion("spark");


            animatedCat1 = makeCatAnimationFromRegion(atlas.findRegion("cats/catframes"));
            animatedCat2 = makeCatAnimationFromRegion(atlas.findRegion("cats/catframes2"));
            animatedCat3 = makeCatAnimationFromRegion(atlas.findRegion("cats/catframes3"));
            animatedCat4 = makeCatAnimationFromRegion(atlas.findRegion("cats/catframes4"));
        }
    }

    private Animation makeCatAnimationFromRegion(TextureRegion catSheet) {
        int FRAME_COLS = 5;
        int FRAME_ROWS = 1;

        TextureRegion[][] tmp = catSheet.split(Constants.CAT_SIZE, Constants.CAT_SIZE);
        TextureRegion[] catFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                catFrames[index++] = tmp[i][j];
            }
        }
        Animation animation = new Animation(.33f, catFrames);
        animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        return animation;
    }

    public class TrackAssets {
        public final TextureRegion upTrack;
        public final TextureRegion downTrack;
        public final TextureRegion straightTrack;
        public final TextureRegion supports;
        public final TextureRegion upTrackCoaster;
        public final TextureRegion downTrackCoaster;
        public final TextureRegion straightTrackCoaster;
        public final TextureRegion supportsCoaster;

        public TrackAssets(TextureAtlas atlas) {
            upTrack = atlas.findRegion("tracks/uptracks");
            downTrack = atlas.findRegion("tracks/downtracks");
            straightTrack = atlas.findRegion("tracks/straighttracks");
            supports = atlas.findRegion("tracks/supports");

            upTrackCoaster = atlas.findRegion("tracks/coasteruptracks");
            downTrackCoaster = atlas.findRegion("tracks/coasterdowntracks");
            straightTrackCoaster = atlas.findRegion("tracks/coasterstraighttracks");
            supportsCoaster = atlas.findRegion("tracks/coastersupports");
        }
    }

    public class PickUpAssets {

        public final Animation coinAnimation;

        public PickUpAssets(TextureAtlas atlas) {

            int FRAME_COLS = 4;
            int FRAME_ROWS = 1;


            TextureRegion coinSheet = atlas.findRegion("coinframes");
            TextureRegion[][] tmp = coinSheet.split(Constants.COIN_SIZE, Constants.COIN_SIZE);
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
        public final TextureRegion bottomDesertTile;
        public final TextureRegion middleDesertTile;
        public final TextureRegion topDesertTile;

        public final TextureRegion bottomOceanTile;
        public final TextureRegion middleOceanTile;
        public final TextureRegion topOceanTile;

        public final TextureRegion bottomMountainTile;
        public final TextureRegion middleMountainTile;
        public final TextureRegion topMountainTile;

        public final TextureRegion bottomForestTile;
        public final TextureRegion middleForestTile;
        public final TextureRegion topForestTile;

        public final TextureRegion bottomPlainsTile;
        public final TextureRegion middlePlainsTile;
        public final TextureRegion topPlainsTile;

        public final TextureRegion fishTile;


        public BackGroundAssets(TextureAtlas atlas) {
            bottomDesertTile = atlas.findRegion("background/desertbottomtile");
            middleDesertTile = atlas.findRegion("background/desertmiddletile");
            topDesertTile = atlas.findRegion("background/deserttoptile");

            bottomOceanTile = atlas.findRegion("background/oceanbottomtile");
            middleOceanTile = atlas.findRegion("background/oceanmiddletile");
            topOceanTile = atlas.findRegion("background/oceantoptile");

            bottomMountainTile = atlas.findRegion("background/mountainbottomtile");
            middleMountainTile = atlas.findRegion("background/mountainmiddletile");
            topMountainTile = atlas.findRegion("background/mountaintoptile");

            bottomForestTile = atlas.findRegion("background/forestbottomtile");
            middleForestTile = atlas.findRegion("background/forestmiddletile");
            topForestTile = atlas.findRegion("background/foresttoptile");

            bottomPlainsTile = atlas.findRegion("background/plainsbottomtile");
            middlePlainsTile = atlas.findRegion("background/plainsmiddletile");
            topPlainsTile = atlas.findRegion("background/plainstoptile");

            fishTile = atlas.findRegion("background/fishtile");
        }
    }

    public class MenuAssets {
        public final TextureRegion selectionRectangle;

        public MenuAssets(TextureAtlas atlas) {
            selectionRectangle = atlas.findRegion("selectionrectangle");
        }
    }

    public class SoundAssets {

        public final Sound coinPickupSound;
        public final Sound gameOverSound;
        public final Sound jumpSound;
        public final Sound longJumpSound;
        public final Sound railContactSound;
        public final Sound transitionSound;
        public final Music trainSound;
        public final Music forestMusic;
        public final Music oceanMusic;
        public final Music mountainMusic;
        public final Music plainsMusic;
        public final Music desertMusic;

        public SoundAssets() {
            coinPickupSound = assetManager.get("sounds/coinpickupsound.wav");
            gameOverSound = assetManager.get("sounds/gameoversound.wav");
            jumpSound = assetManager.get("sounds/jumpsound.wav");
            longJumpSound = assetManager.get("sounds/longjumpsound.wav");
            railContactSound = assetManager.get("sounds/railhitsound.wav");
            transitionSound = assetManager.get("sounds/transitionsound.wav");

            trainSound = assetManager.get("sounds/trainsound.wav");
            trainSound.setLooping(true);
            trainSound.setVolume(.6f);

            oceanMusic = assetManager.get("sounds/oceanmusic.mp3");
            oceanMusic.setLooping(true);
            oceanMusic.setVolume(.4f);

            forestMusic = assetManager.get("sounds/forestmusic.mp3");
            forestMusic.setLooping(true);

            desertMusic = assetManager.get("sounds/desertmusic.mp3");
            desertMusic.setLooping(true);

            plainsMusic = assetManager.get("sounds/plainsmusic.mp3");
            plainsMusic.setLooping(true);
            plainsMusic.setVolume(.6f);

            mountainMusic = assetManager.get("sounds/mountainmusic.mp3");
            mountainMusic.setLooping(true);
            mountainMusic.setVolume(.5f);

        }

        public Music getCorrespondingMusic(Zone zone) {
            switch (zone) {
                case DESERT:
                    return desertMusic;
                case FOREST:
                    return forestMusic;
                case MOUNTAIN:
                    return mountainMusic;
                case OCEAN:
                    return oceanMusic;
                case PLAINS:
                    return plainsMusic;
                default:
                    return oceanMusic;
            }
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

