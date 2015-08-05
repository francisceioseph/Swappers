package br.edu.ifce.swappers.swappers.model;

/**
 * Created by Bolsista on 18/07/2015.
 */
public class Place implements Comparable<Place>{
    private double latitude;
    private double longitude;
    private double distance;

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
