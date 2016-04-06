package ar.uba.fi.superapp.utils;

import org.andengine.engine.Engine;

import android.content.Context;
import android.content.SharedPreferences;
import ar.uba.fi.superapp.GameActivity;



public class PrefsHelper {
	private static final String APP_PREFERENCES = " ar.uba.fi.superapp.utils.PrefsHelper";
	private static final String APP_LAUNCH_COUNT_KEY = " ar.uba.fi.superapp.utils.PrefsHelper.LaunchCount";

	private static final PrefsHelper INSTANCE = new PrefsHelper();
    private SharedPreferences sharedPref;

    public static PrefsHelper get()
    {
        return INSTANCE;
    }
    
    public int getLaunchCount(){
    	return sharedPref.getInt(APP_LAUNCH_COUNT_KEY, 0);
    }
    
    public void setLaunchCount(int count){
    	sharedPref.edit().putInt(APP_LAUNCH_COUNT_KEY, count).commit();
    }
    
    public static void prepareManager(Engine engine, GameActivity activity)
    {
        get().sharedPref = activity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }
}
