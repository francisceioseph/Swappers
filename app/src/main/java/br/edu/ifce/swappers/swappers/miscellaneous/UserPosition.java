package br.edu.ifce.swappers.swappers.miscellaneous;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by francisco on 20/08/15.
 */
public class UserPosition {
    private double latitude;
    private double longitude;

    private UserPosition() {
        this.latitude  = 0.0;
        this.longitude = 0.0;
    }

    private UserPosition(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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

    public static UserPosition getInstance(LocationManager locationManager){
        UserPosition userPosition;

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            userPosition = new UserPosition(location.getLatitude(), location.getLongitude());
        }
        else {
            userPosition = new UserPosition();
        }

        return userPosition;
    }
}
