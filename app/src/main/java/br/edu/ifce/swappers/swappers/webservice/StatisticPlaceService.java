package br.edu.ifce.swappers.swappers.webservice;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.model.Place;

/**
 * Created by gracyaneoliveira on 18/10/2015.
 */
public class StatisticPlaceService {

    private static final String URL ="http://swappersws-oliv.rhcloud.com/swappersws/swappersws/place/statistic";

    public static ArrayList<Place> getBestPlacesCurrentMonth(String city, String state) {
        ArrayList<Place> placeList=new ArrayList<Place>();
        URL url = null;
        HttpURLConnection conn = null;

        try {
            String urlPlace = buildURLtoGetPlace(URL,city,state);

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
                String inputLine=null;
                StringBuffer responseJson = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    responseJson.append(inputLine);
                }
                in.close();

                JSONObject jsonObject = new JSONObject(responseJson.toString());
                placeList = parseUserFromJSON(jsonObject);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return placeList;
    }

    private static String buildURLtoGetPlace(String url, String city, String state) throws UnsupportedEncodingException {
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

    private static ArrayList<Place> parseUserFromJSON(JSONObject jsonObject) throws JSONException {
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
