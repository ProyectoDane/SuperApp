package ar.uba.fi.superapp.scenes;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.particle.BatchedPseudoSpriteParticleSystem;
import org.andengine.entity.particle.emitter.RectangleParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.modifier.ease.EaseLinear;

import android.opengl.GLES20;
import ar.uba.fi.superapp.Config;
import ar.uba.fi.superapp.managers.LevelManager;
import ar.uba.fi.superapp.managers.ResourcesManager;
import ar.uba.fi.superapp.managers.SoundManager;


public class LevelCompleteScene extends MenuScene {

	public static final int MENU_CONTINUE = 0;
	public LevelCompleteScene(Camera pCamera,final
			IOnMenuItemClickListener pOnMenuItemClickListener) {
		super(pCamera, new IOnMenuItemClickListener() {
			public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
					float pMenuItemLocalX, float pMenuItemLocalY) {
				SoundManager.get().pauseEndGameMusic();
				SoundManager.get().fadeInBackgroundMusic();
				SoundManager.get().playBackgroundMusic();
				pOnMenuItemClickListener.onMenuItemClicked(pMenuScene, pMenuItem, pMenuItemLocalX, pMenuItemLocalY);
				return false;
			}
		});
		createScene();
	}

	public void createScene() {
		ResourcesManager.get().loadLevelCompletedResources();
		SoundManager.get().fadeOutBackgroundMusic();
		setPosition(0, 0);
		Sprite background = new Sprite(Config.CAMERA_H_MID, Config.CAMERA_V_MID, Config.CAMERA_WIDTH,Config.CAMERA_HEIGHT ,ResourcesManager.get().lvlCompleteBackgroundRegion, ResourcesManager.get().vbom);
		attachChild(background);
		background.setZIndex(-3);
		
		attachChild( new Sprite(Config.CAMERA_H_MID, 282,ResourcesManager.get().lvlCompletePlateRegion, ResourcesManager.get().vbom));
		
    	final IMenuItem continueMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_CONTINUE, ResourcesManager.get().lvlCompleteContinueRegion, ResourcesManager.get().vbom), 1.1f, 1);
    	addMenuItem(continueMenuItem);
    	buildAnimations();
 	    setBackgroundEnabled(false);
 	    int nextLevel = LevelManager.get().getCurrentLevel()+1;
 	    Text mLevelText = new Text(646, 300, ResourcesManager.get().lvlCompleteFont, "0123456789", new TextOptions(HorizontalAlign.CENTER), ResourcesManager.get().vbom);
 	    mLevelText.setText("" + nextLevel);
 	    attachChild(mLevelText);
 	    continueMenuItem.setPosition(645,224);
 	  if( withBalloonsAndMusic(nextLevel)){
			SoundManager.get().playWin();
 	  }
 	 	  
 	  int progress = LevelManager.get().getProgressForLevel(LevelManager.get().getDifficultyMode(),nextLevel);
 	  final Sprite stars  = new Sprite(Config.CAMERA_H_MID, 109, ResourcesManager.get().progress_regions[progress], ResourcesManager.get().vbom);
 	  attachChild(stars);
 	  if(progress == 5){
 		 stars.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
 		 registerUpdateHandler(new TimerHandler(0.5f,true,new ITimerCallback() {
			public void onTimePassed(TimerHandler pTimerHandler) {
				stars.setAlpha((stars.getAlpha()>0)?0:1f);
			}
		}));
 	  }
 	  
	}
	
	
	private boolean withBalloonsAndMusic(int nextLevel){
		String modeTitle= "";
		boolean gameEnded = false;
		int unlockedExperience = -1;
		ITextureRegion particle =  ResourcesManager.get().lvlCompleteConfetti;
		if(nextLevel == LevelManager.MEDIUM_FIRST_LEVEL ){
			modeTitle= "DIFICULTAD MEDIA";
			unlockedExperience = 1;
		}else if(nextLevel == LevelManager.HARD_FIRST_LEVEL){
			modeTitle= "MODO DIFICIL";
			particle = ResourcesManager.get().lvlCompleteFallingStars;
			unlockedExperience = 2;
		}else if(nextLevel == LevelManager.EXPERT_FIRST_LEVEL){
			modeTitle= "MODO EXPERTO";
			particle = ResourcesManager.get().lvlCompleteConfetti2;
			unlockedExperience = 3;
		} else if (nextLevel == LevelManager.ENDED_GAME_FIRST_LEVEL){
			modeTitle= "FELICITACIONES GANASTE!!!";
			particle = ResourcesManager.get().lvlCompleteConfetti;
			unlockedExperience = 4;
			gameEnded = true;
		}
	
		  int experience = LevelManager.get().getProgressExperience();
		  final Sprite experience1 = new Sprite(438, 154, ResourcesManager.get().experience_regions[(experience>0)?1:0], ResourcesManager.get().vbom);
	 	  attachChild(experience1);
	 	  final Sprite experience2 = new Sprite(480, 154, ResourcesManager.get().experience_regions[(experience>1)?2:0], ResourcesManager.get().vbom);
	 	  attachChild(experience2);
	 	  final Sprite experience3 = new Sprite(522, 154, ResourcesManager.get().experience_regions[(experience>2)?3:0], ResourcesManager.get().vbom);
	 	  attachChild(experience3);
	 	 final Sprite experience4 = new Sprite(564, 154, ResourcesManager.get().experience_regions[(experience>3)?4:0], ResourcesManager.get().vbom);
	 	  attachChild(experience4);
	 	 switch (unlockedExperience) {
			case 1:
				experience1.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		 		 registerUpdateHandler(new TimerHandler(0.5f,true,new ITimerCallback() {
					public void onTimePassed(TimerHandler pTimerHandler) {
						experience1.setAlpha((experience1.getAlpha()>0)?0:1f);
					}
				}));
				break;
			case 2:
		 		 experience2.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		 		 registerUpdateHandler(new TimerHandler(0.5f,true,new ITimerCallback() {
					public void onTimePassed(TimerHandler pTimerHandler) {
						experience2.setAlpha((experience2.getAlpha()>0)?0:1f);
					}
				}));
				break;
			case 3:
				 experience3.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		 		 registerUpdateHandler(new TimerHandler(0.5f,true,new ITimerCallback() {
					public void onTimePassed(TimerHandler pTimerHandler) {
						experience3.setAlpha((experience3.getAlpha()>0)?0:1f);
					}
				}));
				break;
	
			case 4:
				 experience4.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		 		 registerUpdateHandler(new TimerHandler(0.5f,true,new ITimerCallback() {
					public void onTimePassed(TimerHandler pTimerHandler) {
						experience4.setAlpha((experience4.getAlpha()>0)?0:1f);
					}
				}));
				break;
			default:
				 return true;
			}
	 	  
	 		SoundManager.get().pauseBackgroundMusic();
			SoundManager.get().fadeInBackgroundMusic();
			SoundManager.get().playEndGameMusic();
		
		Text modo = new Text(Config.CAMERA_H_MID, -40, ResourcesManager.get().lvlCompleteFont, modeTitle,  ResourcesManager.get().vbom);
		modo.setScale(1.5f);
		
		{
			final BatchedPseudoSpriteParticleSystem particleSystem = new BatchedPseudoSpriteParticleSystem(new RectangleParticleEmitter(Config.CAMERA_H_MID ,Config.CAMERA_HEIGHT+40,Config.CAMERA_WIDTH,1), 4, 10, 35,particle,  ResourcesManager.get().vbom);
			particleSystem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE);
			particleSystem.addParticleInitializer(new VelocityParticleInitializer<Entity>(-20, 20, -120, -150));
			particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Entity>(5, -25));
			particleSystem.addParticleInitializer(new RotationParticleInitializer<Entity>(0.0f, 360.0f));
			particleSystem.addParticleInitializer(new ColorParticleInitializer<Entity>(1.0f, 1.0f, 1.0f));
			particleSystem.addParticleInitializer(new ExpireParticleInitializer<Entity>(5.0f));
			
			attachChild(particleSystem);
			particleSystem.setZIndex(-2);
			sortChildren();
		}
		
		if(gameEnded){
			final BatchedPseudoSpriteParticleSystem particleSystem = new BatchedPseudoSpriteParticleSystem(new RectangleParticleEmitter(Config.CAMERA_H_MID ,-260,Config.CAMERA_WIDTH,1), 5, 10, 15, ResourcesManager.get().lvlCompleteBalloons,  ResourcesManager.get().vbom);
			particleSystem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE);
			particleSystem.addParticleInitializer(new VelocityParticleInitializer<Entity>(-20, 20, 60, 100));
			particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Entity>(5, 25));
			particleSystem.addParticleInitializer(new RotationParticleInitializer<Entity>(-60.0f, 60.0f));
			particleSystem.addParticleInitializer(new ColorParticleInitializer<Entity>(1.0f, 1.0f, 1.0f));
			particleSystem.addParticleInitializer(new ExpireParticleInitializer<Entity>(6.0f));
			
			attachChild(particleSystem);
			particleSystem.setZIndex(-2);
			sortChildren();
		}
		
		attachChild(modo);
		modo.registerEntityModifier(new MoveModifier(1,modo.getX(), modo.getY(), Config.CAMERA_H_MID,Config.CAMERA_HEIGHT - 85,EaseLinear.getInstance()));
		return false;
	}
	
	public void dispose() {
		SoundManager.get().fadeInBackgroundMusic();
		super.dispose();
	}
}
