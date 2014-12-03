package ch.crut.taxi.utils.google.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;
import java.util.Map;

import ch.crut.taxi.R;
import ch.crut.taxi.querymaster.QueryMaster;
import ch.crut.taxi.utils.request.Entities;


public class AdapterInfoWindow implements GoogleMap.InfoWindowAdapter {

    private final Context context;
    private final Map<Marker, Integer> availableMarkersType;
    private Map<Marker, Entities.SearchTaxi> drivers;

    public AdapterInfoWindow(Context context, Map<Marker, Integer> availableMarkersType,
                             Map<Marker, Entities.SearchTaxi> drivers) {
        this.context = context;
        this.availableMarkersType = availableMarkersType;
        this.drivers = drivers;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        final int markerType = availableMarkersType.containsKey(marker) ?
                availableMarkersType.get(marker) : MarkerType.DEFAULT;

        if (markerType == MarkerType.AUTO) {

            if (drivers.containsKey(marker)) {


                final Entities.SearchTaxi driver = drivers.get(marker);

                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View infoView = inflater.inflate(R.layout.info_window_driver, null);

                ((TextView) infoView.findViewById(R.id.infoWindowDriverMarkAuto))
                        .setText(driver.modelCar);

                ((TextView) infoView.findViewById(R.id.infoWindowDriverDistance))
                        .setText(driver.distance);

                return infoView;
            }

            return null;
        }

        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

}
