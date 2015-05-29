package br.edu.ifce.swappers.swappers.webservice;

/**
 * Created by francisco on 29/05/15.
 */
public enum UserService {
    INSTANCE;

    int x = 32;

    private int getX(){
        return this.x;
    }
}
