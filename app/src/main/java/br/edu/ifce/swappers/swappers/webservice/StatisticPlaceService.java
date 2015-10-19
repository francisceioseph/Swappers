package br.edu.ifce.swappers.swappers.webservice;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.model.Place;

/**
 * Created by gracyaneoliveira on 18/10/2015.
 */
public class StatisticPlaceService {

    private static final String URL ="http://swappersws-oliv.rhcloud.com/swappersws/swappersws/place/statistic";

    public static ArrayList<Place> getBestPlacesCurrentMonth() {
        ArrayList<Place> placeList=new ArrayList<Place>();
        java.net.URL url = null;
        HttpURLConnection conn = null;

        try {
            url = new URL(URL);
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
                StringBuffer responseJson = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    responseJson.append(inputLine);
                }
                in.close();
                Log.i("PLACES-BEST",responseJson.toString());
                JSONObject jsonObject = new JSONObject(responseJson.toString());
                placeList = parseUserFromJSON2(jsonObject);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return placeList;
    }

    private static ArrayList<Place> parseUserFromJSON(JSONObject jsonObject) throws JSONException {
        ArrayList<Place> places = new ArrayList<>();

        Object object = jsonObject.get("place");

        if (JSONArray.class.isInstance(object)){
            String jsonPlace = jsonObject.get("place").toString();
            Type collectionType = new TypeToken<ArrayList<Place>>(){}.getType();
            places = new Gson().fromJson(jsonPlace, collectionType);

        }else if (JSONObject.class.isInstance(object)){
            String jsonPlace = jsonObject.get("place").toString();
            Place place = new Gson().fromJson(jsonPlace, Place.class);
            places.add(place);
        }

        return places;
    }

    private static ArrayList<Place> parseUserFromJSON2(JSONObject jsonObject) throws JSONException {
        ArrayList<Place> places = new ArrayList<>();

        Object object = jsonObject.get("place");

        if (JSONArray.class.isInstance(object)){
            JSONArray arrayJson = jsonObject.getJSONArray("place");
            for (int i = 0; i<arrayJson.length();i++){
                Place place = new Place();
                place.setName(arrayJson.getJSONObject(i).getString("name"));
                place.setCity(arrayJson.getJSONObject(i).getString("city"));
                place.setStates(arrayJson.getJSONObject(i).getString("states"));
                place.setStreet(arrayJson.getJSONObject(i).getString("street"));
                place.setDistrict(arrayJson.getJSONObject(i).getString("district"));
                place.setNumber(arrayJson.getJSONObject(i).getString("number"));
                place.setDonation(arrayJson.getJSONObject(i).getInt("donation"));
                place.setRecovered(arrayJson.getJSONObject(i).getInt("recovered"));
                place.setPhoto2(new String(arrayJson.getJSONObject(i).getString("photo").getBytes(Charset.forName("UTF-8"))));
                places.add(place);
            }

        }else if (JSONObject.class.isInstance(object)){
                Place place = new Place();
                place.setName(jsonObject.getString("name"));
                place.setCity(jsonObject.getString("city"));
                place.setStates(jsonObject.getString("states"));
                place.setStreet(jsonObject.getString("street"));
                place.setDistrict(jsonObject.getString("district"));
                place.setNumber(jsonObject.getString("number"));
                place.setDonation(jsonObject.getInt("donation"));
                place.setRecovered(jsonObject.getInt("recovered"));
                place.setPhoto2(new String(jsonObject.getString("photo").getBytes(Charset.forName("UTF-8"))));
                places.add(place);
        }

        return places;
    }


}
