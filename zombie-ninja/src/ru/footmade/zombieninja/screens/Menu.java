package ru.footmade.zombieninja.screens;

import ru.footmade.zombieninja.screens.ui.MenuBackground;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Menu extends ScreenAdapter {
	private Stage stage;
	
	@Override
	public void show() {
		stage = new Stage();
		MenuBackground backgroundView = new MenuBackground();
		backgroundView.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.addActor(backgroundView);
		
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
        stage.draw();
	}
}
