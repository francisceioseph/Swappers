package br.edu.ifce.swappers.swappers.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.miscellaneous.utils.JsonUtil;
import br.edu.ifce.swappers.swappers.model.Notification;

/**
 * Created by gracyaneoliveira on 28/01/2016.
 */
public class NotificationService {

    private final static String URL = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/timeline";

    public static ArrayList<Notification> getNotifications(String state){
        String notificationURL = buildURL(URL, state);
        String jsonString = null;
        ArrayList<Notification> notifications = null;

        try {
            java.net.URL url = new URL(notificationURL);
            jsonString = JsonUtil.retrieveFromServer(url);
            JSONObject reviewObject = new JSONObject(jsonString);
            Object object = reviewObject.get("notification");

            if (JSONArray.class.isInstance(object)){
                String jsonNotification = reviewObject.get("notification").toString();
                Type collectionType = new TypeToken<ArrayList<Notification>>(){}.getType();

                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
                        .create();

                notifications = gson.fromJson(jsonNotification, collectionType);

            }
            else if (JSONObject.class.isInstance(object)) {
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
                        .create();

                Notification notification = gson.fromJson(((JSONObject) object).toString(), Notification.class);
                notifications = new ArrayList<>();
                notifications.add(notification);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            notifications = null;
        }

        return notifications;
    }

    private static String buildURL(String url, String state) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("/");
        try {
            stringBuilder.append(URLEncoder.encode(state, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
