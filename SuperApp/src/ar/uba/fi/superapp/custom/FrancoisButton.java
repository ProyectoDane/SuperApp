package ar.uba.fi.superapp.custom;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class FrancoisButton extends Button {

	public FrancoisButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(!isInEditMode()){
			init();
		}
	}

	private void init(){
		setTypeface( TypefaceSpan.getTypeFace(getContext(), "FrancoisOne.ttf"));
	}

}
