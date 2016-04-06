package ar.uba.fi.superapp;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import ar.uba.fi.superapp.custom.MarketItemsAdapter;
import ar.uba.fi.superapp.models.SuperImage;

public class SuperMarketListActivity extends Activity{
	private ListView mList;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_marketlist);
		ImageButton back = (ImageButton) findViewById(R.id.buttonBack);
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();	
			}
		});
		List<SuperImage> mBuyedItems = SuperImage.getAllToBuyImages();
		mList = (ListView) findViewById(R.id.listView);
		mList.setAdapter(new MarketItemsAdapter(this, mBuyedItems));
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Object item = mList.getAdapter().getItem(arg2);
				if(item instanceof SuperImage){
					SuperImage image = (SuperImage) item;
					image.setBuyed(!image.isBuyed());
					image.save();
					((BaseAdapter)mList.getAdapter()).notifyDataSetChanged();
				}
			}
		});
		
		Button deleteList =  (Button) findViewById(R.id.buttonDelete);
		deleteList.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new AlertDialog.Builder(SuperMarketListActivity.this)
				.setTitle("BORRAR LISTA")
				.setMessage("¿QUIERES BORRAR TODOS LOS ELEMENTOS?")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton("SI", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int whichButton) {
				        SuperImage.clearToBuyImages();
				        List<SuperImage> mBuyedItems = SuperImage.getAllToBuyImages();
				        mList.setAdapter(new MarketItemsAdapter(SuperMarketListActivity.this, mBuyedItems));
				        Toast.makeText(SuperMarketListActivity.this, "TODOS LOS ELEMENTOS FUERON BORRADOS", Toast.LENGTH_LONG).show();
				    }})
				 .setNegativeButton("NO", null).show();
				
			}
		});
		
	}

	
	
}
