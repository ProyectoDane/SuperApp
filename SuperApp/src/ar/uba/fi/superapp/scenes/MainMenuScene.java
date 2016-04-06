package ar.uba.fi.superapp.scenes;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.util.GLState;
import org.andengine.engine.camera.Camera;

import ar.uba.fi.superapp.WebActivity;
import ar.uba.fi.superapp.Config;
import ar.uba.fi.superapp.WebActivity.WebType;
import ar.uba.fi.superapp.managers.SceneManager;
import ar.uba.fi.superapp.managers.SceneManager.SceneType;
import ar.uba.fi.superapp.object.SoundControlButton;
import ar.uba.fi.superapp.utils.PrefsHelper;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener {

	
	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_LIST = 1;
	private final int MENU_AYUDA = 2;
	private final int MENU_INFO = 3;
	private static Boolean showHelpBanner = null;
	
	private void createMenuChildScene()
	{
	    menuChildScene = new MenuScene(camera);
	    menuChildScene.setPosition(0, 0);
	    
	    final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, rm.play_region, vbom), 1.1f, 1);
	    final IMenuItem listMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_LIST, rm.lista_region, vbom), 1.1f, 1);
	    final IMenuItem helpMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_AYUDA, rm.help_region, vbom), 1.1f, 1);
	    final IMenuItem infoMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_INFO, rm.info_region, vbom), 1.1f, 1);
	    if(!showHelpBanner){
	    	menuChildScene.addMenuItem(playMenuItem);
	    	menuChildScene.addMenuItem(listMenuItem);
	    	menuChildScene.addMenuItem(infoMenuItem);
	    }
	    menuChildScene.addMenuItem(helpMenuItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);
	    
	    playMenuItem.setPosition(Config.CAMERA_H_MID + playMenuItem.getWidth()/2 + 35, Config.CAMERA_V_MID - 120);
	    listMenuItem.setPosition(Config.CAMERA_H_MID - listMenuItem.getWidth()/2 - 35, Config.CAMERA_V_MID - 120);
	    helpMenuItem.setPosition(20 + helpMenuItem.getWidth()/2 , 20 + helpMenuItem.getHeight()/2);
	    infoMenuItem.setPosition(Config.CAMERA_WIDTH - (20 + infoMenuItem.getWidth()/2 ), 20 + infoMenuItem.getHeight()/2);
	    
	    menuChildScene.setOnMenuItemClickListener(this);
	    if(showHelpBanner){
	    	Sprite	help = new Sprite(Config.CAMERA_H_MID, Config.CAMERA_V_MID, Config.CAMERA_WIDTH,Config.CAMERA_HEIGHT ,rm.helpBackgroundRegion,rm.vbom);
	    	menuChildScene.attachChild(help);
	    }
	    createHUD(menuChildScene);
	    
	    setChildScene(menuChildScene);
	}
	
	public void createScene()
	{
		if(showHelpBanner == null){
			showHelpBanner = (PrefsHelper.get().getLaunchCount() == 1);
		}
	    createBackground();
	    createMenuChildScene();
	}
	
	public void updateScene(){
		detachChildren();
		clearChildScene();
		createScene();
	}

	private void createHUD(Scene parent){
		ButtonSprite backButton = new ButtonSprite(40, Config.CAMERA_HEIGHT - (rm.backButton.getHeight() / 2) - 20, rm.backButton, vbom);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				MainMenuScene.this.onBackKeyPressed();
			}
		});
		parent.registerTouchArea(backButton);
		parent.attachChild(backButton);
		SoundControlButton soundButton = new SoundControlButton( Config.CAMERA_WIDTH - 40 , Config.CAMERA_HEIGHT -40,vbom);
		parent.registerTouchArea(soundButton);
		parent.attachChild(soundButton);
		
	}
	
	public void onBackKeyPressed() {
		if(showHelpBanner){
			showHelpBanner = false;
			updateScene();
		}else{
	    System.exit(0);
		}
	}

	public SceneType getSceneType() {
		return SceneType.SCENE_MENU;
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
	        case MENU_PLAY:
	        	SceneManager.get().loadGameSelectionScene(engine);
	            return true;
	        case MENU_LIST:{
	        	SceneManager.get().loadMakeListScene();
	        }
	            return true;
	        case MENU_INFO:
	        	WebActivity.launch(rm.activity, WebType.ABOUT);
	        	return true;
	        case MENU_AYUDA:
	        	showHelpBanner = false;
	        	updateScene();
	        	WebActivity.launch(rm.activity, WebType.HELP);
	        	return true;
	        default:
	            return false;
	    }
	}

}
