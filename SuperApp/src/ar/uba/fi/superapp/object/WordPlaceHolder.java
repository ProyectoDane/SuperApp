package ar.uba.fi.superapp.object;

import java.util.ArrayList;

import org.andengine.entity.Entity;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import ar.uba.fi.superapp.managers.ResourcesManager;

public class WordPlaceHolder extends Entity {
	
	private ArrayList<CharHolder> mCharSprites;
	public WordPlaceHolder(ArrayList<ColoredLetter>  word,final VertexBufferObjectManager pVertexBufferObjectManager){
		mCharSprites = new ArrayList<CharHolder>();
		int char_width = (int) ResourcesManager.get().charPlaceHolder.getWidth();
		int posX = 0;
		
		mWidth = ((char_width+20 )*(word.size() -1 ));
		mHeight = ResourcesManager.get().charPlaceHolder.getHeight();
			for (ColoredLetter c : word) {
				CharHolder charHolder = new CharHolder( posX, 0, pVertexBufferObjectManager);
				charHolder.setChar(c);
				mCharSprites.add(charHolder);
				posX += (char_width+20);
				attachChild(charHolder);
			}
	}
	
	public boolean tryToShowChar(CharCard card){
		for (CharHolder ch : mCharSprites) {
			if(ch.collidesWith(card) && ch.charMatch(card.getChar()) && ch.isEmpty()){
				ch.showChar();
				return true;
			}
		}
		return false;
	}

	public boolean allCharsAreVisibles(){
		for (CharHolder ch : mCharSprites) {
			if(ch.isEmpty()){
				return false;
			}
		}
		return true;
	}
	
	public void clear(){
		mCharSprites.clear();
	}
	
}
