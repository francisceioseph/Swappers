package br.edu.ifce.swappers.swappers.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by FAMILIA on 10/06/2015.
 */
public class AndroidUtils {

    public final static int BOOK_DONATION_INTENT_CODE = 1991;
    public final static int BOOK_ADOPTION_INTENT_CODE = 1993;
    public final static String BOOK_INTENT_CODE_ID = "INTENT_CODE";

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
}
