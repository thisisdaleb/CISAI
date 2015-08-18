package greenpumpkin.artemis;

import java.util.Random;

public class Monster {
	private char monsterSymbol = ' ';
	public int[] Loc;
	private MonsterType monsterType;
	public int health;
	public int angle;
	
	public enum MonsterType {
		Archer, Zombie, Goblin, Dragon
	}
	

	Monster(Random rand) {
		
		int x = rand.nextInt(3);
		
		if(x==0){
			setMonsterSymbol('S');
			monsterType=MonsterType.Archer;
			health = 20;
		}
		else if(x==1){
			setMonsterSymbol('Z');
			monsterType=MonsterType.Zombie;
			health = 10;
		}
		else{
			setMonsterSymbol('G');
			monsterType=MonsterType.Goblin;
			health = 30;
		}
		
		angle = 44;
		Loc = new int[] { rand.nextInt(20) + 3, rand.nextInt(45) + 3 };
	}
	
	Monster(boolean isDragon){
		setMonsterSymbol('D');
		monsterType=MonsterType.Dragon;
		health = 250;
		Loc = new int[] { 13, 26 };
	}

	// moves monster at random
	void moveMonster(Random rand) {
		switch (rand.nextInt(8)) {
		case 0:
			moveUpLeft();
			break;
		case 1:
			moveUp();
			break;
		case 2:
			moveUpRight();
			break;
		case 3:
			moveRight();
			break;
		case 4:
			moveDownRight();
			break;
		case 5:
			moveDown();
			break;
		case 6:
			moveDownLeft();
			break;
		case 7:
			moveLeft();
			break;
		}
		checkPlacement();
	}
	
	void setMonster(Random rand) {
		if(playerDistance()<=10){
			if(!Player.sneaking)
				useAI(rand);
			else if(playerAtAngle())
				useAI(rand);
		}
	}

	private boolean playerAtAngle() {
		int comparedAngle = 0;
		int compY = Loc[0] - Player.location[0];
		int compX = Loc[1] - Player.location[1];
		if(compY<0 && compX<=0){
			comparedAngle=90;
		}
		else if(compY>=0 && compX<0){
			comparedAngle=180;
		}
		else if(compY>=0 && compX>=0){
			comparedAngle=270;
		}
		if(comparedAngle>angle)
			return true;
		return false;
	}

	private int playerDistance() {
		return Math.abs(Loc[0] - Player.location[0])
				+ Math.abs(Loc[1] - Player.location[1]);
	}

	void useAI(Random rand) {
		
		if (monsterType == MonsterType.Archer) { // replace with move towards
			moveArcher();
		}
		
		if (monsterType == MonsterType.Zombie) { // replace with move towards
			moveZombie();
		}
		
		if (monsterType == MonsterType.Goblin) { // replace with move towards
			moveGoblin();
		}
		
		if (monsterType == MonsterType.Dragon) { // replace with move towards
			moveDragon();
			breathFire();
		}
		
		if(onPlayer() && Player.alive){
			Player.currHealth-=(50/Player.defense);
		}
		
		checkPlacement();
	}

	private boolean onPlayer() {
		if(compareToPlayerY()==0
				&& compareToPlayerX()==0)
			return true;
		else return false;
	}

	private void checkPlacement() {
		if (Loc[0] < 2)
			Loc[0] += 2;
		else if (Loc[0] > 23)
			Loc[0] -= 2;
		if (Loc[1] < 2)
			Loc[1] += 2;
		else if (Loc[1] > 48)
			Loc[1] -= 2;
	}
	
	private void moveArcher() {
		Loc[0] -= compareToPlayerY();
		Loc[1] -= compareToPlayerX();
	}
	
	private void moveZombie() {
		Loc[0] += compareToPlayerY();
		Loc[1] += compareToPlayerX();
	}
	
	private void moveGoblin() {
		//goblin moves in same manner as people
	}
	
	private void moveDragon() {
		//dragon never moves
	}
	
	private void breathFire() {
		
	}

	char getMonsterSymbol() {
		return monsterSymbol;
	}

	public void setMonsterSymbol(char monsterSymbol) {
		this.monsterSymbol = monsterSymbol;
	}

	int compareToPlayerX() {
		int x = Player.location[1];

		if (x < Loc[1]) {
			return -1;
		} else if (x > Loc[1]) {
			return 1;
		} else
			return 0;
	}

	int compareToPlayerY() {
		int y = Player.location[0];

		if (y < Loc[0]) {
			return -1;
		} else if (y > Loc[0]) {
			return 1;
		} else
			return 0;
	}
	

	private void moveUp() {
		Loc[0]--;
	}

	private void moveDown() {
		Loc[0]++;
	}

	private void moveLeft() {
		Loc[1]--;
	}

	private void moveRight() {
		Loc[1]++;
	}

	private void moveUpLeft() {
		Loc[0]--;
		Loc[1]--;
	}

	private void moveUpRight() {
		Loc[0]--;
		Loc[1]++;
	}

	private void moveDownLeft() {
		Loc[0]--;
		Loc[1]--;
	}

	private void moveDownRight() {
		Loc[0]++;
		Loc[1]++;
	}

	public void hurtByPlayer() {
		if(nearPlayer()){
			health-=10;
		}
	}

	private boolean nearPlayer() {
		if(Math.abs(Loc[0]-Player.location[0])<2
				&& Math.abs(Loc[1]-Player.location[1])<2){
			System.out.println("ahahaha");
			return true;
		}
		else return false;
	}
	
	public boolean monsterIsDead() {
		if(health<=0)
			return true;
		return false;
	}

	public void reduceHealth(int red) {
		health-=red;	
	}
}
