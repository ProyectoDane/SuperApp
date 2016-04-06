package ar.uba.fi.superapp.scenes;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.engine.camera.Camera;

import ar.uba.fi.superapp.Config;
import ar.uba.fi.superapp.managers.LevelManager;
import ar.uba.fi.superapp.managers.SceneManager;
import ar.uba.fi.superapp.managers.SceneManager.SceneType;
import ar.uba.fi.superapp.object.SoundControlButton;
import ar.uba.fi.superapp.utils.GameType;

public class GamesMenuScene extends BaseScene implements IOnMenuItemClickListener {

	
	private MenuScene menuChildScene;
	private final int MENU_BUY = 0;
	private final int MENU_GUESS = 1;

	private void createMenuChildScene()
	{
	    menuChildScene = new MenuScene(camera);
	    menuChildScene.setPosition(0, 0);
	    
	    final IMenuItem buyMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_BUY, rm.buy_region, vbom), 1.1f, 1);
	    final IMenuItem guessMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_GUESS, rm.guess_region, vbom), 1.1f, 1);
	    Text mText = new Text(Config.CAMERA_H_MID, Config.CAMERA_V_MID - 20 , rm.font, "JUEGOS", new TextOptions(HorizontalAlign.CENTER), vbom);
	    mText.setColor(Color.WHITE);
	    mText.setText("JUEGOS");
	    menuChildScene.attachChild(mText);
	    menuChildScene.addMenuItem(buyMenuItem);
	    menuChildScene.addMenuItem(guessMenuItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);
	    
	    buyMenuItem.setPosition(300,150);
	    guessMenuItem.setPosition(700,150);
	    
	    menuChildScene.setOnMenuItemClickListener(this);
	    
	    setChildScene(menuChildScene);
	    
	    ButtonSprite backButton = new ButtonSprite(40, Config.CAMERA_HEIGHT - (rm.backButton.getHeight() / 2) - 20, rm.backButton, vbom);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				onBackKeyPressed();
			}
		});
		this.registerTouchArea(backButton);
		attachChild(backButton);
	    
	    SoundControlButton soundButton = new SoundControlButton( Config.CAMERA_WIDTH - 40 , Config.CAMERA_HEIGHT -40,vbom);
		this.registerTouchArea(soundButton);
		attachChild(soundButton);
	}
	
	public void createScene()
	{
	    createBackground();
	    createMenuChildScene();

	}

	public void onBackKeyPressed() {
		SceneManager.get().loadMenuScene(engine);
	}

	public SceneType getSceneType() {
		return SceneType.SCENE_GAME_MENU;
	}

	public void disposeScene() {

	}
	
	private void createBackground()
	{

	    attachChild(new Sprite(Config.CAMERA_H_MID, Config.CAMERA_V_MID, Config.CAMERA_WIDTH,Config.CAMERA_HEIGHT ,rm.menu_background_region, vbom)
	    {
	        protected void preDraw(GLState pGLState, Camera pCamera) 
	        {
	            super.preDraw(pGLState, pCamera);
	            pGLState.enableDither();
	        }
	    });
	}

	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
	     switch(pMenuItem.getID())
	        {
	        case MENU_BUY:
	        	LevelManager.get().chooseGame(GameType.BUYITEMS);
	        	break;
	        case MENU_GUESS:
	        	LevelManager.get().chooseGame(GameType.GUESSTHEWORD);
	        	break;
	       }
	     SceneManager.get().loadDifficultySelectionScene(engine);
	     return true;
	}

	
	
}
