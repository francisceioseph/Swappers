package br.edu.ifce.swappers.swappers.miscellaneous.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import br.edu.ifce.swappers.swappers.R;
import br.edu.ifce.swappers.swappers.miscellaneous.SwappersToast;
import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.miscellaneous.utils.AndroidUtils;
import br.edu.ifce.swappers.swappers.miscellaneous.interfaces.BookInterface;
import br.edu.ifce.swappers.swappers.webservice.RetrievedService;

/**
 * Created by Bolsista on 04/09/2015.
 */
public class RetrievedTask extends AsyncTask<User,Void,Integer> {

    private Context context;
    private BookInterface bookInterface;

    public RetrievedTask(Context context, BookInterface bookInterface) {
        this.context = context;
        this.bookInterface = bookInterface;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Integer doInBackground(User... params) {
        return RetrievedService.registerRecoveredWS(params[0]);
    }

    @Override
    protected void onPostExecute(Integer statusCode) {

        if(statusCode == 200){
            bookInterface.saveRetrievedBookIntoLocalBase();
            SwappersToast.makeText(this.context, context.getString(R.string.adoption_done_successfully_message), Toast.LENGTH_LONG).show();
        }
        else {
            SwappersToast.makeText(this.context, context.getString(R.string.adoption_error_message), Toast.LENGTH_LONG).show();
        }
    }
}
