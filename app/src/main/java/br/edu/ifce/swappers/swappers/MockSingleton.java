package br.edu.ifce.swappers.swappers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.swappers.swappers.model.Book;
import br.edu.ifce.swappers.swappers.model.Comment;
import br.edu.ifce.swappers.swappers.model.Place;
import br.edu.ifce.swappers.swappers.model.User;

/**
 * A singleton to concetrate all mocked methods
 */
public enum MockSingleton {
    INSTANCE;

    public User user;
    public ArrayList<Place> places = new ArrayList<Place>();
    public ArrayList<User> donators = new ArrayList<>();
    public ArrayList<Place> statisticPlace = new ArrayList<>();
    public ArrayList<Book> statisticBook = new ArrayList<>();

    public String userChangeCity = null;
    public String userChangeState = null;
    //public String city = new String();
    //public String state = new String();


   /* public ArrayList<Place> createMockedPlaceDataSource(double latitude, double longitude) {
        ArrayList<Place> dataSource = new ArrayList<>();


        for (int i = 0; i < 5; i++){
            Place place = new Place(latitude,longitude);
            place.setNamePlace("Shopping Benfica");
            place.setAdressPlace("Av. Carapinima, 2081, Benfica");
            dataSource.add(place);

        }
        return dataSource;
    }*/

    public ArrayList<Place> createPlace(ArrayList<Place> place) {

        //ArrayList<Place> dataSource = new ArrayList<>();
        for (int i = 0; i < place.size(); i++){

            this.places.add(place.get(i));
            Log.i("PLACESNEAR", this.places.get(i).getName());

        }
        return this.places;

    }

    public ArrayList<Place> getPlaces() {
        return this.places;
    }

    public void setPlace(ArrayList<Place> places) {
        this.places = places;
    }

    public ArrayList<Book> createMockedBookDataSource() {
        ArrayList<Book> dataSource = new ArrayList<>();

        for (int i = 0; i < 5; i++){
            dataSource.add(new Book("A Book", "An Author", "A Publisher", 2.1f, 4.2f));

        }
        return dataSource;
    }

    public ArrayList<Comment> createMockedReadersCommentsSource(){
        ArrayList<Comment> dataSource = new ArrayList<>();

        for (int i = 0; i < 1; i++){
            dataSource.add(new Comment("Jon", "Posted at Apr. 24, 2015", "Ygritte said that I know nothing."));
        }
        return dataSource;
    }


    public ArrayList<User> getDonators() {
        return donators;
    }

    public void setDonators(ArrayList<User> donators) {
        this.donators = donators;
    }

    public ArrayList<Place> getStatisticPlace() {
        return statisticPlace;
    }

    public void setStatisticPlace(ArrayList<Place> statisticPlace) {
        this.statisticPlace = statisticPlace;
    }

    public ArrayList<Book> getStatisticBook() {
        return statisticBook;
    }

    public void setStatisticBook(ArrayList<Book> statisticBook) {
        this.statisticBook = statisticBook;
    }

}
