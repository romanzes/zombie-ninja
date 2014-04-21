package ru.footmade.zombieninja;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Ragdoll {
	private final List<Part> parts = new ArrayList<Part>();
	
	protected void addPart(Part part) {
		parts.add(part);
	}
	
	public void draw(SpriteBatch batch) {
		for (Part part : parts) {
			Vector2 bodyPos = part.body.getPosition().sub(part.origin);
			part.sprite.setPosition(bodyPos.x, bodyPos.y);
			part.sprite.setOrigin(part.origin.x, part.origin.y);
			part.sprite.setRotation(part.body.getAngle() * MathUtils.radiansToDegrees);
			part.sprite.draw(batch);
		}
	}
	
	public static class Part {
		private Body body;
		private Sprite sprite;
		private Vector2 origin;
		
		public Part(Body body, Sprite sprite, Vector2 origin) {
			this.body = body;
			this.sprite = sprite;
			this.origin = origin;
		}
	}
}
