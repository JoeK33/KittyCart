package com.myreliablegames.kittycart;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myreliablegames.kittycart.util.Assets;

public class KittyCartGame extends Game {

	@Override
	public void create () {

		Assets.getInstance().init(new AssetManager());
		Gdx.input.setCatchBackKey(true);
		setScreen(new MenuScreen(this));
	}

	public void startGame() {
		setScreen(new GameScreen(this));
	}

	public void openCustomizeMenu() {
		setScreen(new CustomizeScreen(this));
	}
}
