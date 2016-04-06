package ar.uba.fi.superapp.scenes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;

import ar.uba.fi.superapp.Config;
import ar.uba.fi.superapp.managers.LevelManager;
import ar.uba.fi.superapp.managers.SceneManager;
import ar.uba.fi.superapp.managers.SceneManager.SceneType;
import ar.uba.fi.superapp.managers.SoundManager;
import ar.uba.fi.superapp.models.LevelConfig;
import ar.uba.fi.superapp.object.BigImageHolder;
import ar.uba.fi.superapp.object.CharCard;
import ar.uba.fi.superapp.object.ColoredLetter;
import ar.uba.fi.superapp.object.HintWordPlaceHolder;
import ar.uba.fi.superapp.object.WordPlaceHolder;
import ar.uba.fi.superapp.utils.DifficultyMode;
import ar.uba.fi.superapp.utils.GameType;
import ar.uba.fi.superapp.utils.WordsUtils;

public class GuessTheWordScene extends GameScene {

	private BigImageHolder mImage;
	private WordPlaceHolder wordHolder;
	private HintWordPlaceHolder hintWordHolder;
	private ArrayList<CharCard> mCharCards;
	CharCard mCurrentItemJack = null;


	public GuessTheWordScene(LevelConfig config) {
		super(config);
	}

	@Override
	public void gameCreateScene() {
		rm.loadGuessSceneResources(mConfig);
		createBackground();
		createHUD();
		createLevel();
	}

	@Override
	public void onBackKeyPressedCallback() {
		LevelManager.get().chooseGame(GameType.GUESSTHEWORD);
    	SceneManager.get().loadDifficultySelectionScene(engine);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		super.disposeScene();
		wordHolder.clear();
		rm.unloadGuessSceneResources();
	}

	private static Color[] colors = { new Color(0.57f, 0.90f, 0.98f), new Color(0.86f, 0.69f, 0.95f), new Color(1f, 0.67f, 0.51f), new Color(0.38f, 0.91f, 0.79f), new Color(0.87f, 0.91f, 0.38f),
			new Color(0.90f, 0.60f, 0.75f) };

	private void createBackground() {
		int lvl = LevelManager.get().getCurrentLevel();
		int index = lvl % GuessTheWordScene.colors.length;
		setBackground(new Background(GuessTheWordScene.colors[index]));
	}

	private void createLevel() {
		mCharCards = new ArrayList<CharCard>();
		setTouchAreaBindingOnActionDownEnabled(true);
		setTouchAreaBindingOnActionMoveEnabled(true);
		mImage = new BigImageHolder(rm.mPictureTextureRegion, 830, Config.CAMERA_V_MID, vbom);
		this.attachChild(mImage);

		String word = mConfig.getData().optString("word");
		ArrayList<ColoredLetter> coloredWord = null;
		switch (LevelManager.get().getDifficultyMode()) {
		case EASY:
			coloredWord = WordsUtils.get().wordToColoredLettersArray(word);
			break;
		case MEDIUM:
				coloredWord = WordsUtils.get().wordToColoredLettersArray(word);
				break;
		case HARD:
				coloredWord = WordsUtils.get().wordToBlackLettersArray(word);
			break;
		default:
			coloredWord = WordsUtils.get().wordToBlackLettersArray(word);
			break;

		}
		ArrayList<ColoredLetter> hintWord = new ArrayList<ColoredLetter>();
		ArrayList<ColoredLetter> randomWord = new ArrayList<ColoredLetter>();
		String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < coloredWord.size(); i++) {
			randomWord.add(coloredWord.get(i).getCopy());
			hintWord.add(coloredWord.get(i).getCopy());
			abc = abc.replace(coloredWord.get(i).getText(), "");
		}
		if (LevelManager.get().getDifficultyMode() == DifficultyMode.EXPERT) {
			int itemidx = (((int) (Math.random() * 1000)) % abc.length());
			randomWord.add(new ColoredLetter(Color.BLACK, abc.charAt(itemidx)));
		}
		
		
		Collections.shuffle(randomWord, new Random());

		while (hintWord.equals(randomWord)) {
			Collections.shuffle(randomWord, new Random());
		}

		wordHolder = new WordPlaceHolder(coloredWord, vbom);
		hintWordHolder = new HintWordPlaceHolder(hintWord, vbom);
		wordHolder.setPosition(320 - (wordHolder.getWidth() / 2), Config.CAMERA_V_MID - 130);

		if ((DifficultyMode.EASY == LevelManager.get().getDifficultyMode())) {
			// Hint word aligned with _ _ _ _ _
			hintWordHolder.setPosition(320 - (hintWordHolder.getWidth() / 2), Config.CAMERA_V_MID + 50);
		} else {
			hintWordHolder.setPosition(Config.CAMERA_H_MID - (hintWordHolder.getWidth() / 2), Config.CAMERA_HEIGHT - 110);
		}
		attachChild(wordHolder);
		attachChild(hintWordHolder);

		int char_width = (int) rm.squareRegion.getWidth();
		float posX = Config.CAMERA_H_MID - (((char_width + 20) * (randomWord.size() - 1)) / 2);
		for (ColoredLetter c : randomWord) {
			CharCard card = new CharCard(c, posX, 50, vbom) {
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					this.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
					if (pSceneTouchEvent.isActionUp()) {
						if (wordHolder.tryToShowChar(this)) {
							this.setVisible(false);
							unregisterTouchArea(this);
							if (wordHolder.allCharsAreVisibles()) {
								levelComplete();
							}else{
								SoundManager.get().playOk();
							}
							return true;
						}
					}else {
						if ((mCurrentItemJack != null) && (mCurrentItemJack != this)) {
							mCurrentItemJack.comeBackJack();
							mCurrentItemJack = null;
						} else {
							mCurrentItemJack = this;
						}
					}
					return true;
				}
			};
			mCharCards.add(card);
			posX += (char_width + 20);
			attachChild(card);
			registerTouchArea(card);
		}
	}

}
