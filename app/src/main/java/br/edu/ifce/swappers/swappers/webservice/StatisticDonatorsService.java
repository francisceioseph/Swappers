package br.edu.ifce.swappers.swappers.webservice;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.User;

/**
 * Created by gracyaneoliveira on 28/09/2015.
 */
public class StatisticDonatorsService {

    private final static String URL = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login/donors";

    public static ArrayList<User> getStatisticDonatorsForMonthWS(String city, String state) {
        ArrayList<User> users = new ArrayList<User>();

        java.net.URL url = null;
        HttpURLConnection conn = null;

        try {
            String urlPlace = buildURLtoGetDonators(URL, city, state);
            url = new URL(urlPlace);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(30000);
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
                users = parseUserFromJSON(jsonObject);
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
        return users;
    }

    private static String buildURLtoGetDonators(String url, String city, String state) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("?");
        stringBuilder.append("city");
        stringBuilder.append("=");
        stringBuilder.append(URLEncoder.encode(city, "UTF-8"));
        stringBuilder.append("&");
        stringBuilder.append("state");
        stringBuilder.append("=");
        stringBuilder.append(URLEncoder.encode(state, "UTF-8"));
        return stringBuilder.toString();
    }


    private static ArrayList<User> parseUserFromJSON(JSONObject jsonObject) throws JSONException{
        ArrayList<User> users = new ArrayList<>();

        if(jsonObject.has("user") && jsonObject.get("user").toString().contains("[")){
            String jsonUser = jsonObject.get("user").toString();
            Type collectionType = new TypeToken<ArrayList<User>>(){}.getType();
            users = new Gson().fromJson(jsonUser, collectionType);

        }else if(jsonObject.has("user")){
            String jsonUsers = jsonObject.get("user").toString();
            User user = new Gson().fromJson(jsonUsers, User.class);
            users.add(user);
        }

        return users;
    }
}
