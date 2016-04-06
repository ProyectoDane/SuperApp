package ar.uba.fi.superapp.scenes;

import java.util.ArrayList;
import java.util.Collections;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.json.JSONArray;
import org.json.JSONException;

import ar.uba.fi.superapp.Config;
import ar.uba.fi.superapp.managers.LevelManager;
import ar.uba.fi.superapp.managers.SceneManager;
import ar.uba.fi.superapp.managers.SoundManager;
import ar.uba.fi.superapp.managers.SceneManager.SceneType;
import ar.uba.fi.superapp.models.LevelConfig;
import ar.uba.fi.superapp.object.Cart;
import ar.uba.fi.superapp.object.CharCard;
import ar.uba.fi.superapp.object.ImageHolder;
import ar.uba.fi.superapp.object.ImageHolderTick;
import ar.uba.fi.superapp.utils.GameType;

public class BuyItemsGameScene extends GameScene {

	ImageHolder mCurrentItemJack = null;

	public BuyItemsGameScene(LevelConfig config) {
		super(config);
	}

	@Override
	public void gameCreateScene() {
		rm.loadBuyItemsSceneResources(mConfig);
		createBackground();
		createHUD();
		attachSprites();
		engine.registerUpdateHandler(new FPSLogger());
	}

	@Override
	public void onBackKeyPressedCallback() {
		LevelManager.get().chooseGame(GameType.BUYITEMS);
    	SceneManager.get().loadDifficultySelectionScene(engine);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		super.disposeScene();
		rm.unloadBuyItemsResources();
	}

	private void createBackground() {
		attachChild(new Sprite(Config.CAMERA_H_MID, Config.CAMERA_V_MID, Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT, rm.mSupermercadoBackgroundRegion, vbom));
	}

	Cart mCart;
	Rectangle myRectangle;
	CharCard[] mCharCard;
	ArrayList<ImageHolder> mImages;
	ArrayList<ImageHolderTick> mItemsToBuy;

	private void attachSprites() {
		setTouchAreaBindingOnActionDownEnabled(true);
		setTouchAreaBindingOnActionMoveEnabled(true);
		Sprite board = new Sprite(771, 352, rm.mBoardRegion, vbom);
		attachChild(board);
		mCart = new Cart(830, 100, vbom);
		mCart.setZIndex(1001);
		this.attachChild(mCart);

		Text tComprar = new Text(770, 480, rm.blackFont, "COMPRAR", new TextOptions(HorizontalAlign.CENTER), vbom);
		tComprar.setColor(Color.BLACK);
		tComprar.setText("COMPRAR");
		attachChild(tComprar);

		ArrayList<String> ja = getRandomizedItems();
		mImages = new ArrayList<ImageHolder>();
		mItemsToBuy = new ArrayList<ImageHolderTick>();
		boolean itemsImageVisible = true;
		boolean itemsToBuyImageVisible = true;
		boolean itemsTextVisible = true;
		boolean itemsToBuyTextVisible = true;
		switch (LevelManager.get().getDifficultyMode()) {
		case EXPERT:
			itemsTextVisible = false;
			itemsToBuyImageVisible = false;
			break;
		case HARD:
			itemsToBuyImageVisible = false;
			break;
		case MEDIUM:
				itemsTextVisible = false;
			break;
		default:
			break;

		}

		int estante = 0;
		int columna = 0;
		for (int i = 0; i < ja.size(); i++) {
			columna = i % 4;
			estante = (int) Math.floor(i / 4.0f);
			ImageHolder mImage;

			mImage = new ImageHolder((String) ja.get(i), rm.mItemsRegions.get(ja.get(i)), Config.CAMERA_H_MID, Config.CAMERA_V_MID + (rm.imagePlaceHolder.getHeight() / 2), vbom) {
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					this.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
					this.setZIndex(1000);

					if (pSceneTouchEvent.isActionUp()) {
						if (this.collidesWith(mCart)) {
							if (!tryToBuyItem(this)) {
								mCurrentItemJack = null;
								this.comeBackJack();
							}
						}
						this.setZIndex(10);
					} else {
						if ((mCurrentItemJack != null) && (mCurrentItemJack != this)) {
							mCurrentItemJack.comeBackJack();
							mCurrentItemJack = null;
						} else {
							mCurrentItemJack = this;
						}
					}
					BuyItemsGameScene.this.sortChildren();
					return true;
				}
			};

			mImage.setScale(100 / rm.imagePlaceHolder.getWidth());
			;
			mImage.setOrigin(100 + (115 * columna), 467 - (135 * estante));
			mImage.setTextVisible(itemsTextVisible);
			mImage.setImageVisible(itemsImageVisible);
			mImages.add(mImage);
			registerTouchArea(mImage);
			this.attachChild(mImage);

		}

		estante = 0;
		columna = 0;
		ArrayList<String> toBuy = getItemsToBuy();
		Collections.shuffle(toBuy);
		for (int i = 0; i < toBuy.size(); i++) {
			columna = i % 3;
			estante = (int) Math.floor(i / 3.0f);
			ImageHolderTick mToBuy = new ImageHolderTick(toBuy.get(i), rm.mItemsRegions.get(toBuy.get(i)), Config.CAMERA_H_MID, Config.CAMERA_V_MID + (rm.imagePlaceHolder.getHeight() / 2), vbom);
			mToBuy.setImageVisible(itemsToBuyImageVisible);
			mToBuy.setTextVisible(itemsToBuyTextVisible);
			mToBuy.setScale(100 / rm.imagePlaceHolder.getWidth());
			;
			mToBuy.setOrigin(660 + (115 * columna), 398 - (135 * estante));
			mItemsToBuy.add(mToBuy);
			this.attachChild(mToBuy);
		}

	}

	private boolean tryToBuyItem(ImageHolder item) {
		for (int index = 0; index < mItemsToBuy.size(); index++) {
			ImageHolderTick listItem = mItemsToBuy.get(index);
			if (listItem.getName().equals(item.getName())) {
				item.setVisible(false);
				unregisterTouchArea(item);
				listItem.setTicked(true);
				listItem.setImageVisible(true);
				mItemsToBuy.remove(index);
				mCart.addItem();
				if (mItemsToBuy.isEmpty()) {
					levelComplete();
				}else{
					SoundManager.get().playOk();
				}
				return true;
			}
		}
		return false;
	}

	private ArrayList<String> getItemsToBuy() {
		ArrayList<String> toBuy = new ArrayList<String>();
		try {
			int randomItemsCount = mConfig.getData().optInt("difficulty");
			JSONArray images = mConfig.getData().getJSONArray("images");
			for (int i = 0; i < images.length(); i++) {
				toBuy.add(images.get(i).toString());
			}

			int remove = ((images.length() - randomItemsCount) <= 6) ? randomItemsCount : images.length() - 6;
			for (int i = 0; i < remove; i++) {
				toBuy.remove((int) (Math.random() * 1000) % toBuy.size());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return toBuy;
	}
	
	private ArrayList<String> getRandomizedItems() {
		ArrayList<String> items = new ArrayList<String>();
		ArrayList<String> temp = new ArrayList<String>();
		try {
			JSONArray images = mConfig.getData().getJSONArray("images");
			for(int i=0;i<images.length();i++){
				temp.add(images.getString(i));
			}
			while(temp.size()>0){
				items.add( temp.remove(((int) (Math.random() * 1000) % temp.size())) );
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return items;
	}
}
