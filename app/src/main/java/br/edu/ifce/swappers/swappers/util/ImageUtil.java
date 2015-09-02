package br.edu.ifce.swappers.swappers.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by FAMILIA on 11/06/2015.
 */
public class ImageUtil {

    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        Log.i("TAG-PHOTO", "Tamanho = "+temp.length());
        Log.i("TAG-PHOTO", "W = "+bitmap.getWidth());
        Log.i("TAG-PHOTO", "H = "+bitmap.getHeight());
        Log.i("TAG-PHOTO", "ByteCount = "+bitmap.getByteCount());
        return temp;
    }

    public static byte[] BitMapToByte(Bitmap bitmap){
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte [] b = baos.toByteArray();
        Log.i("TAG-PHOTO", "Tamanho="+String.valueOf(b.length));
        return b;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static byte[] StringToByte(String image) throws UnsupportedEncodingException {
            return image.getBytes("UTF-8");
    }
}
