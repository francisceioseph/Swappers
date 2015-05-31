package br.edu.ifce.swappers.swappers.webservice;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.games.GamesMetadata;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import br.edu.ifce.swappers.swappers.model.User;

/**
 * Created by francisco on 29/05/15.
 */
public enum UserService {
    INSTANCE;


    public static void authenticateUserWithWS(String name, String email,String pwd, final Context context){
        AsyncHttpClient client = new AsyncHttpClient();

        StringEntity entity = fillParamUser(name,email,pwd);
        client.post(context.getApplicationContext(), "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login/insert", entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 201) {
                    Toast.makeText(context.getApplicationContext(), "Seja bem vindo!", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(context.getApplicationContext(), "Senha ou usuário incorretos!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("RESPOSTA", error.toString());
                Log.i("RESPOSTA", String.valueOf(statusCode));

            }
        });
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
