package com.myreliablegames.kittycart;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.myreliablegames.kittycart.util.Assets;

public class KittyCartGame extends Game {
	ActionResolver resolver;

	public KittyCartGame(ActionResolver resolver) {
		this.resolver = resolver;
	}

	@Override
	public void create () {
		Gdx.input.setCatchBackKey(true);
		setScreen(new LoadingScreen(this));
	}

	public void openMainMenu() {
		Assets.getInstance().soundAssets.menuMusic.play();
		setScreen (new MenuScreen(this, resolver));}

	public void startGame() {
		Assets.getInstance().soundAssets.menuMusic.stop();
		setScreen(new GameScreen(this, resolver));
	}

	public void openCustomizeMenu() {
		setScreen(new OptionsScreen(this, resolver));
	}
}
