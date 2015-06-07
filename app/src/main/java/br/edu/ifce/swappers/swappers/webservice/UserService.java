package br.edu.ifce.swappers.swappers.webservice;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by francisco on 29/05/15.
 */
public enum UserService {
    INSTANCE;

    private static int status;
    private static final String URL = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login";

    public static int registerUserWithWS(String name, String email, String pwd, final Context context) {
        SyncHttpClient client = new SyncHttpClient();

        StringEntity entity = fillParamUser(name, email, pwd);
        client.post(context.getApplicationContext(), URL, entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 201) {
                    Log.i("RESPONSE",headers.toString());
                    status = statusCode;
                } else {
                    status = statusCode;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                status = statusCode;
                Log.i("RESPOSTA", error.toString());
                Log.i("RESPOSTA", String.valueOf(statusCode));

            }
        });
        return status;
    }

    private static StringEntity  fillParamUser(String name, String email,String pwd) {
        JSONObject  jsonParams = new JSONObject ();

        try {
            jsonParams.put("username", name);
            jsonParams.put("email", email);
            jsonParams.put("password", pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
            entity.setContentType("application/json");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return entity;
    }

}
