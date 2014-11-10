package ch.crut.taxi.utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LocationHelper {

    public static Location getLocation(Context context) {
        Location gpsLocation = getLocationByProvider(context, LocationManager.GPS_PROVIDER);
        Location networkLocation = getLocationByProvider(context, LocationManager.NETWORK_PROVIDER);

        // if we have only one location available, the choice is easy
        if (gpsLocation == null) {
            return networkLocation;
        }
        if (networkLocation == null) {
            return gpsLocation;
        }
        return null;
    }

    public static Location getLocationByProvider(Context context, String provider) {
        Location location = null;
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(provider)) {
            location = locationManager.getLastKnownLocation(provider);
        }
        return location;
    }




}
