package br.edu.ifce.swappers.swappers.miscellaneous.interfaces;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.model.Review;

/**
 * Created by francisco on 14/10/15.
 */

public interface RetrieveReviewsTaskInterface {
    public void onReceiveReviews(ArrayList<Review> reviews);
}
