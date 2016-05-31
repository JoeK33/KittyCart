package com.myreliablegames.kittycart;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.myreliablegames.kittycart.entities.MineCart;

/**
 * Created by Joe on 5/18/2016.
 */
public class Controller implements InputProcessor {
    MineCart mineCart;
    private long touchTime;
    private Level level;

    public Controller(MineCart mineCart, Level level) {
        this.mineCart = mineCart;
        touchTime = 0;
        this.level = level;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            mineCart.jump();
        } else if (keycode == Input.Keys.L) {
            mineCart.longLump();
        } else if (keycode == Input.Keys.P) {
            level.pauseToggle();
        } else if (keycode == Input.Keys.C) {
            Zone.changeZone();
        } else if (keycode == Input.Keys.BACK) {
            if (level.isPaused()) {
                level.goToMenu();
            } else {
                level.pauseToggle();
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchTime = TimeUtils.nanoTime();

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Touch to jump, hold and release to long jump.

        if (level.isPaused()) {
            level.pauseToggle();
        } else {
            if (timeSince(touchTime) < .3f) {
                mineCart.jump();
            } else {
                mineCart.longLump();
            }
        }

        return false;
    }

    private float timeSince(long touchTime) {

        float time = (TimeUtils.nanoTime() - touchTime) * MathUtils.nanoToSec;
        return time;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
