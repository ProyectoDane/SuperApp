package ar.uba.fi.superapp.object;

import java.util.Locale;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;

import ar.uba.fi.superapp.managers.ResourcesManager;

public class ColorButton extends ButtonSprite{

	public ColorButton(float pX, float pY, String text,int index) {
		super(pX, pY, ResourcesManager.get().colorButtonMask[index%ResourcesManager.get().colorButtonMask.length], ResourcesManager.get().vbom);
		Text tName = new Text(65, 25, ResourcesManager.get().miniWhiteFont, text.toUpperCase(Locale.getDefault()), ResourcesManager.get().vbom);
		float tW = tName.getWidth();
		float maxW = ResourcesManager.get().colorButtonMask[index%ResourcesManager.get().colorButtonMask.length].getWidth()*0.8f;
		if(tW > maxW){
			tName.setScale(maxW/tW);
		}
		this.attachChild(tName);
	}

}
