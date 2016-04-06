package ar.uba.fi.superapp.object;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.sprite.ButtonSprite.State;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import ar.uba.fi.superapp.managers.ResourcesManager;
import ar.uba.fi.superapp.managers.SoundManager;


public class SoundControlButton extends TiledSprite{
	private State mState;
	
	public SoundControlButton(float pX, float pY, VertexBufferObjectManager vbom) {
		super(pX,pY,new TiledTextureRegion(ResourcesManager.get().soundOn.getTexture(),ResourcesManager.get().soundOn,ResourcesManager.get().soundOff),vbom);
		updateButtonState();
	}
	
	
	private void updateButtonState(){
		if(SoundManager.get().isMuted()){
			this.setCurrentTileIndex(1);
		}else{
			this.setCurrentTileIndex(0);
		}
	}
	
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionDown()) {
			this.changeState(State.PRESSED);
		} else if (pSceneTouchEvent.isActionCancel() || !this.contains(pSceneTouchEvent.getX(), pSceneTouchEvent.getY())) {
			this.changeState(State.NORMAL);
		} else if (pSceneTouchEvent.isActionUp() && this.mState == State.PRESSED) {
			this.changeState(State.NORMAL);
			SoundManager.get().setMuted(!SoundManager.get().isMuted());
			updateButtonState();
		}

		return true;
	}
	
	private void changeState(final State pState) {
		if (pState == this.mState) {
			return;
		}
		this.mState = pState;
	}
	
	public boolean contains(final float pX, final float pY) {
		if (!this.isVisible()) {
			return false;
		} else {
			return super.contains(pX, pY);
		}
	}
	
}
