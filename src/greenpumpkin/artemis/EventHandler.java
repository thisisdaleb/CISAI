package greenpumpkin.artemis;

import java.util.ArrayList;

public class EventHandler {
	
	private static ArrayList<LocEvent> eventLocList = new ArrayList<LocEvent>();
	private static ArrayList<HitEvent> eventHitList = new ArrayList<HitEvent>();
	private static ArrayList<ArrayList<Integer>> questKillList = new ArrayList<ArrayList<Integer>>();

	public static void createEvents(){
		createLocEvents();
		createHitEvents();
	}
	
	private static void createLocEvents(){
		eventLocList.add(new LocEvent(0, 0, 0));
		eventLocList.add(new LocEvent(0, 0, 0));
		eventLocList.add(new LocEvent(0, 0, 0));
	}
	
	private static void createHitEvents(){
		eventHitList.add(new HitEvent(0, 0, 0, 0, 0, 0, 0));
		eventHitList.add(new HitEvent(0, 0, 0, 0, 0, 0, 0));
		eventHitList.add(new HitEvent(0, 0, 0, 0, 0, 0, 0));
	}
	
	public static void createKillQuest(int y, int x){
		questKillList.add(new ArrayList<Integer>());
		questKillList.get(questKillList.size()-1).add(y);
		questKillList.get(questKillList.size()-1).add(x);
	}
	
	public static void checkLocEvents(){
		for(int k=0; k<eventLocList.size(); k++){
			if(eventLocList.get(k).checkMet()){
				ASCII.cutsceneTime=true;
				eventLocList.remove(k);
				break;
			}
		}
	}
	
	public static void checkHitEvents(){
		for(int k=0; k<eventHitList.size(); k++){
			if(eventHitList.get(k).checkMet()){
				ASCII.cutsceneTime=true;
				eventHitList.remove(k);
				break;
			}
		}
	}
	
	public static void checkKillQuests(){
		for(int k=0; k<questKillList.size(); k++)
			if(ASCII.mapList[ASCII.mapNumber[questKillList.get(k).get(0)]]
					[ASCII.mapNumber[questKillList.get(k).get(1)]].lacksMonsters()){
				Player.maxHealth+=10;
				questKillList.remove(k);
			}
	}
}
