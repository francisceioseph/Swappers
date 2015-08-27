package br.edu.ifce.swappers.swappers.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import br.edu.ifce.swappers.swappers.model.User;


/**
 * Created by FAMILIA on 10/06/2015.
 */
public class AndroidUtils {
    public final static String SELECTED_BOOK_ID = "SELECTED_BOOK";

    public final static int BOOK_DONATION_INTENT_CODE = 1991;
    public final static int BOOK_ADOPTION_INTENT_CODE = 1993;
    public final static String BOOK_INTENT_CODE_ID = "INTENT_CODE";

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


}
