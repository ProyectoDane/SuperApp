package ar.uba.fi.superapp.custom;

import java.util.List;
import java.util.Locale;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import ar.uba.fi.superapp.AddEditItemActivity;
import ar.uba.fi.superapp.R;
import ar.uba.fi.superapp.models.SuperImage;

public class EditItemsAdapter extends BaseAdapter {

	private Context mContext;
	private List<SuperImage> mItems;
	
	public EditItemsAdapter(Context ctx, List<SuperImage> items) {
		mContext = ctx;
		mItems = items;
	}
	
	public int getCount() {
		return mItems.size();
	}

	public Object getItem(int position) {
		return mItems.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	static class SuperImageViewHolder{
		TextView mName;
		ImageView mImage;
		ImageButton mEdit;
		ImageButton mDisable;
		ImageButton mActive;
		SuperImage mItem;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		final SuperImageViewHolder viewHolder;
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_editlist, parent,
					false);
			viewHolder = new SuperImageViewHolder();
			viewHolder.mItem = mItems.get(position);
			viewHolder.mName = (TextView) convertView.findViewById(R.id.titulo_item);
			viewHolder.mImage = (ImageView) convertView.findViewById(R.id.imageItem);
			viewHolder.mEdit = (ImageButton) convertView.findViewById(R.id.buttonEdit);
			viewHolder.mEdit.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					AddEditItemActivity.launch(mContext, viewHolder.mItem,viewHolder.mItem.getmCategory());
				}
			});
			viewHolder.mDisable = (ImageButton) convertView.findViewById(R.id.buttonActivate);
			viewHolder.mDisable.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					viewHolder.mItem.setActive(false);
					viewHolder.mItem.save();
					notifyDataSetChanged();
				}
			});
			viewHolder.mActive = (ImageButton) convertView.findViewById(R.id.buttonDisable);
			viewHolder.mActive.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					viewHolder.mItem.setActive(true);
					viewHolder.mItem.save();
					notifyDataSetChanged();
				}
			});
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (SuperImageViewHolder) convertView.getTag();
			viewHolder.mItem = mItems.get(position);
		}
		viewHolder.mDisable.setVisibility(View.GONE);
		viewHolder.mActive.setVisibility(View.GONE);
		if(viewHolder.mItem.isActive()){
			viewHolder.mDisable.setVisibility(View.VISIBLE);
		}else{
			viewHolder.mActive.setVisibility(View.VISIBLE);
		}
		viewHolder.mName.setText(viewHolder.mItem.getmName().toUpperCase(Locale.getDefault()));
		
		
		Ion.with(mContext).load(viewHolder.mItem.getFullPath()).asBitmap().setCallback(new FutureCallback<Bitmap>() {
			public void onCompleted(Exception arg0, Bitmap arg1) {
				if(arg1 != null){
					viewHolder.mImage.setImageBitmap(arg1);
				}
			}
		});
		
		return convertView;
	}
	
}
