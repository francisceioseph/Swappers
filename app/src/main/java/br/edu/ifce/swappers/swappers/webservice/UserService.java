package br.edu.ifce.swappers.swappers.webservice;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.util.ImageUtil;

/**
 * Created by francisco on 29/05/15.
 */
public class UserService {

    private static final String URL_REGISTER_SERVICE = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login";
    private static final String URL_LOGIN_SERVICE    = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login/dologin";
    private static final String URL_GET_USER_SERVICE = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login/users";

    public static int registerUserWithWS(User user) {
        int status_code = 0;
            try {
                URL url = new URL(URL_REGISTER_SERVICE);

                JSONObject jsonParam = fillParamJson(user);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(25000);
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

    private static JSONObject fillParamJson(User user) throws JSONException, UnsupportedEncodingException {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("username", user.getName());
        jsonParam.put("email", user.getEmail());
        jsonParam.put("password", user.getPassword());
        jsonParam.put("photo2", user.getPhoto2());

        return jsonParam;
    }

    public static boolean checkLoginWS(String email, String pwd){
        boolean isAllowedLogin = false;
        URL url =null;
        HttpURLConnection conn = null;

        try {
            String urlLogin = buildURLtoLogin(URL_LOGIN_SERVICE,email,pwd);
            url = new URL(urlLogin);

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

                JSONObject jsonObject = new JSONObject(response.toString());
                isAllowedLogin = jsonObject.getBoolean("status");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return isAllowedLogin;
    }

    public static String buildURLtoLogin(String url, String email, String pwd){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("?");
        stringBuilder.append("email");
        stringBuilder.append("=");
        stringBuilder.append(email);
        stringBuilder.append("&");
        stringBuilder.append("password");
        stringBuilder.append("=");
        stringBuilder.append(pwd);

        return stringBuilder.toString();
    }

    public static User getUserFromWS(String email, String password) {
        User user = null;

        URL url = null;
        HttpURLConnection conn = null;

        try {
            String urlLogin = buildURLtoLogin(URL_GET_USER_SERVICE,email,password);
            url = new URL(urlLogin);

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

                Log.i("USER-LOGIN-TAG-AWASOME", response.toString());
                JSONObject jsonObject = new JSONObject(response.toString());
                user = parseUserFromJSON(jsonObject);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }


        return user;
    }

    private static User parseUserFromJSON(JSONObject jsonObject) throws JSONException{
        User user = new User();
        String codedPhoto = jsonObject.getString("photo2");
        Log.i("USER-LOGIN-TAG-AWASOME", codedPhoto);


        user.setPhoto2(codedPhoto);
        user.setId(jsonObject.getInt("id"));
        user.setName(jsonObject.getString("username"));
        user.setEmail(jsonObject.getString("email"));
        user.setPassword(jsonObject.getString("password"));

        return user;
    }
}
