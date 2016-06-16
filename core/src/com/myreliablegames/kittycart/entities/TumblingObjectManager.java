package com.myreliablegames.kittycart.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.myreliablegames.kittycart.Zone;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 6/14/2016.
 */
public class TumblingObjectManager {

    private final int NUMBER_OF_LEAVES = 5;
    private long lastDeployedTime;

    private DelayedRemovalArray<TumblingObject> objects;

    public TumblingObjectManager() {
        objects = new DelayedRemovalArray<TumblingObject>();
        lastDeployedTime = TimeUtils.nanoTime();
    }

    public void update(float delta) {

        objects.begin();
        for (TumblingObject tumblingObject : objects) {
            tumblingObject.update(delta);

            if (!tumblingObject.inBounds()) {
                objects.removeValue(tumblingObject, true);
                EntityPools.getInstance().free(tumblingObject);
            }
        }

        if (lastDeployedTime < TimeUtils.nanoTime() - Zone.getTumblingObjectDeployTime()) {
            addObject();
            lastDeployedTime = TimeUtils.nanoTime();
        }
        objects.end();
    }

    public void reset() {
        objects.clear();
    }

    public void addObject() {

        TumblingObject tumblingObject;

        switch (Zone.getZone()) {
            case DESERT:
                tumblingObject = EntityPools.getInstance().tumblePool.obtain();
                tumblingObject.init(TumblingObject.TumbleType.SPIN);
                objects.add(tumblingObject);
                break;
            case FOREST:
                tumblingObject = EntityPools.getInstance().tumblePool.obtain();
                tumblingObject.init(TumblingObject.TumbleType.SWAY);
                objects.add(tumblingObject);
                break;
            case MOUNTAIN:
                tumblingObject = EntityPools.getInstance().tumblePool.obtain();
                tumblingObject.init(TumblingObject.TumbleType.SWAY);
                objects.add(tumblingObject);
                break;
            case OCEAN:
                tumblingObject = EntityPools.getInstance().tumblePool.obtain();
                tumblingObject.init(TumblingObject.TumbleType.SPIN);
                objects.add(tumblingObject);
                break;
            case PLAINS:
               tumblingObject = EntityPools.getInstance().tumblePool.obtain();
                tumblingObject.init(TumblingObject.TumbleType.SPIN);
                objects.add(tumblingObject);
                break;
            default:
                tumblingObject = EntityPools.getInstance().tumblePool.obtain();
                tumblingObject.init(TumblingObject.TumbleType.SPIN);
                objects.add(tumblingObject);
                break;
        }
    }

    public void render(SpriteBatch batch) {
        for (TumblingObject tumblingObject : objects) {
            tumblingObject.render(batch);
        }
    }

}
