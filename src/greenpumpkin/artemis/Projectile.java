package greenpumpkin.artemis;

public class Projectile {
	float currlocX, currlocY;
	int locX, locY;
	float aimX, aimY;
	public char arrow = '/';
	public boolean hurtsPlayer;
	
	/*
	 * Given location of enemy and location of player,
	 * create line of length one for arrow to follow on path until edge of screen is hit.
	 * set starting location to location of player
	 * set aim to be the enemy location minus the player location
	 * then, balance it out so that the max possible value is 2 if they are the same,
	 * with one of the values being equal to itself divided by one (less than 1)
	 * and the other being 1
	 */
	
	public Projectile(boolean attackingEnemy, int enemyX, int enemyY){

		System.out.println("X: " + enemyX + "Y: " + enemyY);
		
		if(attackingEnemy){
			hurtsPlayer=false;
			currlocX = (float)(Player.location[1]);
			currlocY = (float)(Player.location[0]);
			locX = Player.location[1];
			locY = Player.location[0];
			aimX = (float)(enemyX-Player.location[1]);
			aimY = (float)(enemyY-Player.location[0]);
			setAim();
		}
		
		else{
			hurtsPlayer=true;
			currlocX = (float)enemyX;
			currlocY = (float)enemyY;
			locX = enemyX;
			locY = enemyY;
			aimX = Player.location[1] - enemyX;
			aimY = Player.location[0] - enemyY;
			setAim();
		}
	}
	
	private void setAim(){
		if(compare(Math.abs(aimX), Math.abs(aimY))==1){
			aimY/=Math.abs(aimX);
			aimX/=Math.abs(aimX);
		}
		else if(compare(Math.abs(aimX), Math.abs(aimY))==-1){
			aimX/=Math.abs(aimY);
			aimY/=Math.abs(aimY);
		}
		else{
			aimX/=Math.abs(aimX);
			aimY/=Math.abs(aimY);
		}
	}
	
	public void updateLoc(){
		currlocX+=aimX;
		currlocY+=aimY;
		if(currlocX<0)
			locX=(int)(Math.ceil(currlocX)+0.01);
		else
			locX=(int)(currlocX+0.01);
		if(currlocY<0)
			locY=(int)(Math.ceil(currlocY)+0.01);
		else
			locY=(int)(currlocY+0.01);
	}
	
	public boolean hitEnemy(int x, int y){
		if(x==locX && y==locY)
			return true;
		return false;
	}
	
	public boolean hitPlayer(){
		if(Player.location[1]==(int)(locX) && Player.location[0]==(int)(locY))
			return true;
		return false;
	}
	
	private int compare(float one, float two){
		if(one>two)
			return 1;
		if(one<two)
			return -1;
		return 0;
	}
}
