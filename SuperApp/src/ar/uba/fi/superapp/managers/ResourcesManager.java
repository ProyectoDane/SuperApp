package ar.uba.fi.superapp.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;
import org.json.JSONArray;

import ar.uba.fi.superapp.GameActivity;
import ar.uba.fi.superapp.models.LevelConfig;


public class ResourcesManager
{
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final ResourcesManager INSTANCE = new ResourcesManager();
    
    public Engine engine;
    public GameActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;
    
    
    
    //---------------------------------------------
    // TEXTURES & TEXTURE REGIONS
    //---------------------------------------------
    
    public ITextureRegion splash_region;
    private BitmapTextureAtlas splashTextureAtlas;
   
    public ITexture menu_background_texture;
    public ITextureRegion menu_background_region;
    public ITextureRegion play_region;
    public ITextureRegion lista_region;
    public ITextureRegion help_region;
    public ITextureRegion info_region;
    private BuildableBitmapTextureAtlas menuTextureAtlas;

    //GAME SELECTION
    public ITextureRegion buy_region;
    public ITextureRegion guess_region;
    private BuildableBitmapTextureAtlas gameMenuTextureAtlas;
    
    
    //MODE SELECTION
    public ITextureRegion easy_region;
    public ITextureRegion medium_region;
    public ITextureRegion medium_disabled_region;
    public ITextureRegion hard_region;
    public ITextureRegion hard_disabled_region;
    public ITextureRegion expert_region;
    public ITextureRegion expert_disabled_region;
    public ITextureRegion[] progress_regions;
    public ITextureRegion[] experience_regions;
    private BuildableBitmapTextureAtlas difficultyMenuTextureAtlas;
    
    
    
    private BuildableBitmapTextureAtlas levelCompletedTextureAtlas;
    public ITextureRegion lvlCompleteBackgroundRegion;
    public ITextureRegion lvlCompletePlateRegion;
    public ITextureRegion lvlCompleteContinueRegion;
    public ITextureRegion lvlCompleteConfetti;
    public ITextureRegion lvlCompleteConfetti2;
    public ITextureRegion lvlCompleteFallingStars;
    public ITextureRegion lvlCompleteBalloons;

    private BuildableBitmapTextureAtlas chooseLevelTextureAtlas;
    public ITextureRegion chooseLvlBackgroundRegion;
    public ITextureRegion chooseLvlContinueRegion;
    public ITextureRegion chooseLvlNextRegion;
    public ITextureRegion chooseLvlPrevRegion;
    public Font chooseLvlFont;
    
	public ITexture squareTexture;
    public ITextureRegion squareRegion;
    public ITexture tickTexture;
    public ITextureRegion tickRegion;
    
    // Game Texture
    public BuildableBitmapTextureAtlas gameTextureAtlas;
	public ITextureRegion mPictureTextureRegion;
	
	//Guess 
    public ITextureRegion editorButton;
    public ITextureRegion[] colorButtonMask;
    public ITextureRegion charPlaceHolder;
    public ITextureRegion imagePlaceHolder;
    
    public BuildableBitmapTextureAtlas globalHUDTextureAtlas;
    public ITextureRegion soundOn;
    public ITextureRegion soundOff;
    public ITextureRegion backButton;
    
    
    //Supermercado
    public ITextureRegion mCartRegion;
    public ITexture mSupermercadoBackground;
    public ITextureRegion mSupermercadoBackgroundRegion;
    public HashMap<String,ITextureRegion> mItemsRegions;
    public ITexture mBoardTexture;
    public ITextureRegion mBoardRegion;
    public ITextureRegion mNextRegion;
    public ITextureRegion mViewListRegion;
    public ArrayList<ITextureRegion> mBagsRegions;
    public BuildableBitmapTextureAtlas mCartAndBagsTexture;
    
    
    private BuildableBitmapTextureAtlas helpTextureAtlas;
    public ITextureRegion helpBackgroundRegion;
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------

    public Font font;
    public Font miniFont;
    public Font miniWhiteFont;
    public Font blackFont;
    public Font bigFont;
    public Font lvlCompleteFont;

    private void loadMenuFonts()
    {
        FontFactory.setAssetBasePath("font/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "FrancoisOne.ttf", 50, true, Color.WHITE.getARGBPackedInt(), 2, Color.WHITE.getARGBPackedInt());
        font.load();
    }
    
    public void loadMenuResources()
    {
    	loadMenuGraphics();
    	loadMenuFonts();
    }
    
    
    public void loadGlobalAssets(){
    	 BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		 globalHUDTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 backButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(globalHUDTextureAtlas, activity, "backarrow.png");
		 soundOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(globalHUDTextureAtlas, activity, "soundon.png");
		 soundOff = BitmapTextureAtlasTextureRegionFactory.createFromAsset(globalHUDTextureAtlas, activity, "soundoff.png");
		 try 
		    {
		        this.globalHUDTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
		        this.globalHUDTextureAtlas.load();
		    } 
		    catch (final TextureAtlasBuilderException e)
		    {
		        Debug.e(e);
		    }
    }
    
    public void unloadGlobalAssets(){
    	if(globalHUDTextureAtlas != null){
    		globalHUDTextureAtlas.unload();
    		backButton = null;
    		soundOn = null;
    		soundOff = null;
    	}
    }
    public void loadBuyItemsSceneResources(LevelConfig config){
    	
    	loadCartAndBags();
    	try 
    	{
    		mItemsRegions = new HashMap<String,ITextureRegion>();
    		 BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    		 gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    		 imagePlaceHolder = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "imageplaceholder.png");
    		 JSONArray ja = config.getData().getJSONArray("images");
			for (int i = 0; i < ja.length(); i++) {
				String image = ja.getString(i);
				mItemsRegions.put(image,BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "items/" +image + ".jpg"));
			}				
    		 try 
    		    {
    		        this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    		        this.gameTextureAtlas.load();
    		    } 
    		    catch (final TextureAtlasBuilderException e)
    		    {
    		        Debug.e(e);
    		    }
    		mBoardTexture = new AssetBitmapTexture(activity.getTextureManager(), activity.getAssets(), "gfx/super/listboard.png",TextureOptions.BILINEAR_PREMULTIPLYALPHA); 
    		mBoardRegion = TextureRegionFactory.extractFromTexture(mBoardTexture);
    		mBoardTexture.load();
    		int background = LevelManager.get().getCurrentLevel() % 4;
    		this.mSupermercadoBackground = new AssetBitmapTexture(activity.getTextureManager(), activity.getAssets(), "gfx/super/backgroundlista"+background+".jpg", TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.mSupermercadoBackgroundRegion = TextureRegionFactory.extractFromTexture(this.mSupermercadoBackground);
			this.mSupermercadoBackground.load();
			this.tickTexture = new AssetBitmapTexture(activity.getTextureManager(), activity.getAssets(), "gfx/imageplaceholder_green.png", TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.tickRegion = TextureRegionFactory.extractFromTexture(this.tickTexture);
			this.tickTexture.load();
			FontFactory.setAssetBasePath("font/");
		    final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		    final ITexture blackFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		    final ITexture miniFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		    font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "FrancoisOne.ttf", 50, true, Color.WHITE.getARGBPackedInt(), 2, Color.WHITE.getARGBPackedInt());
		    font.load();
		    miniFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), miniFontTexture, activity.getAssets(), "FrancoisOne.ttf", 35, true, Color.BLACK.getARGBPackedInt(), 0, Color.BLACK.getARGBPackedInt());
		    miniFont.load();
		    blackFont  = FontFactory.createStrokeFromAsset(activity.getFontManager(), blackFontTexture, activity.getAssets(), "FrancoisOne.ttf", 35, true, Color.BLACK.getARGBPackedInt(), 0, Color.BLACK.getARGBPackedInt());
		    blackFont.load(); 
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void loadHelpResources(){
    	 BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	 helpTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    	 helpBackgroundRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(helpTextureAtlas, activity, "help_menu.png");
    	 try{
    		 this.helpTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
		     this.helpTextureAtlas.load();    		 
    	 }catch(Exception e){
    	 }
    }
    
    public void loadMakeSuperListResoures(){
    	
    	loadCartAndBags();
    	
		 BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		 gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 editorButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "ic_action_settings.png");
		 colorButtonMask = new ITextureRegion[4];
		 colorButtonMask[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "bluebuttonmask.png");
		 colorButtonMask[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "pinkbuttonmask.png");
		 colorButtonMask[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "orangebuttonmask.png");
		 colorButtonMask[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "lightgreenbuttonmask.png");
		 imagePlaceHolder = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "imageplaceholder.png");
		 mNextRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "super/arrowgreen.png");
		 mViewListRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "super/viewlist.png");
		
		 try 
		    {
		        this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
		        this.gameTextureAtlas.load();
		        mBoardTexture = new AssetBitmapTexture(activity.getTextureManager(), activity.getAssets(), "gfx/super/countboard.png",TextureOptions.BILINEAR_PREMULTIPLYALPHA); 
		 		mBoardRegion = TextureRegionFactory.extractFromTexture(mBoardTexture);
		 		mBoardTexture.load();
		    } 
		    catch (final TextureAtlasBuilderException e)
		    {
		        Debug.e(e);
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FontFactory.setAssetBasePath("font/");
		    final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		    final ITexture blackFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		    final ITexture bigFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		    final ITexture miniFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		    final ITexture miniWhiteFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		    font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "FrancoisOne.ttf", 50, true, Color.WHITE.getARGBPackedInt(), 2, Color.WHITE.getARGBPackedInt());
		    font.load();
		    miniFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), miniFontTexture, activity.getAssets(), "FrancoisOne.ttf", 35, true, Color.BLACK.getARGBPackedInt(), 0, Color.BLACK.getARGBPackedInt());
		    miniFont.load();
		    miniWhiteFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), miniWhiteFontTexture, activity.getAssets(), "FrancoisOne.ttf", 25, true, Color.WHITE.getARGBPackedInt(), 0, Color.WHITE.getARGBPackedInt());
		    miniWhiteFont.load();
		    blackFont  = FontFactory.createStrokeFromAsset(activity.getFontManager(), blackFontTexture, activity.getAssets(), "FrancoisOne.ttf", 35, true, Color.BLACK.getARGBPackedInt(), 0, Color.BLACK.getARGBPackedInt());
		    blackFont.load(); 
		    bigFont  = FontFactory.createStrokeFromAsset(activity.getFontManager(), bigFontTexture, activity.getAssets(), "FrancoisOne.ttf", 150, true, Color.BLACK.getARGBPackedInt(), 0, Color.BLACK.getARGBPackedInt());
		    bigFont.load(); 
    }
    
    public void loadSuperBackground(int index){
		int background = index % 4;
		try {
			this.mSupermercadoBackground = new AssetBitmapTexture(activity.getTextureManager(), activity.getAssets(), "gfx/super/backgroundlista"+background+".jpg", TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.mSupermercadoBackgroundRegion = TextureRegionFactory.extractFromTexture(this.mSupermercadoBackground);
			this.mSupermercadoBackground.load();
		} catch (IOException e) {
		}
    }
    
    public void loadCartAndBags(){
    	mBagsRegions = new ArrayList<ITextureRegion>();
    	 BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		 mCartAndBagsTexture = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    	 mCartRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mCartAndBagsTexture, activity, "super/cart.png");
    	 for (int i = 0; i < 7; i++) {
    		ITextureRegion bag = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mCartAndBagsTexture, activity, "super/bolsa"+i+".png");
    	    mBagsRegions.add(bag);
		}
    	  try {
			this.mCartAndBagsTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.mCartAndBagsTexture.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
    }
    
    public void unloadCartAndBags(){
    	if(mCartAndBagsTexture != null){
    		mCartAndBagsTexture.unload();
    		mCartAndBagsTexture = null;
    		mCartRegion = null;
    	}
    	if(mBagsRegions != null){
    		mBagsRegions.clear();
    		mBagsRegions = null;
    	}
    }
    
    public void unloadSuperBackground(){
    	this.mSupermercadoBackgroundRegion = null;
		if(mSupermercadoBackground != null){
			mSupermercadoBackground.unload();
			mSupermercadoBackground = null;
		}
    }
    
    public void unloadHelpResources(){
    	this.helpBackgroundRegion = null;
		if(helpTextureAtlas != null){
			helpTextureAtlas.unload();
			helpTextureAtlas = null;
		}
    }
    
    public void unloadMakeSuperList(){
    	 unloadCartAndBags();
    	if(gameTextureAtlas != null){
    		gameTextureAtlas.unload();
    		gameTextureAtlas = null;
    	}
    	mBoardRegion = null;
    	if(mBoardTexture != null){
    		mBoardTexture.unload();
    		mBoardTexture = null;
    	}
    	if(font != null){
    		font.unload();
    		font =null;
    	}
    	if(miniFont != null){
    		miniFont.unload();
    		miniFont = null;
    	}
    	if(miniWhiteFont != null){
    		miniWhiteFont.unload();
    		miniWhiteFont = null;
    	}
    	if(blackFont != null){
    		blackFont.unload();
    		blackFont = null;
    	}
    	if(bigFont != null){
    		bigFont.unload();
    		bigFont = null;
    	}
    }
    
    private void loadMenuGraphics()
    {
    	loadHelpResources();
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
    	menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1920, 1480, TextureOptions.BILINEAR);
    	menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "mainmenubackground.jpg");
    	play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "jugar_button.png");
    	lista_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "lista_button.png");
    	help_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "help.png");
    	info_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "info.png");
    	       
    	try 
    	{
    	    this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    	    this.menuTextureAtlas.load();
    	} 
    	catch (final TextureAtlasBuilderException e)
    	{
    	        Debug.e(e);
    	}
    	
    }
    
    
    public void loadDifficultyMenuResources()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
    	difficultyMenuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1920, 1920, TextureOptions.BILINEAR);
    	menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(difficultyMenuTextureAtlas, activity, "mainmenubackground.jpg");
        easy_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(difficultyMenuTextureAtlas, activity, "easy_green.png");
        medium_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(difficultyMenuTextureAtlas, activity, "medium_orange.png");
        medium_disabled_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(difficultyMenuTextureAtlas, activity, "medium_disabled.png");
        hard_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(difficultyMenuTextureAtlas, activity, "hard_red.png");
        hard_disabled_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(difficultyMenuTextureAtlas, activity, "hard_disabled.png");
        expert_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(difficultyMenuTextureAtlas, activity, "expert_pink.png");
        expert_disabled_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(difficultyMenuTextureAtlas, activity, "expert_disabled.png");
        progress_regions = new ITextureRegion[6]; 
        progress_regions[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(difficultyMenuTextureAtlas, activity, "progress0.png");
        progress_regions[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(difficultyMenuTextureAtlas, activity, "progress1.png");
        progress_regions[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(difficultyMenuTextureAtlas, activity, "progress2.png");
        progress_regions[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(difficultyMenuTextureAtlas, activity, "progress3.png");
        progress_regions[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(difficultyMenuTextureAtlas, activity, "progress4.png");
        progress_regions[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(difficultyMenuTextureAtlas, activity, "progress5.png");
       try 
    	{
    	    this.difficultyMenuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    	    this.difficultyMenuTextureAtlas.load();
    	} 
    	catch (final TextureAtlasBuilderException e)
    	{
    	        Debug.e(e);
    	}
    	FontFactory.setAssetBasePath("font/");
	    final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
	    font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "FrancoisOne.ttf", 50, true, Color.WHITE.getARGBPackedInt(), 2, Color.WHITE.getARGBPackedInt());
	    font.load(); 
    }
    
    
    public void loadLevelCompletedResources()
    {
        
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	levelCompletedTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1920, 1920, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    	lvlCompleteBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "levelcomplete.png");
    	lvlCompletePlateRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "levelcompleteplate.png");
    	lvlCompleteContinueRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "continuar.png");
    	lvlCompleteConfetti  =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "confetti.png");
    	lvlCompleteConfetti2  =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "confetti2.png");
    	lvlCompleteFallingStars =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "fallingstars.png");
    	lvlCompleteBalloons =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "balloons.png");
    	progress_regions = new ITextureRegion[6]; 
    	 progress_regions[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "menu/progress0.png");
         progress_regions[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "menu/progress1.png");
         progress_regions[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "menu/progress2.png");
         progress_regions[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "menu/progress3.png");
         progress_regions[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "menu/progress4.png");
         progress_regions[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "menu/progress5.png");
         experience_regions = new ITextureRegion[5]; 
         experience_regions[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "menu/experience0.png");
         experience_regions[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "menu/experience1.png");
         experience_regions[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "menu/experience2.png");
         experience_regions[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "menu/experience3.png");
         experience_regions[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompletedTextureAtlas, activity, "menu/experience4.png");
     	
    	
    	final ITexture lvlCompleteFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 lvlCompleteFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), lvlCompleteFontTexture, activity.getAssets(), "FrancoisOne.ttf", 35, true, Color.WHITE.getARGBPackedInt(), 0, Color.WHITE.getARGBPackedInt());
		 lvlCompleteFont.load();
    	try 
    	{
    	    this.levelCompletedTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    	    this.levelCompletedTextureAtlas.load();
    	} 
    	catch (final TextureAtlasBuilderException e)
    	{
    	        Debug.e(e);
    	}
    	
    }
    
    public void unloadLevelCompletedGraphics()
    {
    	if(levelCompletedTextureAtlas != null ){
	    	levelCompletedTextureAtlas.unload();
	    	lvlCompleteBackgroundRegion = null;
	    	lvlCompleteContinueRegion = null;
    	}
    	if(lvlCompleteFont != null){
    		lvlCompleteFont.unload();
    		lvlCompleteFont = null;
    	}
    }
    
    
    public void loadChooseLevelResources()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	chooseLevelTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	chooseLvlBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(chooseLevelTextureAtlas, activity, "chooselevel.png");
    	chooseLvlContinueRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(chooseLevelTextureAtlas, activity, "continuar.png");
    	chooseLvlNextRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(chooseLevelTextureAtlas, activity, "nextlevel.png");
    	chooseLvlPrevRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(chooseLevelTextureAtlas, activity, "prevlevel.png");
    	 final ITexture chooseLevelFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    	 chooseLvlFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), chooseLevelFontTexture, activity.getAssets(), "FrancoisOne.ttf", 55, true, Color.WHITE.getARGBPackedInt(), 0, Color.WHITE.getARGBPackedInt());
    	 chooseLvlFont.load();
    	try 
    	{
    	    this.chooseLevelTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    	    this.chooseLevelTextureAtlas.load();
    	} 
    	catch (final TextureAtlasBuilderException e)
    	{
    	        Debug.e(e);
    	}
    }
    
    public void unloadChooseLevelGraphics()
    {
    	if(chooseLevelTextureAtlas != null ){
    		chooseLevelTextureAtlas.unload();
	    	chooseLvlBackgroundRegion = null;
	    	chooseLvlContinueRegion = null;
	    	chooseLvlNextRegion = null;
	        chooseLvlPrevRegion = null;
    	}
    	if(chooseLvlFont != null){
    		chooseLvlFont.unload();
    		chooseLvlFont = null;
    	}
    }
    
    public void unloadMenuTextures()
    {
        menuTextureAtlas.unload();
    }
   
    public void unloadDifficutyMenuTextures()
    {
    	difficultyMenuTextureAtlas.unload();
    	if(font != null){
    		font.unload();
    		font = null;
    	}
    }
        
    public void loadMenuTextures()
    {
        menuTextureAtlas.load();
    }
    
    
    public void loadSplashScreen()
    {
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1920, 1080, TextureOptions.BILINEAR);
    	splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.jpg", 0, 0);
    	splashTextureAtlas.load();
    
    }
    
    public void unloadSplashScreen()
    {
    	splashTextureAtlas.unload();
    	splash_region = null;

    }
   
    public void loadGuessSceneResources(LevelConfig config ) {
		 BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		 gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		 squareRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "square.png");
		 charPlaceHolder = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "charplaceholder.png");
		 imagePlaceHolder = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "bigsquare.png");
		 mPictureTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, config.getData().optString("image"));
		 
		 try 
		    {
		        this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
		        this.gameTextureAtlas.load();
		    } 
		    catch (final TextureAtlasBuilderException e)
		    {
		        Debug.e(e);
		    }
		FontFactory.setAssetBasePath("font/");
	    final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
	    font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "FrancoisOne.ttf", 50, true, Color.WHITE.getARGBPackedInt(), 2, Color.WHITE.getARGBPackedInt());
	    font.load(); 	
    }
    
    public void unloadBuyItemsResources(){
    	try {
    		 unloadCartAndBags();
    		if(mItemsRegions != null){
    			mItemsRegions.clear();
    		}
    		if(gameTextureAtlas != null){
    			gameMenuTextureAtlas.unload();
    			gameTextureAtlas = null;
    		}
    		imagePlaceHolder =null;
			this.mSupermercadoBackgroundRegion = null;
			if(mSupermercadoBackground != null){
				mSupermercadoBackground.unload();
				mSupermercadoBackground = null;
    		}
			mBoardRegion = null;
	    	if(mBoardTexture != null){
	    		mBoardTexture.unload();
	    		mBoardTexture = null;
	    	}
			tickRegion = null;
			if(tickTexture != null){
				tickTexture.unload();
				tickTexture = null;
			}
		    if(font != null){
		    	font.unload();
		    	font = null;
		    }
		    if(miniFont != null){
		    	miniFont.unload();
		    	miniFont = null;
		    }
		    if(blackFont != null){
		    	blackFont.unload();
		    	blackFont = null;
		    }
		    System.gc();
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void unloadGuessSceneResources() {
    	if(squareTexture != null){
    		this.squareTexture.unload();
    		this.squareTexture = null;
    		this.squareRegion = null;
    		this.charPlaceHolder = null;
    		this.imagePlaceHolder = null;
    		this.mPictureTextureRegion = null;
    		gameTextureAtlas.unload();
    		gameTextureAtlas = null;
    		font.unload();
    		font = null;
    	}
    	System.gc();
    }
    
    /**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br><br>
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
    public static void prepareManager(Engine engine, GameActivity activity, Camera camera, VertexBufferObjectManager vbom)
    {
        get().engine = engine;
        get().activity = activity;
        get().camera = camera;
        get().vbom = vbom;
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static ResourcesManager get()
    {
        return INSTANCE;
    }

	public void loadGamesMenuResources() {
		    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		    	gameMenuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1920, 1920, TextureOptions.BILINEAR);
		    	menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameMenuTextureAtlas, activity, "mainmenubackground.jpg");
		        buy_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameMenuTextureAtlas, activity, "buyitemsbutton.png");
		        guess_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameMenuTextureAtlas, activity, "guessthewordbutton.png");
		    	       
		    	try 
		    	{
		    	    this.gameMenuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
		    	    this.gameMenuTextureAtlas.load();
		    	} 
		    	catch (final TextureAtlasBuilderException e)
		    	{
		    	        Debug.e(e);
		    	}
		    	FontFactory.setAssetBasePath("font/");
			    final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
			    font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "FrancoisOne.ttf", 50, true, Color.WHITE.getARGBPackedInt(), 2, Color.WHITE.getARGBPackedInt());
			    font.load(); 
    }
	
	public void unloadGamesMenuResources() {
		unloadHelpResources();
		gameMenuTextureAtlas.unload();
    	if(font != null){
    		font.unload();
    		font = null;
    	}
	}
}
