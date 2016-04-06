package ar.uba.fi.superapp.object;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import ar.uba.fi.superapp.managers.ResourcesManager;


public class ImageHolderTick extends ImageHolder{
	
	boolean isTicked = false;
	Sprite tick = null;
	public ImageHolderTick(String name,ITextureRegion texture,float pX, float pY, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(name,texture,pX, pY, pVertexBufferObjectManager);
		tick = new Sprite(getWidth()/2, getHeight()/2, ResourcesManager.get().tickRegion, pVertexBufferObjectManager);
		attachChild(tick);
		setTicked(false);
	}
	
	public boolean isTicked(){
		return isTicked;
	}
	
	public void setTicked( boolean ticked){
		this.isTicked =  ticked;
		tick.setVisible(isTicked);
	}


}
