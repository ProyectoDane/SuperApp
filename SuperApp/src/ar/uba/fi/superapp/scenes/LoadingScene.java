package ar.uba.fi.superapp.scenes;


import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.util.debug.Debug;

import ar.uba.fi.superapp.Config;
import ar.uba.fi.superapp.managers.SceneManager.SceneType;

public class LoadingScene extends BaseScene
{
	BuildableBitmapTextureAtlas menu_background_texture;
	ITextureRegion menu_background_region;
    public void createScene()
    {
    	 BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		 menu_background_texture = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		 menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_background_texture, activity, "loading.jpg");
		  try 
	        {
	            this.menu_background_texture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
	            this.menu_background_texture.load();
	        } 
	        catch (final TextureAtlasBuilderException e)
	        {
	            Debug.e(e);
	        }
		 attachChild(new Sprite(Config.CAMERA_H_MID, Config.CAMERA_V_MID, Config.CAMERA_WIDTH,Config.CAMERA_HEIGHT ,menu_background_region, vbom)
 	    {
 	        protected void preDraw(GLState pGLState, Camera pCamera) 
 	        {
 	            super.preDraw(pGLState, pCamera);
 	            pGLState.enableDither();
 	        }
 	    });
		 
    }

    public void onBackKeyPressed()
    {
        return;
    }

    public SceneType getSceneType()
    {
        return SceneType.SCENE_LOADING;
    }

    public void disposeScene()
    {
    	menu_background_texture.unload();
    	menu_background_texture = null;

    }
}