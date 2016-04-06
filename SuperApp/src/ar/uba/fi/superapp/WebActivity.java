package ar.uba.fi.superapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

public class WebActivity  extends Activity{

	public enum WebType {ABOUT,HELP};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		int mode = getIntent().getIntExtra("MODE", WebType.ABOUT.ordinal());
		WebView browser = (WebView) findViewById(R.id.webView);
		browser.clearCache(true);
		browser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		if(mode == WebType.ABOUT.ordinal()){
			browser.loadUrl("file:///android_asset/html/about.html");
			((TextView) findViewById(R.id.title)).setText("INFO");
		}else{
			browser.setVisibility(View.GONE);
			 findViewById(R.id.helpCotainer).setVisibility(View.VISIBLE);
			((TextView) findViewById(R.id.title)).setText("AYUDA");
		}
		ImageButton back = (ImageButton) findViewById(R.id.buttonBack);
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();	
			}
		});
	}
	
	
	public static void launch(Context context,WebType mode){
		Intent intent = new Intent(context, WebActivity.class);
		intent.putExtra("MODE", mode.ordinal());
		context.startActivity(intent);
	}
}
