package ru.footmade.zombieninja;

import ru.footmade.zombieninja.screens.Menu;
import ru.footmade.zombieninja.tween.TweenCatalogue;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {
	private static Game _self;
	
	public static Game getGame() {
		return _self;
	}
	
	public MyGdxGame() {
		_self = this;
	}
	
	@Override
	public void create() {
		TweenCatalogue.register();
		setScreen(new Menu());
	}
	
	@Override
	public void dispose() {
		super.dispose();
		CommonResources.getInstance().dispose();
	}
}
