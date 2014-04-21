package ru.footmade.dummymagic.repacker;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class Main {

	public static void main(String[] args) {
		Settings settings = new Settings();
		settings.pot = false;
		settings.maxWidth = 1024;
		TexturePacker2.process(settings, "../images", "../zombie-ninja-android/assets/img", "pack");
	}
}
