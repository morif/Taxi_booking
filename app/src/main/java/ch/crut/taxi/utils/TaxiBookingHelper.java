package ch.crut.taxi.utils;


import android.util.Log;

public class TaxiBookingHelper {

    public NavigationPoint original;
    public NavigationPoint destination;

    public TaxiBookingHelper() {
        original = new NavigationPoint();
        destination = new NavigationPoint();
    }

    public boolean isEmpty() {
//        if (original.isEmpty()) {
//            Log.e("", "original.isEmpty");
//        }
//
//        if (destination.isEmpty()) {
//            Log.e("", "destination.isEmpty");
//        }

        return original.isEmpty() || destination.isEmpty();
    }
}

