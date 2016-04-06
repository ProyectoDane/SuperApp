package ar.uba.fi.superapp.models;

import java.io.File;
import java.util.List;

import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.FileBitmapTextureAtlasSource;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import ar.uba.fi.superapp.managers.ResourcesManager;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

public class SuperImage extends SugarRecord<SuperImage>{

	private boolean mInternal;
	private boolean mBuy;
	private boolean mActive;
	private boolean mBuyed;
	private String  mPath;
	private String	mName;
	private Long 	mCategory;
	
	@Ignore
	 private ITexture mImageTexture;
	@Ignore
	private ITextureRegion mImageRegion;
	
	public SuperImage() {
		super();
		mBuy = false;
		mActive = true;
	}
	
	public SuperImage(String name,String path, boolean internal) {
		super();
		mName = name;
		mPath = path;
		mInternal = internal;
		mBuy = false;
		mActive = true;
	}
	
	public boolean ismInternal() {
		return mInternal;
	}

	public void setmInternal(boolean mInternal) {
		this.mInternal = mInternal;
	}

	public String getmPath() {
		return mPath;
	}
	
	public String getFullPath(){
		String basePath = "file://";
		if( ismInternal()){
			basePath = "file:///android_asset/";
		}
		return basePath + getmPath();
	}

	public void setmPath(String mPath) {
		this.mPath = mPath;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public Long getmCategory() {
		return mCategory;
	}

	public void setmCategory(Long mCategory) {
		this.mCategory = mCategory;
	}
	
	public void loadTexture(){
		try {
			if(mInternal){
				this.mImageTexture = new AssetBitmapTexture(ResourcesManager.get().activity.getTextureManager(), ResourcesManager.get().activity.getAssets(), mPath, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
				this.mImageRegion = TextureRegionFactory.extractFromTexture(this.mImageTexture);
			}else{
				File file = new File(mPath);
				if(file.exists()){
					this.mImageTexture = new BitmapTextureAtlas(ResourcesManager.get().activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
					this.mImageRegion = BitmapTextureAtlasTextureRegionFactory.createFromSource((BitmapTextureAtlas) mImageTexture, FileBitmapTextureAtlasSource.create(file), 0, 0);
				}else{
					this.mImageTexture = new AssetBitmapTexture(ResourcesManager.get().activity.getTextureManager(), ResourcesManager.get().activity.getAssets(), "gfx/placeholder.jpg", TextureOptions.BILINEAR_PREMULTIPLYALPHA);
					this.mImageRegion = TextureRegionFactory.extractFromTexture(this.mImageTexture);
				}
			}
			this.mImageTexture.load();
		} catch (Exception e) {
		}

	}

	public void unloadTexture(){
		if(mImageTexture != null){
			mImageTexture.unload();
			mImageRegion = null;
			mImageTexture = null;
		}
	}

	public ITexture getmImageTexture() {
		return mImageTexture;
	}

	public ITextureRegion getmImageRegion() {
		return mImageRegion;
	}
	
	public boolean equals(Object o) {
		return getId() == ((SugarRecord<?>) o).getId();
	}
	
	public void toBuy(boolean buy){
		mBuy = buy;
		if(buy == false){
			mBuyed = false;
		}
	}
	
	public boolean isToBuy(){
		return mBuy;
	}
	
	public boolean isBuyed(){
		return mBuyed;
	}
	
	public boolean isActive(){
		return mActive;
	}
	
	public void setActive(boolean active){
		mActive = active;
	}
	
	public void setBuyed(boolean buyed){
		mBuyed = buyed;
	}
	
	public static List<SuperImage> getAllToBuyImages(){
		 return SuperImage.find(SuperImage.class, "m_buy=?","1");
	}
	
	public static void clearToBuyImages(){
		SuperImage.executeQuery("UPDATE "+getTableName(SuperImage.class)+" SET m_buy=? , m_buyed=? ", "0","0");
	}
	
	public static boolean fileNameUsed(String filename){
		 return (SuperImage.count(SuperImage.class, "m_path=?",new String[]{filename}) > 0);
	}
}
