package ch.crut.taxi.utils.google.map;


import android.content.Context;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.crut.taxi.utils.request.Entities;

public class GoogleMapUtils {

    private final GoogleMap googleMap;
    private final Context context;

    private Map<Marker, Integer> availableMarkersType;

    public GoogleMapUtils(Context context, GoogleMap googleMap) {
        if (googleMap == null) {
            throw new RuntimeException("GoogleMapUtils googleMap is NuLL");
        }
        this.googleMap = googleMap;
        this.context = context;
        this.availableMarkersType = new HashMap<>();

        this.googleMap.setInfoWindowAdapter(new AdapterInfoWindow(context, availableMarkersType));
    }

    public void moveCamera(LatLng latLng) {
        int duration = 3000;
        moveCamera(latLng, duration);
    }

    public void moveCamera(LatLng latLng, int duration) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13), duration, null);
    }

    public void addMarker(LatLng latLng) {
        addMarker(latLng, MarkerType.DEFAULT);
    }

    public void addMarker(LatLng latLng, int markerType) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);

        Marker marker = googleMap.addMarker(markerOptions);

        availableMarkersType.put(marker, markerType);
    }

    public void me(LatLng myLocation) {
        addMarker(myLocation, MarkerType.ME);
    }

    public GoogleMap getMap() {
        return googleMap;
    }

    public void me(double latitude, double longitude) {
        me(new LatLng(latitude, longitude));
    }

    public void moveCamera(double latitude, double longitude) {
        moveCamera(new LatLng(latitude, longitude));
    }

    public void addDriver(List<Entities.SearchTaxi> listDrivers) {
        if (listDrivers != null) {

            for (Entities.SearchTaxi taxi : listDrivers) {
                addDriver(taxi);
            }
        }
    }

    public void addDriver(Entities.SearchTaxi driver) {
        addMarker(driver.getLatLng(), MarkerType.AUTO);
    }
}
