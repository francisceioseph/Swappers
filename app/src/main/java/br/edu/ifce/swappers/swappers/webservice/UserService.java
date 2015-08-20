package br.edu.ifce.swappers.swappers.webservice;

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

import br.edu.ifce.swappers.swappers.util.ImageUtil;

/**
 * Created by francisco on 29/05/15.
 */
public class UserService {

    private static final String URL_REGISTER_SERVICE = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login";
    private static final String URL_LOGIN_SERVICE = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login/dologin";

    public static int registerUserWithWS(String name, String email, String pwd,String photo) {
        int status_code = 0;
            try {
                URL url = new URL(URL_REGISTER_SERVICE);

                JSONObject jsonParam = fillParamJson(name, email, pwd, photo);

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

    private static JSONObject fillParamJson(String name, String email, String pwd,String photo) throws JSONException, UnsupportedEncodingException {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("username", name);
        jsonParam.put("email", email);
        jsonParam.put("password", pwd);

        if (!photo.equals("null")){
            jsonParam.put("photo", ImageUtil.StringToByte(photo));
        }
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
}
