package com.myreliablegames.kittycart.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/19/2016.
 */
public class SparkEmitter {

    private MineCart minecart;
    private DelayedRemovalArray<Spark> sparks;
    private long startTime;
    private boolean sparking;

    public SparkEmitter(MineCart mineCart) {
        this.minecart = mineCart;
        sparks = new DelayedRemovalArray<Spark>();
        sparking = false;
        startTime = TimeUtils.nanoTime();
    }

    public void update(float delta) {

        sparks.begin();
        for (Spark spark : sparks) {
            spark.update(delta);

            if (!spark.inBounds()) {
                sparks.removeValue(spark, true);
                EntityPools.getInstance().free(spark);
            }
        }
        sparks.end();

        if (sparking) {
            if (TimeUtils.nanoTime() < startTime + Constants.SPARK_DURATION_NANO) {
                Spark spark = EntityPools.getInstance().sparkPool.obtain();
                spark.init(minecart.getSparkPosition());
                sparks.add(spark);
            } else {
                sparking = false;
            }
        }

    }

    public void render(SpriteBatch batch) {
        for (Spark spark : sparks) {
            spark.render(batch);
        }
    }

    public void spark() {
        startTime = TimeUtils.nanoTime();
        sparking = true;
    }
}
