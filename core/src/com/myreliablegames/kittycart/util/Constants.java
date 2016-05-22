package com.myreliablegames.kittycart.util;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Joe on 5/17/2016.
 */
public class Constants {

    public static final float GRAVITY = 45f;

    public static final float WORLD_HEIGHT = 600;
    public static final float WORLD_WIDTH = 1200;
    public static final float SPEED_LIMIT = 1000;

    public static final float MINECART_WIDTH = 64;
    public static final float MINECART_WHEEL_HEIGHT = 14;
    public static final float MINECART_EDGE_TO_WHEEL_SIZE = 13;
    public static final float SPARK_WIDTH = Assets.getInstance().mineCartAssets.spark.getWidth();
    public static final float TRACK_WIDTH = 64;
    public static final float JUMP_SPEED = 500;
    public static final int MAX_COINS_IN_PLAY = 5;
    public static final float TRACK_SPEED = -310;
    public static final float JUMP_DURATION = .30f;
    public static final float STRAIGHT_TRACK_THICKNESS = 30;
    public static final float SCORE_MARGIN = 10;
    public static final float COIN_SIZE = 32;
    public static final int COIN_SCORE_VALUE = 100;
    // One Twentieth Second
    public static final long SPARK_DURATION_NANO = 200000000;

    // Seven seconds
    public static final long COIN_DEPLOY_INTERVAL_NANO = 7000000000L;
}
