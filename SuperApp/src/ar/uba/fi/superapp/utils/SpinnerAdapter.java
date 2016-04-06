package ar.uba.fi.superapp.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ar.uba.fi.superapp.R;
import ar.uba.fi.superapp.models.Category;

public class SpinnerAdapter extends ArrayAdapter<Category>{
	
	public SpinnerAdapter(Context context,  List<Category> options) {
	        super(context,  android.R.layout.simple_spinner_item,  options);
	}
	
	public SpinnerAdapter(Context context) {
        super(context,  android.R.layout.simple_spinner_item, ( new  ArrayList<Category>() ));
	}


    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
    	if(convertView == null){
      	  	LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(R.layout.row, parent, false);
    	}
        TextView label=(TextView)convertView.findViewById(R.id.opcion);
        label.setText(getItem(position).toString());
        return convertView;
    }
    
    public int getItemPosition(Category item){
    	for(int i=0; i<getCount();i++ ){
    		if(getItem(i).equals(item)){
    			return i;
    		}
    	}
    	return -1;
    }
}