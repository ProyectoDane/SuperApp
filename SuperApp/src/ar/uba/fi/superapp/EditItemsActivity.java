package ar.uba.fi.superapp;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import ar.uba.fi.superapp.custom.EditItemsAdapter;
import ar.uba.fi.superapp.models.Category;
import ar.uba.fi.superapp.utils.PresetsHelper;
import ar.uba.fi.superapp.utils.SpinnerAdapter;

public class EditItemsActivity extends Activity{
	
	private List<Category> mCategories ;
	private ListView mListItems;
	private Category mCurrentCategory;
	private static final  String CATEGORY_ID = "ar.uba.fi.superapp.EditItemsActivity.CATEGORY_ID";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_itemeditlist);
		PresetsHelper.loadCategoriesAndImages();
		Spinner spinner = (Spinner) findViewById(R.id.categorySpinner);
		mCategories = Category.listAll(Category.class);
		spinner.setAdapter(new SpinnerAdapter(this, mCategories));
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				mCurrentCategory = mCategories.get(arg2);
				setListAdapter(mCurrentCategory);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		Long categoryId = getIntent().getLongExtra(CATEGORY_ID,-1L);
		if(categoryId!= -1){
			for (int i = 0; i < mCategories.size(); i++) {
				if(mCategories.get(i).getId().equals(categoryId)){
					spinner.setSelection(i);
					break;
				}
			}
		}
		
		ImageButton back = (ImageButton) findViewById(R.id.buttonBack);
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();	
			}
		});
		Button newItem = (Button) findViewById(R.id.buttonNew);
		newItem.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AddEditItemActivity.launch(EditItemsActivity.this, null,mCurrentCategory.getId());
			}
		});
		mListItems = (ListView) findViewById(R.id.list);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setUpActivity();
	}
	
	private void setUpActivity(){
		if(mCurrentCategory != null){
			setListAdapter(mCurrentCategory);
		}
	}
	
	private void setListAdapter(Category category){
		mListItems.setAdapter(new EditItemsAdapter(this, category.getAllImages()));
	}
	
	public static void launch(Context context,Category currentCatecorry){
		Intent intent = new Intent(context,EditItemsActivity.class);
		if(currentCatecorry!=null){
			intent.putExtra(CATEGORY_ID, currentCatecorry.getId());
		}
		context.startActivity(intent);
	}
}
