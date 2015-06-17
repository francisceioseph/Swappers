package br.edu.ifce.swappers.swappers.model;

/**
 * Created by francisco on 16/06/15.
 */
public class Book {
    private String title;
    private String author;
    private String publisher;

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
}
