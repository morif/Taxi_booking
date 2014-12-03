package studiovision.disconto.util;


import android.content.Context;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.crut.taxi.R;
import ch.crut.taxi.utils.request.Entities;

public class GoogleMapUtils implements GoogleMap.OnInfoWindowClickListener {

    private final GoogleMap googleMap;
    private final Context context;

    private final Map<Marker, Integer> availableMarkersType;
    private final Map<Marker, Entities.SearchTaxi> drivers;
    private final AdapterInfoWindow adapterInfoWindow;

    public GoogleMapUtils(Context context, GoogleMap googleMap) {
        if (googleMap == null) {
            throw new RuntimeException("GoogleMapUtils googleMap is NuLL");
        }
        this.googleMap = googleMap;
        this.context = context;
        this.availableMarkersType = new HashMap<>();
        this.drivers = new HashMap<>();

        adapterInfoWindow = new AdapterInfoWindow(context,
                availableMarkersType, drivers);


        this.googleMap.setInfoWindowAdapter(adapterInfoWindow);
        this.googleMap.setOnInfoWindowClickListener(this);


        this.googleMap.getUiSettings().setZoomControlsEnabled(false);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(false);
    }

    public void moveCamera(LatLng latLng) {
        int duration = 3000;
        moveCamera(latLng, duration);
    }

    public void moveCamera(LatLng latLng, int duration) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13), duration, null);
    }

    public void addOriginMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_route_origin));
        markerOptions.position(latLng);

        addMarker(markerOptions);
    }

    public void addDestinationMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_route_destination));
        markerOptions.position(latLng);

        addMarker(markerOptions);
    }

    public void addMarker(MarkerOptions markerOptions) {
        Marker marker = googleMap.addMarker(markerOptions);
        availableMarkersType.put(marker, MarkerType.DEFAULT);
    }

    public void addMarker(LatLng latLng) {
        addMarker(latLng, MarkerType.DEFAULT);
    }

    public Marker addMarker(LatLng latLng, int markerType) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);

        Marker marker = googleMap.addMarker(markerOptions);

        availableMarkersType.put(marker, markerType);

        return marker;
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

        MarkerOptions markerOptions = new MarkerOptions();
        if (driver.freeDriver()) {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_taxi_free));
        } else {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_taxi_busy));
        }
        markerOptions.position(driver.getLatLng());

        Marker driverMarker = googleMap.addMarker(markerOptions);

        availableMarkersType.put(driverMarker, MarkerType.AUTO);
        drivers.put(driverMarker, driver);
    }

    public void clearDrivers() {
        drivers.clear();
    }

    public void setOnDriverInfoWindowClick(OnDriverInfoWindowClick onDriverInfoWindowClick) {
        this.onDriverInfoWindowClick = onDriverInfoWindowClick;
    }

    private OnDriverInfoWindowClick onDriverInfoWindowClick;

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (onDriverInfoWindowClick != null) {
            onDriverInfoWindowClick.infoWindowClick(drivers.get(marker));
        }
    }

    public static interface OnDriverInfoWindowClick {
        public void infoWindowClick(Entities.SearchTaxi driver);
    }

}
