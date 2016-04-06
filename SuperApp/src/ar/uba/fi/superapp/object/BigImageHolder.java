package ar.uba.fi.superapp.object;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.ease.EaseLinear;

import android.util.Log;
import ar.uba.fi.superapp.managers.ResourcesManager;

public class BigImageHolder extends Sprite{
	float x,y;
	final  int IH_HEIGHT = 230;
	final  int IH_WIDTH = 230;
	public BigImageHolder(ITextureRegion texture,float pX, float pY, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY,300,300 ,ResourcesManager.get().imagePlaceHolder, pVertexBufferObjectManager);
		x = pX;
		y = pY;
		cropTexture(texture);
		Sprite image = new Sprite(0, 0,IH_WIDTH,IH_HEIGHT, texture, pVertexBufferObjectManager);
		image.setPosition(150, 150);
		this.attachChild(image);
	}

	public void setOrigin(float pX, float pY){
		x = pX;
		y= pY;
		setPosition(pX,pY);
	}
	
	public void comeBackJack(){
		ResourcesManager.get().engine.runOnUpdateThread(new Runnable() {
			public void run() {
				BigImageHolder.this.clearEntityModifiers();
				BigImageHolder.this.registerEntityModifier(new MoveModifier(1, BigImageHolder.this.getX(), BigImageHolder.this.getY(), BigImageHolder.this.x,BigImageHolder.this.y,EaseLinear.getInstance()));
		}});
	}
	
	private void cropTexture(ITextureRegion texture){
		float mRatio = (float)IH_WIDTH / (float)IH_HEIGHT;
		float ratio = texture.getWidth() / texture.getHeight();
		float proportion = 1.0f;
		if(ratio > mRatio){ //ajusto en altura centro horizontal
			proportion = texture.getHeight() / IH_HEIGHT;
			float mW = IH_WIDTH * proportion;
			float nW = texture.getWidth();
			Log.v("CROP", "TO: w:"+texture.getWidth() +" h:"+texture.getHeight()+ " posx:"+texture.getTextureX() + " posy: "+  texture.getTextureY());
			texture.setTexturePosition(texture.getTextureX() + (nW - mW)/2, texture.getTextureY());
			texture.setTextureWidth(mW);
		}else{
			proportion = texture.getWidth() / IH_WIDTH;
			float mH = IH_HEIGHT * proportion;
			float nH = texture.getHeight();
			Log.v("CROP", "TO: w:"+texture.getWidth() +" h:"+texture.getHeight()+ " posx:"+texture.getTextureX() + " posy: "+  texture.getTextureY());
			texture.setTexturePosition(texture.getTextureX() , texture.getTextureY()+ (nH - mH)/2);
			texture.setTextureHeight(mH);
		}
	}
	
	
}
