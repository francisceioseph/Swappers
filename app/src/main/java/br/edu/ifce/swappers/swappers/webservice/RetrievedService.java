package br.edu.ifce.swappers.swappers.webservice;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.edu.ifce.swappers.swappers.model.User;

/**
 * Created by Bolsista on 04/09/2015.
 */
public class RetrievedService {

    private final static String URL = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/book/recovered";

    public static int registerRecoveredWS(User user) {
        int status_code = 0;
        try {
            java.net.URL url = new URL(URL);

            String jsonParam = fillParamJson(user);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(25000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
            os.write(jsonParam);
            os.flush();

            status_code = conn.getResponseCode();
            os.close();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status_code;
    }

    private static String fillParamJson(User user) throws JSONException, UnsupportedEncodingException {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        return json;
    }
}
