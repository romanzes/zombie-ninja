package ru.footmade.zombieninja.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ru.footmade.zombieninja.entity.Ragdoll;
import ru.footmade.zombieninja.entity.Ragdoll.Part;
import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class RagdollLoader {
	private BodyEditorLoader loader;
	private Model model;
	
	public RagdollLoader(FileHandle file, BodyEditorLoader loader) {
		this.loader = loader;
		if (file == null) throw new NullPointerException("file is null");
		model = readJson(file.readString());
	}
	
	public Ragdoll addToWorld(World world, Vector2 position, TextureAtlas atlas, float baseRelativeWidth) {
		Ragdoll ragdoll = new Ragdoll();
		TextureRegion baseRegion = atlas.findRegion(loader.getImagePath(model.parts.get(model.base).name));
		Map<String, Body> bodies = new HashMap<String, Body>();
		for (PartModel part : model.parts.values()) {
			Vector2 origin = new Vector2(position.x + part.origin.x, position.y + part.origin.y);
			
		    BodyDef bd = new BodyDef();
		    bd.position.set(origin);
		    bd.angle = part.rotation;
		    bd.type = BodyType.DynamicBody;
		 
		    FixtureDef fd = new FixtureDef();
		    fd.density = model.density;
		    fd.friction = 0.5f;
		    fd.restitution = 0.3f;
		    fd.filter.groupIndex = -1;
		 
		    Body body = world.createBody(bd);
		    
		    Sprite sprite = atlas.createSprite(loader.getImagePath(part.name));
			sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			float baseRatio = (float) sprite.getRegionWidth() / baseRegion.getRegionWidth();
			float relativeWidth = baseRelativeWidth * baseRatio;
			sprite.setSize(relativeWidth, sprite.getHeight() * relativeWidth / sprite.getWidth());
			
			loader.attachFixture(body, part.name, fd, model.scale * baseRatio);
		    
		    ragdoll.addPart(new Part(body, sprite, loader.getOrigin(part.name, model.scale * baseRatio).cpy()));
		    
		    bodies.put(part.name, body);
		}
		
		Vector2 origin = new Vector2(0, 0);
		
		for (PartModel part : model.parts.values()) {
			Body parent = bodies.get(part.name);
			for (ChildDefModel childDef : part.children) {
				Body child = bodies.get(childDef.name);
				
				RevoluteJointDef jd = new RevoluteJointDef();
				jd.enableMotor = true;
				jd.motorSpeed = 0;
				jd.maxMotorTorque = 0.005f;
				
				jd.bodyA = parent;
				jd.bodyB = child;
				jd.localAnchorA.set(parent.getLocalPoint(child.getWorldPoint(origin)));
				jd.localAnchorB.set(origin);
				if (childDef.limit != null) {
					jd.enableLimit = true;
					jd.lowerAngle = childDef.limit.lowerAngle;
					jd.upperAngle = childDef.limit.upperAngle;
				}
				
				world.createJoint(jd);
			}
		}
		return ragdoll;
	}
	
	private Model readJson(String str) {
		Model m = new Model();
 
		JsonValue map = new JsonReader().parse(str);
 
		m.scale = map.getFloat("scale");
		m.base = map.getString("base");
		m.density = map.getFloat("density");
		JsonValue partElem = map.getChild("parts");
		for (; partElem != null; partElem = partElem.next()) {
			PartModel partModel = readPart(partElem);
			m.parts.put(partModel.name, partModel);
		}
 
		return m;
	}
	
	private PartModel readPart(JsonValue bodyElem) {
		PartModel partModel = new PartModel();
		partModel.name = bodyElem.getString("name");
		JsonValue originElem = bodyElem.get("origin");
		partModel.origin.x = originElem.getFloat("x");
		partModel.origin.y = originElem.getFloat("y");
		partModel.rotation = bodyElem.getFloat("rotation");
 
		JsonValue childrenElem = bodyElem.getChild("children");	
		if (childrenElem != null) {
			for (; childrenElem != null; childrenElem = childrenElem.next()) {
				ChildDefModel childModel = readChild(childrenElem);
				partModel.children.add(childModel);
			}
		}
 
		return partModel;
	}
	
	private ChildDefModel readChild(JsonValue childrenElem) {
		ChildDefModel childModel = new ChildDefModel();
		childModel.name = childrenElem.getString("name");
		JsonValue limitElem = childrenElem.get("limit");
		if (limitElem != null) {
			childModel.limit = new AngleLimitModel();
			childModel.limit.lowerAngle = limitElem.getFloat("lower");
			childModel.limit.upperAngle = limitElem.getFloat("upper");
		}
		return childModel;
	}
	
	public static class Model {
		public float scale;
		public String base;
		public float density;
		public final Map<String, PartModel> parts = new LinkedHashMap<String, PartModel>();
	}
 
	public static class PartModel {
		public String name;
		public final Vector2 origin = new Vector2();
		public float rotation;
		public final List<ChildDefModel> children = new ArrayList<ChildDefModel>();
	}
	
	public static class ChildDefModel {
		public String name;
		public AngleLimitModel limit;
	}
	
	public static class AngleLimitModel {
		public float lowerAngle;
		public float upperAngle;
	}
}
