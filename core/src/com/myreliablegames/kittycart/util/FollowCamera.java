package com.myreliablegames.kittycart.util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.myreliablegames.kittycart.entities.MineCart;


/**
 * Created by Joe on 5/18/2016.
 */
public class FollowCamera {

    Camera camera;

    public FollowCamera (Camera camera) {
        this.camera = camera;
    }

    public void update(MineCart minecart) {
        camera.position.y = minecart.getPosition().y + Constants.MINECART_WIDTH;
        camera.update();
    }

    public Camera getCamera() {
        return camera;
    }
}
