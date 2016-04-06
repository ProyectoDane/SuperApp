package ar.uba.fi.superapp.custom;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class TypefaceSpan extends MetricAffectingSpan {
    /** An <code>LruCache</code> for previously loaded typefaces. */
  private static HashMap<String, Typeface> sTypefaceCache =
          new HashMap<String, Typeface>(12);

  private Typeface mTypeface;

  /**
   * Load the {@link Typeface} and apply to a {@link Spannable}.
   */
  public TypefaceSpan(Context context, String typefaceName) {
      mTypeface = sTypefaceCache.get(typefaceName);
      if (mTypeface == null) {
          mTypeface = Typeface.createFromAsset(context.getApplicationContext()
                  .getAssets(), String.format("font/%s", typefaceName));
          // Cache the loaded Typeface
          sTypefaceCache.put(typefaceName, mTypeface);
      }
      
  }

  
  public static Typeface getTypeFace(Context context, String  typefaceName){
	  Typeface  tmpTypeface = sTypefaceCache.get(typefaceName);

      if (tmpTypeface == null) {
          tmpTypeface = Typeface.createFromAsset(context.getApplicationContext()
                  .getAssets(), String.format("font/%s", typefaceName));

          // Cache the loaded Typeface
          sTypefaceCache.put(typefaceName, tmpTypeface);
      }
      return tmpTypeface;
  }
  
  @Override
  public void updateMeasureState(TextPaint p) {
      p.setTypeface(mTypeface);

      // Note: This flag is required for proper typeface rendering
      p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
  }

  @Override
  public void updateDrawState(TextPaint tp) {
      tp.setTypeface(mTypeface);

      // Note: This flag is required for proper typeface rendering
      tp.setFlags(tp.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
  }
}