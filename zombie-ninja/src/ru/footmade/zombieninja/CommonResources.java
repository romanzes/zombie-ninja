package ru.footmade.zombieninja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class CommonResources implements Disposable {
	private static CommonResources _instance;
	
	private TextureAtlas atlas;
	
	public static CommonResources getInstance() {
		if (_instance == null)
			_instance = new CommonResources();
		return _instance;
	}
	
	public CommonResources() {
		atlas = new TextureAtlas(Gdx.files.internal("img/pack.atlas"));
	}
	
	public TextureAtlas getAtlas() {
		return atlas;
	}
	
	public TextureRegion getRegion(String name) {
		return atlas.findRegion(name);
	}
	
	public TextureRegion getLinearRegion(String name) {
		return getRegion(name, TextureFilter.Linear);
	}
	
	public TextureRegion getRegion(String name, TextureFilter filter) {
		TextureRegion result = atlas.findRegion(name);
		result.getTexture().setFilter(filter, filter);
		return result;
	}

	@Override
	public void dispose() {
		atlas.dispose();
	}
}
