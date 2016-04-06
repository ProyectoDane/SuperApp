package ar.uba.fi.superapp.models;

import org.json.JSONObject;

public class LevelConfig {

	private JSONObject mData;
	
	public LevelConfig( JSONObject data) {
		mData = data;
	}
	
	public JSONObject getData(){
		return mData;
	}

}
