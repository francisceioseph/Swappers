package br.edu.ifce.swappers.swappers.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.miscellaneous.CategoryBook;

/**
 * Created by gracyane on 03/09/2015.
 */
public class BookDAO {

    private SQLiteDatabase db;

    public BookDAO(Context context){
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void insert(Book book , CategoryBook type){
        ContentValues values = new ContentValues();
        values.put("_id", book.getId());
        values.put("title", book.getTitle());
        values.put("author", book.getAuthor());
        values.put("book_photo",book.getPhoto());
        values.put("publishing",book.getPublisher());
        values.put("numPage",book.getNumberPage());
        values.put("synopsis",book.getSynopsis());
        values.put("evaluationAverage",book.getEvaluationAvarage());
        values.put("type", type.toString());

        db.insert("book", null, values);
    }

    public void delete(){
        db.delete("book", null, null);
    }

    public void insertMultiple(ArrayList<Book> books, CategoryBook type) {
        ContentValues values = new ContentValues();
        try {
            if (db != null) {
                db.beginTransaction();
                for (Book book: books) {
                    values.put("_id", book.getId());
                    values.put("title", book.getTitle());
                    values.put("author", book.getAuthor());
                    values.put("book_photo",book.getPhoto());
                    values.put("publishing",book.getPublisher());
                    values.put("numPage",book.getNumberPage());
                    values.put("synopsis",book.getSynopsis());
                    values.put("evaluationAverage",book.getEvaluationAvarage());
                    values.put("type", type.toString());

                    db.insertOrThrow("book", null, values);
                    values.clear();
                }
                db.setTransactionSuccessful();
            }
        }
        finally {
            if (db != null && db.inTransaction()) {
                db.endTransaction();
            }
        }
    }

    public ArrayList<Book> getBookAllByCategory(String category) {
        ArrayList<Book> bookList = new ArrayList<>();

        String sql = "select * from book where type=?;";
        String[] selectionArgs = new String[] {String.valueOf(category)};
        Cursor cursor = db.rawQuery(sql, selectionArgs);

        if(cursor.moveToFirst()){

            do {
                Book book = new Book();
                book.setId(cursor.getString(0));
                book.setTitle(cursor.getString(1));
                book.setAuthor(cursor.getString(2));
                book.setPublisher(cursor.getString(3));
                book.setPhoto(cursor.getString(4));
                book.setNumberPage(cursor.getInt(5));
                book.setSynopsis(cursor.getString(6));
                book.setEvaluationAvarage(cursor.getFloat(7));

                bookList.add(book);
            }while (cursor.moveToNext());
        }
        return bookList;
    }

    public void removeBookFromCategory(String bookId, String category) {
        String tableName = "book";
        String whereClause = "_id=? and type=?";
        String[] whereArgs = new String[] {bookId, category};

        db.delete(tableName, whereClause, whereArgs);
    }

    public boolean isBookFavourited(String bookId){
        String sql = "select * from book where _id=? and type=?";
        String[] selectionArgs = new String[] {bookId, CategoryBook.FAVORITE.toString()};
        Cursor cursor = db.rawQuery(sql, selectionArgs);

        boolean isFavourited = false;

        if (cursor.moveToFirst()) {
            isFavourited = true;
        }

        return isFavourited;
    }
}
