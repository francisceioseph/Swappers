package br.edu.ifce.swappers.swappers.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gracyane on 03/09/2015.
 * Last modified by Joamila on 07/12/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper mInstance = null;

    private static final String NOME_DB = "swappers";
    private static final int VERSAO_DB = 1;

    public DBHelper(Context context) {
        super(context, NOME_DB, null,VERSAO_DB);
    }

    public static DBHelper getInstance (Context context){
        if (mInstance == null){
            mInstance = new DBHelper(context.getApplicationContext());
        }

        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table book(_id text, title text not null, author text, publishing text, book_photo text, numPage integer, synopsis text, evaluationAverage double, type text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table book");
        onCreate(db);
    }
}
