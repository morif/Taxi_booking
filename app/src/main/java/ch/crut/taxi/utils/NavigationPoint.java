package ch.crut.taxi.utils;


import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class NavigationPoint implements Serializable {

    private static final long serialVersionUID = 100L;

    public String addressString;
    public LatLng latLng;
    public Address address;
}
