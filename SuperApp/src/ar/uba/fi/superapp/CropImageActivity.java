package ar.uba.fi.superapp;

import static android.graphics.Bitmap.CompressFormat.JPEG;

import java.io.File;

import com.lyft.android.scissors.CropView;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import ar.uba.fi.superapp.utils.FilesHelper;


public class CropImageActivity extends Activity {
	public static final int PICK_IMAGE_FROM_GALLERY = 10001;  
	CropView cropView;

	    View button1;
	    View button2;

	    View pickButton;


	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        setContentView(R.layout.activity_cropimage);

	        pickButton = findViewById(R.id.pick_fab);
	        cropView = (CropView) findViewById(R.id.crop_view);
	        button1 = findViewById(R.id.pick_mini_fab);
	        button2 = findViewById(R.id.crop_fab);
	        onPickClicked(null);
	    }

	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        if (requestCode == PICK_IMAGE_FROM_GALLERY
	                && resultCode == Activity.RESULT_OK) {
	            Uri galleryPictureUri = data.getData();
	            cropView.extensions()
	                    .load(galleryPictureUri);
	            updateButtons();
	        }
	    }

	    public void onCropClicked(View v) {
	    	 File croppedFile = FilesHelper.makeImageFile(this);
	        cropView.extensions()
	        .crop()
	        .quality(80)
	        .format(JPEG)
	        .into(croppedFile);
	        setResult(Activity.RESULT_OK, new Intent().setData(Uri.fromFile(croppedFile)));
	        finish();
	    }

	    public void onPickClicked(View v) {
	        cropView.extensions()
	                .pickUsing(this, PICK_IMAGE_FROM_GALLERY);
	    }


	    public boolean onTouchCropView(MotionEvent event) { // GitHub issue #4
	        if (event.getPointerCount() > 1 || cropView.getImageBitmap() == null) {
	            return true;
	        }

	        switch (event.getActionMasked()) {
	            case MotionEvent.ACTION_DOWN:
	            case MotionEvent.ACTION_MOVE:
	            	button1.setVisibility(View.INVISIBLE);
	            	button2.setVisibility(View.INVISIBLE);
	                break;
	            default:
	            	button1.setVisibility(View.VISIBLE);
	            	button2.setVisibility(View.VISIBLE);
	                break;
	        }
	        return true;
	    }

	    private void updateButtons() {
	    	button1.setVisibility(View.VISIBLE);
	    	button2.setVisibility(View.VISIBLE);
	        pickButton.setVisibility(View.GONE);
	    }

}
