package br.edu.ifce.swappers.swappers.model;

import java.io.Serializable;

/**
 * Created by francisco on 16/06/15.
 */
public class Book implements Serializable{
    private String id;
    private String title;
    private String author;
    private String publisher;
    private String photo;
    private String datePublisher;
    private String description;
    private int numberPage;
    private float evaluationAvarage;
    private float userEvaluation;

    public Book() {
    }

    public Book(String title, String author, String publisher, float evaluationAvarage, float userEvaluation) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.evaluationAvarage = evaluationAvarage;
        this.userEvaluation = userEvaluation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public float getEvaluationAvarage() {
        return evaluationAvarage;
    }

    public void setEvaluationAvarage(float evaluationAvarage) {
        this.evaluationAvarage = evaluationAvarage;
    }

    public float getUserEvaluation() {
        return userEvaluation;
    }

    public void setUserEvaluation(float userEvaluation) {
        this.userEvaluation = userEvaluation;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDatePublisher() {
        return datePublisher;
    }

    public void setDatePublisher(String datePublisher) {
        this.datePublisher = datePublisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberPage() {
        return numberPage;
    }

    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }
}
