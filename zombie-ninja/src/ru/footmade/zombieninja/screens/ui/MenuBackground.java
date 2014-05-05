package ru.footmade.zombieninja.screens.ui;

import ru.footmade.zombieninja.CommonResources;
import ru.footmade.zombieninja.screens.ui.HorizontalScrollingBackground.Layer;
import ru.footmade.zombieninja.util.Alignment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MenuBackground extends Actor {
	private static final float VIEWER_SPEED = 0.2f;
	private static final float GROUND_HEIGHT = 0.2f;
	private static final float GROUND_DISTANCE = 1;
	private static final float GRASS_HEIGHT = 0.02f;
	private static final float FUJI_HEIGHT = 0.3f;
	private static final float FUJI_POSITION = 0.5f;
	
	private HorizontalScrollingBackground background;
	
	private float viewerX = 0;
	
	@Override
	protected void sizeChanged() {
		CommonResources resources = CommonResources.getInstance();
		
		background = new HorizontalScrollingBackground(getWidth(), getHeight(), resources.getRegion("menu/nofilter/bgr_color"));
		
		Layer fuji = background.addLayer(HorizontalScrollingBackground.INFINITY_DISTANCE);
		fuji.addSingleByHeight(resources.getLinearRegion("menu/fuji"), FUJI_POSITION, GROUND_HEIGHT - GRASS_HEIGHT, FUJI_HEIGHT,
				new Alignment(Alignment.CENTER_HORIZONTAL | Alignment.BOTTOM));
		
		Layer farMountains = background.addLayer(3);
		TextureRegion farMountainsPic = resources.getLinearRegion("menu/mountains1");
		farMountains.addRepeatingByHeight(farMountainsPic, 0, GROUND_HEIGHT - GRASS_HEIGHT, 0.2f, 2);
		farMountains.addRepeatingByHeight(farMountainsPic, 1.5f, GROUND_HEIGHT - GRASS_HEIGHT, 0.2f, 2);
		farMountains.addRepeatingByHeight(farMountainsPic, 1.4f, GROUND_HEIGHT - GRASS_HEIGHT, 0.2f, 2);
		
		Layer nearMountains = background.addLayer(2);
		TextureRegion nearMountainsPic = resources.getLinearRegion("menu/mountains2");
		nearMountains.addRepeatingByHeight(nearMountainsPic, 0, GROUND_HEIGHT - GRASS_HEIGHT, 0.1f, 1.5f);
		nearMountains.addRepeatingByHeight(nearMountainsPic, 0.95f, GROUND_HEIGHT - GRASS_HEIGHT, 0.1f, 1.5f);
		nearMountains.addRepeatingByHeight(nearMountainsPic, 1.2f, GROUND_HEIGHT - GRASS_HEIGHT, 0.1f, 1.5f);
		
		Layer ground = background.addLayer(GROUND_DISTANCE);
		ground.addRepeatingByHeight(resources.getLinearRegion("menu/ground"), 0, 0, GROUND_HEIGHT, 0);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		viewerX -= VIEWER_SPEED * Gdx.graphics.getDeltaTime();
		background.draw(batch, viewerX, 0);
	}
}
