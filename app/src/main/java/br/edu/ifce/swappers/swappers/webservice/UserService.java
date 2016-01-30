package br.edu.ifce.swappers.swappers.webservice;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.AndroidUtils;

/**
 * Created by francisco on 29/05/15.
 */
public class UserService {

    private static final String URL_REGISTER_SERVICE = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login";
    private static final String URL_LOGIN_SERVICE    = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login/dologin";
    private static final String URL_GET_USER_SERVICE = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login/users";
    private static final String URL_UPDATE_PWD_SERVICE = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login/update/pwd";
    private static final String URL_UPDATE_BIRTHDAY_SERVICE = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login/update/birthday";
    private static final String URL_UPDATE_COVER_SERVICE = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login/update/cover";
    private static final String URL_UPDATE_PHOTO_PROFILE_SERVICE = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login/update/photoperfil";
    private static final String URL_DELETE_USER_SERVICE = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login/delete";
    private static final String URL_UPDATE_USER_LOCATION_SERVICE = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/login/update/location";

    public static int registerUserWithWS(Context context,User user) {
        int status_code = 0;
            try {
                URL url = new URL(URL_REGISTER_SERVICE);

                JSONObject jsonParam = fillParamJson(user);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(25000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestProperty("Content-Type", "application/json");
                conn.connect();

                OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
                os.write(jsonParam.toString());
                os.flush();

                status_code = conn.getResponseCode();
                Log.i("STATUSCODE",String.valueOf(status_code));
                if(status_code==HttpURLConnection.HTTP_CREATED){
                    user.setId(getIdFromLocation(conn.getHeaderField("Location")));
                    AndroidUtils.createUser(context, user);
                }
                MockSingleton.INSTANCE.user = user;
                MockSingleton.INSTANCE.user.setId(getIdFromLocation(conn.getHeaderField("Location")));

                os.close();
                conn.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return status_code;
    }

    public static int getIdFromLocation(String location){
        String string[] = location.split("/");
        int idUser = Integer.valueOf(string[string.length-1]);
        return idUser;
    }

    private static JSONObject fillParamJson(User user) throws JSONException, UnsupportedEncodingException {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("username", user.getName());
        jsonParam.put("email", user.getEmail());
        jsonParam.put("password", user.getPassword());
        jsonParam.put("photo2", user.getPhoto2());

        return jsonParam;
    }

    public static boolean checkLoginWS(String email, String pwd){
        boolean isAllowedLogin = false;
        URL url =null;
        HttpURLConnection conn = null;

        try {
            String urlLogin = buildURLtoLogin(URL_LOGIN_SERVICE,email,pwd);
            url = new URL(urlLogin);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonObject = new JSONObject(response.toString());
                isAllowedLogin = jsonObject.getBoolean("status");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return isAllowedLogin;
    }

    public static String buildURLtoLogin(String url, String email, String pwd){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("?");
        stringBuilder.append("email");
        stringBuilder.append("=");
        stringBuilder.append(email);
        stringBuilder.append("&");
        stringBuilder.append("password");
        stringBuilder.append("=");
        stringBuilder.append(pwd);

        return stringBuilder.toString();
    }

    public static User getUserFromWS(String email, String password) {
        User user = null;

        URL url = null;
        HttpURLConnection conn = null;

        try {
            String urlLogin = buildURLtoLogin(URL_GET_USER_SERVICE,email,password);
            Log.i("INFO-LOGIN", urlLogin);
            url = new URL(urlLogin);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            int responseCode = conn.getResponseCode();
            Log.i("INFO-LOGIN", String.valueOf(responseCode));
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                Log.i("INFO-LOGIN",response.toString());
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                Log.i("USER-LOGIN-TAG-AWASOME", response.toString());
                JSONObject jsonObject = new JSONObject(response.toString());
                user = parseUserFromJSON(jsonObject);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }


        return user;
    }

    public static int updateCoverUserService(User user) {
        int status_code = 0;
        try {
            URL url = new URL(URL_UPDATE_COVER_SERVICE);

            JSONObject jsonParam = fillParamJsonToCoverUpdate(user);
            Log.i("#COVER", jsonParam.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("PUT");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
            os.write(jsonParam.toString());
            os.flush();

            status_code = conn.getResponseCode();

            os.close();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status_code;
    }

    public static int updatePhotoProfileUserService(User user) {
        int status_code = 0;
        try {
            URL url = new URL(URL_UPDATE_PHOTO_PROFILE_SERVICE);

            JSONObject jsonParam = fillParamJsonToProfileUpdate(user);
            Log.i("#COVER", jsonParam.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("PUT");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
            os.write(jsonParam.toString());
            os.flush();

            status_code = conn.getResponseCode();

            os.close();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status_code;
    }

    public static int updatePwdUserService(User user) {
        int status_code = 0;
        try {
            URL url = new URL(URL_UPDATE_PWD_SERVICE);

            JSONObject jsonParam = fillParamJsonToUpdate(user);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("PUT");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
            os.write(jsonParam.toString());
            os.flush();

            status_code = conn.getResponseCode();

            os.close();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status_code;
    }

    public static int updateBirthDayUserService(User user) {
        int status_code = 0;
        try {
            URL url = new URL(URL_UPDATE_BIRTHDAY_SERVICE);

            JSONObject jsonParam =  fillParamJsonToBirthdayUpdate(user);
            Log.i("BIRTHDAY", jsonParam.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("PUT");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
            os.write(jsonParam.toString());
            os.flush();

            status_code = conn.getResponseCode();

            os.close();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status_code;
    }

    public static int updateCityStateUserService(User user) {
        int status_code = 0;
        try {
            URL url = new URL(URL_UPDATE_USER_LOCATION_SERVICE);

            JSONObject jsonParam =  fillParamJsonToCityStateUpdate(user);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("PUT");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
            os.write(jsonParam.toString());
            os.flush();

            status_code = conn.getResponseCode();

            os.close();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status_code;
    }

    public static int deleteUserService(int idUser) {
        int status_code = 0;

        String urlDelete = URL_DELETE_USER_SERVICE + "/"+idUser;
        Log.i("#URL_DELETE",urlDelete);

        try {
            URL url = new URL(urlDelete);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("DELETE");
            conn.connect();

            status_code = conn.getResponseCode();

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status_code;
    }

    private static JSONObject fillParamJsonToUpdate(User user) throws JSONException, UnsupportedEncodingException {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("username", user.getName());
        jsonParam.put("email", user.getEmail());
        jsonParam.put("password", user.getPassword());

        return jsonParam;
    }

    private static JSONObject fillParamJsonToBirthdayUpdate(User user) throws JSONException, UnsupportedEncodingException {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("email", user.getEmail());
        jsonParam.put("birthday", user.getBirthday());
        return jsonParam;
    }

    private static JSONObject fillParamJsonToCoverUpdate(User user) throws JSONException, UnsupportedEncodingException {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("email", user.getEmail());
        jsonParam.put("cover", user.getCover());
        return jsonParam;
    }

    private static JSONObject fillParamJsonToProfileUpdate(User user) throws JSONException, UnsupportedEncodingException {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("email", user.getEmail());
        jsonParam.put("photo2", user.getPhoto2());
        return jsonParam;
    }

    private static JSONObject fillParamJsonToCityStateUpdate(User user) throws JSONException, UnsupportedEncodingException {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("email", user.getEmail());
        jsonParam.put("city", user.getCity());
        jsonParam.put("state", user.getState());
        return jsonParam;
    }

    private static User parseUserFromJSON(JSONObject jsonObject) throws JSONException{
        User user = new User();
        String codedPhoto = jsonObject.getString("photo2");

        user.setPhoto2(codedPhoto);
        user.setId(jsonObject.getInt("id"));
        user.setName(jsonObject.getString("username"));
        user.setEmail(jsonObject.getString("email"));
        user.setPassword(jsonObject.getString("password"));

        if(jsonObject.has("birthday")){
            user.setBirthday(jsonObject.getLong("birthday"));
        }

        if(jsonObject.has("cover")){
            user.setCover(jsonObject.getString("cover"));
        }

        if(jsonObject.has("booksDonation") && jsonObject.get("booksDonation").toString().contains("[")){
            String jsonBooks = jsonObject.get("booksDonation").toString();
            Type collectionType = new TypeToken<ArrayList<Book>>(){}.getType();
            ArrayList<Book> books = new Gson().fromJson(jsonBooks, collectionType);
            user.setBookDonationList(books);
        }else if(jsonObject.has("booksDonation")){
            String jsonBooks = jsonObject.get("booksDonation").toString();
            Book book = new Gson().fromJson(jsonBooks, Book.class);
            ArrayList<Book> bookArrayList = new ArrayList<>();
            bookArrayList.add(book);
            user.setBookDonationList(bookArrayList);
        }else{
            user.setBookDonationList(new ArrayList<Book>());
        }
        if(jsonObject.has("booksRetrieved") && jsonObject.get("booksRetrieved").toString().contains("[")){
            String jsonBooks = jsonObject.get("booksRetrieved").toString();
            Type collectionType = new TypeToken<ArrayList<Book>>(){}.getType();
            ArrayList<Book> books = new Gson().fromJson(jsonBooks, collectionType);
            user.setBookRetrievedList(books);
        }else if(jsonObject.has("booksRetrieved")){
            String jsonBooks = jsonObject.get("booksRetrieved").toString();
            Book book = new Gson().fromJson(jsonBooks, Book.class);
            ArrayList<Book> bookArrayList = new ArrayList<>();
            bookArrayList.add(book);
            user.setBookRetrievedList(bookArrayList);
        }else{
            user.setBookRetrievedList(new ArrayList<Book>());
        }

        if(jsonObject.has("booksFavorite") && jsonObject.get("booksFavorite").toString().contains("[")){
            String jsonBooks = jsonObject.get("booksFavorite").toString();
            Type collectionType = new TypeToken<ArrayList<Book>>(){}.getType();
            ArrayList<Book> books = new Gson().fromJson(jsonBooks, collectionType);
            user.setBookFavoriteList(books);
        }else if(jsonObject.has("booksFavorite")){
            String jsonBooks = jsonObject.get("booksFavorite").toString();
            Book book = new Gson().fromJson(jsonBooks, Book.class);
            ArrayList<Book> bookArrayList = new ArrayList<>();
            bookArrayList.add(book);
            user.setBookFavoriteList(bookArrayList);
        }else{
            user.setBookFavoriteList(new ArrayList<Book>());
        }

        return user;
    }
}
