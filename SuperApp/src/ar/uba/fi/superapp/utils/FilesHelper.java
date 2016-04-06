package ar.uba.fi.superapp.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import ar.uba.fi.superapp.models.SuperImage;

public class FilesHelper {

	public static File makeImageFile(Context ctx){
		 String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(new Date());
    	 String relativePath = "Item_" + timeStamp  + ".jpg";
    	 
         File basePath ;
         if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
        	 basePath =  ctx.getExternalFilesDir(null);
         } else {
        	 basePath =  ctx.getFilesDir();
         } 
         File dir = new File(basePath.getAbsolutePath() + "/.SuperAppImages/" );
         dir.mkdir();
         return  new File(dir,relativePath);
	}
	
	public static void cleanUnusedFiles(Context ctx){
		Log.v("SUPERAPP", "start cleaning unused files");
		 if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
        	 deleteUnused( ctx.getExternalFilesDir(null));
         }
		 deleteUnused(ctx.getFilesDir());
		 Log.v("SUPERAPP", "finish cleaning unused files");
	}
	
	private static void deleteUnused(File baseDirectory){
		 File dir = new File(baseDirectory.getAbsolutePath() + "/.SuperAppImages/" );
    	 File[] files = dir.listFiles();
    	 if(files == null ){
    		 return;
    	 }
    	 for(File f : files){
    		 if(!SuperImage.fileNameUsed(f.getAbsolutePath())){
    			 f.delete();
    		 }
    	 }
	}
}
