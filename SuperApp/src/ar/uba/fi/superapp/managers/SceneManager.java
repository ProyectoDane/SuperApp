package ar.uba.fi.superapp.managers;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import ar.uba.fi.superapp.scenes.BaseScene;
import ar.uba.fi.superapp.scenes.LoadingScene;
import ar.uba.fi.superapp.scenes.MainMenuScene;
import ar.uba.fi.superapp.scenes.MakeSupermarketListScene;
import ar.uba.fi.superapp.scenes.SplashScene;
import ar.uba.fi.superapp.utils.FilesHelper;
import ar.uba.fi.superapp.utils.PrefsHelper;

public class SceneManager
{
    //---------------------------------------------
    // SCENES
    //---------------------------------------------
    
    private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene gameScene;
    private BaseScene difficultySelectionScene;
    private BaseScene gameSelectionScene;
    private BaseScene loadingScene;
    
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final SceneManager INSTANCE = new SceneManager();
    
    private SceneType currentSceneType = SceneType.SCENE_SPLASH;
    
    private BaseScene currentScene;
    
    private Engine engine = ResourcesManager.get().engine;
    
    public enum SceneType
    {
        SCENE_SPLASH,
        SCENE_MENU,
        SCENE_DIFFICULTY_MENU,
        SCENE_GAME_MENU,
        SCENE_GAME,
        SCENE_LOADING,
        SCENE_MAKE_LIST,
    }
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
    
    public void setScene(BaseScene scene)
    {
        engine.setScene(scene);
        currentScene = scene;
        currentSceneType = scene.getSceneType();
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static SceneManager get()
    {
        return INSTANCE;
    }
    
    public SceneType getCurrentSceneType()
    {
        return currentSceneType;
    }
    
    public BaseScene getCurrentScene()
    {
        return currentScene;
    }
    
    public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback)
    {
        ResourcesManager.get().loadSplashScreen();
        splashScene = new SplashScene();
        currentScene = splashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }
    
    private void disposeSplashScene()
    {
        ResourcesManager.get().unloadSplashScreen();
        splashScene.disposeScene();
        SoundManager.get().loadResources();
        SoundManager.get().playBackgroundMusic();
        splashScene = null;
    }
    
    public void createMenuScene()
    {
    	int launchCount = PrefsHelper.get().getLaunchCount();
    	launchCount++;
    	launchCount %= 10;
    	PrefsHelper.get().setLaunchCount(launchCount);
        ResourcesManager.get().loadGlobalAssets();
        ResourcesManager.get().loadMenuResources();
        menuScene = new MainMenuScene();
        loadingScene = new LoadingScene();
        FilesHelper.cleanUnusedFiles(ResourcesManager.get().activity);
        SceneManager.get().setScene(menuScene);
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
        disposeSplashScene();
    }
    
    public void loadMenuScene(final Engine mEngine)
    {
    	currentScene.disposeScene();
        setScene(loadingScene);
        ResourcesManager.get().loadMenuTextures();
        setScene(menuScene);
    }
    
    public void loadGameScene(final Engine mEngine)
    {
    	currentScene.disposeScene();
        setScene(loadingScene);
        ResourcesManager.get().unloadDifficutyMenuTextures();
        mEngine.registerUpdateHandler(new TimerHandler(1.1f, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                gameScene = LevelManager.get().getLevelScene();
                setScene(gameScene);
            }
        }));
    }
    
    public void loadDifficultySelectionScene(final Engine mEngine){
    	currentScene.disposeScene();
        setScene(loadingScene);
        ResourcesManager.get().unloadGamesMenuResources();
        difficultySelectionScene = LevelManager.get().getDifficultySelectionScene();
        setScene(difficultySelectionScene);
    }
  
    public void loadGameSelectionScene(final Engine mEngine){
    	currentScene.disposeScene();
        setScene(loadingScene);
        ResourcesManager.get().unloadMenuTextures();
        gameSelectionScene = LevelManager.get().getGameSelectionScene();
        setScene(gameSelectionScene);
    }
    
    
    public void loadMakeListScene(){
    	currentScene.disposeScene();
    	SoundManager.get().pauseBackgroundMusic();
    	setScene(loadingScene);
    	gameScene = new MakeSupermarketListScene();
    	setScene(gameScene);
    }
}