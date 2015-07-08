package br.edu.ifce.swappers.swappers.webservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.swappers.swappers.model.Book;

/**
 * Created by FAMÍLIA on 06/07/2015.
 */
public class BookService {

    private static final String URL_GOOGLE_BOOKS_SERVICE = "https://www.googleapis.com/books/v1/volumes?q=";

    public static ArrayList<Book> getBooksByTitleWS(String title) {
        ArrayList<Book> bookList=null;
        URL url = null;
        HttpURLConnection conn = null;
        String urlGoogleBooks;

        try {
            urlGoogleBooks = buildURLSearchByTitle(URL_GOOGLE_BOOKS_SERVICE, title);
            url = new URL(urlGoogleBooks);

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

                bookList = parseJsonToBook(responseJson.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return bookList;
    }

    //https://www.googleapis.com/books/v1/volumes?q=estrelas&maxResults=40&orderBy=relevance
    //https://www.googleapis.com/books/v1/volumes?q=a+voz+das+estrelas&projection=full&maxResults=40&orderBy=relevance&printType=books
    private static String buildURLSearchByTitle(String url, String title) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);

        String tl[] = title.split(" ");

        if (tl.length > 1) {
            for (int i = 0; i < tl.length; i++) {
                stringBuilder.append(tl[i]);
                stringBuilder.append("+");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        } else {
            stringBuilder.append(title);
        }

        stringBuilder.append("&projection=full&maxResults=30&orderBy=relevance&printType=books");
        return stringBuilder.toString();
    }


    private static ArrayList<Book> parseJsonToBook(String jsonBooks){
        JSONObject json = null;
        String title="";
        String subtitle="";
        String id="";
        String desc="";
        String publisher="";
        String date="";
        String auth="desconhecido";
        String photoLink="";
        double rating=0.0;
        int page=0;

        ArrayList<Book> bookList = new ArrayList<>();
        try {
            json = new JSONObject(jsonBooks);

        JSONArray jsonArray = json.getJSONArray("items");
        for(int i =0; i<jsonArray.length();i++ ){
            JSONObject jsonItems = jsonArray.getJSONObject(i);
            Book book = new Book();
            JSONObject volumeInfo = jsonItems.getJSONObject("volumeInfo");

            book.setId(jsonItems.getString("id"));

            if(volumeInfo.has("title")){
                title = volumeInfo.getString("title");
                book.setTitle(title);
            }else{book.setTitle(title);}

            if(volumeInfo.has("subtitle")){
                subtitle = volumeInfo.getString("subtitle");
                book.setTitle(title + " - " + subtitle);
            }

            if(volumeInfo.has("publisher")){
                publisher = volumeInfo.getString("publisher");
                book.setPublisher(publisher);
            }else{book.setPublisher(publisher);}

            if(volumeInfo.has("description")){
                desc = volumeInfo.getString("description");
                book.setDescription(desc);
            }else{book.setDescription(desc);}

            if(volumeInfo.has("averageRating")){
                rating = volumeInfo.getDouble("averageRating");
                book.setEvaluationAvarage((float) rating);
            }else{book.setEvaluationAvarage((float) rating);}

            if(volumeInfo.has("authors")){
                JSONArray authors = volumeInfo.getJSONArray("authors");
                auth = "";
                if(authors.length()>1){
                    for(int k=0;k<authors.length();k++){
                        auth = auth + authors.getString(k) + " e ";
                    }
                    book.setAuthor(auth.substring(0, auth.length()-2));
                }else{
                    book.setAuthor(authors.getString(0));
                }
            }else{
                book.setAuthor(auth);
            }

            if(volumeInfo.has("imageLinks")){
                JSONObject imageLink = volumeInfo.getJSONObject("imageLinks");
                book.setPhoto(imageLink.getString("smallThumbnail"));
            }else{book.setPhoto(photoLink);}

            if(volumeInfo.has("pageCount")){
                page = volumeInfo.getInt("pageCount");
                System.out.println("Páginas: " + page);
                book.setNumberPage(page);
            }else{book.setNumberPage(page);}

            if(volumeInfo.has("publishedDate")){
                date = volumeInfo.getString("publishedDate");
                System.out.println("Data: " + date);
                book.setDatePublisher(date);
            }else{book.setDatePublisher(date);}

            bookList.add(book);
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bookList;
    }
}