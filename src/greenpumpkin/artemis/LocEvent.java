package greenpumpkin.artemis;

import greenpumpkin.artemis.systems.ControllerInputS;

/*
 * location events:
 * 	dragon
 * 	gem
 *  town with dragon
 */

public class LocEvent extends Event {
	
	public LocEvent(int Y, int X, int sceneN){
		locY = Y;
		locX = X;
		scene = sceneN;
	}

	public boolean checkMet() {
		if (ASCII.mapNumber[0] == locY && ASCII.mapNumber[1] == locX) {
			ControllerInputS.cutsceneN[0] = scene;
			return true;
		}
		return false;
	}

}
