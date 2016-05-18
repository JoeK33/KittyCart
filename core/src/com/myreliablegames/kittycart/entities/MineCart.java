package com.myreliablegames.kittycart.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.myreliablegames.kittycart.util.Assets;
import com.myreliablegames.kittycart.util.Constants;

import java.util.List;

/**
 * Created by Joe on 5/17/2016.
 */
public class MineCart {

    private static final String TAG = "MineCart";
    // Position of minecart is the front corner of texture.
    private Vector2 position;
    private Vector2 velocity;

    public MineCart() {
        this.position = new Vector2(Constants.WORLD_WIDTH / 5, Constants.WORLD_HEIGHT / 2);
        velocity = new Vector2(0, 0);
    }

    public void render(SpriteBatch batch) {

        batch.draw(Assets.getInstance().mineCartAssets.minecart, position.x - Constants.MINECART_WIDTH, position.y);
        Gdx.app.log(TAG, "Position X: " + position.x + " Position Y: " + position.y);
    }

    public void update(float delta, List<Track> trackList) {
        // Gravity
        velocity.y -= Constants.GRAVITY;



        for (Track track : trackList) {
            Vector2 contactPoint = track.contactsAtPosition(position);
            if (contactPoint != null) {
                if (position.y < contactPoint.y) {
                    position.y = contactPoint.y;
                }
            }
        }

        position.mulAdd(velocity, delta);
    }

}
