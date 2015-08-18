package greenpumpkin.artemis;

import com.badlogic.gdx.files.FileHandle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.Gdx;

public class ASCII {
	public static String ascii = "No Level";
	public static Map[][] mapList = new Map[30][20];
	public static String currentWorld;
	private static String mapHidden = "";
	public static String mapShown = "";
	public static String dialog = "";
	public static int[] mapNumber = new int[] { 0, 0 };
	public static Random rand;
	protected static BufferedReader bufferedScript;
	public static boolean showDialog = false;
	public static boolean cutsceneTime = true;
	public static ArrayList<ArrayList<String>> talkList = new ArrayList<ArrayList<String>>();
	public static ArrayList<ArrayList<String>> cutscenes = new ArrayList<ArrayList<String>>();

	public static void initiateMaps() {
		rand = new Random(System.currentTimeMillis());
		for (int j = 0; j < mapList.length; j++)
			for (int k = 0; k < mapList[0].length; k++)
				mapList[j][k] = new Map(j, k);
		mapList[mapNumber[0]][mapNumber[1]].setPlayer(true);
		createDialogList();
		setcurrentMap();
		setMapHiddenString();
		setMapShownString();
		createCutscenes();
	}
	
	private static void createDialogList() {
		for (int k = 0; k < 6; k++) {
			String addLineToDialog = "";
			talkList.add(new ArrayList<String>());
			try {
				FileHandle file = Gdx.files.internal("dialog/speech" + k + ".lol");
				bufferedScript = new BufferedReader(file.reader());
				while ((addLineToDialog = bufferedScript.readLine()) != null) {
					talkList.get(k).add(addLineToDialog);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void setcurrentMap() {
		getDialog(showDialog);
		mapList[mapNumber[0]][mapNumber[1]].setMap();
		currentWorld = mapList[mapNumber[0]][mapNumber[1]].mapString;
		Player.checkGameOver();
	}

	public static void getDialog(boolean setDialog) {
		if (setDialog) {
			if (cityTile(mapNumber[0], mapNumber[1])
					&& mapList[mapNumber[0]][mapNumber[1]].talk()) {
				if(getCity(mapNumber[0],mapNumber[1])>=0)
					dialog = talkList.get(getCity(mapNumber[0],mapNumber[1])).get(rand.nextInt(talkList.get(getCity(mapNumber[0],mapNumber[1])).size()));
				showDialog = false;
			}
		}
	}

	public static void resetDialog() {
		dialog = "";
	}

	public static void setPlayerOnMap(int y, int x) {
		if ((Player.location[0] > 0 && y < 0)
				|| (y > 0 && Player.location[0] < 24))
			Player.location[0] += y;
		if ((Player.location[1] > 1 && x == -1)
				|| (x == 1 && Player.location[1] < 50))
			Player.location[1] += x;

		// THIS MOVES THE PLAYERS AROUND THE MAPS THEMSELVES
		if (Player.location[0] == 0 && y < 0)
			moveToMapUp();
		else if (y > 0 && Player.location[0] == 24)
			moveToMapDown();
		else if (Player.location[1] == 1 && x < 0)
			moveToMapLeft();
		else if (x > 0 && Player.location[1] == 50)
			moveToMapRight();
		else
			mapList[mapNumber[0]][mapNumber[1]].setPlayer(Player.location[0],
					Player.location[1]);
	}

	private static void moveToMapUp() {
		if (mapNumber[0] < 29 && legalMapTile(mapNumber[0] + 1, mapNumber[1])) {
			mapNumber[0]++;
			Player.location[0] = 24;
			mapList[mapNumber[0]][mapNumber[1]].setPlayer(Player.location[0],
					Player.location[1]);
			setMapShownString();
		}
	}

	private static void moveToMapDown() {
		if (mapNumber[0] > 0 && legalMapTile(mapNumber[0] - 1, mapNumber[1])) {
			mapNumber[0]--;
			Player.location[0] = 0;
			mapList[mapNumber[0]][mapNumber[1]].setPlayer(Player.location[0],
					Player.location[1]);
			setMapShownString();
		}
	}

	private static void moveToMapLeft() {
		if (mapNumber[1] > 0 && legalMapTile(mapNumber[0], mapNumber[1] - 1)) {
			mapNumber[1]--;
			Player.location[1] = 50;
			mapList[mapNumber[0]][mapNumber[1]].setPlayer(Player.location[0],
					Player.location[1]);
			setMapShownString();
		}
	}

	private static void moveToMapRight() {
		if (mapNumber[1] < 19 && legalMapTile(mapNumber[0], mapNumber[1] + 1)) {
			mapNumber[1]++;
			Player.location[1] = 1;
			mapList[mapNumber[0]][mapNumber[1]].setPlayer(Player.location[0],
					Player.location[1]);
			setMapShownString();
		}
	}

	private static void setMapShownString() {
		mapShown = mapHidden.substring(0,
				((29 - mapNumber[0]) * 21 + mapNumber[1]))
				+ "@"
				+ mapHidden.substring((29 - mapNumber[0]) * 21 + mapNumber[1]
						+ 1);
	}

	private static void setMapHiddenString() {
		for (int y = mapList.length - 1; y >= 0; y--) {
			for (int x = 0; x < mapList[0].length; x++) {
				if (!legalMapTile(y, x))
					mapHidden += " ";
				else if (cityTile(y, x))
					mapHidden += "O";
				else
					mapHidden += "+";
			}
			mapHidden += "\n";
		}
		mapShown = mapHidden;
	}

	private static boolean legalMapTile(int y, int x) {
		if (((y == 18 || y == 19) && x > 2) || (y > 22 && x < 2)
				|| (y <= 1 && x > 8) || (y == 17 && x > 9) || (y > 25 && x < 6)
				|| ((y == 19 || y == 20) && x > 14))
			return false;
		return true;
	}

	private static boolean cityTile(int y, int x) {
		if ((y < 7 && x > 9) || (y < 23 && y > 18 && x < 3))
			return true;
		return false;
	}
	
	private static int getCity(int y, int x) {
		if ((y < 7 && x > 9)) 
			return 0; 
		else if (y < 23 && y > 18 && x < 3)
			return 1;
		else return -1;
	}
	
	private static void createCutscenes() {
		for (int k = 0; k < 10; k++) {
			String addLineToScene = "";
			cutscenes.add(new ArrayList<String>());
			try {
				FileHandle file = Gdx.files.internal("dialog/cutscene" + k + ".lol");
				bufferedScript = new BufferedReader(file.reader());
				while ((addLineToScene = bufferedScript.readLine()) != null) {
					cutscenes.get(k).add(addLineToScene);
					System.out.println(cutscenes.get(k).get(
							cutscenes.get(k).size() - 1));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
