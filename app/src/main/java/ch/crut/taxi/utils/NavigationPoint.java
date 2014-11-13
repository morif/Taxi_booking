package ch.crut.taxi.utils;


import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class NavigationPoint implements Serializable {

    private static final long serialVersionUID = 100L;

    public String addressString = "";
    public LatLng latLng;
    public Address address;

    private String home = "";

    public void setHome() {
        String[] split = addressString.split(",");
        if (split.length > 0) {
            home = addressString.split(",")[1].trim();
        }
    }

    public String getHome() {
        return home;
    }
}
