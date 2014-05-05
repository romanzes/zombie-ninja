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
		
		Layer clouds = background.addLayer(3);
		TextureRegion cloud1Pic = resources.getLinearRegion("menu/cloud1");
		TextureRegion cloud2Pic = resources.getLinearRegion("menu/cloud2");
		TextureRegion cloud3Pic = resources.getLinearRegion("menu/cloud3");
		TextureRegion cloud4Pic = resources.getLinearRegion("menu/cloud4");
		Alignment center = new Alignment(Alignment.CENTER_HORIZONTAL, Alignment.CENTER_VERTICAL);
		clouds.addRepeatingByHeight(cloud1Pic, 0.56f, 0.64f, 0.084f, 5f, center);
		clouds.addRepeatingByHeight(cloud1Pic, 4.7f, 0.7f, 0.084f, 5f, center);
		clouds.addRepeatingByHeight(cloud2Pic, 3f, 0.41f, 0.094f, 4.4f, center);
		clouds.addRepeatingByHeight(cloud2Pic, 3.47f, 0.48f, 0.094f, 4.4f, center);
		clouds.addRepeatingByHeight(cloud3Pic, 12.2f, 0.51f, 0.05f, 6.5f, center);
		clouds.addRepeatingByHeight(cloud4Pic, 4.7f, 0.69f, 0.05f, 6.5f, center);
		
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
