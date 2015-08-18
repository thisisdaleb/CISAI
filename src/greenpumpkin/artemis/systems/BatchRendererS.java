package greenpumpkin.artemis.systems;

import greenpumpkin.artemis.ASCII;
import greenpumpkin.artemis.CWorld;
import greenpumpkin.artemis.Player;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;

//import com.artemis.ComponentMapper;
//import com.artemis.annotations.Mapper;
//import greenpumpkin.artemis.components.DialogC;
import greenpumpkin.artemis.components.PositionC;
import greenpumpkin.screens.MainWorld;

import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

//DRAWS ALL SPRITES AND BACKGROUNDS, THEN DRAWS THE DIALOG
public class BatchRendererS extends EntitySystem {
	//@Mapper ComponentMapper<PositionC> posMap;
	//@Mapper ComponentMapper<DialogC> dialogMap;
	//static String dialogLine = "";
	//static String displayedLine = "";
	private BitmapFont uiFont;
	private BitmapFont mapFont;
	private BitmapFont gameFont;
	HAlignment alignment;
	
	@SuppressWarnings("unchecked")
	public BatchRendererS() {
		super(Aspect.getAspectForAll(PositionC.class));
	}
	
	protected void initialize() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/font.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 24;
		uiFont = generator.generateFont(parameter); // font size 24 pixels
		parameter.size = 21;
		gameFont = generator.generateFont(parameter);
		parameter.size = 16;
		mapFont = generator.generateFont(parameter);
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		uiFont.setColor(Color.WHITE);
		gameFont.setColor(Color.LIGHT_GRAY);
	}
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		//THIS UPDATES THE WORLD
		updatedCISAIWorld();
		CWorld.batch.setProjectionMatrix(CWorld.camera.combined);
		CWorld.batch.begin();
		//drawDialog();
		
		//DRAWS UI
		gameFont.drawMultiLine(CWorld.batch, ASCII.currentWorld, 10, 700);
		gameFont.draw(CWorld.batch, ASCII.dialog, 10, 70);
		mapFont.drawMultiLine(CWorld.batch, ASCII.mapShown, 740, 700);
		uiFont.drawMultiLine(CWorld.batch, "Player Health: " + Player.currHealth, 10, 30);
		uiFont.drawMultiLine(CWorld.batch, "Ammo: " + Player.currAmmo, 300, 30);
		uiFont.drawMultiLine(CWorld.batch, "fps: " + (int)(1/MainWorld.time), 800, 30);
		//END
		CWorld.batch.end();
	}
	
	private void updatedCISAIWorld() {
		ASCII.setcurrentMap();
		Player.updateArrow();
	}

	/*
	private void drawDialog() {
		if(displayedLine.length() < dialogLine.length()){
			displayedLine=dialogLine.substring(0, displayedLine.length()+1);
		}
		uiFont.drawMultiLine(CWorld.batch, displayedLine, 80, 160);
	}

	//dealing with dialog and stuff
	public static void clearDialog(){
		dialogLine="";
		displayedLine="";
	}

	public static void setDialog(String newDialog){
		dialogLine=newDialog;
		addNewLines();
	}

	public static void addNewLines(){
		if(dialogLine.length() >48){
			dialogLine = dialogLine.substring(0,dialogLine.substring(0,48).lastIndexOf(" "))
					+ "\n" + (dialogLine.substring(dialogLine.substring(0,48).lastIndexOf(" ")+1));
			if(dialogLine.length() >94){
				dialogLine = dialogLine.substring(0,dialogLine.substring(0,94).lastIndexOf(" "))
						+ "\n" + (dialogLine.substring(dialogLine.substring(0,94).lastIndexOf(" ")+1));
				if(dialogLine.length() >140){
					dialogLine = dialogLine.substring(0,dialogLine.substring(0,140).lastIndexOf(" "))
							+ "\n" + (dialogLine.substring(dialogLine.substring(0,140).lastIndexOf(" ")+1));
				}
			}
		}
	}
	*/

	//pls ignore
	@Override
	protected boolean checkProcessing() {
		return true;
	}
}
