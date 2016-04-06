package ar.uba.fi.superapp.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import ar.uba.fi.superapp.managers.LevelManager;
import ar.uba.fi.superapp.models.LevelConfig;

public class LevelHelper {

	private static LevelHelper _instance = null;

	private LevelHelper() {

	}

	public static LevelHelper get() {
		if (LevelHelper._instance == null) {
			LevelHelper._instance = new LevelHelper();
		}
		return LevelHelper._instance;
	}

	public LevelConfig getLevel(int level) {
		JSONObject jo = new JSONObject();
		try {
			ArrayList<String> aWords = getWords();
			String word =  getLevelWord(level);
			jo.put("word",word.toUpperCase(Locale.getDefault()));
			jo.put("internalimage", true);
			jo.put("image", "items/" + word + ".jpg");
			JSONArray ja = new JSONArray();
			aWords = getBuyItemsWords(level);
			for (String string : aWords) {
				ja.put(string);
			}
			jo.put("images", ja);
			jo.put("internal_mode", ((level >= internalModeLevel(level)) ? 2 : 1));
		} catch (Exception e) {
			e.printStackTrace();
		}

		LevelConfig l = new LevelConfig(jo);
		return l;
	}

	private int internalModeLevel(int level) {
		// Check if extends it for all levels is needed
		return Double.valueOf(Math.floor((((LevelManager.HARD_FIRST_LEVEL - LevelManager.MEDIUM_FIRST_LEVEL) / 2) + LevelManager.MEDIUM_FIRST_LEVEL))).intValue();
	}

	private ArrayList<String> getWords() {
		switch (LevelManager.get().getDifficultyMode()) {
		case MEDIUM:
			return getMediumWords();
		case HARD:
			return getHardWords();
		case EXPERT:
			return getExpertWords();
		default:
			return getEasyWords();
		}

	}
	
	private  ArrayList<String> getBuyItemsWords(int level) {
		ArrayList<String> words;
		int normalized = level;
		switch (LevelManager.get().getDifficultyMode()) {
		case MEDIUM:
			words = getMediumWords();
			words.addAll(getHardWords());
			normalized -= LevelManager.MEDIUM_FIRST_LEVEL;
			break;
		case HARD:
			words = getHardWords();
			words.addAll(getExpertWords());
			normalized -= LevelManager.HARD_FIRST_LEVEL;
			break;
		case EXPERT:
			/*
			 * En modo experto se generan infinitos niveles aleatoreamente, utilizando todas las palabras aprendidas mas las dificiles
			 * */
			words = getExpertWords();
			words.addAll(getHardWords());
			words.addAll(getMediumWords());
			words.addAll(getEasyWords());
			normalized -= LevelManager.EXPERT_FIRST_LEVEL;
			break;
		default:
			words = getEasyWords();
			words.addAll(getMediumWords());
			normalized -= LevelManager.EASY_FIRST_LEVEL;
			break;
		}
		int itemsCount = itemsByLevel(normalized);
		return extractNRandom(itemsCount,words);
	}
	
	String getLevelWord(int level){
		int normalized = level;
		ArrayList<String> words;
		boolean inExpertMode = false;
		switch (LevelManager.get().getDifficultyMode()) {
		case MEDIUM:
			normalized -= LevelManager.MEDIUM_FIRST_LEVEL;
			words = getMediumWords();
			break;
		case HARD:
			normalized -= LevelManager.HARD_FIRST_LEVEL;
			words = getHardWords();
			break;
		case EXPERT:
			normalized -= LevelManager.EXPERT_FIRST_LEVEL;
			words = getExpertWords();
			inExpertMode = true;
			break;
		default:
			normalized -= LevelManager.EASY_FIRST_LEVEL;
			words = getEasyWords();
		}
		if(normalized <0 ){
			normalized = 0;
		}
		/*
		 * En modo expert una vez completados todos los niveles, se generan infinitos niveles aleatoreamente.
		 * */
		if(inExpertMode && normalized >= words.size()){
			words.addAll(getMediumWords());
			words.addAll(getEasyWords());
			words.addAll(getHardWords());
			return extractNRandom(1, words).get(0);
		}
		normalized %= words.size();
		return words.get(normalized);
	}
	
	private int itemsByLevel(int nLevel){
		if(nLevel-1<0){
			nLevel = 0;
		}
		
		int n = nLevel/15;
		int r = nLevel%15;
		if(n>0){
			return 12;
		}else {
			return ((r/3)+8);
		}
	}
	
	private ArrayList<String> extractNRandom(int n, ArrayList<String> array ){
		ArrayList<String> results = new ArrayList<String>();
		for(int i = 0;i<n;i++){
			int itemidx = (((int) (Math.random() * 1000)) % array.size());
			results.add(array.remove(itemidx));
		}
		return results;
	}

	private ArrayList<String> getEasyWords() {
		String[] easy = { "ajo","cafe", "coca", "lata", "mate", "pan", "papa","pala", "pera", "sal", "te", "uva","vaso","pure", "sopa" };
		return new ArrayList<String>(Arrays.asList(easy));
	}

	private ArrayList<String> getMediumWords() {
		String[] medium = { "atun","balde", "banana","batata" ,"leche","limon","carne","cereal", "choclo", "huevo", "jamon", "jabon","melon", "pate","palta"};
		return new ArrayList<String>(Arrays.asList(medium));
	}

	private ArrayList<String> getHardWords() {
		String[] hard = {  "arroz","cereza", "arveja", "pollo", "pepino" ,"sandia","tomate","aceite","escoba","helado" ,"durazno", "lapiz","morron","naranja", "queso" };
		return new ArrayList<String>(Arrays.asList(hard));
	}

	private ArrayList<String> getExpertWords() {
		String[] expert = { "azucar","pizza","hongos","alfajor","cebolla","fideos","esponja","pescado","yerba" ,"kiwi","lechuga", "tostada","brocoli","manzana","zapallo" };
		return new ArrayList<String>(Arrays.asList(expert));
	}
	
}
