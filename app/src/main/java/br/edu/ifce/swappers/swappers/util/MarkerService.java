package br.edu.ifce.swappers.swappers.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import br.edu.ifce.swappers.swappers.model.Place;

/**
 * Created by Joamila on 24/08/2015.
 */
public class MarkerService {
    private static final String URL = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/place";

    public static Place getDetailPlaceWS(double latitude, double longitude){
        java.net.URL url = null;
        HttpURLConnection conn = null;
        Place placeInformation = new Place();

        try {
            String urlPlace = buildURLtoGetDetailPlace(URL, String.valueOf(latitude), String.valueOf(longitude));
            Log.i("URLPLACE", urlPlace);

            url = new URL(urlPlace);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            int responseCode = conn.getResponseCode();
            Log.i("STATUS-CODE", String.valueOf(responseCode));
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String inputLine;
                StringBuffer responseJson = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    responseJson.append(inputLine);
                }
                in.close();
                Log.i("RESPONSE-PLACE", responseJson.toString());

                placeInformation = parseJsonToDetailPlace(responseJson.toString());

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            //e.printStackTrace();
            e.getCause().toString();
        } catch (ConnectException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return placeInformation;
    }

    private static String buildURLtoGetDetailPlace(String url, String latitude, String longitude) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("?");
        stringBuilder.append("latitude");
        stringBuilder.append("=");
        stringBuilder.append(URLEncoder.encode(latitude, "UTF-8"));
        stringBuilder.append("&");
        stringBuilder.append("longitude");
        stringBuilder.append("=");
        stringBuilder.append(URLEncoder.encode(longitude, "UTF-8"));
        return stringBuilder.toString();
    }

    private static Place parseJsonToDetailPlace(String jsonPlace) throws JSONException {
        JSONObject json;
        JSONObject jsonItems;
        Place placeInformation = new Place();

        json = new JSONObject(jsonPlace);
        jsonItems = (JSONObject)json.get("place");

        placeInformation.setId(jsonItems.getInt("id"));
        placeInformation.setName(jsonItems.get("nome").toString());
        placeInformation.setStates(jsonItems.getString("states"));
        placeInformation.setCity(jsonItems.getString("city"));
        placeInformation.setDistrict(jsonItems.getString("district"));
        placeInformation.setStreet(jsonItems.getString("street"));
        placeInformation.setNumber(jsonItems.getString("number"));
        placeInformation.setCep(jsonItems.getString("cep"));
        placeInformation.setHour_func(jsonItems.getString("hour_func"));
        placeInformation.setLatitude(jsonItems.getDouble("latitude"));
        placeInformation.setLongitude(jsonItems.getDouble("longitude"));
        //placeInformation.setPhoto(jsonItems.get("photo").toString());

        return placeInformation;
    }
}
