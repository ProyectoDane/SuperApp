package ar.uba.fi.superapp.managers;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;

import android.content.Context;
import android.content.SharedPreferences;
import ar.uba.fi.superapp.GameActivity;

public class SoundManager {
	private static final float MAX_MASTER_SOUND_LEVEL = 0.4f;
	private static final float MAX_MASTER_MUSIC_LEVEL = 0.2f;
	private static final String SOUND_PREFERENCES = "ar.uba.fi.superapp.managers.SoundManager";
    private static final SoundManager INSTANCE = new SoundManager();
    public Engine engine;
    public GameActivity activity;
    //---------------------------------------------
    // MUSIC & SOUNDS
    //---------------------------------------------
    
    private Music music,endGame;
    private Sound win;
    private Sound ok;
    private SharedPreferences sharedPref;
    
    public static SoundManager get()
    {
        return INSTANCE;
    }
    
    public void loadResources(){
    	try
    	{
    	    music = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity,"sfx/music.mp3");
    	    endGame = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity,"sfx/fin.mp3");
    	    win = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "sfx/levelcompleted.mp3");
    	    ok = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "sfx/ok.mp3");
    	    music.setLooping(true);
    	    music.setVolume(0.6f);
    	    endGame.setLooping(true);
    	    endGame.setVolume(1f);
    	}
    	catch (IOException e)
    	{
    	    e.printStackTrace();
    	}
    }
    
    public void playBackgroundMusic(){
    	if(music != null && !music.isPlaying()){
    		music.play();
    	}
    }

    public void playOk(){
    	if(ok != null){
    		ok.play();
    	}
    }
    public void playWin(){
    	if(win != null){
    		win.play();
    	}
    }
    
    public void pauseBackgroundMusic(){
    	if(music != null  && music.isPlaying()){
    		music.pause();
    	}
    }
    
    public void playEndGameMusic(){
    	if(endGame != null && !endGame.isPlaying()){
    		endGame.play();
    	}
    }
    
    public void pauseEndGameMusic(){
    	if(endGame != null && endGame.isPlaying()){
    		endGame.pause();
    	}
    }
    
    public void fadeOutBackgroundMusic(){
    	float original = engine.getMusicManager().getMasterVolume();
    	engine.getMusicManager().setMasterVolume(original/2);
    }
    
    public void fadeInBackgroundMusic(){
    	float original = engine.getMusicManager().getMasterVolume();
    	float newVolume = original*2;
    	engine.getMusicManager().setMasterVolume((newVolume>MAX_MASTER_MUSIC_LEVEL)?MAX_MASTER_MUSIC_LEVEL:newVolume);
    }
    
    public void setMuted(boolean mute){
    	if(mute){
    		engine.getSoundManager().setMasterVolume(0);
    		engine.getMusicManager().setMasterVolume(0);
    	}else{
    		engine.getSoundManager().setMasterVolume(MAX_MASTER_SOUND_LEVEL);
    		engine.getMusicManager().setMasterVolume(MAX_MASTER_MUSIC_LEVEL);
    	}
    	SharedPreferences.Editor editor = sharedPref.edit();
    	editor.putBoolean("isMuted", mute);
    	editor.commit();
    }
    
    public boolean isMuted(){
    	return sharedPref.getBoolean("isMuted", false);
    }
    
    
    /**
     * @param engine
     * @param activity
     */
    public static void prepareManager(Engine engine, GameActivity activity)
    {
        get().engine = engine;
        get().activity = activity;
        get().sharedPref = activity.getSharedPreferences(SOUND_PREFERENCES, Context.MODE_PRIVATE);
        get().setMuted(get().isMuted());
    }

    
    public void saveState(){
    	SharedPreferences.Editor editor = sharedPref.edit();
    	editor.putBoolean("playingMusic", (music!=null && music.isPlaying()));
    	editor.putBoolean("playingEndMusic", (endGame!=null && endGame.isPlaying()));
    	editor.commit();
    	pauseBackgroundMusic();
    	pauseEndGameMusic();
    }
    
    public void restoreState(){
    	if(sharedPref.getBoolean("playingMusic", true)){
    		playBackgroundMusic();
    	}
    	if(sharedPref.getBoolean("playingEndMusic", false)){
    		playEndGameMusic();
    	}
    }
    
}
