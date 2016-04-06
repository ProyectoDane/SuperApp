package ar.uba.fi.superapp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.andengine.util.adt.color.Color;

import ar.uba.fi.superapp.object.ColoredLetter;

public class WordsUtils {
	
	private static WordsUtils _instance = null;
	
	private WordsUtils(){
		
	}
	public static WordsUtils get() {
		if(_instance == null){
			_instance = new WordsUtils();
		}
		return _instance;
	}
	
	
	public ArrayList<ColoredLetter> wordToColoredLettersArray(String word){
		Color[] colors = { new Color(0.04f,0.10f,0.85f) ,new Color(0.04f,0.60f,0.7f)  , new Color(0.88f,0.08f,0.40f),
						   new Color(0.99f,0.01f,0.15f) ,new Color(0.99f,0.54f,0.01f) , new Color(0.58f,0.9f,0.46f) , 
						   new Color(1f,0.15f,0.89f)};
		shuffleArray(colors);
		ArrayList<ColoredLetter> coloredLetters = new ArrayList<ColoredLetter>();
		HashMap<Character, Color>  colorMap = new HashMap<Character, Color>();
		char[] letters = word.toCharArray();
		int index = 0;
		for (char c : letters ) {
			if(!colorMap.containsKey(c)){
				colorMap.put(c,colors[index]);
				index++;
				index = index%colors.length;
			}
			coloredLetters.add(new ColoredLetter(colorMap.get(c), c));
		}
		return coloredLetters;
	}
	
	public ArrayList<ColoredLetter> wordToBlackLettersArray(String word){
		ArrayList<ColoredLetter> coloredLetters = new ArrayList<ColoredLetter>();
		char[] letters = word.toCharArray();
		for (char c : letters ) {
			coloredLetters.add(new ColoredLetter(Color.BLACK, c));
		}
		return coloredLetters;
	}
	
	
	static void shuffleArray(Object[] ar)
	  {
	    Random rnd = new Random();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      Object a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	  }
}
