package greenpumpkin.artemis;

import greenpumpkin.artemis.systems.ControllerInputS;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Player {
	protected static BufferedReader bufferedScript;
	public static int[] location = new int[] { 13, 26 };
	public static Projectile arrow = null;
	public static boolean sneaking = true;
	public static boolean alive = true;
	public static int maxHealth = 250;
	public static int currHealth = 250;
	public static int maxAmmo = 25;
	public static int currAmmo = 25;
	public static int defense = 3;

	static void checkGameOver() {
		if (currHealth < 1) {
			currHealth = 0;
			alive = false;
			ControllerInputS.cutsceneN[0] = 9;
			ASCII.cutsceneTime = true;
		}
	}

	static void loadSave() {
		try {
			FileHandle file = Gdx.files.internal("data/save.lol");
			bufferedScript = new BufferedReader(file.reader());
			maxHealth = Integer.parseInt(bufferedScript.readLine());
			maxAmmo = Integer.parseInt(bufferedScript.readLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (maxHealth > 9999)
			maxHealth = 9999;
		if (maxAmmo > 999)
			maxAmmo = 999;
		currHealth = maxHealth;
		currAmmo = maxAmmo;
	}

	public static void attackOpponents() {
		ASCII.mapList[ASCII.mapNumber[0]][ASCII.mapNumber[1]]
				.attackFromPlayer();
	}

	public static void makeArrow() {
		switch (ASCII.mapList[ASCII.mapNumber[0]][ASCII.mapNumber[1]]
				.shootFromPlayer()) {
		case 0:
			createArrow(0);
			break;
		case 1:
			createArrow(1);
			break;
		case 2:
			createArrow(2);
			break;
		default:
			break;
		}
	}

	private static void createArrow(int i) {
		System.out.println("chosen: " + i);
		if(ASCII.mapList[ASCII.mapNumber[0]][ASCII.mapNumber[1]].monsterList[i]!=null && currAmmo>0){
			arrow = new Projectile(
				true,
				ASCII.mapList[ASCII.mapNumber[0]][ASCII.mapNumber[1]].monsterList[i].Loc[1],
				ASCII.mapList[ASCII.mapNumber[0]][ASCII.mapNumber[1]].monsterList[i].Loc[0]);
			currAmmo--;
		}
	}
	
	public static boolean updateArrow = true;
	public static void updateArrow() {
		if(arrow!=null && updateArrow){
			arrow.updateLoc();
			if(arrow.locX<1 || arrow.locX>49 || arrow.locY<1 || arrow.locY>24)
				arrow = null;
		}
		 updateArrow = !updateArrow;


	}
}
