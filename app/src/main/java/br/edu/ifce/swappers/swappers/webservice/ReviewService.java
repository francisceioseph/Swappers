package br.edu.ifce.swappers.swappers.webservice;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.edu.ifce.swappers.swappers.model.Review;
import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.util.JsonUtil;

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
}
