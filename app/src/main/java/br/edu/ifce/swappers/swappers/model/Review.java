package br.edu.ifce.swappers.swappers.model;

/**
 * Created by francisco on 14/10/15.
 */

public class Review {
    private int idUser;
    private Book book;
    private String review;

    public Review() {
    }

    public Review(int idUser, Book book, String review) {
        this.idUser = idUser;
        this.book = book;
        this.review = review;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
