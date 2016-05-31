package com.myreliablegames.kittycart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.myreliablegames.kittycart.util.Assets;

/**
 * Created by Joe on 5/28/2016.
 */
public class PlayerPreferences {

    private static Preferences prefs = Gdx.app.getPreferences("My Preferences");
    private static Cart cart = Cart.getCartFromValue(prefs.getInteger("cart", 1));
    private static Cat cat = Cat.getCatFromValue(prefs.getInteger("cat", 1));
    private static boolean coasterModeOn = prefs.getBoolean("coasterModeOn", false);

    public enum Cart {
        MINECART, LITTERBOX, COASTER, SKATEBOARD;

        public static int getValue(Cart cart) {
            switch (cart) {
                case MINECART:
                    return 1;
                case LITTERBOX:
                    return 2;
                case COASTER:
                    return 3;
                case SKATEBOARD:
                    return 4;
                default:
                    return 1;
            }
        }
        public static Cart getCartFromValue(int value) {
            if (value > 4 || value < 1) {
                throw new RuntimeException("Invalid cart value");
            }

            switch (value) {
                case 1:
                    return MINECART;
                case 2:
                    return LITTERBOX;
                case 3:
                    return COASTER;
                case 4:
                    return SKATEBOARD;
                default: return MINECART;
            }
        }
    }

    public enum Cat {
        Cat1, Cat2, Cat3, Cat4;

        public static int getValue(Cat cat) {
            switch (cat) {
                case Cat1:
                    return 1;
                case Cat2:
                    return 2;
                case Cat3:
                    return 3;
                case Cat4:
                    return 4;
                default: return 1;
            }
        }

        public static Cat getCatFromValue(int value) {
            if (value > 4 || value < 1) {
                throw new RuntimeException("Invalid cat value");
            }

            switch (value) {
                case 1:
                    return Cat1;
                case 2:
                    return Cat2;
                case 3:
                    return Cat3;
                case 4:
                    return Cat4;
                default: return Cat1;
            }
        }
    }

    public static boolean coasterModeOn() {
        return coasterModeOn;
    }

    public static void toggleCoasterMode() {
        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        coasterModeOn = !coasterModeOn;
        prefs.putBoolean("coasterModeOn", coasterModeOn);
    }

    public static void setCart(Cart newCart) {
        cart = newCart;
        prefs.putInteger("cart", Cart.getValue(cart));
    }

    public static void setCat(Cat newCat) {
        cat = newCat;
        prefs.putInteger("cat", Cat.getValue(cat));
    }

    public static Cart getCart() {
        return cart;
    }

    public static Cat getCat() {
        return cat;
    }

    public static TextureRegion getCartFront() {

        switch (PlayerPreferences.cart) {
            case MINECART:
                return Assets.getInstance().mineCartAssets.minecartFront;
            case SKATEBOARD:
                return Assets.getInstance().mineCartAssets.skateboardFront;
            case LITTERBOX:
                return Assets.getInstance().mineCartAssets.litterboxFront;
            case COASTER:
                return Assets.getInstance().mineCartAssets.coasterCarFront;
            default:
                return Assets.getInstance().mineCartAssets.minecartFront;
        }
    }

    public static TextureRegion getCartBack() {

        switch (PlayerPreferences.cart) {
            case MINECART:
                return Assets.getInstance().mineCartAssets.minecartBack;
            case SKATEBOARD:
                return Assets.getInstance().mineCartAssets.skateboardBack;
            case LITTERBOX:
                return Assets.getInstance().mineCartAssets.litterboxBack;
            case COASTER:
                return Assets.getInstance().mineCartAssets.coasterCarBack;
            default:
                return Assets.getInstance().mineCartAssets.minecartBack;
        }
    }

    public static TextureRegion getCartFront(Cart cart) {

        switch (cart) {
            case MINECART:
                return Assets.getInstance().mineCartAssets.minecartFront;
            case SKATEBOARD:
                return Assets.getInstance().mineCartAssets.skateboardFront;
            case LITTERBOX:
                return Assets.getInstance().mineCartAssets.litterboxFront;
            case COASTER:
                return Assets.getInstance().mineCartAssets.coasterCarFront;
            default:
                return Assets.getInstance().mineCartAssets.minecartFront;
        }
    }

    public static TextureRegion getCartBack(Cart cart) {

        switch (cart) {
            case MINECART:
                return Assets.getInstance().mineCartAssets.minecartBack;
            case SKATEBOARD:
                return Assets.getInstance().mineCartAssets.skateboardBack;
            case LITTERBOX:
                return Assets.getInstance().mineCartAssets.litterboxBack;
            case COASTER:
                return Assets.getInstance().mineCartAssets.coasterCarBack;
            default:
                return Assets.getInstance().mineCartAssets.minecartBack;
        }
    }

    public static Animation getCatAnimation() {

        switch (PlayerPreferences.cat) {
            case Cat1:
                return Assets.getInstance().mineCartAssets.animatedCat1;
            case Cat2:
                return Assets.getInstance().mineCartAssets.animatedCat2;
            case Cat3:
                return Assets.getInstance().mineCartAssets.animatedCat3;
            case Cat4:
                return Assets.getInstance().mineCartAssets.animatedCat4;
            default:
                return Assets.getInstance().mineCartAssets.animatedCat1;

        }
    }

    public static Animation getCatAnimation(Cat cat) {

        switch (cat) {
            case Cat1:
                return Assets.getInstance().mineCartAssets.animatedCat1;
            case Cat2:
                return Assets.getInstance().mineCartAssets.animatedCat2;
            case Cat3:
                return Assets.getInstance().mineCartAssets.animatedCat3;
            case Cat4:
                return Assets.getInstance().mineCartAssets.animatedCat4;
            default:
                return Assets.getInstance().mineCartAssets.animatedCat1;

        }
    }
}
