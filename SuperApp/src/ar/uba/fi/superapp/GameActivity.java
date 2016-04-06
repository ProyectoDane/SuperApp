package ar.uba.fi.superapp;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;
import ar.uba.fi.superapp.managers.LevelManager;
import ar.uba.fi.superapp.managers.ResourcesManager;
import ar.uba.fi.superapp.managers.SceneManager;
import ar.uba.fi.superapp.managers.SoundManager;
import ar.uba.fi.superapp.scenes.BaseScene;
import ar.uba.fi.superapp.utils.PrefsHelper;

public class GameActivity extends BaseGameActivity {

	
	
	private Camera camera;
	
	public Engine onCreateEngine(final EngineOptions pEngineOptions) {
		return new LimitedFPSEngine(pEngineOptions, 60);
	}
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT), this.camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engineOptions;
	}

	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException
	{
	    ResourcesManager.prepareManager(getEngine(), this, camera, getVertexBufferObjectManager());
	    PrefsHelper.prepareManager(getEngine(), this);
	    SoundManager.prepareManager(getEngine(), this);
	    LevelManager.prepareManager( this);
	    ResourcesManager.get();
	    pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws IOException {
	    SceneManager.get().createSplashScene(pOnCreateSceneCallback);
		
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws IOException {
		  mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
		    {
		            public void onTimePassed(final TimerHandler pTimerHandler) 
		            {
		            	mEngine.unregisterUpdateHandler(pTimerHandler);
		                // load menu resources, create menu scene
		                // set menu scene using scene manager
		                // disposeSplashScene();
		                // READ NEXT ARTICLE FOR THIS PART.
		                SceneManager.get().createMenuScene();
		            }
		    }));
		    pOnPopulateSceneCallback.onPopulateSceneFinished();
		
	}
	

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_BACK)
		    {
		        SceneManager.get().getCurrentScene().onBackKeyPressed();
		    }
		    return false; 	
	}
	
	protected void onDestroy()
	{
		super.onDestroy();
	    System.exit(0);	
	}
	
	@Override
	protected synchronized void onResume() {
		super.onResume();
		if(isGameLoaded()){
			BaseScene scene = SceneManager.get().getCurrentScene();
			if(scene != null){
				scene.onResume();
			}
		}
		
	}

	protected void onPause() {
		super.onPause();
		if(isGameLoaded()){
			BaseScene scene = SceneManager.get().getCurrentScene();
			if(scene != null){
				scene.onPause();
			}
		}

	}
	
}
