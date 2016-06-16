package com.myreliablegames.kittycart;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Joe on 6/4/2016.
 */
public class UnlockableTextButton extends TextButton {
    private boolean isLocked;

    public UnlockableTextButton(String text, Skin skin, boolean locked) {
        super(text, skin);
        isLocked = locked;
    }

    public void setLock(boolean locked) {
        isLocked = locked;
    }
    public boolean isLocked() {
        return isLocked;
    }
}
