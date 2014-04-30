package ru.footmade.zombieninja;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "zombie-ninja";
		cfg.width = 1400;
		cfg.height = 850;
		
		new LwjglApplication(new MyGdxGame(), cfg);
	}
}
