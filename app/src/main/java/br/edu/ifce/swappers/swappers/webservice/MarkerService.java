package br.edu.ifce.swappers.swappers.webservice;

import android.util.Log;

import org.json.JSONArray;
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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.swappers.swappers.model.Book;
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
        JSONObject jsonItemsBook;
        JSONArray jsonArray;
        Place placeInformation = new Place();

        List<Book> books = new ArrayList<Book>();

        json = new JSONObject(jsonPlace);

        placeInformation.setId(json.getInt("id"));
        placeInformation.setName(json.getString("name"));
        placeInformation.setStates(json.getString("states"));
        placeInformation.setCity(json.getString("city"));
        placeInformation.setDistrict(json.getString("district"));
        placeInformation.setStreet(json.getString("street"));
        placeInformation.setNumber(json.getString("number"));
        placeInformation.setCep(json.getString("cep"));
        placeInformation.setHour_func(json.getString("hour_func"));
        placeInformation.setLatitude(json.getDouble("latitude"));
        placeInformation.setLongitude(json.getDouble("longitude"));
        placeInformation.setDonation(json.getInt("donation"));
        placeInformation.setRecovered(json.getInt("recovered"));
        placeInformation.setPhoto2(new String(json.getString("photo").getBytes(Charset.forName("UTF-8"))));

        Log.i("PHOTO-BYTES-PLACE", String.valueOf(json.getString("photo").getBytes(Charset.forName("UTF-8"))));
        Log.i("PHOTO-BYTES-PLACE",new String(placeInformation.getPhoto2()));

        if(json.has("books")){
            if(json.toString().contains("[")){
                jsonArray = json.getJSONArray("books");
                for (int i = 0; i<jsonArray.length();i++){
                    Book book= new Book();
                    book.setId(jsonArray.getJSONObject(i).getString("id"));
                    book.setAuthor(jsonArray.getJSONObject(i).getString("author"));
                    book.setPublisher(jsonArray.getJSONObject(i).getString("publisher"));
                    book.setSynopsis(jsonArray.getJSONObject(i).getString("synopsis"));
                    book.setPhoto(jsonArray.getJSONObject(i).getString("photo"));
                    book.setTitle(jsonArray.getJSONObject(i).getString("title"));
                    book.setEvaluationAvarage((float) jsonArray.getJSONObject(i).getDouble("evaluationAverage"));
                    books.add(book);
                }
            }else{
                JSONObject jsonItems = json.getJSONObject("books");
                Book book= new Book();
                book.setId(jsonItems.getString("id"));
                book.setAuthor(jsonItems.getString("author"));
                book.setPublisher(jsonItems.getString("publisher"));
                book.setSynopsis(jsonItems.getString("synopsis"));
                book.setPhoto(jsonItems.getString("photo"));
                book.setTitle(jsonItems.getString("title"));
                book.setEvaluationAvarage((float) jsonItems.getDouble("evaluationAverage"));
                books.add(book);
            }
            placeInformation.setBooks(books);
        }
        return placeInformation;
    }
}
