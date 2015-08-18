package greenpumpkin.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import greenpumpkin.screens.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

////////////////////////////////////////////////////////////
//This is the main class.It sets the default settings///////
//for the game and loads the first area.////////////////////
////////////////////////////////////////////////////////////

public class CISAI extends Game {
	public static int WIDTH = 900, HEIGHT = 720;
	protected static int[] fps = new int[] { 60, -1 };

	@Override
	public void create() {
		setScreen(new MainWorld()); // This defines what screen loads first.
	}

	public static void main(String[] arg) {
		useConfig();
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "CISAI";
		cfg.width = WIDTH;
		cfg.height = HEIGHT;
		cfg.foregroundFPS = fps[0];
		cfg.backgroundFPS = fps[1];
		cfg.foregroundFPS = 600;
		new LwjglApplication(new CISAI(), cfg);
	}

	private static void useConfig() {
		try {
			File jarPath = new File(CISAI.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String propertiesPath = jarPath.getParentFile().getAbsolutePath();
			FileInputStream scriptInput = new FileInputStream(propertiesPath+ "/assets/data/cfg.ini");
			InputStreamReader scriptReader = new InputStreamReader(scriptInput);
			BufferedReader bufferedScript = new BufferedReader(scriptReader);
			WIDTH  = Integer.parseInt(bufferedScript.readLine());
			HEIGHT = Integer.parseInt(bufferedScript.readLine());
			fps[0] = Integer.parseInt(bufferedScript.readLine());
			fps[1] = Integer.parseInt(bufferedScript.readLine());
			bufferedScript.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// FileHandle file = new
	// FileHandle("D:/CodeFolder/CISAI/CISAIGame/assets/data/cfg.ini");
	// file = new FileHandle(new File("assets/data/cfg.ini"));
	// FileHandle file = Gdx.files.internal("assets/data/cfg.ini");
	// bufferedScript = new BufferedReader(file.reader());
}
