package br.edu.ifce.swappers.swappers.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import br.edu.ifce.swappers.swappers.MockSingleton;
import br.edu.ifce.swappers.swappers.dao.BookDAO;
import br.edu.ifce.swappers.swappers.model.User;
import br.edu.ifce.swappers.swappers.webservice.UserService;

/**
 * Created by francisco on 21/08/15.
 */

public class UserTask extends AsyncTask<String,Void,User> {
    Context context;
    ProgressDialog progressDialog;
    TaskInterface taskInterface;

    public UserTask(Context context, TaskInterface taskInterface) {
        this.context = context;
        this.taskInterface = taskInterface;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Logando...");
        progressDialog.show();
    }

    @Override
    protected User doInBackground(String... params) {
        Log.i("INFO-LOGIN", params[0] + params[1]);
        return UserService.getUserFromWS(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(User user) {
        Log.i("INFO-LOGIN","entrou");
        progressDialog.dismiss();
        progressDialog.cancel();

        if (user != null) {
            MockSingleton.INSTANCE.user = user;
            saveBook(user);
            taskInterface.startNextActivity();
        }
        else {
            SwappersToast.makeText(context, "Login ou senha incorretos!!!", Toast.LENGTH_SHORT).show();
        }

    }

    private void saveBook(User user){
        BookDAO bookDAO = new BookDAO(context);
        if(!user.getBookDonationList().isEmpty()) bookDAO.insertMultiple(user.getBookDonationList(),CategoryBook.DONATION);
        if(!user.getBookRetrievedList().isEmpty()) bookDAO.insertMultiple(user.getBookRetrievedList(),CategoryBook.RETRIEVED);
        if(!user.getBookFavoriteList().isEmpty()) bookDAO.insertMultiple(user.getBookFavoriteList(),CategoryBook.FAVORITE);
    }
}
