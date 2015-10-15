package br.edu.ifce.swappers.swappers.webservice;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.model.Book;

/**
 * Created by gracyaneoliveira on 15/10/2015.
 */
public class StatisticBookService {

    private static final String URL ="http://swappersws-oliv.rhcloud.com/swappersws/swappersws/book/statistic";

    public static ArrayList<Book> getBestBooksCurrentMonth() {
        ArrayList<Book> bookList=new ArrayList<Book>();
        URL url = null;
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

                JSONObject jsonObject = new JSONObject(responseJson.toString());
                bookList = parseUserFromJSON(jsonObject);
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
        return bookList;
    }

    private static ArrayList<Book> parseUserFromJSON(JSONObject jsonObject) throws JSONException {
        ArrayList<Book> books = new ArrayList<>();

        Object object = jsonObject.get("book");

        if (JSONArray.class.isInstance(object)){
            String jsonBook = jsonObject.get("book").toString();
            Type collectionType = new TypeToken<ArrayList<Book>>(){}.getType();
            books = new Gson().fromJson(jsonBook, collectionType);

        }else if (JSONObject.class.isInstance(object)){
            String jsonBook = jsonObject.get("book").toString();
            Book book = new Gson().fromJson(jsonBook, Book.class);
            books.add(book);
        }

        return books;
    }

}
