package ar.uba.fi.superapp.object;

import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import ar.uba.fi.superapp.managers.ResourcesManager;

public class CharHolder extends Sprite{
	
	private Rectangle checkRectangle;
	private ColoredLetter mChar;
	public CharHolder(float pX, float pY, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, ResourcesManager.get().charPlaceHolder, pVertexBufferObjectManager);
		 float size = ResourcesManager.get().charPlaceHolder.getWidth();
         checkRectangle = new Rectangle(size/2, size/2, size, size,getVertexBufferObjectManager());
         checkRectangle.setVisible(false);
         attachChild(checkRectangle);
	}

	public boolean collidesWith(IEntity pOtherEntity) {
		return checkRectangle.collidesWith(pOtherEntity);
	}
	
	public void setChar(ColoredLetter ch){
		mChar = ch;
		mChar.setPosition(checkRectangle.getWidth()/ 2, (checkRectangle.getHeight() / 2 ) + this.getHeight()/2) ;
		this.attachChild(mChar);
		mChar.setVisible(false);
	}
	
	public void showChar(){
		mChar.setVisible(true);
	}
	
	public boolean isEmpty(){
		return !mChar.isVisible();
	}
	
	public boolean charMatch(String c ){
		return c.equals(mChar.toString());
	}
}
