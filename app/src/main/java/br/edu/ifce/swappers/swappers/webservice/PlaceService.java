package br.edu.ifce.swappers.swappers.webservice;

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
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.Place;

/**
 * Created by gracyane and joamila on 19/08/2015.
 */
public class PlaceService {

    private static final String URL = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/place";
    private static int responseCode;
    public static int getResponseCode() { return responseCode; }
    public static void setResponseCode(int resCode) { responseCode = resCode; }

    public static ArrayList<Place> getPlaceWS(String city, String states){
        URL url = null;
        HttpURLConnection conn = null;
        ArrayList<Place> placeList=null;

        try {
            String urlPlace = buildURLtoGetPlace(URL,city,states);

            url = new URL(urlPlace);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String inputLine;
                StringBuffer responseJson = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    responseJson.append(inputLine);
                }
                in.close();

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

        JSONObject jsonObject = new JSONObject(jsonPlace);
        JSONObject dataObject = jsonObject.optJSONObject("place");
        if (dataObject!=null){
            verifyJson = true;
        }else {
            verifyJson = false;
        }

        if(verifyJson){
            json = new JSONObject(jsonPlace);
            jsonItems = (JSONObject)json.get("place");

            List<Book> books = new ArrayList<Book>();
            Place placeUnique = new Place();

            placeUnique.setId(jsonItems.getInt("id"));
            placeUnique.setName(jsonItems.get("name").toString());
            placeUnique.setCity(jsonItems.get("city").toString());
            placeUnique.setDistrict(jsonItems.getString("district"));
            placeUnique.setStates(jsonItems.get("states").toString());
            placeUnique.setStreet(jsonItems.getString("street"));
            placeUnique.setNumber(jsonItems.get("number").toString());
            placeUnique.setCep(jsonItems.get("cep").toString());
            placeUnique.setHour_func(jsonItems.getString("hour_func"));
            placeUnique.setLatitude(jsonItems.getDouble("latitude"));
            placeUnique.setLongitude(jsonItems.getDouble("longitude"));
            placeUnique.setDonation(jsonItems.getInt("donation"));
            placeUnique.setRecovered(jsonItems.getInt("recovered"));
            placeUnique.setPhoto2(new String(jsonItems.getString("photo").getBytes(Charset.forName("UTF-8"))));

            if(jsonItems.has("books")&& jsonItems.toString().contains("[")){
                JSONArray arrayJson = jsonItems.getJSONArray("books");

                for (int i = 0; i < arrayJson.length(); i++) {
                    Book book= new Book();
                    book.setId(arrayJson.getJSONObject(i).getString("id"));
                    book.setAuthor(arrayJson.getJSONObject(i).getString("author"));
                    book.setPublisher(arrayJson.getJSONObject(i).getString("publisher"));
                    book.setSynopsis(arrayJson.getJSONObject(i).getString("synopsis"));
                    book.setPhoto(arrayJson.getJSONObject(i).getString("photo"));
                    book.setTitle(arrayJson.getJSONObject(i).getString("title"));
                    book.setEvaluationAvarage((float) arrayJson.getJSONObject(i).getDouble("evaluationAverage"));
                    book.setDateDonation(convertDateFromString(arrayJson.getJSONObject(i).getString("dateDonation")));
                    books.add(book);
                }
                placeUnique.setBooks(books);
            }else if(jsonItems.has("books")){
                JSONObject jsonItem = jsonItems.getJSONObject("books");
                Book book= new Book();
                book.setId(jsonItem.getString("id"));
                book.setAuthor(jsonItem.getString("author"));
                book.setPublisher(jsonItem.getString("publisher"));
                book.setSynopsis(jsonItem.getString("synopsis"));
                book.setPhoto(jsonItem.getString("photo"));
                book.setTitle(jsonItem.getString("title"));
                book.setEvaluationAvarage((float) jsonItem.getDouble("evaluationAverage"));
                book.setDateDonation(convertDateFromString(jsonItem.getString("dateDonation")));
                books.add(book);

                placeUnique.setBooks(books);
            }

            placeList.add(placeUnique);

        } else if(!jsonPlace.isEmpty() &&!verifyJson){
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
                        book.setDateDonation(convertDateFromString(arrayBook.getJSONObject(j).getString("dateDonation")));
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
                    book.setDateDonation(convertDateFromString(jsonItem.getString("dateDonation")));
                    books.add(book);

                    place.setBooks(books);
                }

                placeList.add(place);
            }
        }
        return placeList;
    }

    private static Date convertDateFromString(String dateStr){
        Date birthDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            birthDate = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return birthDate;
    }
}