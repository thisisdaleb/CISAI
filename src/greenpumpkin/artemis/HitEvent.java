package greenpumpkin.artemis;

import greenpumpkin.artemis.systems.ControllerInputS;

/*
 * press attack button near:
 * 	gem
 *  dragon (immune to arrows)
 *  auction with gem
 */

public class HitEvent extends Event {
	int areaY1, areaY2;
	int areaX1, areaX2;
	
	public HitEvent(int Y, int X, int y1, int y2, int x1, int x2, int sceneN){
		locY = Y;
		locX = X;
		areaY1 = y1;
		areaY2 = y2;
		areaX1 = x1;
		areaX2 = x2;
		scene = sceneN;
	}
	
	public boolean checkMet(){
		if(ASCII.mapNumber[0]==locY && ASCII.mapNumber[1]==locX){
			if(areaY1<Player.location[0] && Player.location[0]<areaY2){
				if(areaX1<Player.location[1] && Player.location[1]<areaX2){
					ControllerInputS.cutsceneN[0]=scene;
					return true;
				}
			}
		}
		return false;
	}
}
