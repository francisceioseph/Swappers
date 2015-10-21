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

    public static void createUser(Context context, User user){
        SharedPreferences manager = context.getSharedPreferences(USER_SECRET_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = manager.edit();

        int coverPhotoDrawableID  = ImageUtil.getRandomCoverDrawableID();
        Bitmap coverPhotoDrawable = ((BitmapDrawable) ContextCompat.getDrawable(context, coverPhotoDrawableID)).getBitmap();
        String coverPhotoBase64   = ImageUtil.BitMapToString(coverPhotoDrawable);

        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());
        editor.putString("photo", user.getPhoto2());
        editor.putString("photo_cover", coverPhotoBase64);
        editor.putString("name", user.getName());
        editor.putInt("id", user.getId());

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
        }

        return user;
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

    public static String loadCoverPictureBase64(Context context) {
        String photoBase64 = null;

        if (userHasBeenLoaded(context)) {
            SharedPreferences manager = context.getSharedPreferences(USER_SECRET_DATA, Context.MODE_PRIVATE);
            photoBase64 = manager.getString("photo_cover", null);
        }

        return photoBase64;
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
