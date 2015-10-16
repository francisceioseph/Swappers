package br.edu.ifce.swappers.swappers.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.model.Review;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.JsonUtil;

/**
 * Created by francisco on 14/10/15.
 */
public class ReviewService {
    private final static String URL = "http://swappersws-oliv.rhcloud.com/swappersws/swappersws/book/review";

    public static int uploadReview(Review review) {
        int status_code = 0;

        try {
            java.net.URL reviewUrl = new URL(ReviewService.URL);
            String reviewJson = JsonUtil.parseToJson(review);
            status_code = JsonUtil.sendToServer(reviewUrl, reviewJson);

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }

        return status_code;
    }

    public static ArrayList<Review> getReviewByBookId(String book_id){
        String reviewURL = URL + "/" + book_id;
        String jsonString = null;
        ArrayList<Review> reviews = null;

        try {
            java.net.URL url = new URL(reviewURL);
            jsonString = JsonUtil.retrieveFromServer(url);
            JSONObject reviewObject = new JSONObject(jsonString);
            Object object = reviewObject.get("review");

            if (JSONArray.class.isInstance(object)){
                String jsonReviews = reviewObject.get("review").toString();
                Type collectionType = new TypeToken<ArrayList<Review>>(){}.getType();

                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
                        .create();

                reviews = gson.fromJson(jsonReviews, collectionType);

            }
            else if (JSONObject.class.isInstance(object)) {
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
                        .create();

                Review review = gson.fromJson(((JSONObject) object).toString(), Review.class);
                reviews = new ArrayList<>();
                reviews.add(review);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            reviews = null;
        }

        return reviews;
    }
}
