package br.edu.ifce.swappers.swappers.webservice;

import java.util.ArrayList;

import br.edu.ifce.swappers.swappers.model.Place;

/**
 * Created by alessandra on 29/08/15.
 */
public class PlaceSingleton {
    private static PlaceSingleton instance;
    private  ArrayList<Place> places;

    public PlaceSingleton() {
        places = new ArrayList<>();

    }
     public static PlaceSingleton getInstance(){
         if(instance == null){
             instance = new PlaceSingleton();
         }
         return instance;
     }


    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public static void setInstance(PlaceSingleton instance) {
        PlaceSingleton.instance = instance;
    }
}
