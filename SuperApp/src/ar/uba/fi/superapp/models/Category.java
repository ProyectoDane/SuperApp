package ar.uba.fi.superapp.models;

import java.util.List;
import java.util.Locale;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

public class Category extends SugarRecord<Category> {
	
	String mName;
	String mPath;
	
	@Ignore
	List<SuperImage> mImages = null;
	@Ignore
	 private SuperImage mCategoryImage = null;
	
	public Category() {
		super();
	}
	
	public Category(String name,String path ,List<SuperImage> images) {
		super();
		mName = name;
		mImages = images;
		mPath = path;
		mCategoryImage = new SuperImage(name, path, true);
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}
	
	public void save() {
		super.save();
		for (SuperImage superImage : mImages) {
			superImage.setmCategory(id);
		}
		SuperImage.saveInTx(mImages);
	}

	public static Category findCategoryById(Long id){
		Category category = Category.findById(Category.class, id);
		category.updateImagesList();
		return category;
	}
	
	public List<SuperImage> getAllImages(){
		return	SuperImage.find(SuperImage.class, "m_category=?", id.toString());
	}
	
	public List<SuperImage> getImages(){
		 return SuperImage.find(SuperImage.class, "m_category=?  and m_active=?", id.toString(),"1");
	}
	public List<SuperImage> getNotBuyImages(){
		 return SuperImage.find(SuperImage.class, "m_category=? and m_buy=? and m_active=?", id.toString(),"0","1");
	}
	
	private void updateImagesList(){
		mImages = getImages();
	}
	
	public void loadTexture(){
		getImage().loadTexture();
	}

	public void unloadTexture(){
		getImage().unloadTexture();
	}
	
	public SuperImage getImage(){
		if(mCategoryImage == null){
			mCategoryImage = new SuperImage(mName, mPath, true);
		}
		return mCategoryImage;
	}
	
	public String toString() {
		return getmName().toUpperCase(Locale.getDefault());
	}
	
	public boolean equals(Object o) {
		return this.id == ((Category)o).id;
	}
}
