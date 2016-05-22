package com.myreliablegames.kittycart.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

/**
 * Created by Joe on 5/21/2016.
 */
public class Cat {

    private float stateTime;
    private final float CAT_FALL_SPACING = 8;

    public Cat() {

        stateTime = 0;
    }

    public void render(SpriteBatch batch, Vector2 position, float bobOffset, float rotationAngle, MineCart.JumpState jumpState) {


        if (jumpState == MineCart.JumpState.FALLING) {
            batch.draw(Assets.getInstance().mineCartAssets.animatedCat.getKeyFrame(stateTime),
                    position.x - Constants.MINECART_WIDTH,
                    position.y + bobOffset + CAT_FALL_SPACING,
                    Constants.MINECART_WIDTH / 2,
                    Constants.MINECART_WIDTH / 2,
                    Constants.MINECART_WIDTH,
                    Constants.MINECART_WIDTH,
                    1f,
                    1f,
                    rotationAngle );

        } else {
            batch.draw(Assets.getInstance().mineCartAssets.animatedCat.getKeyFrame(stateTime),
                    position.x - Constants.MINECART_WIDTH,
                    position.y + bobOffset,
                    Constants.MINECART_WIDTH / 2,
                    Constants.MINECART_WIDTH / 2,
                    Constants.MINECART_WIDTH,
                    Constants.MINECART_WIDTH,
                    1f,
                    1f,
                    rotationAngle);
        }
    }

    public void update(float delta) {
        stateTime += delta;
    }


}
