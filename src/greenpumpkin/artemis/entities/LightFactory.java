package greenpumpkin.artemis.entities;

import greenpumpkin.artemis.components.LightC;
import greenpumpkin.artemis.components.PositionC;
import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.graphics.Color;

public class LightFactory {
	
	public static Entity createPoint(World world, RayHandler rayHandler, int numRays, Color color, float lightDistance, float x, float y) {
		Entity e = world.createEntity();
		
		LightC light = new LightC();
		light.light = new PointLight(rayHandler, numRays, color, lightDistance, x, y);
		e.addComponent(light);
		
		PositionC pos = new PositionC();
		pos.x=x;
		pos.y=y;
		e.addComponent(pos);
		
		return e;
	}
	
	public static Entity createCyclePoint(World world, RayHandler rayHandler, int numRays, Color color, float lightDistance, float x, float y, float size, float time) {
		Entity e = world.createEntity();
		
		LightC light = new LightC();
		light.light = new PointLight(rayHandler, numRays, color, lightDistance, x, y);
		e.addComponent(light);
		
		PositionC pos = new PositionC();
		pos.x=x;
		pos.y=y;
		e.addComponent(pos);
		
		return e;
	}
	
	public static Entity createCone(World world, RayHandler rayHandler, int numRays, Color color, float lightDistance, float posX, float posY, float angle) {
		Entity e = world.createEntity();
		
		LightC light = new LightC();
		light.light = new ConeLight(rayHandler, numRays, color, lightDistance, posX, posY, angle+45, 45);
		e.addComponent(light);
		
		return e;
	}
}
