package greenpumpkin.artemis;

import java.util.ArrayList;
import java.util.Random;

public class Map {
	public ArrayList<Projectile> arrows = new ArrayList<Projectile>();
	public Monster[] monsterList = new Monster[3];
	public Person[] personList = new Person[3];
	public int[] houses = new int[] { 0, 0, 0, 0 };
	public String defString = "NO LEVEL";
	public String mapString = "NO LEVEL";
	public int[] mapNumber;
	private Random rand;

	public Map(int y, int x) {
		mapString = "";
		for (int k = 0; k < 25; k++)
			mapString += "--------------------------------------------------\n";
		defString = mapString;
		mapNumber = new int[] { y, x };
		rand = new Random(2015 * (1 + mapNumber[0]) * (1 + mapNumber[1]));

		if (cityTile(y, x)) {
			placeTown();
			personList[0] = new Person();
			personList[1] = new Person();
			personList[2] = new Person();
		}
		if (!monsterlessTile(y, x)) {
			 monsterList[0] = new Monster(rand);
			 monsterList[1] = new Monster(rand);
			//monsterList[0] = new Monster(rand, '0');
			//monsterList[1] = new Monster(rand, '1');
			if (rand.nextInt(10) < 3)
				monsterList[2] = new Monster(rand);
		}
	}

	// CALLED EVERY FRAME THE MAP NEEDS UPDATED
	public void setMap() {
		mapString = defString;
		setPlayer(Player.location[0], Player.location[1]);
		if (cityTile(mapNumber[0], mapNumber[1])) {
			setPeople();
			placePersons();
			placeHouses();
		}
		if (!monsterlessTile(mapNumber[0], mapNumber[1])) {
			setMonsters();
			placeArrows();
			placeMonsters();
		}
	}

	public void setPlayer(boolean center) {
		mapString = mapString.substring(0, mapString.length() / 2 - 1) + '@'
				+ mapString.substring(mapString.length() / 2);
	}

	public void setPlayer(int y, int x) {
		mapString = mapString.substring(0, (y * 51 + x) - 1) + '@'
				+ mapString.substring(y * 51 + x);
	}

	private void setPeople() {
		for (int k = 0; k < 3; k++) {
			if (rand.nextInt(180) < 1)
				personList[k].movePerson();
		}
	}

	private void setMonsters() {
		for (int k = 0; k < 3; k++) {
			if (monsterList[k] != null) {
				if (rand.nextInt(180) < 2)
					monsterList[k].moveMonster(rand);
				if (rand.nextInt(180) < 10)
					monsterList[k].setMonster(rand);

				if (Player.arrow != null)
					if (Player.arrow.hitEnemy(monsterList[k].Loc[1],
							monsterList[k].Loc[0]))
						monsterList[k].reduceHealth(20);
				if (monsterList[k].monsterIsDead())
					monsterList[k] = null;
			}
		}
	}

	public void attackFromPlayer() {
		for (int k = 0; k < 3; k++) {
			if (monsterList[k] != null) {
				monsterList[k].hurtByPlayer();
				if (monsterList[k].monsterIsDead())
					monsterList[k] = null;
			}
		}
	}

	public int shootFromPlayer() {
		float closest = 10000f;
		float formuoli = 10000f;
		int z = -1;
		for (int k = 0; k < 3; k++) {
			if (monsterList[k] != null) {
				// formuoli = (float)Math.sqrt(
				// ((monsterList[k].Loc[0]-Player.location[0])^2) +
				// ((monsterList[k].Loc[1]-Player.location[1])^2));
				formuoli = Math.abs(monsterList[k].Loc[0] - Player.location[0])
						+ Math.abs(monsterList[k].Loc[1] - Player.location[1]);
				if (formuoli < closest) {
					closest = formuoli;
					z = k;
				}
			}
		}
		System.out.println("chosen distance:" + formuoli + "for sure:  " + closest + "chosen monster: " + z);
		return z;
	}

	private void placeHouses() {
		// House 1
		mapString = mapString.substring(0, (houses[0] * 51 + houses[1]) - 1)
				+ "HHH" + mapString.substring(houses[0] * 51 + houses[1] + 2);
		mapString = mapString.substring(0, (houses[0] * 51 + houses[1]) + 50)
				+ "HHH" + mapString.substring(houses[0] * 51 + houses[1] + 53);
		// house 2
		if (houses[2] != 0) {
			mapString = mapString
					.substring(0, (houses[2] * 51 + houses[3]) - 1)
					+ "HHH"
					+ mapString.substring(houses[2] * 51 + houses[3] + 2);
			mapString = mapString.substring(0,
					(houses[2] * 51 + houses[3]) + 50)
					+ "HHH"
					+ mapString.substring(houses[2] * 51 + houses[3] + 53);
		}

	}

	private void placeArrows() {
		if (Player.arrow != null) {
			mapString = mapString.substring(0,
					(int) ((Player.arrow.locY * 51 + Player.arrow.locX) - 1))
					+ Player.arrow.arrow
					+ mapString
							.substring((int) (Player.arrow.locY * 51 + Player.arrow.locX));
		}
	}

	private void placeMonsters() {
		for (int q = 0; q < monsterList.length; q++) {
			if (monsterList[q] != null)
				mapString = mapString
						.substring(
								0,
								(monsterList[q].Loc[0] * 51 + monsterList[q].Loc[1]) - 1)
						+ monsterList[q].getMonsterSymbol()
						+ mapString.substring(monsterList[q].Loc[0] * 51
								+ monsterList[q].Loc[1]);
		}
	}

	private void placePersons() {
		for (int q = 0; q < personList.length; q++) {
			if (personList[q] != null)
				mapString = mapString
						.substring(
								0,
								(personList[q].personLoc[0] * 51 + personList[q].personLoc[1]) - 1)
						+ personList[q].personSymbol
						+ mapString.substring(personList[q].personLoc[0] * 51
								+ personList[q].personLoc[1]);
		}
	}

	private void placeTown() {
		for (int k = 0; k < 4; k++) {
			if (k % 2 == 0)
				houses[k] = rand.nextInt(20) + 2;
			else
				houses[k] = rand.nextInt(45) + 2;
		}
	}

	private static boolean cityTile(int y, int x) {
		if ((y < 7 && x > 9) || (y < 23 && y > 18 && x < 3))
			return true;
		return false;
	}

	private static boolean monsterlessTile(int y, int x) {
		if ((y < 8 && x > 8) || (y < 24 && y > 17 && x < 4)
				|| (y == 0 && x == 0))
			return true;
		return false;
	}

	public class Person {
		private char personSymbol = ' ';
		public int[] personLoc;

		private Person() {
			personSymbol = 'p';
			personLoc = new int[] { rand.nextInt(20) + 2, rand.nextInt(45) + 2 };
		}

		private void movePerson() {
			switch (rand.nextInt(8)) {
			case 0:
				personLoc[0]--;
				personLoc[1]--;
				break;
			case 1:
				personLoc[0]--;
				break;
			case 2:
				personLoc[0]--;
				personLoc[1]++;
				break;
			case 3:
				personLoc[1]++;
				break;
			case 4:
				personLoc[0]++;
				personLoc[1]++;
				break;
			case 5:
				personLoc[0]++;
				break;
			case 6:
				personLoc[0]++;
				personLoc[1]--;
				break;
			case 7:
				personLoc[1]--;
				break;
			}
			if (personLoc[0] < 2)
				personLoc[0] += 2;
			else if (personLoc[0] > 23)
				personLoc[0] -= 2;
			if (personLoc[1] < 2)
				personLoc[1] += 2;
			else if (personLoc[1] > 48)
				personLoc[1] -= 2;
		}
	}

	public boolean talk() {
		for (int k = 0; k < 3; k++)
			if (Math.abs(personList[k].personLoc[0] - Player.location[0]) < 2)
				if (Math.abs(personList[k].personLoc[1] - Player.location[1]) < 2)
					return true;
		return false;
	}

	public boolean lacksMonsters() {
		 if(monsterList[0] == null && monsterList[1] == null && monsterList[2] == null)
			 return true;
		return false;
	}
}
