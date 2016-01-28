package br.edu.ifce.swappers.swappers.model;

import java.util.Date;

/**
 * Created by francisco on 27/01/16.
 */
public class Notification {
    private String userName;
    private String placeName;
    private String photoUser;
    private String bookTitle;
    private String city;
    private String region;
    private String eventType;
    private Date timestamp;

    public Notification() {
    }

    public Notification(String userName, String placeName, String bookTitle, String city, String region, String eventType, Date timestamp) {
        this.userName = userName;
        this.placeName = placeName;
        this.bookTitle = bookTitle;
        this.city = city;
        this.region = region;
        this.eventType = eventType;
        this.timestamp = timestamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPhotoUser() {
        return photoUser;
    }

    public void setPhotoUser(String photoUser) {
        this.photoUser = photoUser;
    }
}
