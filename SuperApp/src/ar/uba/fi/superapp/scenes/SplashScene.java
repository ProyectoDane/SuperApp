package ar.uba.fi.superapp.scenes;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.engine.camera.Camera;

import ar.uba.fi.superapp.Config;
import ar.uba.fi.superapp.managers.SceneManager.SceneType;

public class SplashScene extends BaseScene {

	private Sprite splash;

	
	public void createScene() {
		splash = new Sprite(0, 0, Config.CAMERA_WIDTH,Config.CAMERA_HEIGHT ,rm.splash_region, vbom)
		{
		    protected void preDraw(GLState pGLState, Camera pCamera) 
		    {
		       super.preDraw(pGLState, pCamera);
		       pGLState.enableDither();
		    }
		};
		        
		splash.setPosition(Config.CAMERA_H_MID, Config.CAMERA_V_MID);
		attachChild(splash);

	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SPLASH;
	}


	public void disposeScene() {
		 splash.detachSelf();
		 splash.dispose();
		 this.detachSelf();
		 this.dispose();
	}

}
