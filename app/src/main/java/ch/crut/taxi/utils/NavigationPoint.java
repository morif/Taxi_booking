package ch.crut.taxi.utils;


import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class NavigationPoint implements Serializable {

    private static final long serialVersionUID = 100L;

    private String addressString = "";
    private LatLng latLng;
    private Address address;
    private String home = "";

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setHome() {
        String[] split = addressString.split(",");
        if (split.length > 0) {
            String expectedHome = addressString.split(",")[1].trim();
            if (expectedHome.length() < 5) {
                this.home = addressString.split(",")[1].trim();
            }
        }
    }

    public String getHome() {
        return home;
    }

}
