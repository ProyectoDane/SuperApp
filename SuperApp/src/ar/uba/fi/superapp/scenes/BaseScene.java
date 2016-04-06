package ar.uba.fi.superapp.scenes;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.app.Activity;
import ar.uba.fi.superapp.managers.ResourcesManager;
import ar.uba.fi.superapp.managers.SoundManager;
import ar.uba.fi.superapp.managers.SceneManager.SceneType;

public abstract class BaseScene extends Scene
{
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    protected Engine engine;
    protected Activity activity;
    protected ResourcesManager rm;
    protected VertexBufferObjectManager vbom;
    protected Camera camera;
    
    //---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------
    
    public BaseScene()
    {
        this.rm = ResourcesManager.get();
        this.engine = rm.engine;
        this.activity = rm.activity;
        this.vbom = rm.vbom;
        this.camera = rm.camera;
        createScene();
    }
    
    //---------------------------------------------
    // ABSTRACTION
    //---------------------------------------------
    
    public abstract void createScene();
    
    public abstract void onBackKeyPressed();
    
    public abstract SceneType getSceneType();
    
    public abstract void disposeScene();
    
    public  void onPause(){
    	SoundManager.get().saveState();
    };
    public  void onResume(){
    	SoundManager.get().restoreState();
    };
}