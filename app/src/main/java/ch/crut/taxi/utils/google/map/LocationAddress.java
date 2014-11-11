package ch.crut.taxi.utils.google.map;


import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Message;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationAddress extends Thread {

    private final Context context;
    private double latitude;
    private double longitude;

    private OnCompleteListener onCompleteListener;

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    private ProgressDialog progressDialog;

    public LocationAddress(Context context, double latitude, double longitude) {
        this.context = context;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationAddress(Context context, LatLng latLng) {
        this(context, latLng.latitude, latLng.longitude);
    }


    @Override
    public void run() {
        super.run();
        try {
            List<Address> addresses = getAddress(context, latitude, longitude);

            if (onCompleteListener != null) {
                Message msg = new Message();
                msg.obj = addresses;
                msg.what = Actions.DONE;

                handler.sendMessage(msg);
            } else {
                throw new IllegalStateException("LocationHelper.GetAddress must have OnCompleteListener to retrieve result");
            }
        } catch (IOException e) {
            e.printStackTrace();

            if (onCompleteListener != null) {
                handler.sendEmptyMessage(Actions.ERROR);
            } else {
                throw new IllegalStateException("LocationHelper.GetAddress must have OnCompleteListener to retrieve result");
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Actions.DONE:
                    @SuppressWarnings("unchecked")
                    List<Address> addresses = (List<Address>) msg.obj;
                    onCompleteListener.complete(addresses);
                    break;
                case Actions.ERROR:
                    onCompleteListener.error();
                    break;
            }

            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
    };

    public static interface OnCompleteListener {
        public void complete(final List<Address> addresses);

        public void error();
    }

    private static interface Actions {
        public static final int DONE = 0;
        public static final int ERROR = 1;
    }

    public static List<Address> getAddress(Context context, double latitude, double longitude) throws IOException {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        addresses = geocoder.getFromLocation(latitude, longitude, 1);

//            String countryCode = addresses.get(0).getCountryCode();
//            String address = addresses.get(0).getAddressLine(0);
//            String city = addresses.get(0).getAddressLine(1);
//            String country = addresses.get(0).getAddressLine(2);

        return addresses;
    }
}
