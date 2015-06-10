package br.edu.ifce.swappers.swappers.webservice;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by francisco on 29/05/15.
 */
public class UserService {

    private static final String URL_SERVICE = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login";

    public static int registerUserWithWS(String name, String email, String pwd) {
        int status_code = 0;
            try {
                URL url = new URL(URL_SERVICE);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("username", name);
                jsonParam.put("email", email);
                jsonParam.put("password", pwd);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestProperty("Content-Type", "application/json");
                conn.connect();

                OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
                os.write(jsonParam.toString());
                os.flush();

                status_code = conn.getResponseCode();

                //InputStream response = conn.getInputStream();
                //String jsonReply = convertStreamToString(response);
                //Log.i("POST-RESPONSE", conn.getHeaderField("Location"));

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
}
