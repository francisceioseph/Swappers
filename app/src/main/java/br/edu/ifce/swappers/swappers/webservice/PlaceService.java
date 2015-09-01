package br.edu.ifce.swappers.swappers.webservice;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.util.SwappersToast;

/**
 * Created by gracyane and joamila on 19/08/2015.
 */
public class PlaceService {

    private static final String URL = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/place";

    public static ArrayList<Place> getPlaceWS(String city, String states){
        URL url = null;
        HttpURLConnection conn = null;
        ArrayList<Place> placeList=null;

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
                if(responseJson != null)
                    placeList = parseJsonToPlace(responseJson.toString());
                else {
                    Place placeDefault = new Place();
                    placeList.add(placeDefault);
                }
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


    private static ArrayList<Place> parseJsonToPlace(String jsonPlace) throws JSONException {
        JSONArray jsonArray = null;
        JSONObject json = null;
        JSONObject jsonItems = null;
        ArrayList<Place> placeList = new ArrayList<>();
        int count = 0;
        boolean verifyJson = false;
        /**
        for(int i=0; i<jsonPlace.length(); i++){
            if(jsonPlace.charAt(i) == '}') count++;
        }

        Log.i("tagSIZE", String.valueOf(count));
       **/
        JSONObject jsonObject = new JSONObject(jsonPlace);
        JSONObject dataObject = jsonObject.optJSONObject("place");
        if (dataObject!=null){
            verifyJson = true;
        }else {
            verifyJson = false;
        }

        //if(!jsonPlace.isEmpty() && count == 4){
        if(verifyJson){
            json = new JSONObject(jsonPlace);
            jsonItems = (JSONObject)json.get("place");

            List<Book> books = new ArrayList<Book>();
            Place placeUnique = new Place();

            placeUnique.setId(jsonItems.getInt("id"));
            placeUnique.setName(jsonItems.get("name").toString());
            placeUnique.setCity(jsonItems.get("city").toString());
            placeUnique.setLatitude(jsonItems.getDouble("latitude"));
            placeUnique.setLongitude(jsonItems.getDouble("longitude"));
            placeUnique.setDonation(jsonItems.getInt("donation"));
            placeUnique.setRecovered(jsonItems.getInt("recovered"));
            placeList.add(placeUnique);

        } else if(!jsonPlace.isEmpty() && count > 4){
            json = new JSONObject(jsonPlace);
            jsonArray = json.getJSONArray("place");
            for(int i =0; i<jsonArray.length();i++ ){
                Place place = new Place();
                List<Book> books = new ArrayList<Book>();
                jsonItems = jsonArray.getJSONObject(i);

                place.setId(jsonItems.getInt("id"));
                place.setCity(jsonItems.get("city").toString());
                place.setName(jsonItems.get("name").toString());
                place.setDistrict(jsonItems.getString("district"));
                place.setStates(jsonItems.get("states").toString());
                place.setStreet(jsonItems.getString("street"));
                place.setNumber(jsonItems.get("number").toString());
                place.setCep(jsonItems.get("cep").toString());
                place.setHour_func(jsonItems.getString("hour_func"));
                place.setLatitude(jsonItems.getDouble("latitude"));
                place.setLongitude(jsonItems.getDouble("longitude"));
                place.setDonation(jsonItems.getInt("donation"));
                place.setRecovered(jsonItems.getInt("recovered"));
                place.setPhoto2(new String(jsonItems.getString("photo").getBytes(Charset.forName("UTF-8"))));

                if(jsonArray.getJSONObject(i).has("books")&& jsonArray.getJSONObject(i).toString().contains("[")){
                        String arrayString = jsonArray.getJSONObject(i).getString("books");
                        JSONArray arrayBook = new JSONArray(arrayString);

                        for (int j = 0; j<arrayBook.length();j++){
                            Book book= new Book();
                            book.setId(arrayBook.getJSONObject(j).getString("id"));
                            book.setAuthor(arrayBook.getJSONObject(j).getString("author"));
                            book.setPublisher(arrayBook.getJSONObject(j).getString("publisher"));
                            book.setSynopsis(arrayBook.getJSONObject(j).getString("synopsis"));
                            book.setPhoto(arrayBook.getJSONObject(j).getString("photo"));
                            book.setTitle(arrayBook.getJSONObject(j).getString("title"));
                            book.setEvaluationAvarage((float) arrayBook.getJSONObject(j).getDouble("evaluationAverage"));
                            books.add(book);
                        }
                        place.setBooks(books);
                }else if(jsonArray.getJSONObject(i).has("books")){
                    JSONObject jsonItem = jsonArray.getJSONObject(i).getJSONObject("books");
                    Book book= new Book();
                    book.setId(jsonItem.getString("id"));
                    book.setAuthor(jsonItem.getString("author"));
                    book.setPublisher(jsonItem.getString("publisher"));
                    book.setSynopsis(jsonItem.getString("synopsis"));
                    book.setPhoto(jsonItem.getString("photo"));
                    book.setTitle(jsonItem.getString("title"));
                    book.setEvaluationAvarage((float) jsonItem.getDouble("evaluationAverage"));
                    books.add(book);

                    place.setBooks(books);
                }
                placeList.add(place);
            }
        }

        return placeList;
    }
}