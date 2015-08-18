package greenpumpkin.artemis.systems;

import greenpumpkin.artemis.ASCII;
import greenpumpkin.artemis.CWorld;
import greenpumpkin.artemis.Player;
import greenpumpkin.artemis.entities.LightFactory;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.graphics.Color;

public class ASCIILightS extends VoidEntitySystem {

	@Override
	protected void processSystem() {
		CWorld.flushRayHandler();
		world.addEntity(LightFactory.createCyclePoint(world, CWorld.rayHandler,
				CWorld.numRays, new Color(0.5f, 0.5f, 0.5f, 1.0f),
				CWorld.lightDistance * 20f, 100, 70, 0f, 0f));
		world.addEntity(LightFactory.createCyclePoint(world, CWorld.rayHandler,
				CWorld.numRays, new Color(0.3f, 0.3f, 0.3f, 1.0f),
				CWorld.lightDistance * 15f, 600, 70, 0f, 0f));
		processPlayer();
		processMap();
		processPeople();
		processMonsters();
		processTowns();
	}

	private void processPlayer() {
		if (ControllerInputS.cutsceneN[0] > 0
				|| ControllerInputS.cutsceneN[1] > 2) {
			int y = 720 - 24 * (Player.location[0]) - 22;
			int x = 13 * (Player.location[1]) + 5;
			// create light
			world.addEntity(LightFactory.createCyclePoint(world,
					CWorld.rayHandler, CWorld.numRays, new Color(0.89f, 0.72f,
							0.42f, 1.0f), CWorld.lightDistance * 3f, x, y,
					7.7f, 1.875f * 4f));
		}
	}

	private void processPeople() {
		for (int k = 0; k < 3; k++) {
			if (ASCII.mapList[ASCII.mapNumber[0]][ASCII.mapNumber[1]].personList[k] != null) {
				// set light locations
				int y = 720 - 24 * (ASCII.mapList[ASCII.mapNumber[0]][ASCII.mapNumber[1]].personList[k].personLoc[0]) - 22;
				int x = 13 * (ASCII.mapList[ASCII.mapNumber[0]][ASCII.mapNumber[1]].personList[k].personLoc[1]) + 5;
				// create light
				world.addEntity(LightFactory.createCyclePoint(world,
						CWorld.rayHandler, CWorld.numRays, new Color(0.89f,
								0.72f, 0.42f, 1.0f), CWorld.lightDistance * 2f,
						x, y, 7.7f, 1.875f * 4f));
			}
		}
	}

	private void processMonsters() {
		for (int k = 0; k < 3; k++) {
			if (ASCII.mapList[ASCII.mapNumber[0]][ASCII.mapNumber[1]].monsterList[k] != null) {
				// set light locations
				int currX, currY;
				currY = (ASCII.mapList[ASCII.mapNumber[0]][ASCII.mapNumber[1]].monsterList[k].Loc[0]);
				currX = (ASCII.mapList[ASCII.mapNumber[0]][ASCII.mapNumber[1]].monsterList[k].Loc[1]);
				int y = 720 - 24 * currY - 22;
				int x = 13 * currX + 5;
				
				// create light
				if(Player.sneaking){
					world.addEntity(LightFactory.createCone(world,
							CWorld.rayHandler, CWorld.numRays, new Color(0.9f,
									0.5f, 0.2f, 1.0f), CWorld.lightDistance * 10f,
							x, y, ASCII.mapList[ASCII.mapNumber[0]][ASCII.mapNumber[1]].monsterList[k].angle));
					world.addEntity(LightFactory.createCyclePoint(world,
							CWorld.rayHandler, CWorld.numRays, new Color(0.9f,
									0.5f, 0.2f, 1.0f), CWorld.lightDistance * 1f,
							x, y, 7.7f, 1.875f * 4f));
				}
				else
					world.addEntity(LightFactory.createCyclePoint(world,
							CWorld.rayHandler, CWorld.numRays, new Color(0.9f,
									0.5f, 0.2f, 1.0f), CWorld.lightDistance * 1.5f,
							x, y, 7.7f, 1.875f * 4f));
			}
		}
	}

	private void processTowns() {
		for (int k = 0; k < 3; k += 2) {
			if (ASCII.mapList[ASCII.mapNumber[0]][ASCII.mapNumber[1]].houses[k] != 0) {
				// set light locations
				int y = 720 - 24 * (ASCII.mapList[ASCII.mapNumber[0]][ASCII.mapNumber[1]].houses[k]) - 42;
				int x = 13 * (ASCII.mapList[ASCII.mapNumber[0]][ASCII.mapNumber[1]].houses[k + 1]) + 15;
				// create light
				world.addEntity(LightFactory.createCyclePoint(world,
						CWorld.rayHandler, CWorld.numRays, new Color(0.89f,
								0.7f, 0.3f, 1.0f), CWorld.lightDistance * 1f,
						x, y, 7.7f, 1.875f * 4f));
			}
		}
	}

	private void processMap() {
			float y = 19 * (ASCII.mapNumber[0]) + 145;
			float x = (float) (10.5 * (ASCII.mapNumber[1]) + 740);
			// create light
			world.addEntity(LightFactory.createCyclePoint(world,
					CWorld.rayHandler, CWorld.numRays, new Color(1f, 1f, 0.5f,
							1.0f), CWorld.lightDistance * 1f, x, y, 7.7f,
					1.875f * 4f));
	}
}
