package ar.uba.fi.superapp.custom;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class FrancoisTextView extends TextView {

	public FrancoisTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(!isInEditMode()){
			init();
		}
	}

	private void init(){
		setTypeface( TypefaceSpan.getTypeFace(getContext(), "FrancoisOne.ttf"));
	}

}
