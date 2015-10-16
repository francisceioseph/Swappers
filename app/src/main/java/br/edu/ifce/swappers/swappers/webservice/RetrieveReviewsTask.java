package br.edu.ifce.swappers.swappers.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.model.Review;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.RetrieveReviewsTaskInterface;

/**
 * Created by francisco on 14/10/15.
 */
public class RetrieveReviewsTask extends AsyncTask<String, Void, ArrayList<Review>>  {

    private Context context;
    private ProgressDialog progressDialog;
    private RetrieveReviewsTaskInterface retrieveReviewsTaskInterface;

    public RetrieveReviewsTask(Context context, RetrieveReviewsTaskInterface retrieveReviewsTaskInterface) {
        this.context = context;
        this.retrieveReviewsTaskInterface = retrieveReviewsTaskInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        this.progressDialog = new ProgressDialog(this.context);
        this.progressDialog.setMessage("Retrieving Reviews...");
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }

    @Override
    protected ArrayList<Review> doInBackground(String... params) {
        return ReviewService.getReviewByBookId(params[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Review> reviews) {
        super.onPostExecute(reviews);

        this.progressDialog.dismiss();
        this.retrieveReviewsTaskInterface.onReceiveReviews(reviews);
    }
}
