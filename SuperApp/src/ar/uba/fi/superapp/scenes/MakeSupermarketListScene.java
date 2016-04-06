package ar.uba.fi.superapp.scenes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import android.content.Intent;
import ar.uba.fi.superapp.Config;
import ar.uba.fi.superapp.EditItemsActivity;
import ar.uba.fi.superapp.SuperMarketListActivity;
import ar.uba.fi.superapp.managers.ResourcesManager;
import ar.uba.fi.superapp.managers.SceneManager;
import ar.uba.fi.superapp.managers.SceneManager.SceneType;
import ar.uba.fi.superapp.managers.SoundManager;
import ar.uba.fi.superapp.models.Category;
import ar.uba.fi.superapp.models.SuperImage;
import ar.uba.fi.superapp.object.Cart;
import ar.uba.fi.superapp.object.ColorButton;
import ar.uba.fi.superapp.object.ScaledButtonSprite;
import ar.uba.fi.superapp.object.SuperImageHolder;
import ar.uba.fi.superapp.utils.PresetsHelper;

public class MakeSupermarketListScene extends BaseScene {
	Cart mCart;
	ScaledButtonSprite mNext;
	ScaledButtonSprite mViewList;
	Category mCurrentCategory = null;
	List<Category> mCategories;
	List<SuperImage> mImages;
	List<SuperImage> mBuyedItems;
	List<SuperImageHolder> mImageHolders;
	List<ColorButton> mCategoriesHolders;
	Sprite mBackgorund = null; 
	int mCurrentPage = 0;
	SuperImageHolder mCurrentItemJack = null;
	Text itemsCountText;
	private HUD mGameHUD;
	Text mTitle;
	private static int PAGE_SIZE = 12;
	public MakeSupermarketListScene() {
		rm.loadMakeSuperListResoures();
		loadCategoriesItems();
		mCurrentPage = 0;
		mBuyedItems = SuperImage.getAllToBuyImages();
		setTouchAreaBindingOnActionDownEnabled(true);
		setTouchAreaBindingOnActionMoveEnabled(true);
		Sprite board =  new Sprite(821,388,rm.mBoardRegion, vbom);
		board.setZIndex(3);
		attachChild(board);
		mNext = new ScaledButtonSprite(596, 160, rm.mNextRegion, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				/*Evitamos que el boton consuma el evento del item que se esta tratando de mover hacia el carrito*/
				if (pSceneTouchEvent.isActionDown()
						&& mCurrentItemJack != null
						&& mCurrentItemJack.contains(pTouchAreaLocalX,
								pTouchAreaLocalY)) {
					return false;
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		mNext.setOnClickListener(new OnClickListener() {
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				goNextPage();				
			}
		});
		
		mViewList = new ScaledButtonSprite(822, 300, rm.mViewListRegion, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				/*Evitamos que el boton consuma el evento del item que se esta tratando de mover hacia el carrito*/
				if (pSceneTouchEvent.isActionDown()
						&& mCurrentItemJack != null
						&& mCurrentItemJack.contains(pTouchAreaLocalX,
								pTouchAreaLocalY)) {
					return false;
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		mViewList.setOnClickListener(new OnClickListener() {
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				rm.activity.startActivity(new Intent(rm.activity, SuperMarketListActivity.class));
			}
		});
		mViewList.setZIndex(3);
		attachChild(mViewList);
		registerTouchArea(mViewList);
		
		
		Text tCantidad = new Text(827,480, rm.blackFont, "CANTIDAD", new TextOptions(HorizontalAlign.CENTER), vbom);
		tCantidad.setColor(Color.BLACK);
		tCantidad.setText("CANTIDAD");
		tCantidad.setZIndex(3);
		attachChild(tCantidad);
	
		itemsCountText = new Text(823, 390, ResourcesManager.get().bigFont,"1234567890",  new TextOptions(HorizontalAlign.CENTER),vbom);
		itemsCountText.setText(""+ mBuyedItems.size());
		itemsCountText.setZIndex(3);
		attachChild(itemsCountText);
		
		mNext.setZIndex(3);
		attachChild(mNext);
		registerTouchArea(mNext);
		mCart =  new Cart(830, 100, vbom);
		mCart.setZIndex(1001);
		this.attachChild(mCart);
		createHUD();
		mCart.setInitialCount(mBuyedItems.size());
		if(mCategories.size() > 0){
			loadCategory(0);
		}
	}
	
	private void createHUD() {
		mGameHUD = new HUD();
		mTitle = new Text(Config.CAMERA_H_MID, Config.CAMERA_HEIGHT - (rm.font.getLineHeight() / 2), rm.font, "LISTA DEL SUPER", new TextOptions(HorizontalAlign.CENTER), vbom){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionUp()){
					EditItemsActivity.launch(rm.activity, mCurrentCategory);
				}
				
				return super
						.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		// mLevelText.setAnchorCenter(0, 0);
		mTitle.setColor(Color.WHITE);
		mTitle.setText("LISTA DEL SUPER");
		mGameHUD.attachChild(mTitle);
		ButtonSprite backButton = new ButtonSprite(40, Config.CAMERA_HEIGHT - (rm.backButton.getHeight() / 2) - 10, rm.backButton, vbom);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				MakeSupermarketListScene.this.onBackKeyPressed();
			}
		});
		this.registerTouchArea(backButton);
		mGameHUD.attachChild(backButton);
		this.registerTouchArea(mTitle);
		camera.setHUD(mGameHUD);
	}
	
	private void updateTitle(String newTitle){
		mTitle.setText(newTitle.toUpperCase(Locale.getDefault()));
	}

	private void loadCategory(int index){
		createBackground(index);
		mCurrentPage = 0;
		if(mCategories.size() > index ){
			List<SuperImage> images = mImages;
			Category category = mCategories.get(index);
			detachSuperImageHolders();
			mCurrentCategory = Category.findCategoryById(category.getId());
			updateTitle(mCurrentCategory.getmName());
			mImageHolders = new ArrayList<SuperImageHolder>();
			mImages = mCurrentCategory.getNotBuyImages();
			for (SuperImage superImage : mImages) {
				superImage.loadTexture();
			}
			if(images != null){
				for (SuperImage superImage : images) {
					superImage.unloadTexture();
				}
			}
			setItesmCurrentPage();
		}
	}
	
	private void detachSuperImageHolders(){
		if(mImageHolders != null){
			for (SuperImageHolder superImage : mImageHolders) {
				unregisterTouchArea(superImage);
				detachChild(superImage);
			}
		}
	}
	
	private void unloadCategoryImages(){
		detachSuperImageHolders();
		if(mCurrentCategory !=null){
			mCurrentCategory.unloadTexture();
		}
		rm.unloadSuperBackground();
	}
	
	private void unloadCategoriesImages(){
		if(mCategoriesHolders != null){
			for (ColorButton superImage : mCategoriesHolders) {
				unregisterTouchArea(superImage);
				detachChild(superImage);
			}
		}
		//@todo: si no se van a usar categorias con imagenes, hay que sacar este bloque
		if(mCategories != null){
			for (Category category : mCategories) {
				category.unloadTexture();
			}
		}
	}
	
	
	private void goNextPage(){
		if(mImages != null && (mImages.size() / PAGE_SIZE)>0){
			mCurrentPage++;
			float pages = (((float)mImages.size()) / PAGE_SIZE);
			mCurrentPage %= (int)Math.ceil(pages);
			setItesmCurrentPage();
		}
	}
	
	private void setItesmCurrentPage(){
		int estante = 0;
		int columna = 0;
		int index = 0;
		detachSuperImageHolders();
		if(mImages.size() <= (mCurrentPage*PAGE_SIZE)){
			if(mCurrentPage>0){
				mCurrentPage--;
			}
		}
		if(mImages != null && (mImages.size() > PAGE_SIZE)){
			mNext.setVisible(true);
		}else{
			mNext.setVisible(false);
		}
		for(int i=(mCurrentPage*PAGE_SIZE);(i<mImages.size() && i<((mCurrentPage+1)*PAGE_SIZE));i++){
			columna = index % 4;
			estante  = (int) Math.floor( index / 4.0f);
			SuperImageHolder mImage = new SuperImageHolder(mImages.get(i), Config.CAMERA_H_MID, Config.CAMERA_V_MID + (rm.imagePlaceHolder.getHeight() / 2), vbom){
					public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						this.setPosition(pSceneTouchEvent.getX() , pSceneTouchEvent.getY() );
						this.setZIndex(1000);
						
						if(pSceneTouchEvent.isActionUp() ){
							if(this.collidesWith(mCart)){
								mCurrentItemJack = null;
								addToBuyList(this);
							}
							this.setZIndex(10);
						}else{
							if(mCurrentItemJack != null && mCurrentItemJack!=this){
								mCurrentItemJack.comeBackJack();
								mCurrentItemJack = null;
							}else{
								mCurrentItemJack = this;
							}
						}
						MakeSupermarketListScene.this.sortChildren();
						return true;
					}
				};
			
			mImage.setScale(100/rm.imagePlaceHolder.getWidth());
			mImage.setOrigin(100 + (115*columna ), 467 - (135*estante));
			mImage.setTextVisible(true);
			mImage.setImageVisible(true);
			mImage.setZIndex(10);
			mImageHolders.add(mImage);
			registerTouchArea(mImage);
			this.attachChild(mImage);
			index++;
		}
	}
	
	private void loadCategoriesItems(){
		mCategoriesHolders = new ArrayList<ColorButton>();
		PresetsHelper.loadCategoriesAndImages();
		mCategories  = Category.find(Category.class,null);
		int index = 0;
		for(final Category category : mCategories){
			ColorButton categoryImage = new ColorButton(0, 0, category.getImage().getmName(),index);
			categoryImage.setOnClickListener(new OnClickListener() {
				public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
						float pTouchAreaLocalY) {
					onCategoryTouched(category);
				}
			});
			categoryImage.setPosition(80 + (155*index ),40);
			categoryImage.setZIndex(10);
			mCategoriesHolders.add(categoryImage);
			registerTouchArea(categoryImage);
			this.attachChild(categoryImage);
			index++;
		}
		
	}
	
	private void onCategoryTouched(Category category){
		int index = mCategories.indexOf(category);
		if(index != -1){
			loadCategory(index);
		}
	}
	
	private void addToBuyList(SuperImageHolder item){
		item.setVisible(false);		
		//detachChild(item);
		unregisterTouchArea(item);
		mImageHolders.remove(item);
		SuperImage s = item.getImage();
		mImages.remove(s);
		s.toBuy(true);
		s.save();
		s.unloadTexture();
		mBuyedItems.add(s);
		mCart.addItem();
		itemsCountText.setText(""+mBuyedItems.size());
		setItesmCurrentPage();
		
	}
	private void createBackground(int index){
		Sprite tmpBackground = mBackgorund;
		if(tmpBackground!= null){
		 tmpBackground.setZIndex(0);
		}
		rm.unloadSuperBackground();
		rm.loadSuperBackground(index);
		mBackgorund = new Sprite(Config.CAMERA_H_MID, Config.CAMERA_V_MID, rm.mSupermercadoBackgroundRegion, vbom);
		mBackgorund.setZIndex(1);
		attachChild(mBackgorund);
		if(tmpBackground != null){		
			detachChild(tmpBackground);
		}
		sortChildren();
	}
	
	public void onBackKeyPressed() {
	    SceneManager.get().loadMenuScene(engine);
	}

	public SceneType getSceneType() {
		return SceneType.SCENE_MAKE_LIST;
	}

	public void disposeScene() {
		camera.setHUD(null);
		unloadCategoryImages();
		unloadCategoriesImages();
		rm.unloadMakeSuperList();
		SoundManager.get().playBackgroundMusic();
	}

	public void createScene() {
		
	}
	
	@Override
	public void onResume() {
		onCategoryTouched(mCurrentCategory);
		mBuyedItems = SuperImage.getAllToBuyImages();
		mCart.setInitialCount(mBuyedItems.size());
		itemsCountText.setText(""+ mBuyedItems.size());
	}
	
}
