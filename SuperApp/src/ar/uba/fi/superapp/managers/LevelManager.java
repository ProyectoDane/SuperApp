package ar.uba.fi.superapp.managers;

import android.content.Context;
import android.content.SharedPreferences;
import ar.uba.fi.superapp.GameActivity;
import ar.uba.fi.superapp.models.LevelConfig;
import ar.uba.fi.superapp.scenes.BaseScene;
import ar.uba.fi.superapp.scenes.BuyItemsGameScene;
import ar.uba.fi.superapp.scenes.DifficultySelectionMenuScene;
import ar.uba.fi.superapp.scenes.GamesMenuScene;
import ar.uba.fi.superapp.scenes.GuessTheWordScene;
import ar.uba.fi.superapp.utils.DifficultyMode;
import ar.uba.fi.superapp.utils.GameType;
import ar.uba.fi.superapp.utils.LevelHelper;

public class LevelManager {
	
	private static final String LEVEL_PREFERENCES = "ar.uba.fi.superapp.managers.LevelManager";
    public static final int EASY_FIRST_LEVEL = 1;
	public static final int MEDIUM_FIRST_LEVEL = 16;
	public static final int HARD_FIRST_LEVEL = 31;
	public static final int EXPERT_FIRST_LEVEL = 46;
	public static final int ENDED_GAME_FIRST_LEVEL = 61;
	private int mCurrentLevel = 1;
	private DifficultyMode mMode;
	private GameType mGameType;

	public GameActivity activity;
    private SharedPreferences sharedPref;

	private static final LevelManager INSTANCE = new LevelManager();

	public static LevelManager get() {
		return LevelManager.INSTANCE;
	}

	public int getCurrentLevel() {
		return mCurrentLevel;
	}

	private LevelManager() {
		mMode = DifficultyMode.EASY;
		mGameType = GameType.BUYITEMS;
	}

	public BaseScene getLevelScene() {
		LevelConfig config = LevelHelper.get().getLevel(getCurrentLevel());
		if (mGameType == GameType.GUESSTHEWORD) {
			return new GuessTheWordScene(config);
		} else {
			return new BuyItemsGameScene(config);
		}
	}

	public void nextLevel() {
		mCurrentLevel++;
		upgradeDifficultyMode();
		saveLevel();
	}

	private void upgradeDifficultyMode() {
		if(mCurrentLevel < LevelManager.MEDIUM_FIRST_LEVEL){
			mMode = DifficultyMode.EASY;
		}else if( mCurrentLevel < LevelManager.HARD_FIRST_LEVEL){
			mMode = DifficultyMode.MEDIUM;
		} else if( mCurrentLevel < LevelManager.EXPERT_FIRST_LEVEL){
			mMode = DifficultyMode.HARD;
		}else {
			mMode = DifficultyMode.EXPERT;
		}
	}

	public void prevLevel() {
		mCurrentLevel--;
		if (mCurrentLevel < 1) {
			mCurrentLevel = 1;
		}
		saveLevel();
	}

	public void chooseDifficulty(DifficultyMode mode) {
		mMode = mode;
		int maxLevel = getMaxLevel(); 
		switch (mMode) {
		case EASY:
			mCurrentLevel =(maxLevel >= LevelManager.MEDIUM_FIRST_LEVEL)?LevelManager.EASY_FIRST_LEVEL:maxLevel;
			break;
		case MEDIUM:
			mCurrentLevel = (maxLevel >= LevelManager.HARD_FIRST_LEVEL)?LevelManager.MEDIUM_FIRST_LEVEL:maxLevel;
			break;
		case HARD:
			mCurrentLevel = (maxLevel >= LevelManager.EXPERT_FIRST_LEVEL)?LevelManager.HARD_FIRST_LEVEL:maxLevel;
			break;
		case EXPERT:
			mCurrentLevel = maxLevel;
			break;
		}
	}

	public void chooseGame(GameType type) {
		mGameType = type;
		loadLevel();
	}

	public DifficultyMode getDifficultyMode() {
		return mMode;
	}

	public GameType getGameMode() {
		return mGameType;
	}

	public BaseScene getDifficultySelectionScene() {
		ResourcesManager.get().loadDifficultyMenuResources();
		return new DifficultySelectionMenuScene();
	}

	public BaseScene getGameSelectionScene() {
		ResourcesManager.get().loadGamesMenuResources();
		return new GamesMenuScene();
	}

	public boolean difficultyMediumEnabled() {
		return getMaxLevel() >= MEDIUM_FIRST_LEVEL;
	}

	public boolean difficultyHardEnabled() {
		return getMaxLevel() >= HARD_FIRST_LEVEL;
	}

	public boolean difficultyExpertEnabled() {
		return getMaxLevel() >= EXPERT_FIRST_LEVEL;
	}
	
	private void saveLevel(){
		int maxLevel = getMaxLevel();
		SharedPreferences.Editor editor = sharedPref.edit();
    	editor.putInt("LEVEL_MODE_"+mGameType.ordinal(), mCurrentLevel);
    	if(mCurrentLevel > maxLevel){
    		editor.putInt("MAX_LEVEL_MODE_"+mGameType.ordinal(), mCurrentLevel);
    	}
    	editor.commit();
	}
	
	public int getMaxLevel(){
		return sharedPref.getInt("MAX_LEVEL_MODE_"+mGameType.ordinal(), 1);
	}
	
	private void loadLevel(){
		mCurrentLevel =  sharedPref.getInt("LEVEL_MODE_"+mGameType.ordinal(), 1);
	}
	
	public void setCurrentLevel(int currentLevel){
		if(currentLevel > getMaxLevel()){
			mCurrentLevel = getMaxLevel();
		}else{
			mCurrentLevel = currentLevel;
		}
	}
	
	public static void prepareManager(GameActivity activity) {
		get().activity = activity;
		get().sharedPref = activity.getSharedPreferences(
				LEVEL_PREFERENCES, Context.MODE_PRIVATE);
	}

	public int getProgressForLevel(DifficultyMode mode,int maxLevel){
		int firstNextLevel = 0;
		int firstThisLevel = 0;
		switch (mode) {
		case EASY:
			firstThisLevel = EASY_FIRST_LEVEL;
			firstNextLevel = MEDIUM_FIRST_LEVEL;
			break;
		case MEDIUM:
			firstThisLevel =MEDIUM_FIRST_LEVEL;
			firstNextLevel = HARD_FIRST_LEVEL;
			break;
		case HARD: 
			firstThisLevel =HARD_FIRST_LEVEL;
			firstNextLevel = EXPERT_FIRST_LEVEL;
			break;
		default:
			firstThisLevel = EXPERT_FIRST_LEVEL;
			firstNextLevel = ENDED_GAME_FIRST_LEVEL;
			break;
		}
		
		if(maxLevel >= firstNextLevel){
			return 5;
		}else{
			if(maxLevel <= firstThisLevel){
				return 0;
			}else{
			 return Double.valueOf(Math.floor((maxLevel - firstThisLevel)/3)).intValue();
			}
		}
	}
	
	public int getProgressExperience(){
		int maxLevel = getMaxLevel();
		if(maxLevel>= ENDED_GAME_FIRST_LEVEL - 1){
			return 4;
		}else if(maxLevel>=EXPERT_FIRST_LEVEL - 1){
			return 3;
		}else if(maxLevel >= HARD_FIRST_LEVEL - 1){
			return 2;
		}else if(maxLevel >= MEDIUM_FIRST_LEVEL - 1){
			return 1;
		}
		return 0;
	}

}
