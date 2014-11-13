package ch.crut.taxi.fragments;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;


public class FragmentGoogleMap extends SupportMapFragment {

    public static FragmentGoogleMap newInstance() {
        return new FragmentGoogleMap();
    }

    public void setOnMapInitialized(OnMapInitialized onMapInitialized) {
        this.onMapInitialized = onMapInitialized;
    }

    private OnMapInitialized onMapInitialized;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (onMapInitialized != null) {
            onMapInitialized.mapWasInitialized(getMap());
        }
    }

    public static interface OnMapInitialized {
        public void mapWasInitialized(GoogleMap googleMap);
    }
}
