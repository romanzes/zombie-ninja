package ru.footmade.zombieninja.screens.ui;

import ru.footmade.zombieninja.CommonResources;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MenuBackground extends Actor {
	private static final float GROUND_HEIGHT = 0.2f;
	private static final float GROUND_SPEED = -0.07f; // screens per second
	private static final float GRASS_HEIGHT = 0.02f;
	private static final float FUJI_HEIGHT = 0.3f;
	private static final float FUJI_POSITION = 0.5f;
	private static final MountainsInfo[] MOUNTAINS = { new MountainsInfo("mountains1", 0.2f, 0.4f, -0.02f),
													   new MountainsInfo("mountains2", 0.1f, 1f, -0.04f, -1f),
													   new MountainsInfo("mountains2", 0.1f, 1f, -0.04f, -0.25f),
													   new MountainsInfo("mountains2", 0.1f, 1f, -0.04f) };
	
	private TextureRegion backgroundColor;
	private TextureRegion ground;
	private TextureRegion fuji;
	
	public MenuBackground() {
		CommonResources resources = CommonResources.getInstance();
		backgroundColor = resources.getRegion("menu/nofilter/bgr_color");
		ground = resources.getLinearRegion("menu/ground");
		fuji = resources.getLinearRegion("menu/fuji");
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(backgroundColor, 0, 0, getWidth(), getHeight());
		drawFuji(batch);
		drawMountains(batch);
		drawGround(batch);
	}
	
	private void drawGround(Batch batch) {
		float groundPartHeight = getHeight() * GROUND_HEIGHT;
		int groundPartWidth = (int) Math.ceil(ground.getRegionWidth() * groundPartHeight / ground.getRegionHeight());
		int groundPartCount = (int) (getWidth() / groundPartWidth) + 2;
		int groundPartPeriodMs = (int) (groundPartWidth / getWidth() * 1000 / GROUND_SPEED);
		float groundOffset = groundPartWidth * (System.currentTimeMillis() % groundPartPeriodMs) / groundPartPeriodMs;
		float position = groundOffset - groundPartWidth;
		for (int i = 0; i <= groundPartCount; i++) {
			batch.draw(ground, position, 0, groundPartWidth, groundPartHeight);
			position += groundPartWidth;
		}
	}
	
	private void drawFuji(Batch batch) {
		float fujiHeight = getHeight() * FUJI_HEIGHT;
		float fujiWidth = fuji.getRegionWidth() * fujiHeight / fuji.getRegionHeight();
		float yPosition = getHeight() * (GROUND_HEIGHT - GRASS_HEIGHT);
		batch.draw(fuji, getWidth() * FUJI_POSITION - fujiWidth / 2, yPosition, fujiWidth, fujiHeight);
	}
	
	private void drawMountains(Batch batch) {
		float yPosition = getHeight() * (GROUND_HEIGHT - GRASS_HEIGHT);
		for (MountainsInfo mountains : MOUNTAINS) {
			float mountainsHeightPx = getHeight() * mountains.height;
			float mountainsWidthPx = mountainsHeightPx * mountains.picture.getRegionWidth() / mountains.picture.getRegionHeight();
			float mountainsWidth = mountainsWidthPx / getWidth();
			float largeInterval = mountainsWidth + mountains.interval;
			float largeIntervalPx = getWidth() * largeInterval;
			// how many mountains can be seen on the screen?
			int mountainsCount = (int) (1 / largeInterval) + 2;
			int mountainsPeriodMs = (int) (largeInterval * 1000 / mountains.speed);
			float mountainsOffset = largeIntervalPx * (System.currentTimeMillis() % mountainsPeriodMs) / mountainsPeriodMs;
			float position = mountainsOffset - mountainsWidthPx + mountains.relativeOffset * mountainsWidthPx;
			for (int i = 0; i <= mountainsCount; i++) {
				batch.draw(mountains.picture, position, yPosition, mountainsWidthPx, mountainsHeightPx);
				position += largeIntervalPx;
			}
		}
	}
	
	private static class MountainsInfo {
		public TextureRegion picture;
		public float height;
		public float interval;
		public float speed;
		public float relativeOffset;
		
		public MountainsInfo(String name, float height, float interval, float speed) {
			this(name, height, interval, speed, 0);
		}
		
		public MountainsInfo(String name, float height, float interval, float speed, float relativeOffset) {
			picture = CommonResources.getInstance().getAtlas().findRegion("menu/" + name);
			this.height = height;
			this.interval = interval;
			this.speed = speed;
			this.relativeOffset = relativeOffset;
		}
	}
}
