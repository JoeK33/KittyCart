package com.myreliablegames.kittycart.entities;

import com.badlogic.gdx.utils.Pool;

/**
 * Created by Joe on 5/24/2016.
 */
public class EntityPools {

    private static EntityPools instance;

    private EntityPools(){}

    public static EntityPools getInstance() {
        if (instance == null) {
            instance = new EntityPools();
        }
        return instance;
    }

    public final Pool<Track> trackPool = new Pool<Track>() {
        @Override
        protected Track newObject() {
            return  new Track();
        }
    };

    public final Pool<Coin> coinPool = new Pool<Coin>() {
        @Override
        protected Coin newObject() {
            return  new Coin();
        }
    };

    public final Pool<Spark> sparkPool = new Pool<Spark>() {
        @Override
        protected Spark newObject() {
            return  new Spark();
        }
    };

    public void free(Track track) {
        trackPool.free(track);
    }

    public void free(Spark spark) {
        sparkPool.free(spark);
    }

    public void free (Coin coin) {
        coinPool.free(coin);
    }
}
