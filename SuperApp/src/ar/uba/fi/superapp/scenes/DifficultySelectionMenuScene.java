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
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import ar.uba.fi.superapp.Config;
import ar.uba.fi.superapp.managers.LevelManager;
import ar.uba.fi.superapp.managers.SceneManager;
import ar.uba.fi.superapp.managers.SceneManager.SceneType;
import ar.uba.fi.superapp.object.SoundControlButton;
import ar.uba.fi.superapp.utils.DifficultyMode;

public class DifficultySelectionMenuScene extends BaseScene implements IOnMenuItemClickListener {

	private MenuScene menuChildScene;
	private final int MENU_EASY = 0;
	private final int MENU_MEDIUM = 1;
	private final int MENU_HARD = 2;
	private final int MENU_EXPERT = 3;
	private boolean mediumEnabled;
	private boolean hardEnabled;
	private boolean expertEnabled;

	private void createMenuChildScene() {
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(0, 0);
		mediumEnabled = LevelManager.get().difficultyMediumEnabled();
		hardEnabled = LevelManager.get().difficultyHardEnabled();
		expertEnabled = LevelManager.get().difficultyExpertEnabled();
		final IMenuItem easyMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_EASY, rm.easy_region, vbom), 1.1f, 1);
		final IMenuItem mediumMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_MEDIUM, (mediumEnabled) ? rm.medium_region : rm.medium_disabled_region, vbom), (mediumEnabled) ? 1.1f : 1,
				1);
		final IMenuItem hardMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_HARD, (hardEnabled) ? rm.hard_region : rm.hard_disabled_region, vbom), (hardEnabled) ? 1.1f : 1, 1);
		final IMenuItem expertMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_EXPERT, (expertEnabled) ? rm.expert_region : rm.expert_disabled_region, vbom), (expertEnabled) ? 1.1f : 1, 1);
		
		Text mText = new Text(Config.CAMERA_H_MID, Config.CAMERA_V_MID - 50, rm.font, "MODO DE JUEGO", new TextOptions(HorizontalAlign.CENTER), vbom);
		// mText.setAnchorCenter(0, 0);
		mText.setColor(Color.WHITE);
		mText.setText("MODO DE JUEGO");
		menuChildScene.attachChild(mText);

		menuChildScene.addMenuItem(easyMenuItem);
		menuChildScene.addMenuItem(mediumMenuItem);
		menuChildScene.addMenuItem(hardMenuItem);
		menuChildScene.addMenuItem(expertMenuItem);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);

		easyMenuItem.setPosition(144, 130);
		mediumMenuItem.setPosition(382, 130);
		hardMenuItem.setPosition(620, 130);
		expertMenuItem.setPosition(858, 130);

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
		setUpLevelsProgress();
	}

	private void setUpLevelsProgress(){
		int level = LevelManager.get().getMaxLevel();
		attachChild(new Sprite(144, 65, rm.progress_regions[LevelManager.get().getProgressForLevel(DifficultyMode.EASY,level)], vbom));
		attachChild(new Sprite(382, 65, rm.progress_regions[LevelManager.get().getProgressForLevel(DifficultyMode.MEDIUM,level)], vbom));
		attachChild(new Sprite(620, 65, rm.progress_regions[LevelManager.get().getProgressForLevel(DifficultyMode.HARD,level)], vbom));
		attachChild(new Sprite(858, 65, rm.progress_regions[LevelManager.get().getProgressForLevel(DifficultyMode.EXPERT,level)], vbom));
	}
	
	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();

	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.get().loadGameSelectionScene(engine);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_DIFFICULTY_MENU;
	}

	@Override
	public void disposeScene() {

	}

	private void createBackground() {

		attachChild(new Sprite(Config.CAMERA_H_MID, Config.CAMERA_V_MID, Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT, rm.menu_background_region, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		});
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_EASY:
			LevelManager.get().chooseDifficulty(DifficultyMode.EASY);
			break;
		case MENU_MEDIUM:
			if (mediumEnabled) {
				LevelManager.get().chooseDifficulty(DifficultyMode.MEDIUM);
			} else {
				return true;
			}
			break;
		case MENU_HARD:
			if (hardEnabled) {
				LevelManager.get().chooseDifficulty(DifficultyMode.HARD);
			} else {
				return true;
			}
			break;
		case MENU_EXPERT:
			if (expertEnabled) {
				LevelManager.get().chooseDifficulty(DifficultyMode.EXPERT);
			} else {
				return true;
			}
			break;
		default:
			return false;
		}
		SceneManager.get().loadGameScene(engine);
		return true;
	}

}
