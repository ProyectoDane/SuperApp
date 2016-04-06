package ar.uba.fi.superapp.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import ar.uba.fi.superapp.Config;
import ar.uba.fi.superapp.managers.LevelManager;
import ar.uba.fi.superapp.managers.SceneManager;
import ar.uba.fi.superapp.models.LevelConfig;
import ar.uba.fi.superapp.object.SoundControlButton;

public abstract class GameScene extends BaseScene  implements IOnMenuItemClickListener, OnChooseLevelListener{

	protected LevelConfig mConfig;
	private HUD mGameHUD;
	private Text mLevelText;
	
	public GameScene(LevelConfig config) {
		super();
		mConfig = config;
		gameCreateScene();
	}
	
	public abstract void gameCreateScene();
	
	public abstract void onBackKeyPressedCallback();
	
	final public void createScene() {
		
	}
	
	public void disposeScene() {
		camera.setHUD(null);
		rm.unloadLevelCompletedGraphics();
	}
	
	public final void createHUD() {
		mGameHUD = new HUD();
		// CREATE SCORE TEXT
		mLevelText = new Text(Config.CAMERA_H_MID, Config.CAMERA_HEIGHT - (rm.font.getLineHeight() / 2), rm.font, "NIVEL 0123456789", new TextOptions(HorizontalAlign.CENTER), vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionUp()){
					chooseLevel();
				}
				
				return super
						.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		// mLevelText.setAnchorCenter(0, 0);
		mLevelText.setColor(Color.WHITE);
		mLevelText.setText("NIVEL " + LevelManager.get().getCurrentLevel());
		this.registerTouchArea(mLevelText);
		ButtonSprite backButton = new ButtonSprite(40, Config.CAMERA_HEIGHT - (rm.backButton.getHeight() / 2) - 20, rm.backButton, vbom);
		backButton.setZIndex(1000);
		backButton.setVisible(true);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				onBackKeyPressed();
			}
		});
		this.registerTouchArea(backButton);
		mGameHUD.attachChild(backButton);
		mGameHUD.attachChild(mLevelText);
		SoundControlButton soundButton = new SoundControlButton( Config.CAMERA_WIDTH - 40 , Config.CAMERA_HEIGHT -40,vbom);
		this.registerTouchArea(soundButton);
		mGameHUD.attachChild(soundButton);
		camera.setHUD(mGameHUD);
	}
	
	public void chooseLevel(){
		setChildScene(  new ChooseLevelScene(camera,this));
	}
	
	public void levelComplete() {
		setChildScene(  new LevelCompleteScene(camera,this));
	}
	
	private void goNextLevel(){
		LevelManager.get().nextLevel();
		SceneManager.get().loadGameScene(engine);
	}

	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		goNextLevel();
		return true;
	}
	
	public void onChooseLevel(int levelChoosed) {
		LevelManager.get().setCurrentLevel(levelChoosed-1);
		LevelManager.get().nextLevel();
		SceneManager.get().loadGameScene(engine);
	}
	
	public void onBackKeyPressed() {
		if(hasChildScene()){
			Scene child = getChildScene();
			if(child != null  &&  child instanceof ChooseLevelScene){
				((ChooseLevelScene) child).closeMenuScene();
			}
		}else{
			onBackKeyPressedCallback();
		}
	}
}
