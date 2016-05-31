package com.myreliablegames.kittycart.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.myreliablegames.kittycart.Tracks.TrackLayer;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/19/2016.
 * Deals with coins.  Uses a timer to deploy a set of coins at an interval above the track.
 */
public class CoinHandler {

    private DelayedRemovalArray<Coin> coins;
    private TrackLayer trackLayer;
    private long coinTimer;

    public CoinHandler(TrackLayer trackLayer) {
        coins = new DelayedRemovalArray<Coin>();
        this.trackLayer = trackLayer;
        coinTimer = TimeUtils.nanoTime();
    }

    public void update(float delta) {

        if (coins.size < 1 && TimeUtils.nanoTime() > coinTimer + Constants.COIN_DEPLOY_INTERVAL_NANO) {
            // Put a coin every other track space until there are no more to place.
            int everyOther = 0;
            int coinsToPlace = (int)((Math.random() * Constants.MAX_COINS_IN_PLAY) + 1);
            for (Track track : trackLayer.getTracksInPlay()) {

                if (coins.size < coinsToPlace && (everyOther % 2 == 0)) {
                if (track.getPosition().x > Constants.WORLD_WIDTH) {
                    addCoin(new Vector2(track.getPosition().x, track.getPosition().y + Constants.TRACK_WIDTH + (Constants.COIN_SIZE * coinOffsetFactor())));
                }
            }
            everyOther++;
            }
            coinTimer = TimeUtils.nanoTime();
        }

        coins.begin();
        for (Coin coin : coins) {
            coin.update(delta);
            if (coin.getPosition().x < 0 - Constants.COIN_SIZE) {
                coins.removeValue(coin, true);
                EntityPools.getInstance().coinPool.free(coin);
            }
        }
        coins.end();
    }

    public void removeOffScreenCoins() {

        coins.begin();
        for (Coin coin : coins) {
            if (coin.getPosition().x > Constants.WORLD_WIDTH) {
                coins.removeValue(coin, true);
            }
        }
        coins.end();
    }

    public void render(SpriteBatch batch) {
        for (Coin coin : coins) {
            coin.render(batch);
        }
    }

    public DelayedRemovalArray<Coin> getCoins() {
        return coins;
    }

    public void addCoin(Vector2 position) {
        Coin coin = EntityPools.getInstance().coinPool.obtain();
        coin.init(position);
        coins.add(coin);
    }

    public void remove(Coin coin) {
        coins.begin();
        if (coins.contains(coin, true)) {
            coins.removeValue(coin, true);
            EntityPools.getInstance().free(coin);
        }
        coins.end();
    }

    public int coinOffsetFactor() {
       return (int)((Math.random() * 3) + 1);
    }

}
