package ch.crut.taxi.utils.google.map;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;
import java.util.Map;

import ch.crut.taxi.R;


public class AdapterInfoWindow implements GoogleMap.InfoWindowAdapter {

    private final Context context;
    private final Map<Marker, Integer> availableMarkersType;

    public AdapterInfoWindow(Context context, Map<Marker, Integer> availableMarkersType) {
        this.context = context;
        this.availableMarkersType = availableMarkersType;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        final int markerType = availableMarkersType.containsKey(marker) ?
                availableMarkersType.get(marker) : MarkerType.DEFAULT;

        if (markerType == MarkerType.AUTO) {

            ImageView imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.icon_auto);
            return imageView;
        } else if (markerType == MarkerType.ME) {

            ImageView imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.icon_me);
            return imageView;
        } else if (markerType == MarkerType.DEFAULT) {

            return null;
        }
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
