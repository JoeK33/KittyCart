package com.myreliablegames.kittycart.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
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
        assetManager.load("spark.png", Texture.class);

        assetManager.load("downtracks.png", Texture.class);
        assetManager.load("straighttracks.png", Texture.class);
        assetManager.load("uptracks.png", Texture.class);

        assetManager.load("coin.png", Texture.class);

        assetManager.finishLoading();

        mineCartAssets = new MineCartAssets();
        trackAssets = new TrackAssets();
        pickUpAssets = new PickUpAssets();
    }

    public class MineCartAssets {
        public final Texture minecart;
        public final Texture spark;

        public MineCartAssets() {
            minecart = assetManager.get("minecart.png");
            spark = assetManager.get("spark.png");
            minecart.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }
    }

    public class TrackAssets {
        public final Texture upTrack;
        public final Texture downTrack;
        public final Texture straightTrack;

        public TrackAssets() {
            upTrack = assetManager.get("uptracks.png");
            downTrack = assetManager.get("downtracks.png");
            straightTrack = assetManager.get("straighttracks.png");
        }
    }

    public class PickUpAssets {
        public final Texture coin;

        public PickUpAssets() {
            coin = assetManager.get("coin.png");
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

