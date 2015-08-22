package br.edu.ifce.swappers.swappers.model;

/**
 * Created by Bolsista on 18/07/2015.
 */
public class Place implements Comparable<Place>{

    private int idPlace;
    private String namePlace;
    private String adressPlace;
    private double latitude;
    private double longitude;
    private double distance;

    public Place(){

    }

    public Place(int idPlace, String namePlace, String adressPlace, double latitude, double longitude, double distance) {
        this.idPlace = idPlace;
        this.namePlace = namePlace;
        this.adressPlace = adressPlace;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    public Place(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.namePlace = "Place Next ";
    }

    public Place(double latitude, double longitude, double distance){
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setDistance(distance);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAdressPlace() {
        return adressPlace;
    }

    public void setAdressPlace(String adressPlace) {
        this.adressPlace = adressPlace;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    @Override
    public int compareTo(Place place){
        if (this.getDistance() < place.getDistance()) {
            return -1;
        }
        if (this.getDistance() > place.getDistance()) {
            return 1;
        }
        return 0;
    }
}
