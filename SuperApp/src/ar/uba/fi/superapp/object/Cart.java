package ar.uba.fi.superapp.object;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import ar.uba.fi.superapp.managers.ResourcesManager;

public class Cart extends Sprite {

	ArrayList<Sprite> mBags;
	int mItemsCount;
	public Cart(float pX, float pY, VertexBufferObjectManager pVertexBufferObjectManager){
		super(pX,pY,ResourcesManager.get().mCartRegion,pVertexBufferObjectManager);
		mItemsCount = 0;
		mBags = new ArrayList<Sprite>();
		ArrayList<ITextureRegion> bagsRegion =  ResourcesManager.get().mBagsRegions;
		Sprite bag = new Sprite(120, 108, bagsRegion.get(0), pVertexBufferObjectManager);
		mBags.add(bag);
		bag = new Sprite(130, 130, bagsRegion.get(1), pVertexBufferObjectManager);
		mBags.add(bag);
		bag = new Sprite(77, 126, bagsRegion.get(2), pVertexBufferObjectManager);
		mBags.add(bag);
		bag = new Sprite(44, 118, bagsRegion.get(3), pVertexBufferObjectManager);
		mBags.add(bag);
		bag = new Sprite(92, 137, bagsRegion.get(4), pVertexBufferObjectManager);
		mBags.add(bag);
		bag = new Sprite(52, 142, bagsRegion.get(5), pVertexBufferObjectManager);
		mBags.add(bag);
		bag = new Sprite(115, 120, bagsRegion.get(6), pVertexBufferObjectManager);
		mBags.add(bag);
		for (Sprite mBag : mBags) {
			mBag.setZIndex(-2);
			attachChild(mBag);
			mBag.setVisible(false);
		}
	} 
	
	public void addItem(){
		mItemsCount++;
		if(mItemsCount >= mBags.size() ){
			Sprite top = mBags.get(new Random().nextInt(mBags.size()));
			for (Sprite tmp : mBags) {
				tmp.setZIndex(-2);
			}
			top.setZIndex(-1);
		}else{
			mBags.get(mItemsCount).setVisible(true);
		}
		sortChildren();
	}

	public void setInitialCount(int count){
		hideAllBags();
		mItemsCount = count;
		count = Math.min(count, mBags.size());
		for (int i = 0; i < count; i++) {
			mBags.get(i).setVisible(true);
		}
	}
	
	public void hideAllBags(){
		mItemsCount = 0;
		for (Sprite mBag : mBags) {
			mBag.setZIndex(-2);
			mBag.setVisible(false);
		}
	}
}
