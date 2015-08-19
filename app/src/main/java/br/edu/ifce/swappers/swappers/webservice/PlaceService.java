package br.edu.ifce.swappers.swappers.webservice;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.swappers.swappers.model.Place;

/**
 * Created by gracyane e joamila on 19/08/2015.
 */
public class PlaceService {

    private static final String URL = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/place";

    public static List<Place> getPlaceWS(String city, String states){
        URL url = null;
        HttpURLConnection conn = null;
        List<Place> placeList=null;

        try {
            String urlPlace = buildURLtoGetPlace(URL,city,states);
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
                placeList = parseJsonToPlace(responseJson.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return placeList;
    }

    private static String buildURLtoGetPlace(String url, String city, String states) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("?");
        stringBuilder.append("city");
        stringBuilder.append("=");
        stringBuilder.append(URLEncoder.encode(city, "UTF-8"));
        stringBuilder.append("&");
        stringBuilder.append("states");
        stringBuilder.append("=");
        stringBuilder.append(URLEncoder.encode(states, "UTF-8"));
        return stringBuilder.toString();
    }

    private static List<Place> parseJsonToPlace(String jsonPlace) throws JSONException {

        JSONObject json = null;
        List<Place> placeList=new ArrayList<>();

        json = new JSONObject(jsonPlace);

        Log.i("TAG-PLACE",json.toString());

        return placeList;
    }
}
