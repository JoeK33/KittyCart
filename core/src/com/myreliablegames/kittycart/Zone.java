package com.myreliablegames.kittycart;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

import java.util.Random;

/**
 * Created by Joe on 5/23/2016.
 */
public enum Zone {

    DESERT, FOREST, MOUNTAIN, OCEAN, PLAINS;

    private static Zone zone = Zone.chooseRandomZone();

    public static Zone getZone() {
        return zone;
    }

    public static void changeZone() {
        Zone newZone = Zone.chooseRandomZone();
        while (getZone() == newZone) {
            newZone = Zone.chooseRandomZone();
        }
        zone = newZone;
    }

    public static Zone chooseRandomZone() {
        Random rand = new Random(TimeUtils.nanoTime());
        int choice = rand.nextInt(5);
        switch (choice) {
            case 0:
                return Zone.DESERT;
            case 1:
                return Zone.FOREST;
            case 2:
                return Zone.MOUNTAIN;
            case 3:
                return Zone.OCEAN;
            case 4:
                return Zone.PLAINS;
            default:
                return Zone.DESERT;
        }
    }

    public static TextureRegion getTopTile() {
        switch (Zone.getZone()) {
            case DESERT:
                return Assets.getInstance().backGroundAssets.topDesertTile;
            case FOREST:
                return Assets.getInstance().backGroundAssets.topForestTile;
            case MOUNTAIN:
                return Assets.getInstance().backGroundAssets.topMountainTile;
            case OCEAN:
                return Assets.getInstance().backGroundAssets.topOceanTile;
            case PLAINS:
                return Assets.getInstance().backGroundAssets.topPlainsTile;
            default:
                return Assets.getInstance().backGroundAssets.topDesertTile;
        }
    }

    public static TextureRegion getMiddleTile() {
        switch (Zone.getZone()) {
            case DESERT:
                return Assets.getInstance().backGroundAssets.middleDesertTile;
            case FOREST:
                return Assets.getInstance().backGroundAssets.middleForestTile;
            case MOUNTAIN:
                return Assets.getInstance().backGroundAssets.middleMountainTile;
            case OCEAN:
                return Assets.getInstance().backGroundAssets.middleOceanTile;
            case PLAINS:
                return Assets.getInstance().backGroundAssets.middlePlainsTile;
            default:
                return Assets.getInstance().backGroundAssets.middleDesertTile;
        }
    }

    public static TextureRegion getBottomTile() {
        switch (Zone.getZone()) {
            case DESERT:
                return Assets.getInstance().backGroundAssets.bottomDesertTile;
            case FOREST:
                return Assets.getInstance().backGroundAssets.bottomForestTile;
            case MOUNTAIN:
                return Assets.getInstance().backGroundAssets.bottomMountainTile;
            case OCEAN:
                return Assets.getInstance().backGroundAssets.bottomOceanTile;
            case PLAINS:
                return Assets.getInstance().backGroundAssets.bottomPlainsTile;
            default:
                return Assets.getInstance().backGroundAssets.bottomDesertTile;
        }
    }

    public static TextureRegion getTumblingObject() {
        switch (Zone.getZone()) {
            case DESERT:
                return Assets.getInstance().tumbleAssets.desertObject;
            case FOREST:
                return Assets.getInstance().tumbleAssets.forestObject;
            case MOUNTAIN:
                return Assets.getInstance().tumbleAssets.mountainObject;
            case OCEAN:
                return Assets.getInstance().tumbleAssets.oceanObject;
            case PLAINS:
                return Assets.getInstance().tumbleAssets.plainsObject;
            default:
                return Assets.getInstance().tumbleAssets.desertObject;
        }
    }

    public static long getTumblingObjectDeployTime() {
        switch (Zone.getZone()) {
            case DESERT:
                return Constants.TEN_SECONDS_NANO;
            case FOREST:
                return Constants.ONE_AND_A_HALF_SECONDS_NANO;
            case MOUNTAIN:
                return Constants.TEN_SECONDS_NANO;
            case OCEAN:
                return Constants.TWENTY_SECONDS_NANO;
            case PLAINS:
                return Constants.TWENTY_SECONDS_NANO;
            default:
                return Constants.TEN_SECONDS_NANO;
        }
    }
}


