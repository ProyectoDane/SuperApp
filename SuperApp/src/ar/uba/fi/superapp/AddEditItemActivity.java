package ar.uba.fi.superapp;

import java.util.List;
import java.util.Locale;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemSelectedListener;
import ar.uba.fi.superapp.models.Category;
import ar.uba.fi.superapp.models.SuperImage;
import ar.uba.fi.superapp.utils.SpinnerAdapter;

public class AddEditItemActivity extends Activity {
	private List<Category> mCategories ;
	SuperImage currentItem;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addedititem);
		currentItem =  new SuperImage("",null,false);
		Long itemId = getIntent().getLongExtra("SUPERIMAGE_ID", -1);
		if(itemId>=0){
				currentItem = SuperImage.findById(SuperImage.class, itemId);
		}else{
			Long category = getIntent().getLongExtra("CATEGORY_ID", -1);
			currentItem.setmCategory(category);
		}
		
		Spinner spinner = (Spinner) findViewById(R.id.categorySpinner);
		mCategories = Category.listAll(Category.class);
		SpinnerAdapter adapter = new SpinnerAdapter(this, mCategories);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Category categorySelected= mCategories.get(arg2);
				currentItem.setmCategory(categorySelected.getId());
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		spinner.setSelection(adapter.getPosition(Category.findCategoryById(currentItem.getmCategory())));
		
		ImageButton back = (ImageButton) findViewById(R.id.buttonBack);
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();	
			}
		});
		Button saveItem = (Button) findViewById(R.id.buttonSave);
		saveItem.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(saveItem()){
					finish();
				}else{
					Toast.makeText(AddEditItemActivity.this, "No se pueden guardar los cambios.", Toast.LENGTH_LONG).show();
				}
			}

		});
		if(!currentItem.ismInternal()){
			((ImageView)findViewById(R.id.itemImage)).setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					startActivityForResult(new Intent(AddEditItemActivity.this,CropImageActivity.class),CropImageActivity.PICK_IMAGE_FROM_GALLERY);
				}
			});
		}else{
			findViewById(R.id.imageHint).setVisibility(View.INVISIBLE);
		}
		
		setView(currentItem);
	}
	
	
	private boolean saveItem() {
		if(currentItem.getmPath() == null){
			return false;
		}
		EditText edittext =  ((EditText) findViewById(R.id.itemName));
		if(edittext.getText().toString().trim().equals("") ){
			return false;
		}
		currentItem.setmName(edittext.getText().toString().trim().toUpperCase(Locale.getDefault()));
		currentItem.setActive(((ToggleButton) findViewById(R.id.itemActive)).isChecked());
		currentItem.save();
		return true;
	}
	private void setView(SuperImage item){
		((ToggleButton) findViewById(R.id.itemActive)).setChecked(item.isActive());
		
		
		if(item.getmPath() != null){
			Ion.with(this).load(item.getFullPath()).asBitmap().setCallback(new FutureCallback<Bitmap>() {
				public void onCompleted(Exception arg0, Bitmap arg1) {
					if(arg1 != null){
					ImageView imageView = (ImageView)findViewById(R.id.itemImage);
					imageView.setImageBitmap(arg1);
					}
				}
			});
		}
		setUpItemNameField(item);
	}
	
	private void setUpItemNameField(SuperImage item){
		final EditText edit = ((EditText) findViewById(R.id.itemName));
		InputFilter filter = new InputFilter() { 
		     public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) { 
		          for (int i = start; i < end; i++) { 
		              if (!Character.isLetterOrDigit(source.charAt(i)) && !Character.isWhitespace(source.charAt(i))) { 
		                  return ""; 
		                  } 
		           } 
		           return null; 
		     }
		}; 

		edit.setFilters(new InputFilter[]{filter,new InputFilter.AllCaps()});
		edit.setText(item.getmName());
	}
	
	public static void launch(Context ctx, SuperImage item, Long categoryId){
		Intent intent = new Intent(ctx,AddEditItemActivity.class);
		if(item != null){
			intent.putExtra("SUPERIMAGE_ID", item.getId());
		}
		intent.putExtra("CATEGORY_ID", categoryId);
		ctx.startActivity(intent);
	}
	
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);

	        if (requestCode == CropImageActivity.PICK_IMAGE_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
	            Uri croppedImageUri = data.getData();
	            if(currentItem.ismInternal()){
	            	currentItem.setId(null);
	            }
	            currentItem.setmInternal(false);
	            currentItem.setmPath(croppedImageUri.toString().replace("file://", ""));
	            setView(currentItem);
	        }
	    }
	
}
