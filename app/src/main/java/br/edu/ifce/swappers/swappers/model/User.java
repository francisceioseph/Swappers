package br.edu.ifce.swappers.swappers.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by FAMÍLIA on 30/05/2015.
 */
public class User implements Serializable{

    private Integer id;
    private String name;
    private String email;
    private String password;
    private String city;
    private String state;
    private Integer donationNum;
    private byte[] photo;
    private String photo2;
    private Book book;
    private ArrayList<Book> bookWithdraweList;
    private ArrayList<Book> bookDonationList;
    private ArrayList<Book> bookFavoriteList;


    public User() {

    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    private byte[] cover;
    private Date bithday;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public Date getBithday() {
        return bithday;
    }

    public void setBithday(Date bithday) {
        this.bithday = bithday;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Integer getDonationNum() {
        return donationNum;
    }

    public void setDonationNum(Integer donationNum) {
        this.donationNum = donationNum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public ArrayList<Book> getBookWithdraweList() {
        return bookWithdraweList;
    }

    public void setBookWithdraweList(ArrayList<Book> bookWithdraweList) {
        this.bookWithdraweList = bookWithdraweList;
    }

    public ArrayList<Book> getBookDonationList() {
        return bookDonationList;
    }

    public void setBookDonationList(ArrayList<Book> bookDonationList) {
        this.bookDonationList = bookDonationList;
    }

    public ArrayList<Book> getBookFavoriteList() {
        return bookFavoriteList;
    }

    public void setBookFavoriteList(ArrayList<Book> bookFavoriteList) {
        this.bookFavoriteList = bookFavoriteList;
    }
}
