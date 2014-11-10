package ch.crut.taxi.utils;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapUtils {

    private GoogleMap googleMap;

    public GoogleMapUtils(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public void moveCamera(LatLng latLng) {
        int duration = 3000;
        moveCamera(latLng, duration);
    }

    public void moveCamera(LatLng latLng, int duration) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13), duration, null);
    }


    public void me(LatLng myLocation) {
        MarkerOptions options = new MarkerOptions();
        options.title("ME");
        options.position(myLocation);

        googleMap.addMarker(options);
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
}
