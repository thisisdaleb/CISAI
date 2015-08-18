package greenpumpkin.artemis.systems;

import greenpumpkin.artemis.ASCII;
import greenpumpkin.artemis.CWorld;
import greenpumpkin.artemis.Player;
import greenpumpkin.artemis.components.VelocityC;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Mapper;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class ControllerInputS extends VoidEntitySystem implements
		InputProcessor {
	@Mapper
	ComponentMapper<VelocityC> velMap;

	private int runSpeed = 4;
	private int walkSpeed = 15;
	private boolean up, down, left, right, space, shift, melee, ranged, stealth;
	int timer[] = new int[8];
	public static int cutsceneN[] = new int[] { 0, 0, 0 };

	public ControllerInputS() {
	}

	@Override
	protected void initialize() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	protected void processSystem() {

		if (!ASCII.cutsceneTime) {
			cutsceneN[1] = 0;
			if (up && timer[0] < 1) {
				ASCII.setPlayerOnMap(-1, 0);
				if (shift)
					timer[0] = runSpeed;
				else
					timer[0] = walkSpeed;
			}

			else if (down && timer[1] < 1) {
				ASCII.setPlayerOnMap(1, 0);
				if (shift)
					timer[1] = runSpeed;
				else
					timer[1] = walkSpeed;
			}

			if (left && timer[2] < 1) {
				ASCII.setPlayerOnMap(0, -1);
				if (shift)
					timer[2] = runSpeed;
				else
					timer[2] = walkSpeed;
			}

			else if (right && timer[3] < 1) {
				ASCII.setPlayerOnMap(0, 1);
				if (shift)
					timer[3] = runSpeed;
				else
					timer[3] = walkSpeed;
			}

			if (space && timer[4] < 1) {
				ASCII.showDialog = true;
				if (ASCII.dialog.equals(""))
					timer[4] = 30;
				else
					timer[4] = 90;
			}

			else if (timer[4] < 0 && (up || down || left || right)) {
				ASCII.resetDialog();
			}
			
			if(melee && timer[5]<0){
				Player.attackOpponents();
				timer[5]=30;
			}
			
			if (ranged && timer[6]<0){
				if(Player.arrow==null)
					Player.makeArrow();
				timer[6]=20;
			}
			
			if(stealth && timer[7]<0){
				timer[7]=30;
			}
		}

		else {
			showCutscene();
		}

		for (int k = 0; k < 8; k++)
			timer[k]--;
	}

	public void showCutscene() {
		if (cutsceneN[1] < ASCII.cutscenes.get(cutsceneN[0]).size()) {
			if (cutsceneN[2] < 1) {
				ASCII.dialog = ASCII.cutscenes.get(cutsceneN[0]).get(
						cutsceneN[1]);
				cutsceneN[1]++;
				cutsceneN[2] = 480;
			} else {
				if (space && cutsceneN[2] < 460) {
					System.out.println(480 - cutsceneN[2]);
					cutsceneN[2] = 0;
				}
				cutsceneN[2]--;
			}
		} else {
			cutsceneN[2] = 2;
			ASCII.cutsceneTime = false;
			ASCII.showDialog = false;
			ASCII.dialog = "";
			cutsceneN[1] = 0;
			cutsceneN[0]++;
			if(cutsceneN[0]>4)
				CWorld.exit();
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.SHIFT_LEFT) {
			shift = true;
		} else if (keycode == Input.Keys.A) {
			left = true;
		} else if (keycode == Input.Keys.D) {
			right = true;
		} else if (keycode == Input.Keys.W) {
			up = true;
		} else if (keycode == Input.Keys.S) {
			down = true;
		} else if (keycode == Input.Keys.SPACE) {
			space = true;
		}
		else if (keycode == Input.Keys.I) {
			melee = true;
		}
		else if (keycode == Input.Keys.J) {
			ranged= true;
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.SHIFT_LEFT) {
			shift = false;
		} else if (keycode == Input.Keys.A) {
			left = false;
		} else if (keycode == Input.Keys.D) {
			right = false;
		} else if (keycode == Input.Keys.W) {
			up = false;
		} else if (keycode == Input.Keys.S) {
			down = false;
		} else if (keycode == Input.Keys.SPACE) {
			space = false;
		}
		else if (keycode == Input.Keys.I) {
			melee = false;
		}
		else if (keycode == Input.Keys.J) {
			ranged = false;
		}

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
