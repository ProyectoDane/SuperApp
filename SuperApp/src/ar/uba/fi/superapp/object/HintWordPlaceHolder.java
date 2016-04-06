package ar.uba.fi.superapp.object;

import java.util.ArrayList;

import org.andengine.entity.Entity;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import ar.uba.fi.superapp.managers.ResourcesManager;

public class HintWordPlaceHolder extends Entity {
	
	public HintWordPlaceHolder(ArrayList<ColoredLetter>  word,final VertexBufferObjectManager pVertexBufferObjectManager){
		int char_width = (int) ResourcesManager.get().charPlaceHolder.getWidth();
		int posX = 0;
		mWidth = ((char_width+20 )*(word.size() -1 ));
		mHeight = ResourcesManager.get().charPlaceHolder.getHeight();
			for (ColoredLetter c : word) {
				c.setPosition(posX, 0);
				posX += (char_width+20);
				attachChild(c);
			}
	}
	
}
