package ru.footmade.zombieninja;

import ru.footmade.zombieninja.screens.Gameplay;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {
	
	@Override
	public void create() {
		setScreen(new Gameplay());
	}
}
