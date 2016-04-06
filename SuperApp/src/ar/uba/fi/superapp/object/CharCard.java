package ar.uba.fi.superapp.object;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.ease.EaseLinear;

import ar.uba.fi.superapp.managers.ResourcesManager;


public class CharCard extends Sprite {
	
	private ColoredLetter mChar;
	float x,y;
	public CharCard(ColoredLetter pChar,float pX, float pY, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, ResourcesManager.get().squareRegion, pVertexBufferObjectManager);
		x = pX;
		y = pY;
		mChar = pChar;
		mChar.setPosition(this.getWidth()/ 2, this.getHeight() / 2);
        this.attachChild(mChar);
	}

	public String getChar(){
		return mChar.toString();
	}
	
	public void comeBackJack(){
		ResourcesManager.get().engine.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				CharCard.this.clearEntityModifiers();
				CharCard.this.registerEntityModifier(new MoveModifier(1, CharCard.this.getX(), CharCard.this.getY(), CharCard.this.x,CharCard.this.y,EaseLinear.getInstance()));
		}});
	}

}
