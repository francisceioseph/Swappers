package br.edu.ifce.swappers.swappers.miscellaneous.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.activities.LoginActivity;
import br.edu.ifce.swappers.swappers.activities.MainActivity;
import br.edu.ifce.swappers.swappers.model.User;


/**
 * Created by FAMILIA on 10/06/2015.
 */
public class AndroidUtils {
    public final static String SELECTED_BOOK_ID  = "SELECTED_BOOK";
    public final static String SELECTED_PLACE_ID = "SELECTED_PLACE";


    public final static int     ADD_COMMENT_INTENT_CODE = 1991;
    public final static int     FROM_DETAIL_PLACE_INTENT_CODE = 1992;
    public final static String  ORIGIN_DETAIL_BOOK_TITLE = "ORIGIN";

    public final static String USER_SECRET_DATA = "my_awesome_secrets";

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean userHasBeenLoaded(Context context){
        SharedPreferences manager = context.getSharedPreferences(USER_SECRET_DATA, Context.MODE_PRIVATE);
        String name, password;

        name = manager.getString("email", null);
        password = manager.getString("password", null);

        if (name != null && password != null){
            return true;
        }

        return false;
    }

    public static boolean userHasCityState(Context context){
        SharedPreferences manager = context.getSharedPreferences(USER_SECRET_DATA, Context.MODE_PRIVATE);
        String name, password;

        name = manager.getString("city", null);
        password = manager.getString("state", null);

        if (name != null && password != null){
            return true;
        }

        return false;
    }

    public static void createUser(Context context, User user){
        SharedPreferences manager = context.getSharedPreferences(USER_SECRET_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = manager.edit();

        int coverPhotoDrawableID  = ImageUtil.getRandomCoverDrawableID();
        Bitmap coverPhotoDrawable = ((BitmapDrawable) ContextCompat.getDrawable(context, coverPhotoDrawableID)).getBitmap();
        String coverPhotoBase64   = ImageUtil.BitMapToString(coverPhotoDrawable);

        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());
        editor.putString("photo", user.getPhoto2());

        if(user.getCover()!=null){
            editor.putString("photo_cover", user.getCover());
        }else{
            editor.putString("photo_cover", coverPhotoBase64);
        }
        editor.putString("name", user.getName());
        editor.putInt("id", user.getId());
        if(user.getBirthday()!=null) {
            editor.putLong("birthday", user.getBirthday());
        }
        editor.apply();
    }

    public static User loadUser(Context context) {
        User user = null;

        if (userHasBeenLoaded(context)) {
            SharedPreferences manager = context.getSharedPreferences(USER_SECRET_DATA, Context.MODE_PRIVATE);

            user = new User();

            user.setId(manager.getInt("id", 0));
            user.setName(manager.getString("name", null));
            user.setEmail(manager.getString("email", null));
            user.setPassword(manager.getString("password", null));
            user.setPhoto2(manager.getString("photo", null));
            user.setBirthday(manager.getLong("birthday", 0L));
            user.setCover(manager.getString("cover", null));
            user.setCity(manager.getString("city",null));
            user.setState(manager.getString("state", null));
        }

        return user;
    }

    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(USER_SECRET_DATA, Context.MODE_PRIVATE);
    }

    public static void deleteUser(Context context) {
        SharedPreferences manager = context.getSharedPreferences(USER_SECRET_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = manager.edit();

        editor.clear();
        editor.apply();
    }

    public static String loadProfilePictureBase64(Context context) {
        String photoBase64 = null;

        if (userHasBeenLoaded(context)) {
            SharedPreferences manager = context.getSharedPreferences(USER_SECRET_DATA, Context.MODE_PRIVATE);
            photoBase64 = manager.getString("photo", null);
        }

        return photoBase64;
    }

    public static void saveProfilePicture(Context context, String encodedImage){
        if(userHasBeenLoaded(context)) {
            SharedPreferences manager = context.getSharedPreferences(USER_SECRET_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = manager.edit();

            editor.putString("photo", encodedImage);
            editor.apply();
        }
    }

    public static void saveCityState(Context context, String city,String state){
        if(userHasBeenLoaded(context)) {
            SharedPreferences manager = context.getSharedPreferences(USER_SECRET_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = manager.edit();

            editor.putString("state", state);
            editor.putString("city", city);
            editor.apply();
        }
    }

    public static void updatePasswordSharedPreferences(Context context, String newPassword){
        if(userHasBeenLoaded(context)) {
            SharedPreferences manager = context.getSharedPreferences(USER_SECRET_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = manager.edit();

            editor.putString("password", newPassword);
            editor.apply();
        }
    }

    public static void updateBirthDaySharedPreferences(Context context, Long newBirthDay){
        if(userHasBeenLoaded(context)) {
            SharedPreferences manager = context.getSharedPreferences(USER_SECRET_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = manager.edit();

            editor.putLong("birthday", newBirthDay);
            editor.apply();
        }
    }

    public static String loadCoverPictureBase64(Context context) {
        String photoBase64 = null;

        if (userHasBeenLoaded(context)) {
            SharedPreferences manager = context.getSharedPreferences(USER_SECRET_DATA, Context.MODE_PRIVATE);
            photoBase64 = manager.getString("photo_cover", null);
        }

        return photoBase64;
    }

    public static Long loadBirthDayUser(Context context) {
        Long birhtdayLong = null;
        if (userHasBeenLoaded(context)) {
            SharedPreferences manager = context.getSharedPreferences(USER_SECRET_DATA, Context.MODE_PRIVATE);
             birhtdayLong = manager.getLong("birthday", 0L);
        }
        return birhtdayLong;
    }

    public static String codecSHA256(String str){
        return new String(Hex.encodeHex(DigestUtils.sha256(str.getBytes())));
    }

    public static void saveCoverPicture(Context context, String encodedImage){
        if(userHasBeenLoaded(context)) {
            SharedPreferences manager = context.getSharedPreferences(USER_SECRET_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = manager.edit();

            editor.putString("photo_cover", encodedImage);
            editor.apply();
        }
    }

    public static AlertDialog makeDialog(Context context, String alertTitle, String alertMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.SWDialogTheme);

        builder.setTitle(alertTitle);
        builder.setMessage(alertMessage);

        builder.setPositiveButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startSignInActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
