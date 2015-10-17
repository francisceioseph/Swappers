package br.edu.ifce.swappers.swappers.miscellaneous.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by FAMILIA on 11/06/2015.
 */
public class ImageUtil {

    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b = baos.toByteArray();

        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
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

    public static Bitmap getScaledBitMap(Bitmap bitmap, int newWidth) {
        float newHeight = ((float) newWidth / bitmap.getWidth()) * bitmap.getHeight();
        return Bitmap.createScaledBitmap(bitmap, newWidth, Math.round(newHeight), true);
    }

    public static Bitmap prepareImageFromGallery(String picturePath) {
        Bitmap scaledBitmap = ImageUtil.getScaledBitMap(BitmapFactory.decodeFile(picturePath), 256);

        try {
            Matrix matrix = createFixMatrix(scaledBitmap, picturePath);
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scaledBitmap;
    }

    private static Matrix createFixMatrix(Bitmap bitmap, String imagePath) throws IOException {
        ExifInterface exifInterface = new ExifInterface(imagePath);
        Matrix matrix = new Matrix();

        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

        switch (orientation) {
            case 2:
                //Flip Horizontal
                matrix.preScale(-1.0f, 1.0f);
                break;

            case 3:
                matrix.postRotate(180);
                break;

            case 4:
                //Flip vertical
                matrix.preScale(1.0f, -1.0f);
                break;

            case 5:
                //Transpose
                matrix.preScale(1.0f, -1.0f);
                matrix.postRotate(90);
                break;

            case 6:
                matrix.postRotate(90);
                break;

            case 7:
                //transverse
                matrix.preScale(1.0f, -1.0f);
                matrix.postRotate(270);
                break;
            case 8:
                matrix.postRotate(270);
                break;

            default:
                break;
        }

        return matrix;
    }

}
