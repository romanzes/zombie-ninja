package ru.footmade.zombieninja.screens.ui;

import java.util.ArrayList;
import java.util.List;

import ru.footmade.zombieninja.util.Alignment;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HorizontalScrollingBackground {
	public static final int INFINITY_DISTANCE = 0;
	
	private float scrW, scrH;
	private float viewportH;
	
	private TextureRegion color;
	private final List<Layer> layers = new ArrayList<Layer>();
	
	public HorizontalScrollingBackground(float scrW, float scrH, TextureRegion color) {
		this.scrW = scrW;
		this.scrH = scrH;
		this.color = color;
	}
	
	public void setViewportHeight(float height) {
		viewportH = height;
	}
	
	public Layer addLayer(float distance) {
		Layer layer = new Layer();
		layer.distance = distance;
		layers.add(layer);
		return layer;
	}
	
	public void draw(Batch batch, float viewerX, float viewerY) {
		batch.draw(color, 0, 0, scrW, scrH);
		for (Layer layer : layers) {
			layer.draw(batch, viewerX, viewerY);
		}
	}
	
	public class Layer {
		private float distance;
		private final List<LayerElement> elements = new ArrayList<LayerElement>();
		
		public void addSingleByHeight(TextureRegion picture, float x, float y, float height) {
			this.addSingleByHeight(picture, x, y, height, new Alignment(Alignment.LEFT | Alignment.BOTTOM));
		}
		
		public void addSingleByHeight(TextureRegion picture, float x, float y, float height, Alignment alignment) {
			LayerElement element = new LayerElement();
			element.picture = picture;
			element.height = scrH * height;
			element.width = element.height * picture.getRegionWidth() / picture.getRegionHeight();
			element.interval = 0;
			element.alignment = alignment;
			element.instanceCount = 1;
			element.xPosition = x;
			element.yPosition = y;
			elements.add(element);
		}
		
		public void addRepeatingByHeight(TextureRegion picture, float x, float y, float height, float relativeInterval) {
			this.addRepeatingByHeight(picture, x, y, height, relativeInterval, new Alignment(Alignment.LEFT | Alignment.BOTTOM));
		}
		
		public void addRepeatingByHeight(TextureRegion picture, float relativeX, float y, float height,
				float relativeInterval, Alignment alignment) {
			LayerElement element = new LayerElement();
			element.picture = picture;
			element.height = scrH * height;
			element.width = element.height * picture.getRegionWidth() / picture.getRegionHeight();
			float width = element.width / scrW;
			element.interval = width * (relativeInterval + 1);
			element.alignment = alignment;
			element.instanceCount = (int) (1 / element.interval) + 3;
			element.xPosition = (width * relativeX % element.interval) - element.interval;
			element.yPosition = y;
			elements.add(element);
		}
		
		public void draw(Batch batch, float viewerX, float viewerY) {
			for (LayerElement element : elements) {
				element.draw(batch, viewerX, viewerY);
			}
		}
		
		public class LayerElement {
			private TextureRegion picture;
			private Alignment alignment;
			private float width, height;
			private float xPosition, yPosition;
			private float interval;
			private int instanceCount;
			
			public void draw(Batch batch, float viewerX, float viewerY) {
				float offsetX = xPosition;
				if (distance != INFINITY_DISTANCE)
					offsetX += (viewerX / distance) % interval;
				float originX;
				float originY;
				switch (alignment.getHorizontalAlignment()) {
				case Alignment.LEFT:
				default:
					originX = 0;
					break;
				case Alignment.CENTER_HORIZONTAL:
					originX = width / 2;
					break;
				case Alignment.RIGHT:
					originX = width;
					break;
				}
				switch (alignment.getVerticalAlignment()) {
				case Alignment.BOTTOM:
				default:
					originY = 0;
					break;
				case Alignment.CENTER_VERTICAL:
					originY = height / 2;
					break;
				case Alignment.TOP:
					originY = height;
					break;
				}
				float x = offsetX * scrW;
				for (int i = 0; i < instanceCount; i++) {
					float y = yPosition * scrH;
					batch.draw(picture, x - originX, y - originY, width + 1, height);
					x += scrW * interval;
				}
			}
		}
	}
}
