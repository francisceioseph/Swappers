package br.edu.ifce.swappers.swappers.util;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import br.edu.ifce.swappers.swappers.model.User;

/**
 * Created by francisco on 14/10/15.
 */
public class JsonUtil {
    public static String parseToJson(Object object) throws JSONException, UnsupportedEncodingException {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static int sendToServer(URL url, String jsonParam) throws IOException {
        int status_code = 0;

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

        return status_code;
    }

    public static String retrieveFromServer(URL url) throws IOException {

        HttpURLConnection conn;
        String parsedResponse = null;

        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        conn.setRequestProperty("Content-Type", "application/json");
        conn.connect();

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            parsedResponse = response.toString();
        }

        return parsedResponse;
    }
}
