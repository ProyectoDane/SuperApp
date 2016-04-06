package ar.uba.fi.superapp.custom;

import java.util.List;
import java.util.Locale;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import ar.uba.fi.superapp.R;
import ar.uba.fi.superapp.models.SuperImage;

public class MarketItemsAdapter extends BaseAdapter {

	private Context mContext;
	private List<SuperImage> mItems;
	
	public MarketItemsAdapter(Context ctx, List<SuperImage> items) {
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
		ImageButton mDelete;
		ImageView mBuyed;
		SuperImage mItem;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		final SuperImageViewHolder viewHolder;
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_marketlist, parent,
					false);
			viewHolder = new SuperImageViewHolder();
			viewHolder.mItem = mItems.get(position);
			viewHolder.mName = (TextView) convertView.findViewById(R.id.titulo_item);
			viewHolder.mImage = (ImageView) convertView.findViewById(R.id.imageItem);
			viewHolder.mDelete = (ImageButton) convertView.findViewById(R.id.deleteButton);
			viewHolder.mDelete.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
							new AlertDialog.Builder(mContext)
							.setTitle("QUITAR DE LA LISTA")
							.setMessage("¿QUIERES QUITAR "+viewHolder.mItem.getmName().toUpperCase(Locale.getDefault())+" DE LA LISTA?")
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setPositiveButton("SI", new DialogInterface.OnClickListener() {
							    public void onClick(DialogInterface dialog, int whichButton) {
									viewHolder.mItem.toBuy(false);
									viewHolder.mItem.setBuyed(false);
									viewHolder.mItem.save();
									mItems.remove(viewHolder.mItem);
									notifyDataSetChanged();
							    }})
							 .setNegativeButton("NO", null).show();
				}
			});
			viewHolder.mBuyed = (ImageView) convertView.findViewById(R.id.checkImage);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (SuperImageViewHolder) convertView.getTag();
			viewHolder.mItem = mItems.get(position);
		}
		viewHolder.mBuyed.setVisibility((viewHolder.mItem.isBuyed()?View.VISIBLE:View.INVISIBLE));
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
