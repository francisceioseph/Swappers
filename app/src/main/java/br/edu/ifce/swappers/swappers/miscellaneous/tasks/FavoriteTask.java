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
import br.edu.ifce.swappers.swappers.webservice.FavoriteService;

/**
 * Created by Bolsista on 05/09/2015.
 */
public class FavoriteTask extends AsyncTask<User,Void,Integer> {

    private Context context;
    private BookInterface bookInterface;
    private ProgressDialog progressDialog;

    public FavoriteTask(Context context,BookInterface bookInterface) {
        this.context = context;
        this.bookInterface = bookInterface;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.favourite_task_progress_dialog_message));
        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(User... params) {
        return FavoriteService.registerFavoriteWS(params[0]);
    }

    @Override
    protected void onPostExecute(Integer statusCode) {
        progressDialog.dismiss();

        if(statusCode == 200){
            bookInterface.saveFavouriteBookIntoLocalBase();
        }
        else {
            SwappersToast.makeText(this.context, context.getString(R.string.favouriting_error_message), Toast.LENGTH_LONG).show();
        }
    }
}
