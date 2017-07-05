package com.cursoslicad.android.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by javier on 7/5/17.
 */

public class PictureUtils {
    public static Bitmap getScaledBitmap(String path, Activity activity){
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaledBitmap(path, size.x, size.y);
    }

    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight){
        // Lee las dimensiones de la imagen que se encuentra guardada en el disco
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // No se carga en memoria, solo dimensiones
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        int inSampleSize = 1;
        // Averigua por cuanto escalar la imagen
        if(srcHeight > destHeight || srcWidth > destWidth){
            if(srcWidth > srcHeight){
                inSampleSize = Math.round(srcHeight / destHeight);
            }
            else{
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize; // Escala con un valor de 1/iSampleSize en horizontal
        return BitmapFactory.decodeFile(path, options);


    }

}
