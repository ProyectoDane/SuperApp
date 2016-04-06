package ar.uba.fi.superapp.object;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class ScaledButtonSprite extends ButtonSprite {

	public ScaledButtonSprite(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
	}

	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionDown() || pSceneTouchEvent.isActionMove()) {
			setScale(1.2f);
		} else {
			setScale(1f);
		}
		return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	}
	
}
