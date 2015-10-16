package br.edu.ifce.swappers.swappers.miscellaneous.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.miscellaneous.SwappersToast;
import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.AndroidUtils;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.BookInterface;
import br.edu.ifce.swappers.swappers.webservice.DonationService;

/**
 * Created by alessandra on 29/08/15.
 */
public class DonationTask extends AsyncTask<User,Void,Integer> {
    private Context context;
    private BookInterface bookInterface;

    public DonationTask(Context context, BookInterface bookInterface) {
        this.context = context;
        this.bookInterface = bookInterface;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Integer doInBackground(User... params) {
        return DonationService.registerDonationWS(params[0]);
    }

    @Override
    protected void onPostExecute(Integer statusCode) {

        if(statusCode == 200){
            bookInterface.saveDonatedBookIntoLocalBase();
            SwappersToast.makeText(this.context, context.getString(R.string.donation_processed_successfully_message), Toast.LENGTH_LONG).show();
        }
        else {
            SwappersToast.makeText(this.context, context.getString(R.string.error_while_processing_donation_message), Toast.LENGTH_LONG).show();
        }
    }
}
