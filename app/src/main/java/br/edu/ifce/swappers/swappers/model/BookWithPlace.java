package br.edu.ifce.swappers.swappers.model;

/**
 * Created by Joamila on 03/12/2015.
 */
public class BookWithPlace{

    public BookWithPlace(Book book, int idPlace){
        this.book = book;
        this.idPlace = idPlace;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    private Book book;
    private int idPlace;

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

}
