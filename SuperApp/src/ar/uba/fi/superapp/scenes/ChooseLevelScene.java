package ar.uba.fi.superapp.scenes;

import org.andengine.engine.camera.Camera;
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
import org.andengine.util.adt.align.HorizontalAlign;

import ar.uba.fi.superapp.Config;
import ar.uba.fi.superapp.managers.LevelManager;
import ar.uba.fi.superapp.managers.ResourcesManager;


public class ChooseLevelScene extends MenuScene implements IOnMenuItemClickListener{

	public static final int MENU_CONTINUE = 0;
	ButtonSprite nextButton;
	ButtonSprite prevButton;
	Text mLevelText;
	OnChooseLevelListener chooseListener;
	int maxLevel = 1;
	int currentLevel = 1;
	
	public ChooseLevelScene(Camera pCamera,
			OnChooseLevelListener pChooseListener) {
		super(pCamera);
		chooseListener = pChooseListener;
		setOnMenuItemClickListener(this);
		createScene();
	}

	public void createScene() {
		ResourcesManager.get().loadChooseLevelResources();
		setPosition(0, 0);
		attachChild(new Sprite(Config.CAMERA_H_MID, Config.CAMERA_V_MID, Config.CAMERA_WIDTH,Config.CAMERA_HEIGHT ,ResourcesManager.get().chooseLvlBackgroundRegion, ResourcesManager.get().vbom));
    	final IMenuItem continueMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_CONTINUE, ResourcesManager.get().chooseLvlContinueRegion, ResourcesManager.get().vbom), 1.1f, 1);
    	addMenuItem(continueMenuItem);
    	buildAnimations();
 	    setBackgroundEnabled(false);
 	    currentLevel = LevelManager.get().getCurrentLevel();
 	    maxLevel = LevelManager.get().getMaxLevel();
 	    mLevelText = new Text(Config.CAMERA_H_MID, Config.CAMERA_V_MID, ResourcesManager.get().chooseLvlFont, "0123456789", new TextOptions(HorizontalAlign.CENTER), ResourcesManager.get().vbom);
 	    mLevelText.setText("" + currentLevel);
 	    attachChild(mLevelText);
 	    continueMenuItem.setPosition(Config.CAMERA_H_MID,200);
 	    
 	    nextButton = new ButtonSprite(640, Config.CAMERA_V_MID, ResourcesManager.get().chooseLvlNextRegion, ResourcesManager.get().vbom);
 	    nextButton.setZIndex(1000);
 	    nextButton.setVisible(true);
 	  	nextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				nextLevel();
			}
		});
 	  	registerTouchArea(nextButton);
		attachChild(nextButton);
		prevButton = new ButtonSprite(360, Config.CAMERA_V_MID,
				ResourcesManager.get().chooseLvlPrevRegion,
				ResourcesManager.get().vbom);
		prevButton.setZIndex(1000);
		prevButton.setVisible(true);
		prevButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(ButtonSprite pButtonSprite,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				prevLevel();
			}
		});
		attachChild(prevButton);
		registerTouchArea(prevButton);
		setUpButtons();
	}
	
	
	private void setUpButtons(){
		nextButton.setVisible(true);
		prevButton.setVisible(true);
		if(currentLevel == maxLevel){
			nextButton.setVisible(false);
		}
		if( currentLevel == 1){
			prevButton.setVisible(false);
		}
		mLevelText.setText("" + currentLevel);
	}
	private void nextLevel(){
		if(currentLevel < maxLevel){
			currentLevel++;
		}
		setUpButtons();
	}
	
	private void prevLevel(){
		if(currentLevel > 1){
			currentLevel--;
		}
		setUpButtons();
	}

	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		chooseListener.onChooseLevel(currentLevel);
		return false;
	}
}
