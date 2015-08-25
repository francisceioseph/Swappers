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

    public static Place getDetailPlaceWS(int idPlace){
        java.net.URL url = null;
        HttpURLConnection conn = null;
        Place placeInformation = null;

        try {
            String urlPlace = buildURLtoGetDetailPlace(URL, idPlace);
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
                Log.i("RESPONSE-PLACE-1", responseJson.toString());

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

    private static String buildURLtoGetDetailPlace(String url, int idPlace) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("/");
        stringBuilder.append(idPlace);
        return stringBuilder.toString();
    }

    private static Place parseJsonToDetailPlace(String jsonPlace) throws JSONException {
        JSONObject json;
        JSONObject jsonItems;
        Place placeInformation = new Place();

        json = new JSONObject(jsonPlace);
        //jsonItems = (JSONObject)json.get("");
        Log.i("IDPLACE",String.valueOf(json.getInt("id")));
        Log.i("IDPLACE",String.valueOf(json.getString("name")));
        Log.i("IDPLACE",String.valueOf(json.getString("states")));
        Log.i("IDPLACE",String.valueOf(json.getString("city")));
        Log.i("IDPLACE",String.valueOf(json.getString("district")));
        Log.i("IDPLACE",String.valueOf(json.getString("street")));
        Log.i("IDPLACE",String.valueOf(json.getString("number")));
        Log.i("IDPLACE",String.valueOf(json.getString("cep")));
        Log.i("IDPLACE",String.valueOf(json.getString("hour_func")));
        Log.i("IDPLACE",String.valueOf(json.getString("latitude")));
        Log.i("IDPLACE",String.valueOf(json.getString("longitude")));

        placeInformation.setId(json.getInt("id"));
        Log.i("PONTO=", String.valueOf(1));
        placeInformation.setName(json.getString("name"));
        Log.i("PONTO=", String.valueOf(2));
        placeInformation.setStates(json.getString("states"));
        Log.i("PONTO=", String.valueOf(3));
        placeInformation.setCity(json.getString("city"));
        Log.i("PONTO=", String.valueOf(4));
        placeInformation.setDistrict(json.getString("district"));
        Log.i("PONTO=", String.valueOf(5));
        placeInformation.setStreet(json.getString("street"));
        Log.i("PONTO=", String.valueOf(6));
        placeInformation.setNumber(json.getString("number"));
        Log.i("PONTO=", String.valueOf(7));
        placeInformation.setCep(json.getString("cep"));
        Log.i("PONTO=", String.valueOf(8));
        placeInformation.setHour_func(json.getString("hour_func"));
        Log.i("PONTO=", String.valueOf(9));
        placeInformation.setLatitude(json.getDouble("latitude"));
        Log.i("PONTO=", String.valueOf(10));
        placeInformation.setLongitude(json.getDouble("longitude"));
        Log.i("PONTO=", String.valueOf(11));
        //placeInformation.setPhoto(jsonItems.get("photo").toString());
        Log.i("PONTO=", placeInformation.getName());
        return placeInformation;
    }
}
