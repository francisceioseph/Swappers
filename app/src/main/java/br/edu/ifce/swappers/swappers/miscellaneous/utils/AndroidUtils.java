package br.edu.ifce.swappers.swappers.miscellaneous.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import br.edu.ifce.swappers.swappers.R;
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

    public static void create(Context context, User user){
        SharedPreferences manager = context.getSharedPreferences(USER_SECRET_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = manager.edit();

        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());
        editor.putString("photo", user.getPhoto2());
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

    public static AlertDialog makeDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.SWDialogTheme);

        builder.setTitle("Alert");
        builder.setMessage(message);
        builder.setIcon(R.drawable.ic_alert);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
