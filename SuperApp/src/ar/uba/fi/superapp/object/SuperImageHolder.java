package ar.uba.fi.superapp.object;

import java.util.Locale;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.ease.EaseLinear;

import android.util.Log;
import ar.uba.fi.superapp.managers.ResourcesManager;
import ar.uba.fi.superapp.models.SuperImage;

public class SuperImageHolder extends Sprite{
	float x,y;
	Sprite mImage;
	Text tName;
	SuperImage mSuperImage;
	final  int IH_HEIGHT = 100;
	final  int IH_WIDTH = 135;
	public SuperImageHolder(SuperImage image,float pX, float pY, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, ResourcesManager.get().imagePlaceHolder, pVertexBufferObjectManager);
		mSuperImage = image;
		x = pX;
		y = pY;
		cropTexture(image.getmImageRegion());
		 mImage = new Sprite(0, 0,IH_WIDTH,IH_HEIGHT ,image.getmImageRegion(), pVertexBufferObjectManager);
		 mImage.setPosition(80, 90);
		this.attachChild(mImage);
		tName = new Text(75, 25, ResourcesManager.get().miniFont, image.getmName().toUpperCase(Locale.getDefault()), pVertexBufferObjectManager);
		float tW = tName.getWidth();
		if(tW > IH_WIDTH){
			tName.setScale(IH_WIDTH/tW);
		}
		this.attachChild(tName);
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

	public void setTextVisible(boolean visible){
		tName.setVisible(visible);
	}
	
	public void setImageVisible(boolean visible){
		mImage.setVisible(visible);
		tName.setPosition(75, (visible)?25:75);
	}
	
	public void setOrigin(float pX, float pY){
		x = pX;
		y= pY;
		setPosition(pX,pY);
	}
	
	public void comeBackJack(){
		ResourcesManager.get().engine.runOnUpdateThread(new Runnable() {
			public void run() {
				SuperImageHolder.this.clearEntityModifiers();
				SuperImageHolder.this.registerEntityModifier(new MoveModifier(1, SuperImageHolder.this.getX(), SuperImageHolder.this.getY(), SuperImageHolder.this.x,SuperImageHolder.this.y,EaseLinear.getInstance()));
		}});
	}
	
	public String getName(){
		return mSuperImage.getmName();
	}
	
	public SuperImage getImage(){
		return mSuperImage;
}
	
}
