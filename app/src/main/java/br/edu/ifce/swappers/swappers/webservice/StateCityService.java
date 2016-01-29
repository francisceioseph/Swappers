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
import br.edu.ifce.swappers.swappers.model.StateCity;

/**
 * Created by gracyaneoliveira on 28/01/2016.
 */
public class StateCityService {

    private final static String URL = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/statecity";

    public static ArrayList<StateCity> getStateCityList(){
        String jsonString = null;
        ArrayList<StateCity> stateCityList = null;

        try {
            java.net.URL url = new URL(URL);
            jsonString = JsonUtil.retrieveFromServer(url);
            JSONObject reviewObject = new JSONObject(jsonString);
            Object object = reviewObject.get("stateCity");

            if (JSONArray.class.isInstance(object)){
                String jsonNotification = reviewObject.get("stateCity").toString();
                Type collectionType = new TypeToken<ArrayList<StateCity>>(){}.getType();

                stateCityList = new Gson().fromJson(jsonNotification, collectionType);

            }
            else if (JSONObject.class.isInstance(object)) {

                StateCity statecity = new Gson().fromJson(((JSONObject) object).toString(), StateCity.class);
                stateCityList = new ArrayList<>();
                stateCityList.add(statecity);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            stateCityList = null;
        }

        return stateCityList;
    }

}
