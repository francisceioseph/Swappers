package br.edu.ifce.swappers.swappers.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.net.HttpURLConnection;

import br.edu.ifce.swappers.swappers.model.Review;
import br.edu.ifce.swappers.swappers.webservice.ReviewService;

/**
 * Created by francisco on 14/10/15.
 */
public class UploadReviewTask extends AsyncTask<Review,Void, Integer> {
    private Context context;
    private ProgressDialog progressDialog;

    public UploadReviewTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        this.progressDialog = new ProgressDialog(this.context);
        this.progressDialog.setMessage("Uploading Reviews...");
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }

    @Override
    protected Integer doInBackground(Review... params) {
        return ReviewService.uploadReview(params[0]);
    }

    @Override
    protected void onPostExecute(Integer status_code) {
        super.onPostExecute(status_code);
        this.progressDialog.dismiss();

        if (status_code == HttpURLConnection.HTTP_CREATED){
            SwappersToast.makeText(context, "Comentário enviado com sucesso...", Toast.LENGTH_LONG).show();
        }
        else{
            Log.i("ReviewTask", String.format("%d", status_code));
            SwappersToast.makeText(context, "Ocorreu um erro... Tente outra vez.", Toast.LENGTH_LONG).show();
        }
    }
}
