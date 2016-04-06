package ar.uba.fi.superapp.custom;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class FrancoisEditTextView extends EditText {

	public FrancoisEditTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(!isInEditMode()){
			init();
		}
	}

	private void init(){
		setTypeface( TypefaceSpan.getTypeFace(getContext(), "FrancoisOne.ttf"));
	}

}
